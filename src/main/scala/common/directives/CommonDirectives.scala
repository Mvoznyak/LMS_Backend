package common.directives

import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import common.exceptions.ApiException

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait CommonDirectives extends Directives {

  def withErrorHandling[T](future: => Future[T])(implicit marshaller: ToResponseMarshaller[T]): Route = {
    onComplete(future) {
      case Success(value) => complete(value)
      case Failure(exception) => exception match {
        case ApiException(message) => {
          complete(StatusCodes.BadRequest -> message)
        }
        case other => {
          throw other
        }
      }
    }
  }

  def withErrorHandling(future: => Future[Unit]): Route = {
    onComplete(future) {
      case Success(_) => complete(StatusCodes.OK)
      case Failure(exception) => exception match {
        case ApiException(message) => {
          complete(StatusCodes.BadRequest -> message)
        }
        case other => {
          throw other
        }
      }
    }
  }

}
