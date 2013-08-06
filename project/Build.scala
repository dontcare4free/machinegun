import sbt._
import Keys._
import scalabuff.ScalaBuffPlugin

object MachineGunBuild extends Build {
	object Deps {
		object V {
			val Scala = "2.10.2"
      val ScalaTest = "2.0.M5b"
      val ScalaBuff = "1.3.2"
      val Akka = "2.2.0"
		}
    val ScalaReflect = "org.scala-lang" % "scala-reflect" % V.Scala
    val ScalaTest = "org.scalatest" %% "scalatest" % V.ScalaTest
    val AkkaActor = "com.typesafe.akka" %% "akka-actor" % V.Akka
	}

	override def settings = super.settings ++ Seq(
		scalaVersion := Deps.V.Scala,
    libraryDependencies ++= Seq(
      Deps.ScalaReflect,
      Deps.ScalaTest % "test"
    ),
    parallelExecution in Test := false
	)

	lazy val root = Project("root", file(".")) aggregate (server, defaultServer)

	lazy val server = Project("machine-gun-server", file("machine-gun-server"))
    .settings(ScalaBuffPlugin.scalabuffSettings: _*)
    .settings(
      ScalaBuffPlugin.scalabuffVersion := Deps.V.ScalaBuff,
      libraryDependencies += Deps.AkkaActor
     )
    .configs(ScalaBuffPlugin.ScalaBuff)
  lazy val defaultServer = Project("machine-gun-server-default", file("machine-gun-server-default")) dependsOn server
}