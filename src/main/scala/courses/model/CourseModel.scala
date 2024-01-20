package courses.model

import java.util.UUID

object CourseModel {

  import slick.jdbc.PostgresProfile.api._

  class CourseTable(tag: Tag) extends Table[Course](tag, "course") {

    def id = column[UUID]("id", O.PrimaryKey)
    def name = column[String]("name")
    def description = column[String]("description")


    def * = (
      id,
      name,
      description
      ).mapTo[Course]

  }

  val course = TableQuery[CourseTable]

}
