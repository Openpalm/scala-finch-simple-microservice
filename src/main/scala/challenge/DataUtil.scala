package challenge.finch

import scala.annotation.tailrec

object Util {

  type LookupTable = List[(Int, (Int, Char))]

  //running length
  @tailrec
  def compress_runningLength(
    list: List[Char],
    acc: List[(Int, Char)] = Nil
  ): List[(Int, Char)] = list match {
    case Nil => acc
    case x :: xs =>
      acc match {
        case (count, `x`) :: cs =>
          compress_runningLength(xs, List[(Int, Char)]((count + 1, x)) ::: cs)
        case acc => compress_runningLength(xs, List[(Int, Char)]((1, x)) ::: acc)
      }
  }

  //running sum
  def transform_runningSum(
    xs: List[(Int, Char)]
  ): LookupTable =
    xs.map(x => x._1)
      .scanLeft(0)(_ + _)
      .tail
      .zip(xs)
}
