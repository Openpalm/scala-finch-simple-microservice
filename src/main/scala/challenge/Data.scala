package challenge.finch

object util {

  import scala.util.{Try, Success, Failure}
  import scala.annotation.tailrec
  import globals.remote

  type lookupTable[A] = List[(Int, (Int, A))]

  def get: Option[String] = Try { scala.io.Source.fromURL(remote).mkString } match {
    case Success(list) => Some(list.replaceAll("\n", ""))
    case Failure(e)    => { 
      println(s"something went wrong while accessing $remote: $e")
      None
    }
  }

  //running length
  @tailrec
  def compress[A](
    list: List[A],
    acc: List[(Int, A)] = Nil
  ): List[(Int, A)] = list match {
    case Nil => acc
    case x :: xs =>
      acc match {
        case (count, `x`) :: cs =>
          compress(xs, List[(Int, A)]((count + 1, x)) ::: cs)
        case acc => compress(xs, List[(Int, A)]((1, x)) ::: acc)
      }
  }

  //running sum
  def buildLookupIndex[A](
    xs: List[(Int, A)]
  ): lookupTable[A] =
    xs.map(x => x._1)
      .scanLeft(0)(_ + _)
      .tail
      .zip(xs)

  def retrieve[A](xs: lookupTable[A], i: Int): A = ???

}

