package users.model

import java.util.UUID

object UsersProtocol {

  case class RegisterRequest(token: String,
                             email: String,
                             password: String)

  case class UserResponse(id: UUID,
                          email: Option[String],
                          firstName: Option[String],
                          middleName: Option[String],
                          lastName: Option[String],
                          phoneNumber: Option[String],
                          city: Option[String],
                          info: Option[String],
                          vkLink: Option[String],
                          facebookLink: Option[String],
                          linkedinLink: Option[String],
                          instagramLink: Option[String],
                          groupId: Option[UUID],
                          admissionYear: Option[Int],
                          degree: Option[String],
                          form: Option[String],
                          basis: Option[String])

  object UserResponse {

    def from(userAccount: UserAccount): UserResponse = {
      UserResponse(
        id = userAccount.id,
        email = userAccount.email,
        firstName = userAccount.firstName,
        middleName = userAccount.middleName,
        lastName = userAccount.lastName,
        phoneNumber = userAccount.phoneNumber,
        city = userAccount.city,
        info = userAccount.info,
        vkLink = userAccount.vkLink,
        facebookLink = userAccount.facebookLink,
        linkedinLink = userAccount.linkedinLink,
        instagramLink = userAccount.instagramLink,
        groupId = None,
        admissionYear = None,
        degree = None,
        form = None,
        basis = None
      )
    }

    def from(userAccount: UserAccount, studentOpt: Option[Student]): UserResponse = {
      val rawUser = UserResponse.from(userAccount)
      studentOpt match {
        case None => rawUser
        case Some(student) => rawUser.copy(
          groupId = Some(student.groupId),
          admissionYear = Some(student.admissionYear),
          degree = Some(student.degree),
          form = Some(student.form),
          basis = Some(student.basis)
        )
      }
    }

  }

  case class UserUpdateRequest(phoneNumber: Option[String],
                               city: Option[String],
                               info: Option[String],
                               vkLink: Option[String],
                               facebookLink: Option[String],
                               linkedinLink: Option[String],
                               instagramLink: Option[String])

}
