val finchVersion = "0.26.0"
val circeVersion = "0.10.1"
val scalatestVersion = "3.0.5"

lazy val root = (project in file("."))
  .settings(
    organization := "challenge",
    name := "21re-finch",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.7",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.1.3",
      //the server
      "com.github.finagle" %% "finchx-core" % finchVersion,
      "com.github.finagle" %% "finchx-circe" % finchVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      //cache
      "com.github.blemale" %% "scaffeine" % "3.1.0" % "compile",
      "com.github.cb372" %% "scalacache-caffeine" % "0.28.0",
      //
      //lihao console's
      "com.lihaoyi" % "ammonite" % "1.6.9-19-827dffe" % "test" cross CrossVersion.full,
      //testing once im done
      "org.scalatest" %% "scalatest" % scalatestVersion % "test"
    )
  )

//for REPL dev
sourceGenerators in Test += Def.task {
  val file = (sourceManaged in Test).value / "amm.scala"
  IO.write(file, """object amm extends App { ammonite.Main.main(args) }""")
  Seq(file)
}.taskValue

trapExit := false
