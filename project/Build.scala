import sbt._
import Keys._

object MachineGunBuild extends Build {
	object Deps {
		object V {
			val Scala = "2.10.2"
			val Snakeyaml = "1.12"
      val ScalaTest = "2.0.M5b"
      val Objenesis = "1.3"
		}
    val ScalaReflect = "org.scala-lang" % "scala-reflect" % V.Scala
		val Snakeyaml = "org.yaml" % "snakeyaml" % V.Snakeyaml
    val ScalaTest = "org.scalatest" %% "scalatest" % V.ScalaTest
    val Objenesis = "org.objenesis" % "objenesis" % V.Objenesis
	}

	override def settings = super.settings ++ Seq(
		scalaVersion := Deps.V.Scala,
    libraryDependencies ++= Seq(
      Deps.ScalaReflect,
      Deps.ScalaTest % "test"
    ),
    parallelExecution in Test := false
	)

	lazy val root = Project("root", file(".")) aggregate (server, yaml)

	lazy val server = Project("machine-gun-server", file("machine-gun-server")) dependsOn yaml
	lazy val yaml = Project("yaml-gun", file("yaml-gun")) settings (
		libraryDependencies ++= Seq(
      Deps.Snakeyaml,
      Deps.Objenesis
    )
	)
}