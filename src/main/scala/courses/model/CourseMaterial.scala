package courses.model

import java.time.Instant
import java.util.UUID

import courses.model.CourseProtocol.AddMaterialRequest

case class CourseMaterial(id: UUID,
                          courseId: UUID,
                          name: String,
                          material: String,
                          createdOn: Instant)

object CourseMaterial {

  def from(courseId: UUID, request: AddMaterialRequest): CourseMaterial = {
    CourseMaterial(
      id = UUID.randomUUID(),
      courseId = courseId,
      name = request.name,
      material = request.material,
      createdOn = Instant.now()
    )
  }

  def from(courseId: UUID, materialId: UUID, request: AddMaterialRequest): CourseMaterial = {
    CourseMaterial(
      id = materialId,
      courseId = courseId,
      name = request.name,
      material = request.material,
      createdOn = Instant.now()
    )
  }

}
