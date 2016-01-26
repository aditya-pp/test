package com.smartsense.upload

import org.scalatra._
import org.scalatra.auth.strategy.{BasicAuthStrategy, BasicAuthSupport}
import org.scalatra.auth.{ScentrySupport, ScentryConfig}
import com.smartsense.upload.BasicAuthExample.AuthenticationSupport
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

object BasicAuthExample {

  case class MyUser(id: String)

  class OurBasicAuthStrategy(protected override val app: ScalatraBase, realm: String)
    extends BasicAuthStrategy[MyUser](app, realm) {

    protected def validate(userName: String, password: String)(implicit request: HttpServletRequest, response: HttpServletResponse): Option[MyUser] = {
      if(userName == "admin" && password == "admin") Some(MyUser("admin"))
      else None
    }

    protected def getUserId(user: MyUser)(implicit request: HttpServletRequest, response: HttpServletResponse): String = user.id
  }

  trait AuthenticationSupport extends ScentrySupport[MyUser] with BasicAuthSupport[MyUser] { 
    self: ScalatraBase =>

    val realm = "Scalatra Basic Auth Example"

    protected def fromSession = { case id: String => MyUser(id)  }
    protected def toSession   = { case usr: MyUser => usr.id }

    protected val scentryConfig = (new ScentryConfig {}).asInstanceOf[ScentryConfiguration]


    override protected def configureScentry = {
      scentry.unauthenticated {
        scentry.strategies("Basic").unauthenticated()
      }
    }

    override protected def registerAuthStrategies = {
      scentry.register("Basic", new OurBasicAuthStrategy(_, realm))
    }

  }
}

class BasicAuthExample extends ScalatraServlet with AuthenticationSupport {
  get("/?") {
    basicAuth
    val nodes = Seq(
      <h1>Hello from Scalatra</h1>,
      <p><a href="/auth/linked" >click</a></p>
    )

    Template.page("Basic Auth Example", nodes, url(_))
  }

  get("/linked") {
    basicAuth
    val nodes = Seq(
      <h1>Hello from Scalatra</h1>,
      <p><a href="/" >back</a></p>
    )

    Template.page("Basic Auth Example", nodes, url(_))
  }
}