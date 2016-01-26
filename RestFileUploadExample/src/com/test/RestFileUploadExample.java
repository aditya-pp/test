package com.test;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

import org.apache.log4j.Logger;

import com.sun.jersey.core.header.FormDataContentDisposition;
 
@Path("/files")
public class RestFileUploadExample {
  static Logger logger = Logger.getLogger(RestFileUploadExample.class);
  private static final String api_version = "1";
  private static final String SAVE_FOLDER = "/tmp/upload/";
   
  @Path("/version")
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String returnVersion() {
	  System.out.println("Calling version API");
    return "<p>Version: " + api_version + "</p>";
  }
   
  @POST
  @Path("/upload")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces("text/html")
  public Response uploadFile(
      @FormDataParam("file") InputStream fileInputString,
      @FormDataParam("file") FormDataContentDisposition fileInputDetails,
      @FormDataParam("comment") FormDataBodyPart commentDetails) {
	  
	System.out.println("Calling upload API");
	System.out.println(fileInputString);
	System.out.println(fileInputDetails.getParameters());
	System.out.println(commentDetails.toString());
	System.out.println(commentDetails.getEntity().toString());
	System.out.println(commentDetails.getName());
	System.out.println(commentDetails.getValue());
  
    String fileLocation = SAVE_FOLDER + fileInputDetails.getFileName();
    String status = null;
    NumberFormat myFormat = NumberFormat.getInstance();
    myFormat.setGroupingUsed(true);
     
    // Save the file
    try {
     OutputStream out = new FileOutputStream(new File(fileLocation));
     byte[] buffer = new byte[1024];
     int bytes = 0;
     long file_size = 0;
     while ((bytes = fileInputString.read(buffer)) != -1) {
      out.write(buffer, 0, bytes);
      file_size += bytes;
     }
     out.flush(); 
     out.close();
             
     logger.info(String.format("Inside uploadFile==> fileName: %s,  fileSize: %s",
          fileInputDetails.getFileName(), myFormat.format(file_size)));
             
     status = "File has been uploaded to:" + fileLocation
                 + ", size: " + myFormat.format(file_size) + " bytes";
    } catch (IOException ex) {
      logger.error("Unable to save file: "  + fileLocation);
      ex.printStackTrace();
    }
 
    return Response.status(200).entity(status).build();
  }
}
