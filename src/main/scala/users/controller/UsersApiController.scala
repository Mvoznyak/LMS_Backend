package users.controller

import java.util.UUID

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive1, Directives, Route}
import common.directives.{CommonDirectives, LmsAuthenticator}
import users.model.{UserAccount, UsersFormat}
import users.model.UsersProtocol._
import users.service.{UsersAccountDBService, UsersApiControllerService}

import scala.concurrent.ExecutionContext


class UsersApiController(usersAccountDBService: UsersAccountDBService,
                         usersApiControllerService: UsersApiControllerService,
                         authenticator: LmsAuthenticator)
                        (implicit executionContext: ExecutionContext) extends CommonDirectives
  with UsersFormat {

  val route: Route =
    pathPrefix("user") {
      pathEndOrSingleSlash {
        getInfo() ~
        updateInfo()
      } ~
      path(JavaUUID) { id =>
        viewInfo(id)
      }
    } ~
    path("register" / JavaUUID) { register } ~
    path("groupmates") { getGroupmates }


  def getInfo(): Route = {
    get {
      authenticator.authenticate { userAccount =>
        withErrorHandling {
          usersApiControllerService.enrichUserWithStudent(userAccount)
        }
      }
    }
  }

  def viewInfo(id: UUID): Route = {
    get {
      authenticator.authenticate { _ =>
        existingUser(id) { user =>
          withErrorHandling {
            usersApiControllerService.enrichUserWithStudentWithoutBasis(user)
          }
        }
      }
    }
  }

  def updateInfo(): Route = {
    put {
      authenticator.authenticate { userAccount =>
        entity(as[UserUpdateRequest]) { request =>
          withErrorHandling {
            usersApiControllerService.updateUser(userAccount, request)
          }
        }
      }
    }
  }

  def register(id: UUID): Route = {
    post {
      existingUser(id) { user =>
        entity(as[RegisterRequest]) { request =>
          withErrorHandling {
            usersApiControllerService.registerUser(user, request)
          }
        }
      }
    }
  }

  def getGroupmates(): Route = {
    get {
      authenticator.authenticate { user =>
        withErrorHandling {
          usersApiControllerService.getGroupmates(user)
        }
      }
    }
  }

  private def existingUser(id: UUID): Directive1[UserAccount] = {
    onSuccess(usersAccountDBService.find(id)).flatMap {
      case None => complete(StatusCodes.NotFound)
      case Some(user) => provide(user)
    }
  }

}
