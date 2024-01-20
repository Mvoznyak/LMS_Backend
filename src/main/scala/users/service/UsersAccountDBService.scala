package users.service

import java.util.UUID

import users.model.{UserAccount, UserRegisterInfo}
import users.model.UserAccountModel._
import slick.jdbc.PostgresProfile.api._
import users.model.UsersProtocol.UserUpdateRequest

import scala.concurrent.Future

class UsersAccountDBService(db: Database) {

  def find(id: UUID): Future[Option[UserAccount]] = {
    val query = userAccounts.filter(_.id === id.bind)
      .result
      .headOption
    db.run(query)
  }

  def findByEmail(email: String): Future[Option[UserAccount]] = {
    val query = userAccounts.filter(_.email === email.bind)
      .result
      .headOption
    db.run(query)
  }

  def updateRegisterInfo(id: UUID, userRegisterInfo: UserRegisterInfo): Future[Int] = {
    val query = userAccounts.filter(_.id === id.bind)
      .map(x => (x.email, x.passwordHash))
      .update((Some(userRegisterInfo.email), Some(userRegisterInfo.passwordHash)))
    db.run(query)
  }

  def updateUser(id: UUID, userUpdateRequest: UserUpdateRequest): Future[Int] = {
    val query = userAccounts.filter(_.id === id.bind)
      .map(x => (x.phoneNumber, x.city, x.info, x.vkLink, x.facebookLink, x.instagramLink, x.linkedinLink))
      .update((
        userUpdateRequest.phoneNumber,
        userUpdateRequest.city,
        userUpdateRequest.info,
        userUpdateRequest.vkLink,
        userUpdateRequest.facebookLink,
        userUpdateRequest.instagramLink,
        userUpdateRequest.linkedinLink
      ))
    db.run(query)
  }

}
