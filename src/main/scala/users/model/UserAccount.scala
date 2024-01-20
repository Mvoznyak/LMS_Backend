package users.model

import java.util.UUID

import users.model.UsersProtocol.RegisterRequest
import utils.hash.HashUtils

case class UserAccount(id: UUID,
                       verificationCode: String,
                       email: Option[String],
                       passwordHash: Option[String],
                       firstName: Option[String],
                       middleName: Option[String],
                       lastName: Option[String],
                       phoneNumber: Option[String],
                       city: Option[String],
                       info: Option[String],
                       vkLink: Option[String],
                       facebookLink: Option[String],
                       linkedinLink: Option[String],
                       instagramLink: Option[String])

case class UserRegisterInfo(email: String,
                            passwordHash: String)

object UserRegisterInfo {
  def from(request: RegisterRequest): UserRegisterInfo = {
    UserRegisterInfo(
      email = request.email,
      passwordHash = HashUtils.md5HashString(request.password)
    )
  }
}