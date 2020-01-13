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

import globals._

object Main extends App {

  
  // the API
  case class Message(i: Int)

  // the routes
  def healthcheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  def getCharAtIndexRoute: Endpoint[IO, Message] =
    get(path[Int]) { i: Int =>
      FuturePool.unboundedPool {
        println(s"received request for $i element")
        //
        //logic
        //
        Ok(Message(i))
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
  Await.ready(Http.server.serve(s":$port", service))

  println(s"started at 0.0.0.0:$port")
  println(s"remote is $remote")
  println(s"refresh every $rate")

}
