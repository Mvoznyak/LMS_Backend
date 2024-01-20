package courses.service

import java.util.UUID

import courses.model.CourseModerator
import courses.model.CourseModeratorModel._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class CourseModeratorsDBService(db: Database) {

  def listModerators(courseId: UUID): Future[Seq[CourseModerator]] = {
    val query = courseModerators.filter(_.courseId === courseId.bind).result
    db.run(query)
  }

  def addModerator(moderator: CourseModerator): Future[Int] = {
    val query = courseModerators += moderator
    db.run(query)
  }

  def deleteModerator(moderator: CourseModerator): Future[Int] = {
    val query = courseModerators
      .filter(x => x.courseId === moderator.courseId.bind && x.moderatorId === moderator.moderatorId.bind)
      .delete
    db.run(query)
  }

}
