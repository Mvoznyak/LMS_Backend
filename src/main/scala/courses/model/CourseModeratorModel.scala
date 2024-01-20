package courses.model

import java.util.UUID

object CourseModeratorModel {

  import slick.jdbc.PostgresProfile.api._

  class CourseModeratorTable(tag: Tag) extends Table[CourseModerator](tag, "course_moderator") {

    def courseId = column[UUID]("course_id")
    def moderatorId = column[UUID]("moderator_id")

    def * = (
      courseId,
      moderatorId
      ).mapTo[CourseModerator]

  }

  val courseModerators = TableQuery[CourseModeratorTable]

}
