package users.model

import java.util.UUID

object StudentModel {

  import slick.jdbc.PostgresProfile.api._

  class StudentTable(tag: Tag) extends Table[Student](tag, "student") {

    def id = column[UUID]("id", O.PrimaryKey)
    def groupId = column[UUID]("group_id")
    def admissionYear = column[Int]("admission_year")
    def degree = column[String]("degree")
    def form = column[String]("form")
    def basis = column[String]("basis")

    def * = (
      id,
      groupId,
      admissionYear,
      degree,
      form,
      basis
      ).mapTo[Student]

  }

  val students = TableQuery[StudentTable]

}
