package courses.controller

import java.util.UUID

import akka.http.scaladsl.server.{Directive0, Route}
import common.directives.{CommonDirectives, LmsAuthenticator}
import courses.model.CourseProtocol.{AddHomeworkRequest, AddMaterialRequest, SubmitHomeworkRequest}
import courses.model.CoursesFormat
import courses.service.{CourseModeratorsDBService, CoursesApiControllerService, CoursesDBService}
import users.model.UserAccount

import scala.concurrent.ExecutionContext

class CoursesApiController(authenticator: LmsAuthenticator,
                           coursesApiControllerService: CoursesApiControllerService,
                           coursesDBService: CoursesDBService,
                           moderatorsDBService: CourseModeratorsDBService)
                          (implicit executionContext: ExecutionContext) extends CommonDirectives
  with CoursesFormat {

  val route: Route = {
    path("courses") { getCourses } ~
    pathPrefix("course" / JavaUUID) { courseId =>
      pathEndOrSingleSlash { getCourse(courseId) } ~
      path("moderator" / JavaUUID) { moderatorId =>
        addModerator(courseId, moderatorId) ~
        deleteModerator(courseId, moderatorId)
      } ~
      pathPrefix("material") {
        pathEndOrSingleSlash { addMaterial(courseId) } ~
        path(JavaUUID) { materialId =>
          updateMaterial(courseId, materialId) ~
          deleteMaterial(courseId, materialId)
        }
      }
      pathPrefix("homework") {
        pathEndOrSingleSlash { addHomework(courseId) } ~
          pathPrefix(JavaUUID) { homeworkId =>
            pathEndOrSingleSlash {
              updateHomework(courseId, homeworkId) ~
                deleteHomework(courseId, homeworkId)
            }
            path("submit") { submitHomework(courseId, homeworkId) }
          }
      }
    }
  }

  def getCourses(): Route = {
    get {
      authenticator.authenticate { user =>
        withErrorHandling {
          coursesApiControllerService.listCourses(user)
        }
      }
    }
  }

  def getCourse(courseId: UUID): Route = {
    get {
      authenticator.authenticate { user =>
        withErrorHandling {
          coursesApiControllerService.findCourse(courseId, user)
        }
      }
    }
  }

  def addMaterial(courseId: UUID): Route = {
    post {
      authenticator.authenticate { user =>
        (authorizedAsTeacher(user, courseId) | authorizedAsModerator(user, courseId)) {
          entity(as[AddMaterialRequest]) { request =>
            withErrorHandling {
              coursesApiControllerService.addCourseMaterial(courseId, request)
            }
          }
        }
      }
    }
  }

  def updateMaterial(courseId: UUID, materialId: UUID): Route = {
    put {
      authenticator.authenticate { user =>
        (authorizedAsTeacher(user, courseId) | authorizedAsModerator(user, courseId)) {
          entity(as[AddMaterialRequest]) { request =>
            withErrorHandling {
              coursesApiControllerService.updateCourseMaterial(courseId, materialId, request)
            }
          }
        }
      }
    }
  }

  def deleteMaterial(courseId: UUID, materialId: UUID): Route = {
    delete {
      authenticator.authenticate { user =>
        (authorizedAsTeacher(user, courseId) | authorizedAsModerator(user, courseId)) {
          withErrorHandling {
            coursesApiControllerService.deleteCourseMaterial(courseId, materialId)
          }
        }
      }
    }
  }

  def addHomework(courseId: UUID): Route = {
    post {
      authenticator.authenticate { user =>
        authorizedAsTeacher(user, courseId) {
          entity(as[AddHomeworkRequest]) { request =>
            withErrorHandling {
              coursesApiControllerService.addCourseHomework(courseId, request)
            }
          }
        }
      }
    }
  }

  def updateHomework(courseId: UUID, homeworkId: UUID): Route = {
    put {
      authenticator.authenticate { user =>
        authorizedAsTeacher(user, courseId) {
          entity(as[AddHomeworkRequest]) { request =>
            withErrorHandling {
              coursesApiControllerService.updateCourseHomework(courseId, homeworkId, request)
            }
          }
        }
      }
    }
  }

  def deleteHomework(courseId: UUID, homeworkId: UUID): Route = {
    delete {
      authenticator.authenticate { user =>
        authorizedAsTeacher(user, courseId) {
          withErrorHandling {
            coursesApiControllerService.deleteCourseHomework(courseId, homeworkId)
          }
        }
      }
    }
  }

  def addModerator(courseId: UUID, moderatorId: UUID): Route = {
    post {
      authenticator.authenticate { user =>
        authorizedAsTeacher(user, courseId) {
          withErrorHandling {
            coursesApiControllerService.addCourseModerator(courseId, moderatorId)
          }
        }
      }
    }
  }

  def deleteModerator(courseId: UUID, moderatorId: UUID): Route = {
    delete {
      authenticator.authenticate { user =>
        authorizedAsTeacher(user, courseId) {
          withErrorHandling {
            coursesApiControllerService.deleteCourseModerator(courseId, moderatorId)
          }
        }
      }
    }
  }

  def submitHomework(courseId: UUID, homeworkId: UUID): Route = {
    post {
      authenticator.authenticate { user =>
        authorizedAsStudent(user, courseId) {
          entity(as[SubmitHomeworkRequest]) { request =>
            withErrorHandling {
              coursesApiControllerService.submitHomework(user.id, homeworkId, request)
            }
          }
        }
      }
    }
  }

  private def authorizedAsTeacher(user: UserAccount, courseId: UUID): Directive0 = {
    onSuccess(coursesDBService.listTeachers(courseId)).flatMap { teachers =>
      if (teachers.map(_.teacherId).contains(user.id)) pass
      else reject
    }
  }

  private def authorizedAsModerator(user: UserAccount, courseId: UUID): Directive0 = {
    onSuccess(moderatorsDBService.listModerators(courseId)).flatMap { teachers =>
      if (teachers.map(_.moderatorId).contains(user.id)) pass
      else reject
    }
  }

  private def authorizedAsStudent(user: UserAccount, courseId: UUID): Directive0 = {
    onSuccess(coursesApiControllerService.isCourseListener(courseId, user.id)).flatMap { isListener =>
      if (isListener) pass
      else reject
    }
  }

}
