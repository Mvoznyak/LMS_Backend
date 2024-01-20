package users.service
import java.util.UUID

import slick.jdbc.PostgresProfile.api._
import users.model.Student
import users.model.StudentModel._

import scala.concurrent.Future

class StudentsDBService(db: Database) {

  def find(id: UUID): Future[Option[Student]] = {
    val query = students.filter(_.id === id.bind)
      .result
      .headOption
    db.run(query)
  }

  def getGroupmates(groupId: UUID): Future[Seq[Student]] = {
    val query = students.filter(_.groupId === groupId.bind)
      .result
    db.run(query)
  }

}
