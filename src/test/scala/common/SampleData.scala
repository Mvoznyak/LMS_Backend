package common

import java.util.UUID

import users.model.UserAccount
import utils.hash.HashUtils

trait SampleData {
  
  val sampleUUID = UUID.randomUUID()
  val sampleVerificationCode = "code"
  val sampleEmail = "sample@email.com"
  val samplePassword = "passWORd123"
  val samplePasswordHash = HashUtils.md5HashString(samplePassword)
  
  val sampleEmptyUserAccount = UserAccount(
    id = sampleUUID,
    verificationCode = sampleVerificationCode,
    email = None,
    passwordHash = None,
    firstName = None,
    middleName = None,
    lastName = None,
    phoneNumber = None,
    city = None,
    info = None,
    vkLink = None,
    facebookLink = None,
    linkedinLink = None,
    instagramLink = None
  )
  
}
