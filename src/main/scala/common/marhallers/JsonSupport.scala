package common.marhallers

import java.time.Instant
import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, JsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object UUIDFormat extends JsonFormat[UUID] {
    def write(uuid: UUID) = JsString(uuid.toString)
    def read(value: JsValue) = {
      value match {
        case JsString(uuid) => UUID.fromString(uuid)
        case _ => throw DeserializationException("Expected hexadecimal UUID string")
      }
    }
  }

  implicit object InstantFormat extends JsonFormat[Instant] {
    def write(instant: Instant) = JsString(instant.toString)
    def read(value: JsValue) = {
      value match {
        case JsString(instant) => Instant.parse(instant)
        case _ => throw DeserializationException("Expected java instant string")
      }
    }
  }

}
