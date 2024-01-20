package courses.model

import java.time.Instant
import java.util.UUID

import courses.model.CourseProtocol.SubmitHomeworkRequest

case class HomeworkSubmission(studentId: UUID,
                              homeworkId: UUID,
                              submission: String,
                              sendTime: Instant)

object HomeworkSubmission {
  def from(studentId: UUID, homeworkId: UUID, request: SubmitHomeworkRequest): HomeworkSubmission = {
    HomeworkSubmission(
      studentId = studentId,
      homeworkId = homeworkId,
      submission = request.submission,
      sendTime = Instant.now()
    )
  }
}