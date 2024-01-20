package users.model

import java.util.UUID

object UserAccountModel {

  import slick.jdbc.PostgresProfile.api._

  class UserAccountTable(tag: Tag) extends Table[UserAccount](tag, "user_account") {

    def id = column[UUID]("id", O.PrimaryKey)
    def verificationCode = column[String]("verification_code")
    def email = column[Option[String]]("email")
    def passwordHash = column[Option[String]]("password_hash")
    def firstName = column[Option[String]]("first_name")
    def middleName = column[Option[String]]("middle_name")
    def lastName = column[Option[String]]("last_name")
    def phoneNumber = column[Option[String]]("phone_number")
    def city = column[Option[String]]("city")
    def info = column[Option[String]]("info")
    def vkLink = column[Option[String]]("vk_link")
    def facebookLink = column[Option[String]]("facebook_link")
    def linkedinLink = column[Option[String]]("linkedin_link")
    def instagramLink = column[Option[String]]("instagram_link")

    def * = (
      id,
      verificationCode,
      email,
      passwordHash,
      firstName,
      middleName,
      lastName,
      phoneNumber,
      city,
      info,
      vkLink,
      facebookLink,
      linkedinLink,
      instagramLink
      ).mapTo[UserAccount]

  }

  val userAccounts = TableQuery[UserAccountTable]

}