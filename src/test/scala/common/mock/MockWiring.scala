package common.mock

import org.mockito.MockitoSugar
import users.service.{StudentsDBService, UsersAccountDBService, UsersApiControllerService}
import wirings.ConcurrentModule

trait MockWiring extends MockitoSugar with ConcurrentModule {
  val mockUsersAccountDBService = mock[UsersAccountDBService]
  val mockStudentsDBService = mock[StudentsDBService]
  val mockUsersApiControllerService = mock[UsersApiControllerService]
}
