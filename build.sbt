ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"
libraryDependencies += "org.scalafx" %% "scalafx" % "14-R19"
lazy val root = (project in file("."))
  .settings(
    name := "towerDefenceGame"
  )
