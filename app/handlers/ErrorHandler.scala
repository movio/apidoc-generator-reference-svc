
package handlers

import scala.concurrent._
import javax.inject.Singleton;

import play.api.http.HttpErrorHandler
import play.api.http.{Status ⇒ HttpStatus}
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.Logger

@Singleton
class ErrorHandler extends HttpErrorHandler {
  import movio.apidoc.generator.reference.v0.models._
  import movio.apidoc.generator.reference.v0.models.json._

  private val logger = Logger(this.getClass)

  /*
   * The original JSON error message looks like:
   *
   *   Invalid Json: Unexpected end-of-input: expected close marker for OBJECT (from [Source: [B@596b69fa; line: 1, column: 3])
   *     at [Source: [B@596b69fa; line: 3, column: 80]]
   *
   * The `Source` part doesn't make sense as it just prints the underlying byte
   * array. To make it look nicer, we can remove that part so that it becomes:
   *
   *  Invalid Json: Unexpected end-of-input: expected close marker for OBJECT (from [line: 1, column: 3])
   *    at [line: 3, column: 80]
   *
   */
  def cleanupJsonError(message: String) =
    message.replaceAll("Source:.*;\\s?", "").replaceAll("\n\\s?", " ")

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    logger.warn(s"Client error handling request: [$request], status: [$statusCode], error message: [$message]")
    val msg = statusCode match {
      case HttpStatus.NOT_FOUND ⇒ "Requested resource doesn't exist"
      case _                    ⇒ cleanupJsonError(message)
    }

    Future.successful(
      Status(statusCode)(Json.toJson(Error(statusCode.toString, msg)))
    )
  }

  def onServerError(request: RequestHeader, ex: Throwable) = {
    logger.error(s"Unexpected error handling request: [$request]", ex)
    Future.successful(
      InternalServerError(Json.toJson(Error("500", "Unexpected server error, please try again")))
    )
  }
}
