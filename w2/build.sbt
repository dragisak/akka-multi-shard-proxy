name := "w2"

version := "1.0"

scalaVersion := "2.11.6"

val akkaVersion = "2.3.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka"         %% "akka-contrib"         % akkaVersion exclude("org.iq80.leveldb","leveldb"),
  "org.iq80.leveldb"    % "leveldb"                         % "0.7"
)



