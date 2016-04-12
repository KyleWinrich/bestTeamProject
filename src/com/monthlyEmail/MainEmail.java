package com.monthlyEmail;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/*
    Crap we might need:
        -Fix fromEmailAddress so user can enter their own - still working on this
            -Maybe just tell user to Edit properties file?


    added
        -a method to accept variables via the url with only an email required

*/


/**
 * Created by Student on 3/23/2016.
 */
@Path("/helloworld")
public class MainEmail {

    private org.apache.log4j.Logger log = Logger.getLogger(this.getClass());
    private final String username = "madisonjavaee2016.noreply@gmail.com";//
    private final String password = "student2016";
    private final String fromEmailAddress = "madisonjavaee2016.noreply@gmail.com";

    // The Java method will process HTTP GET requests
    @POST
    @Path("/send")
    public Response addUser(
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("subject") String subjectText,
            @FormParam("userMessage") String messageText) {

        // Get a Properties object

        Properties props = loadProperties();


        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});

            // -- Create a new message --
            Message msg = createMessage(session, name, email, messageText, subjectText);

            Transport.send(msg);
            log.info("Message sent.");
        } catch (MessagingException e){
            log.error("error cause: " + e);

            return Response.status(500)
                    .entity("Yeah... that didn't work. Something Broke. Have a nice day.")
                    .build();
        }

        return Response.status(200)
                .entity("Email sent successfuly!<br> Name: " + name + "<br>Email: " + email)
                .build();
    }

    @GET
    @Path("/{email}")
    public Response sendDefaultEmail(
            @PathParam("email") String email,
            @DefaultValue("No-Name")@QueryParam("name") String name,
            @DefaultValue("Message")@QueryParam("message") String message,
            @DefaultValue("subject")@QueryParam("subject") String subject) {
        Properties props = loadProperties();
        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});
            log.info(props.getProperty("default.message"));
            log.info(props.getProperty("default.subject"));
            // -- Create a new message --
            Message msg = createMessage(session, name, email, message, subject);

            Transport.send(msg);
            log.info("Message sent.");
        } catch (MessagingException e){
            log.error("error cause: " + e);

            return Response.status(500)
                    .entity("Yeah... that didn't work. Something Broke. Have a nice day.")
                    .build();
        }

        return Response.status(200)
                .entity("Email sent successfuly!<br> Name: " + name + "<br>Email: " + email)
                .build();
    }




    public Message createMessage(Session session, String name, String email,
                                 String messageText, String subjectText)
                throws MessagingException {
        Message msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(fromEmailAddress));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email,false));
        msg.setSubject(subjectText);
        msg.setText(messageText);
        msg.setSentDate(new Date());

        return msg;
    }


    public Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(
                    "/email.properties"));
        } catch (IOException ioException) {
            log.error("IOException", ioException);
        } catch (Exception exception) {
            log.error("Some kind of exception - keep reading.", exception);
        }

        return properties;
    }
}
