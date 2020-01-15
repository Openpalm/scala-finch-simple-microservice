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
import scala.util.{Try, Success, Failure}

object Main extends App {

  // data type
  case class Result(char: Char)

  // routes
  def healthcheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  def getCharAtIndexRoute: Endpoint[IO, Result] =
    get(path[Int]) { i: Int =>
      FuturePool.unboundedPool {
        logger.debug(s"received request for $i-th element")
        Data.lookup(i) match {
          case Success(c) =>  { 
            logger.debug(s"retrieved $c")
            Ok(Result(c)) 
          }
          case Failure(_) => {
            logger.debug(s"failed retrieving $i-th element")
            NoContent
          }
        }
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
        Await.ready(Http.server.serve(s"0.0.0.0:$port", service)) 
}
