package challenge.finch

import cats.effect.IO
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import com.twitter.util.FuturePool

import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._

import Globals._


object Main extends App {

  // the API
  case class Result(char: Char)

  // the routes
  def healthcheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  def getCharAtIndexRoute: Endpoint[IO, Result] =
    get(path[Int]) { i: Int =>
      FuturePool.unboundedPool {
        logger.info(s"received request for $i-th element")
        Ok(Result(Data.lookup(i)))
      }
    }.handle {
      case e: Error.NotPresent => BadRequest(e)
    }

  // service bootstrap
  def service: Service[Request, Response] =
    Bootstrap
      .serve[Text.Plain](healthcheck)
      .serve[Application.Json](getCharAtIndexRoute)
      .toService

  // server up
  logger.info(s"started at 0.0.0.0:$port")
  logger.info(s"remote is $remote")
  logger.info(s"refresh every $rate seconds")

  Await.ready(Http.server.serve(s":$port", service))

}
