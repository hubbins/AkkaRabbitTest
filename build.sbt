name := """AkkaRabbitTest"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.1",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.1" % "test",
  "com.rabbitmq" % "amqp-client" % "3.6.0",
  "junit" % "junit" % "4.12"  % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)
