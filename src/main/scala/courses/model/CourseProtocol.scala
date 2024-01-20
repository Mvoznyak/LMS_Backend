package courses.model

import java.time.Instant
import java.util.UUID

object CourseProtocol {

  case class CourseDescription(courseId: UUID,
                               name: String,
                               description: String,
                               teachers: Seq[UUID],
                               moderators: Seq[UUID],
                               materials: Seq[CourseMaterial],
                               homeworks: Seq[Homework])

  object CourseDescription {
    def from(course: Course,
             teachers: Seq[UUID],
             moderators: Seq[UUID],
             materials: Seq[CourseMaterial],
             homeworks: Seq[Homework]): CourseDescription = {
      CourseDescription(
        courseId = course.id,
        name = course.name,
        description = course.description,
        teachers = teachers,
        moderators = moderators,
        materials = materials,
        homeworks = homeworks
      )
    }
  }

  case class AddMaterialRequest(name: String,
                                material:String)

  case class AddHomeworkRequest(name: String,
                                description: String,
                                startTime: Instant,
                                endTime: Instant)

  case class SubmitHomeworkRequest(submission: String)

}
