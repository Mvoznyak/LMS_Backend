package courses.service

import java.time.Instant
import java.util.UUID

import common.exceptions.{HomeworkNotAvailableException, NoListenerException, NoSuchCourseException}
import courses.model.{Course, CourseMaterial, CourseModerator, Homework, HomeworkSubmission}
import courses.model.CourseProtocol.{AddHomeworkRequest, AddMaterialRequest, CourseDescription, SubmitHomeworkRequest}
import users.model.UserAccount
import users.service.StudentsDBService

import scala.concurrent.{ExecutionContext, Future}

class CoursesApiControllerService(coursesBDService: CoursesDBService,
                                  studentsDBService: StudentsDBService,
                                  courseMaterialsDBService: CourseMaterialsDBService,
                                  courseHomeworksDBService: CourseHomeworksDBService,
                                  courseModeratorsDBService: CourseModeratorsDBService)
                                 (implicit executionContext: ExecutionContext) {

  def listCourses(user: UserAccount): Future[Seq[Course]] = {
    for {
      groupCourses <- listGroupCourses(user)
      teacherCourses <- coursesBDService.listCoursesByTeacherId(user.id)
    } yield groupCourses.concat(teacherCourses)
  }

  def findCourse(courseId: UUID, user: UserAccount): Future[CourseDescription] = {
    for {
      courseOpt <- coursesBDService.findCourse(courseId)
      course = courseOpt.getOrElse(throw new NoSuchCourseException)
      teachers <- coursesBDService.listTeachers(courseId)
      teachersId = teachers.map(_.teacherId)
      moderators <- courseModeratorsDBService.listModerators(courseId)
      moderatorsId = moderators.map(_.moderatorId)
      materials <- courseMaterialsDBService.listMaterials(courseId)
      homeworks <- courseHomeworksDBService.listHomeworks(courseId)
      filteredHomeworks = {
        if (teachers.contains(user.id)) homeworks
        else homeworks.filter(_.startTime isBefore Instant.now())
      }
    } yield CourseDescription.from(course, teachersId, moderatorsId, materials, filteredHomeworks)
  }

  def addCourseMaterial(courseId: UUID, request: AddMaterialRequest): Future[Unit] = {
    val material = CourseMaterial.from(courseId, request)
    courseMaterialsDBService.upsertMaterial(material).map(_ => ())
  }

  def updateCourseMaterial(courseId: UUID, materialId: UUID, request: AddMaterialRequest): Future[Unit] = {
    val material = CourseMaterial.from(courseId, materialId, request)
    courseMaterialsDBService.upsertMaterial(material).map(_ => ())
  }

  def deleteCourseMaterial(courseId: UUID, materialId: UUID): Future[Unit] = {
    courseMaterialsDBService.deleteMaterial(courseId, materialId).map(_ => ())
  }

  def addCourseHomework(courseId: UUID, request: AddHomeworkRequest): Future[Unit] = {
    val homework = Homework.from(courseId, request)
    courseHomeworksDBService.upsertHomework(homework).map(_ => ())
  }

  def updateCourseHomework(courseId: UUID, homeworkId: UUID, request: AddHomeworkRequest): Future[Unit] = {
    val homework = Homework.from(courseId, homeworkId, request)
    courseHomeworksDBService.upsertHomework(homework).map(_ => ())
  }

  def deleteCourseHomework(courseId: UUID, HomeworkId: UUID): Future[Unit] = {
    courseHomeworksDBService.deleteHomework(courseId, HomeworkId).map(_ => ())
  }

  def addCourseModerator(courseId: UUID, moderatorId: UUID): Future[Unit] = {
    for {
      isListener <- isCourseListener(courseId, moderatorId)
      _ = if (!isListener) throw new NoListenerException
      _ <- courseModeratorsDBService.addModerator(CourseModerator(courseId, moderatorId))
    } yield ()
  }

  def deleteCourseModerator(courseId: UUID, moderatorId: UUID): Future[Unit] = {
    for {
      isListener <- isCourseListener(courseId, moderatorId)
      _ = if (!isListener) throw new NoListenerException
      _ <- courseModeratorsDBService.deleteModerator(CourseModerator(courseId, moderatorId))
    } yield ()
  }

  def submitHomework(studentId: UUID, homeworkId: UUID, request: SubmitHomeworkRequest): Future[Unit] = {
    val homeworkSubmission = HomeworkSubmission.from(studentId, homeworkId, request)
    for {
      isAvailable <- isHomeworkAvailable(homeworkId, homeworkSubmission.sendTime)
      _ = if (!isAvailable) throw new HomeworkNotAvailableException
      _ <- courseHomeworksDBService.upsertHomeworkSubmission(homeworkSubmission)
    } yield ()
  }

  def isCourseListener(courseId: UUID, userId: UUID): Future[Boolean] = {
    studentsDBService.find(userId).flatMap {
      case None => Future.successful(false)
      case Some(student) => coursesBDService.findGroupCourse(courseId, student.groupId).map(_.isDefined)
    }
  }

  private def listGroupCourses(user: UserAccount): Future[Seq[Course]] = {
    studentsDBService.find(user.id).flatMap {
      case None => Future.successful(Seq.empty[Course])
      case Some(student) => coursesBDService.listCoursesByGroup(student.groupId)
    }
  }

  private def isHomeworkAvailable(homeworkId: UUID, time: Instant): Future[Boolean] = {
    courseHomeworksDBService.findHomework(homeworkId).map {
      case None => false
      case Some(homework) => (homework.startTime isBefore time) && (homework.endTime isAfter time)
    }
  }

}
