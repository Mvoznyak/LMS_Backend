package courses.model

import java.util.UUID

object GroupCourseModel {

  import slick.jdbc.PostgresProfile.api._

  class GroupCourseTable(tag: Tag) extends Table[GroupCourse](tag, "group_course") {

    def courseId = column[UUID]("course_id")
    def groupId = column[UUID]("group_id")

    def * = (
      courseId,
      groupId
      ).mapTo[GroupCourse]

  }

  val courseGroups = TableQuery[GroupCourseTable]

}
