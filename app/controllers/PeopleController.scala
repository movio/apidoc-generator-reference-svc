/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.1.0-SNAPSHOT
 * apidoc:0.11.21 http://dockerhost:9000/movio/apidoc-generator-reference/0.1.0-SNAPSHOT/test_play_app_controllers
 */

package controllers

import javax.inject.Inject
import javax.inject.Singleton

import play.api.libs.json._
import play.api.Logger

import scala.concurrent.duration._
import scala.concurrent.Future

import services.PeopleService

class PeopleController @Singleton @Inject() (service: PeopleService) extends play.api.mvc.Controller {
  import movio.apidoc.generator.reference.v0.models._
  import movio.apidoc.generator.reference.v0.models.json._
  import play.api.libs.concurrent.Execution.Implicits.defaultContext

  private val logger = Logger(this.getClass)

  
  def postV0AndPersonByTenant(
    tenant: String
  ) = play.api.mvc.Action.async(play.api.mvc.BodyParsers.parse.json) {  request =>
    request.body.validate[movio.apidoc.generator.reference.v0.models.Person] match {
      case errors: JsError =>
        logger.warn(s"[postV0AndPersonByTenant] Error validating the body for the request [tenant: $tenant], body: [${request.body}], error: [$errors]")
        errorResponse(errors, msg => Error("400", msg))
      case body: JsSuccess[movio.apidoc.generator.reference.v0.models.Person] =>
        service.post(request, body.get, tenant).map{_ match {
          case scala.util.Success(result) =>
            Status(201)(Json.toJson(result))
          case scala.util.Failure(ex) =>
            logger.error(s"[postV0AndPersonByTenant] Error processing request [tenant: $tenant]", ex)
            errorResponse(ex, msg => Error("500", msg))
        }}
    }
  }

  def postV0ByTenant(
    tenant: String
  ) = play.api.mvc.Action.async(play.api.mvc.BodyParsers.parse.json) {  request =>
    request.body.validate[Seq[movio.apidoc.generator.reference.v0.models.Person]] match {
      case errors: JsError =>
        logger.warn(s"[postV0ByTenant] Error validating the body for the request [tenant: $tenant], body: [${request.body}], error: [$errors]")
        errorResponse(errors, msg => Error("400", msg))
      case body: JsSuccess[Seq[movio.apidoc.generator.reference.v0.models.Person]] =>
        service.post(request, body.get, tenant).map{_ match {
          case scala.util.Success(result) =>
            Status(200)(Json.toJson(result.size))
          case scala.util.Failure(ex) =>
            logger.error(s"[postV0ByTenant] Error processing request [tenant: $tenant]", ex)
            errorResponse(ex, msg => Error("500", msg))
        }}
    }
  }

  private def errorResponse[A: Writes](errors: JsError, create: String => A): Future[play.api.mvc.Result] = {
    val msg = errors.errors.flatMap(node => {
      val nodeName = node._1.path.map(_.toString + ": ").mkString
      val message = node._2.map(_.message).mkString
      s"$nodeName$message"
    }).mkString
    scala.concurrent.Future(BadRequest(Json.toJson(create(msg))))
  }

  private def errorResponse[A: Writes](ex: Throwable, create: String => A): play.api.mvc.Result =
    InternalServerError(Json.toJson(create("Unexpected server error, please try again")))

}
