import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import wirings.{ConcurrentModule, DatabaseModule}
import com.softwaremill.macwire._
import common.directives.LmsAuthenticator
import courses.controller.CoursesApiController
import courses.service.CoursesApiControllerService
import users.controller.UsersApiController
import users.service.UsersApiControllerService

object LmsApp extends App
  with ConcurrentModule
  with DatabaseModule {
  
  lazy val lmsAuthenticator = wire[LmsAuthenticator]
  lazy val usersApiControllerService = wire[UsersApiControllerService]
  lazy val coursesApiControllerService = wire[CoursesApiControllerService]
  lazy val usersApiController = wire[UsersApiController]
  lazy val coursesApiController = wire[CoursesApiController]

  Http().bindAndHandle(
    usersApiController.route ~ coursesApiController.route,
    "localhost",
    8080
  )

}
