package wirings

import courses.service.{CourseHomeworksDBService, CourseMaterialsDBService, CourseModeratorsDBService, CoursesDBService}
import users.service.{StudentsDBService, UsersAccountDBService}

trait DatabaseModule {
  import slick.jdbc.PostgresProfile.api._
  lazy val lmsDb = Database.forConfig("lmsDb")
  lazy val userAccountDBService = new UsersAccountDBService(lmsDb)
  lazy val studentsDBService = new StudentsDBService(lmsDb)
  lazy val coursesBDService = new CoursesDBService(lmsDb)
  lazy val courseMaterialsDBService = new CourseMaterialsDBService(lmsDb)
  lazy val courseHomeworksDBService = new CourseHomeworksDBService(lmsDb)
  lazy val courseModeratorsDBService = new CourseModeratorsDBService(lmsDb)
}
