package com.smartsense.upload.swagger

import org.scalatra._
import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, NativeSwaggerBase, Swagger}
// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}



class ResourcesApp(implicit val swagger: Swagger) extends ScalatraServlet with NativeSwaggerBase{

  // Sets up automatic case class to JSON output serialization
  protected implicit override lazy val jsonFormats: Formats = DefaultFormats
}
object UploadApiInfo extends ApiInfo(
    "The File Upload API",
    "Docs for the File Upload API",
    "http://localhost:8080/api-docs",
    "apathak@hortonworks.com",
    "Hortonworks",
    "http://www.apache.org/licenses/LICENSE-2.0")

class UploadSwagger extends Swagger(Swagger.SpecVersion, "1.0.0", UploadApiInfo)