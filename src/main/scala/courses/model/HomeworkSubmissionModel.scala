package courses.model

import java.time.Instant
import java.util.UUID

object HomeworkSubmissionModel {

  import slick.jdbc.PostgresProfile.api._

  class HomeworkSubmissionTable(tag: Tag) extends Table[HomeworkSubmission](tag, "homework_submission") {

    def homeworkId = column[UUID]("homework_id", O.PrimaryKey)
    def studentId = column[UUID]("student_id", O.PrimaryKey)
    def submission = column[String]("submission")
    def sendTime = column[Instant]("send_time")

    def * = (
      homeworkId,
      studentId,
      submission,
      sendTime
      ) <> ((HomeworkSubmission.apply _).tupled, HomeworkSubmission.unapply)

  }

  val homeworkSubmissions = TableQuery[HomeworkSubmissionTable]

}
