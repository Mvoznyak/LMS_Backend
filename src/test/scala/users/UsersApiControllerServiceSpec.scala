package users

import com.softwaremill.macwire._
import common.mock.MockWiring
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import common.SampleData
import users.model.UserRegisterInfo
import users.model.UsersProtocol.RegisterRequest
import users.service.UsersApiControllerService

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Success

class UsersApiControllerServiceSpec extends AnyFlatSpec with Matchers with MockWiring with SampleData {

  "registerUser" should "register user if everything is ok" in {
    val request = RegisterRequest(
      token = sampleVerificationCode,
      email = sampleEmail,
      password = samplePassword
    )
    val userRegisterInfo = UserRegisterInfo(
      email = sampleEmail,
      passwordHash = samplePasswordHash
    )
    when(mockUsersAccountDBService.updateRegisterInfo(sampleUUID, userRegisterInfo)).thenReturn(Future.successful(1))

    val result = usersApiControllerService.registerUser(sampleEmptyUserAccount, request)

    Await.result(result, 5.seconds)
    verify(mockUsersAccountDBService).updateRegisterInfo(sampleUUID, userRegisterInfo)
    result.value.get shouldBe Success(())
  }

  val usersApiControllerService = wire[UsersApiControllerService]

}
