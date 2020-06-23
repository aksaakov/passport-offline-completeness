name := "passport-offline-completeness"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "org.dispatchhttp" %% "dispatch-core" % "1.2.0",
  "org.json4s" %% "json4s-jackson" % "3.6.7",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)