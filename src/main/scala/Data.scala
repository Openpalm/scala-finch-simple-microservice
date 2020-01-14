package challenge.finch

import org.slf4j.LoggerFactory
import ch.qos.logback.classic.{Level, Logger}
import scala.util.{Try, Success, Failure}
import scala.annotation.tailrec

import Globals.remote
import Globals.logger
import Globals.rate
import Util.LookupTable

import com.github.blemale.scaffeine.{Cache, Scaffeine}
import scala.concurrent.duration._

object Data {

  val cache: Cache[String, LookupTable] =
    Scaffeine()
      .recordStats()
      .expireAfterWrite(rate.toInt.seconds)
      .maximumSize(1000)
      .build[String, LookupTable]()

  def xs: LookupTable = cache.asMap.get(remote) match {
    case Some(data) => {
      logger.debug(s"retrieved cached list")
      data
    }
    case None =>
      fetch match {
        case Some(data) => {
          logger.info(s"cached new list")
          cache.put(remote, data)
          data
        }
      }
  }

  def fetch: Option[LookupTable] =
    Try {
      logger.info(s"fetching from $remote")
      scala.io.Source.fromURL(remote).mkString
    } match {
      case Success(list) => {
        for {
          compressed <- Util.compress_runningLength(
            list.replaceAll("\n", "").toList,
            Nil
          )
          transformed <- Util.transform_runningSum(compressed)
        } yield transformed
      }
      case Failure(e) => {
        logger.error(s"something went wrong $e")
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
