package com.smartsense.upload

import org.scalatra._
import scalate.ScalateSupport

class UploadServlet extends UploadtestappStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">Yo man !! Wassup !!</a>.
      </body>
    </html>
  }

}
