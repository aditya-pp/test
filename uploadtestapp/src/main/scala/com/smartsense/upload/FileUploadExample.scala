package com.smartsense.upload

import org.scalatra._
import org.scalatra.servlet.{MultipartConfig, SizeConstraintExceededException, FileUploadSupport}
import xml.Node
import org.scalatra.swagger._
// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}
// JSON handling support from Scalatra
import org.scalatra.json._

class FileUploadExample(implicit val swagger: Swagger) extends ScalatraServlet with FileUploadSupport with FlashMapSupport with NativeJsonSupport with SwaggerSupport{
  
  protected val applicationDescription = "The file upload API."
  override protected val applicationName = Some("UploadAPI")
  
  // Sets up automatic case class to JSON output serialization
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  // Before every action runs, set the content type to be in JSON format.
//  before() {
//    contentType = formats("json")
//  }
  
  configureMultipartHandling(MultipartConfig(maxFileSize = Some(3*1024*1024)))

  def displayPage(content: Seq[Node]) = Template.page("File upload example", content, url(_))
  error {
    case e: SizeConstraintExceededException =>
      RequestEntityTooLarge(displayPage(
        <p>The file you uploaded exceeded the 3 MB limit.</p>))
  }
  
  val getUploadPage =
    (apiOperation("upload")
      summary "page for upload"
      notes "Only file upto 3MB allowed"
      parameter queryParam[Option[String]]("name").description("A name to search for"))

  get("/",operation(getUploadPage)) {
    displayPage(
      <form action={url("/upload")} method="post" enctype="multipart/form-data">
       <p>File to upload: <input type="file" name="file" /></p>
       <p><input type="submit" value="Upload" /></p>
      </form>
      <p>
        Upload a file using the above form. After you hit "Upload"
        the file will be uploaded and your browser will start
        downloading it.
      </p>

      <p>
        The maximum file size accepted is 3 MB.
      </p>)
  }

  post("/upload") {
    fileParams.get("file") match {
      case Some(file) =>
        Ok(file.get(), Map(
          "Content-Type"        -> (file.contentType.getOrElse("application/octet-stream")),
          "Content-Disposition" -> ("attachment; filename=\"" + file.name + "\"")
        ))

      case None =>
        BadRequest(displayPage(
          <p>
            Hey! You forgot to select a file.
          </p>))
    }
  }
}