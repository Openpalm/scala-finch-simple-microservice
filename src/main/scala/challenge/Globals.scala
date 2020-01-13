package challenge.finch

object Globals {
    import scala.util.Properties

    def remote = Properties.envOrElse("APP.REMOTE", "http://localhost:8080")
    def port = Properties.envOrElse("APP.PORT", "1337")
    def rate = Properties.envOrElse("APP.REFRESH", "3")
}


