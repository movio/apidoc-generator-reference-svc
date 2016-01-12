import sbt._

object Dependencies {
  val apidocDependencies = Set(
    "com.github" %% "apidoc-generator-reference-kafkalib_0_8" % "0.1.0-SNAPSHOT",
    "com.github" %% "apidoc-generator-reference-playlib" % "0.1.0-SNAPSHOT"
  )
}
