package challenge.finch

import scala.annotation.tailrec
import scala.util.{Try, Success, Failure}
import Globals.logger

trait DataUtil extends types {

  //running length
  @tailrec
  final def compress_runningLength(
      list: List[Char],
      acc: List[(Int, Char)] = Nil
  ): Option[List[(Int, Char)]] = list match {
    case Nil => Some(acc)
    case x :: xs =>
      acc match {
        case (count, `x`) :: cs =>
          compress_runningLength(xs, List[(Int, Char)]((count + 1, x)) ::: cs)
        case acc =>
          compress_runningLength(xs, List[(Int, Char)]((1, x)) ::: acc)
      }
  }

  //running sum
  def transform_runningSum(
      xs: List[(Int, Char)]
  ): Option[LookupTable] =
    Try {
      xs.map(x => x._1)
        .scanLeft(0)(_ + _)
        .tail
        .zip(xs)
    } match {
      case Success(list) => Some(list.map(x=> x._1 -> x._2._2))
      case Failure(e) => {
        logger.info(s"transform error: $e")
        None
      }
    }
}
