scalaVersion := "3.3.0"
val zioLoggingVersion = "2.1.9"
val logbackClassicVersion = "1.4.4"
val quillVersion = "4.6.0.1"
val testContainersVersion = "0.40.11"
val zioVersion = "2.0.11"
val zioMockVersion = "1.0.0-RC8"
organization := "fi.ke"
name := "htmxExamples"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-http" % "3.0.0-RC2",
  "dev.zio" %% "zio-logging" % zioLoggingVersion,
  "dev.zio" %% "zio-logging-slf4j" % zioLoggingVersion,
  "ch.qos.logback" % "logback-classic" % logbackClassicVersion
)
addCompilerPlugin("com.hmemcpy" %% "zio-clippy" % "0.0.1")
