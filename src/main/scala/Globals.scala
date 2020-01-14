package challenge.finch

object Globals {
  import scala.util.Properties
  import org.slf4j.LoggerFactory
  import ch.qos.logback.classic.{Level, Logger}

  def remote = Properties.envOrElse("APP.REMOTE", "http://localhost:8080")
  def port = Properties.envOrElse("APP.PORT", "1337")
  def rate = Properties.envOrElse("APP.REFRESH", "10")
  def logLevel =  Properties.envOrElse("APP.LOGLEVEL", "info") match { 
    case "debug" => Level.DEBUG
    case "info" => Level.INFO
  }

  LoggerFactory
    .getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
    .asInstanceOf[Logger]
    .setLevel(logLevel)

  val logger = LoggerFactory.getLogger("")

}
