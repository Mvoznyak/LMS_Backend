package courses.model

import java.time.Instant
import java.util.UUID

object CourseMaterialModel {

  import slick.jdbc.PostgresProfile.api._

  class CourseMaterialTable(tag: Tag) extends Table[CourseMaterial](tag, "course_material") {

    def id = column[UUID]("id", O.PrimaryKey)
    def courseId = column[UUID]("course_id")
    def name = column[String]("name")
    def material = column[String]("material")
    def createdOn = column[Instant]("created_on")

    def * = (
      id,
      courseId,
      name,
      material,
      createdOn
      ) <> ((CourseMaterial.apply _).tupled, CourseMaterial.unapply)

  }

  val courseMaterials = TableQuery[CourseMaterialTable]

}
