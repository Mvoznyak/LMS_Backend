package courses.service

import java.util.UUID

import courses.model.CourseMaterial
import courses.model.CourseMaterialModel._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class CourseMaterialsDBService(db: Database) {

  def listMaterials(courseId: UUID): Future[Seq[CourseMaterial]] = {
    val query = courseMaterials.filter(_.courseId === courseId.bind).result
    db.run(query)
  }

  def upsertMaterial(courseMaterial: CourseMaterial): Future[Int] = {
    val query = courseMaterials.insertOrUpdate(courseMaterial)
    db.run(query)
  }

  def deleteMaterial(courseId: UUID, materialId: UUID): Future[Int] = {
    val query = courseMaterials.filter(x => x.courseId === courseId.bind && x.id === materialId.bind).delete
    db.run(query)
  }

}
