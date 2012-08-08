import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "viral"
    val appVersion      = "0.1-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "se.radley" %% "play-plugins-salat" % "1.0.7"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
      routesImport += "se.radley.plugin.salat.Binders._",
      templatesImport += "org.bson.types.ObjectId"
    )

}
