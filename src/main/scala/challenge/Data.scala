package challenge.finch

import org.slf4j.LoggerFactory
import ch.qos.logback.classic.{Level,Logger}
import scala.util.{Try, Success, Failure}
import scala.annotation.tailrec

import Globals.remote
import Globals.logger
import Util.LookupTable

object Data  {

  //TODO needs caching
  def xs: LookupTable = fetch match { case Some(data) => data }

  def fetch: Option[LookupTable] =
    Try { 
      logger.info(s"fetching from $remote")
      scala.io.Source.fromURL(remote).mkString 
      } match {
        case Success(list) => { 
          for { 
          compressed <- Util.compress_runningLength(list.replaceAll("\n", "").toList, Nil)
          transformed <- Util.transform_runningSum(compressed)
          }  yield transformed
        }
        case Failure(e) => {
          logger.info(s"something went wrong $e")
          None
        }
      }

      def lookup(i: Int): Try[Char] = {
        Try { 
          xs.dropWhile(x => x._1 < i)
            .head
            ._2 // (count, char)
            ._2 // (char)
        } 
      }
}
