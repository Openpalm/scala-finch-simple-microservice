package challenge.finch

import cats.effect.IO
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._

import scala.annotation.tailrec

object Main extends App {

  object util {

    type lookupTable[A] = List[(Int, (Int, A))]

    val url = "http://localhost:8080"

    def getDataFromUrl (url: String): String = scala.io.Source.fromURL(url).mkString
    def getData: List[Char] = getDataFromUrl(url).replaceAll("\n", "").toList

    //running length
    @tailrec
    def compress[A](
      list:List[A], 
      acc: List[(Int, A)] = Nil
    ): List[(Int, A)] = list match  {
      case Nil => acc
      case x :: xs => acc match {
        case (count, `x`) :: cs => compress (xs, List[(Int, A)]((count + 1, x)) ::: cs)
        case acc                => compress (xs, List[(Int, A)]((1, x)) ::: acc)
      }
    }

    //running sum
    def buildLookupIndex[A](
      xs: List[(Int, A)]
    ): lookupTable[A] = xs
      .map(x=> x._1)
      .scanLeft(0)(_ + _)
      .tail
      .zip(xs)

      def retrieve[A](xs: lookupTable[A], i: Int): A = ???

  }

  //the API
  case class Message(i: Int)

  def healthcheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  def getCharAtIndexRoute: Endpoint[IO, Message] = get(path[Int]) { i: Int =>
    Ok(Message(i))
  }

  def service: Service[Request, Response] = Bootstrap
    .serve[Text.Plain](healthcheck)
    .serve[Application.Json](getCharAtIndexRoute)
    .toService
    Await.ready(Http.server.serve(":1337", service))

}
