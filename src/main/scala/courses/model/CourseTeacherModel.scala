package courses.model

import java.util.UUID

object CourseTeacherModel {

  import slick.jdbc.PostgresProfile.api._

  class CourseTeacherTable(tag: Tag) extends Table[CourseTeacher](tag, "course_teacher") {

    def courseId = column[UUID]("course_id")
    def teacherId = column[UUID]("teacher_id")

    def * = (
      courseId,
      teacherId
      ).mapTo[CourseTeacher]

  }

  val courseTeachers = TableQuery[CourseTeacherTable]

}
