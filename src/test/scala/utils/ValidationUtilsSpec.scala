package utils

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import utils.validation.ValidationUtils

class ValidationUtilsSpec extends AnyFlatSpec with Matchers {

  "validateEmail" should "accept valid email" in {
    val validEmail = "test@test.com"
    ValidationUtils.validateEmail(validEmail) shouldBe true
  }

  it should "not accept email without domain" in {
    val invalidEmail = "test@"
    ValidationUtils.validateEmail(invalidEmail) shouldBe false
  }

  "validatePasswordComplexity" should "accept strong enough password" in {
    val strongPassword = "STRong123"
    ValidationUtils.validatePasswordComplexity(strongPassword) shouldBe true
  }

  it should "not accept password without capital letters" in {
    val weakPassword = "qwerty123"
    ValidationUtils.validatePasswordComplexity(weakPassword) shouldBe false
  }

  it should "not accept password without digits" in {
    val weakPassword = "qwertyGSFD"
    ValidationUtils.validatePasswordComplexity(weakPassword) shouldBe false
  }

  it should "not accept short password" in {
    val weakPassword = "SHort12"
    ValidationUtils.validatePasswordComplexity(weakPassword) shouldBe false
  }

}
