import com.smartsense.upload._
import org.scalatra._
import com.smartsense.upload.swagger._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  
  implicit val swagger = new UploadSwagger
  
  override def init(context: ServletContext) {
    //context.mount(new UploadServlet, "/*")
    context.mount(new FileUploadExample, "/upload")
    context.mount(new TemplateExample, "/")
    context.mount(new BasicAuthExample, "/basic-auth")
    context.mount(new ResourcesApp, "/api-docs")
  }
}
