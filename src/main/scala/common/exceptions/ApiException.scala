package common.exceptions

case class ApiException(message: String) extends Exception

class InvalidEmailException extends ApiException("Invalid email")
class WeakPasswordException extends ApiException("Weak password")
class UserIsAlreadyRegisteredException extends ApiException("User is already registered")
class WrongTokenException extends ApiException("Wrong token")
class WrongPhoneNumberException extends ApiException("Wrong phone number")
class NoSuchCourseException extends ApiException("Course doesn't exist")
class NoListenerException extends ApiException("User is not course listener")
class HomeworkNotAvailableException extends ApiException("Homework is not available")

class WrongLinkException(link: String) extends ApiException(s"Wrong $link link exception")
class WrongVkLinkException extends WrongLinkException("vk")
class WrongFacebookLinkException extends WrongLinkException("facebook")
class WrongInstagramLinkException extends WrongLinkException("instagram")
class WrongLinkedinLinkException extends WrongLinkException("linkedin")