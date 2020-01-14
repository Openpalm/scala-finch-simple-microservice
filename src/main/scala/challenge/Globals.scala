package challenge.finch

object Globals {
    import scala.util.Properties
    import org.slf4j.LoggerFactory
    import ch.qos.logback.classic.{Level, Logger}


    def remote = Properties.envOrElse("APP.REMOTE", "http://localhost:8080")
    def port = Properties.envOrElse("APP.PORT", "1337")
    def rate = Properties.envOrElse("APP.REFRESH", "10")

    LoggerFactory
      .getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
      .asInstanceOf[Logger]
      .setLevel(Level.INFO)
  
    val logger = LoggerFactory.getLogger("")

}
