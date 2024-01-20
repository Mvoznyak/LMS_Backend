package wirings

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait ConcurrentModule {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
}
