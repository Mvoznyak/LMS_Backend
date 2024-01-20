package users.service

import common.exceptions.{InvalidEmailException, UserIsAlreadyRegisteredException, WeakPasswordException, WrongPhoneNumberException, WrongTokenException, WrongFacebookLinkException, WrongInstagramLinkException, WrongLinkedinLinkException, WrongVkLinkException}
import users.model.UsersProtocol.{RegisterRequest, UserResponse, UserUpdateRequest}
import users.model.{UserAccount, UserRegisterInfo}
import utils.validation.ValidationUtils

import scala.concurrent.{ExecutionContext, Future}

class UsersApiControllerService(userAccountDBService: UsersAccountDBService,
                                studentsDBService: StudentsDBService)
                               (implicit executionContext: ExecutionContext) {

  def registerUser(user: UserAccount, request: RegisterRequest): Future[Unit] = {
    Future {
      if (!ValidationUtils.validateEmail(request.email))
        throw new InvalidEmailException
      if (!ValidationUtils.validatePasswordComplexity(request.password))
        throw new WeakPasswordException
      if (user.email.nonEmpty)
        throw new UserIsAlreadyRegisteredException
      if (user.verificationCode != request.token)
        throw new WrongTokenException
    }.flatMap { _ =>
      val registerInfo = UserRegisterInfo.from(request)
      userAccountDBService.updateRegisterInfo(user.id, registerInfo).map(_ => ())
    }
  }

  def enrichUserWithStudent(user: UserAccount): Future[UserResponse] = {
    studentsDBService.find(user.id).map { studentOpt =>
      UserResponse.from(user, studentOpt)
    }
  }

  def enrichUserWithStudentWithoutBasis(user: UserAccount): Future[UserResponse] = {
    studentsDBService.find(user.id).map { studentOpt =>
      UserResponse.from(user, studentOpt).copy(basis = None)
    }
  }

  def updateUser(user: UserAccount, request: UserUpdateRequest): Future[Unit] = {
    Future {
      if (!request.phoneNumber.forall(ValidationUtils.validatePhoneNumber))
        throw new WrongPhoneNumberException
      if (!request.facebookLink.forall(ValidationUtils.validateFacebookLink))
        throw new WrongFacebookLinkException
      if (!request.vkLink.forall(ValidationUtils.validateVkLink))
        throw new WrongVkLinkException
      if (!request.instagramLink.forall(ValidationUtils.validateInstagramLink))
        throw new WrongInstagramLinkException
      if (!request.linkedinLink.forall(ValidationUtils.validateLinkedinLink))
        throw new WrongLinkedinLinkException
    }
  }.flatMap { _ =>
    userAccountDBService.updateUser(user.id, request).map(_ => ())
  }

  def getGroupmates(user: UserAccount): Future[Seq[UserResponse]] = {
    studentsDBService.find(user.id).flatMap {
      case None => Future.successful(Seq.empty[UserResponse])
      case Some(student) => {
        studentsDBService.getGroupmates(student.groupId).flatMap { students =>
          Future.sequence {
            students.map { student =>
              userAccountDBService.find(student.id).map {
                _.map {
                  UserResponse.from(_, Some(student)).copy(basis = None)
                }
              }
            }
          }.map(_.flatten)
        }
      }
    }
  }

}
