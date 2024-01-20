package courses.model

import java.time.Instant
import java.util.UUID

object HomeworkModel {

  import slick.jdbc.PostgresProfile.api._

  class HomeworkTable(tag: Tag) extends Table[Homework](tag, "homework") {

    def id = column[UUID]("id", O.PrimaryKey)
    def courseId = column[UUID]("course_id")
    def name = column[String]("name")
    def description = column[String]("description")
    def startTime = column[Instant]("start_time")
    def endTime = column[Instant]("end_time")

    def * = (
      id,
      courseId,
      name,
      description,
      startTime,
      endTime
      )<> ((Homework.apply _).tupled, Homework.unapply)

  }

  val homeworks = TableQuery[HomeworkTable]

}
