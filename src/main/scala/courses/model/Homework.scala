package courses.model

import java.time.Instant
import java.util.UUID

import courses.model.CourseProtocol.AddHomeworkRequest

case class Homework(id: UUID,
                    courseId: UUID,
                    name: String,
                    description: String,
                    startTime: Instant,
                    endTime: Instant)

object Homework {

  def from(courseId: UUID, request: AddHomeworkRequest): Homework = {
    Homework(
      id = UUID.randomUUID(),
      courseId = courseId,
      name = request.name,
      description = request.description,
      startTime = request.startTime,
      endTime = request.endTime
    )
  }

  def from(courseId: UUID, homeworkId: UUID, request: AddHomeworkRequest): Homework = {
    Homework(
      id = homeworkId,
      courseId = courseId,
      name = request.name,
      description = request.description,
      startTime = request.startTime,
      endTime = request.endTime
    )
  }

}
