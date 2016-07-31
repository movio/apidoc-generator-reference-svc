/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.1.0-SNAPSHOT
 * apidoc:0.11.21 http://dockerhost:9000/movio/apidoc-generator-reference/0.1.0-SNAPSHOT/test_play_app_services
 */

package services

import scala.concurrent.Future
import scala.util.Try
import javax.inject.Inject

import com.typesafe.config.Config

import play.api.mvc.Request
import play.api.Logger

class PeopleService @Inject() (config: Config) {
  import movio.apidoc.generator.reference.v0.models._
  import movio.apidoc.generator.reference.v0.kafka._
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  val kafkaPersonProducer = new KafkaPersonProducer(config)

  private val logger = Logger(this.getClass)

  
  def post[T](
    request: Request[T],
    data: movio.apidoc.generator.reference.v0.models.Person,
    tenant: String
  ): Future[Try[movio.apidoc.generator.reference.v0.models.Person]] = {
    Future {
      logger.debug(s"[tenant: $tenant] Producing a single People message: [${data}]")
      kafkaPersonProducer.send(data, tenant)
    }
  }

  def post[T](
    request: Request[T],
    data: Seq[movio.apidoc.generator.reference.v0.models.Person],
    tenant: String
  ): Future[Try[Seq[movio.apidoc.generator.reference.v0.models.Person]]] = {
    Future {
      logger.debug(s"[tenant: $tenant] Producing a batch of [${data.size}] People messages")
      kafkaPersonProducer.send(data, tenant)
    }
  }
}
