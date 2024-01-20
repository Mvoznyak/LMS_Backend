package courses.service

import java.util.UUID

import courses.model.{Course, CourseTeacher, GroupCourse}
import courses.model.CourseModel._
import courses.model.CourseTeacherModel._
import courses.model.GroupCourseModel._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class CoursesDBService(db: Database) {

  def listCoursesByGroup(groupId: UUID): Future[Seq[Course]] = {
    val courseIdsQuery = courseGroups.filter(_.groupId === groupId.bind).map(_.courseId)
    val query = course.filter(_.id in courseIdsQuery).result
    db.run(query)
  }

  def listCoursesByTeacherId(teacherId: UUID): Future[Seq[Course]] = {
    val courseIdsQuery = courseTeachers.filter(_.teacherId === teacherId.bind).map(_.courseId)
    val query = course.filter(_.id in courseIdsQuery).result
    db.run(query)
  }

  def findCourse(courseId: UUID): Future[Option[Course]] = {
    val query = course.filter(_.id === courseId.bind).result.headOption
    db.run(query)
  }

  def findGroupCourse(courseId: UUID, groupId: UUID): Future[Option[GroupCourse]] = {
    val query = courseGroups.filter(x => x.courseId === courseId.bind && x.groupId === groupId.bind).result.headOption
    db.run(query)
  }

  def listTeachers(courseId: UUID): Future[Seq[CourseTeacher]] = {
    val query = courseTeachers.filter(_.courseId === courseId.bind).result
    db.run(query)
  }

}
