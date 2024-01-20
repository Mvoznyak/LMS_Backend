package users.model

import common.marhallers.JsonSupport
import users.model.UsersProtocol.{RegisterRequest, UserResponse, UserUpdateRequest}

trait UsersFormat extends JsonSupport {

  implicit val registerRequestFormat = jsonFormat3(RegisterRequest)
  implicit val userResponseFormat = jsonFormat17(UserResponse.apply)
  implicit val userUpdateRequestFormat = jsonFormat7(UserUpdateRequest)

}
