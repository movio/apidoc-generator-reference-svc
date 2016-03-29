/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.1.0-SNAPSHOT
 * apidoc:0.11.21 http://dockerhost:9000/movio/apidoc-generator-reference/0.1.0-SNAPSHOT/test_play_app_services
 */

package services

import javax.inject.Inject

import com.typesafe.config.Config

import play.api.mvc.Request
import scala.concurrent.Future
import scala.util.Try

class HealthchecksService @Inject() (config: Config) {
  import movio.apidoc.generator.reference.v0.models._
  import movio.apidoc.generator.reference.v0.kafka._
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  
  
  def get[T](
    request: Request[T]
  ): Future[Try[movio.apidoc.generator.reference.v0.models.Healthcheck]] = {
    Future {
      Try { 
        Healthcheck (
          status = "status1"
        ) }
    }
  }
}
