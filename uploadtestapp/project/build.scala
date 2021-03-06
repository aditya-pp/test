import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.earldouglas.xwp.JettyPlugin
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object UploadtestappBuild extends Build {
  val Organization = "com.smartsense"
  val Name = "UploadTestApp"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.6"
  val ScalatraVersion = "2.4.0"

  lazy val project = Project (
    "uploadtestapp",
    file("."),
    settings = ScalatraPlugin.scalatraSettings ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-auth" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.1.3" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.2.14.v20151106" % "container",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
        "org.json4s" %% "json4s-ast_2.10" % "3.2.4",
        "org.json4s" %% "json4s-core_2.10" % "3.2.4",
        "org.json4s" %% "json4s-ext_2.10" % "3.3.0",
        "org.json4s" %% "json4s-scalap_2.10" % "3.3.0",
        "org.json4s" %% "json4s-native_2.10" % "3.2.4",
        "org.scalatra" %% "scalatra-json"  % ScalatraVersion,
        "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  ).enablePlugins(JettyPlugin)
}
