package common.directives

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.directives.{AuthenticationDirective, Credentials}
import users.model.UserAccount
import users.service.UsersAccountDBService
import utils.hash.HashUtils

import scala.concurrent.{ExecutionContext, Future}

// it is possible to make OAuth 2, but imho basic auth is enough for study project

class LmsAuthenticator(usersAccountDBService: UsersAccountDBService)
                      (implicit executionContext: ExecutionContext) extends Directives {
  private val realm = "lms_secured"

  private def lmsUserPassAuthenticator(credentials: Credentials): Future[Option[UserAccount]] =
    credentials match {
      case p @ Credentials.Provided(email) =>
        usersAccountDBService.findByEmail(email).map { userOpt =>
          for {
            user <- userOpt
            passwordHash <- user.passwordHash
            if p.verify(passwordHash, HashUtils.md5HashString)
          } yield user
        }
      case _ => Future.successful(None)
    }

  def authenticate: AuthenticationDirective[UserAccount] = {
    authenticateBasicAsync(realm, lmsUserPassAuthenticator)
  }

}
