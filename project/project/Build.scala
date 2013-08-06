import sbt._
import Keys._

object MachineGunBuildBuild extends Build {
  override val settings = super.settings ++ Seq(
    scalaVersion := "2.9.2",
    scalacOptions ++= Seq("-deprecation")
  )

  lazy val scalaBuffPlugin = RootProject(uri("git://github.com/dontcare4free/sbt-scalabuff.git#a8b5bf249e9e71347212d6bd1cd9612f076fb5e0"))
  lazy val root = Project("game-engine-project", file(".")) dependsOn scalaBuffPlugin
}