package utils.validation

object ValidationUtils {

  private val emailRegex = """^[a-zA-Z0-9\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$""".r
  private val passwordRegex = """^(?=.*[A-Z].*[A-Z])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$""".r
  private val phoneNumberRegex = """^\+7\d{10}$""".r

  private val vkStart = "https://vk.com/"
  private val facebookStart = "https://facebook.com/"
  private val instagramStart = "https://instagram.com/"
  private val linkedinStart = "https://linkedin.com/"

  def validateEmail(email: String): Boolean = email match {
    case emailRegex(_*) => true
    case _ => false
  }

  def validatePasswordComplexity(password: String): Boolean = password match {
    case passwordRegex(_*) => true
    case _ => false
  }

  def validatePhoneNumber(phoneNumber: String): Boolean = phoneNumber match {
    case phoneNumberRegex(_*) => true
    case _ => false
  }

  def validateVkLink(link: String) = validateLink(link, vkStart)

  def validateFacebookLink(link: String) = validateLink(link, facebookStart)

  def validateInstagramLink(link: String) = validateLink(link, instagramStart)

  def validateLinkedinLink(link: String) = validateLink(link, linkedinStart)

  private def validateLink(link: String, start: String): Boolean = link.startsWith(start)

}
