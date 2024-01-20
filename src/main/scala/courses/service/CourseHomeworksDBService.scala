package courses.service

import java.util.UUID

import courses.model.{Homework, HomeworkSubmission}
import courses.model.HomeworkModel._
import courses.model.HomeworkSubmissionModel._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class CourseHomeworksDBService(db: Database) {

  def listHomeworks(courseId: UUID): Future[Seq[Homework]] = {
    val query = homeworks.filter(_.courseId === courseId.bind).result
    db.run(query)
  }

  def findHomework(homeworkId: UUID): Future[Option[Homework]] = {
    val query = homeworks.filter(_.id === homeworkId.bind).result.headOption
    db.run(query)
  }

  def upsertHomework(homework: Homework): Future[Int] = {
    val query = homeworks.insertOrUpdate(homework)
    db.run(query)
  }

  def deleteHomework(courseId: UUID, homeworkId: UUID): Future[Int] = {
    val query = homeworks.filter(x => x.courseId === courseId.bind && x.id === homeworkId.bind).delete
    db.run(query)
  }

  def upsertHomeworkSubmission(homeworkSubmission: HomeworkSubmission): Future[Int] = {
    val query = homeworkSubmissions.insertOrUpdate(homeworkSubmission)
    db.run(query)
  }

}
