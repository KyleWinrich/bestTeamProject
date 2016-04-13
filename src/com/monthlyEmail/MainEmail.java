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


/**
 * Created by Student on 3/23/2016.
 */
@Path("/emailService")
public class MainEmail {
    private Properties props = loadProperties();
    private org.apache.log4j.Logger log = Logger.getLogger(this.getClass());
    private final String username = props.getProperty("email.username");//
    private final String password = props.getProperty("email.password");
    private final String fromEmailAddress = props.getProperty("email.username");

    /**
     * used to send emails via a form.
     *
     * @param name
     * @param email
     * @param subjectText
     * @param messageText
     * @return
     */
    @POST
    @Path("/send")
    public Response addUser(
            @FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("subject") String subjectText,
            @FormParam("userMessage") String messageText) {
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
                    .entity("Yeah... that didn't work. Something Broke. Have a nice day.<br>" + e.toString())
                    .build();
        }

        return Response.status(200)
                .entity("Email sent successfully!<br> Name: " + name + "<br>Email: " + email)
                .build();
    }

    /**
     *  Used to send emails via the url instead of a form param.
     *
     * @param email
     * @param name
     * @param message
     * @param subject
     * @return
     */
    @GET
    @Path("/{email}")
    public Response sendDefaultEmail(
            @PathParam("email") String email,
            @DefaultValue("No-Name")@QueryParam("name") String name,
            @DefaultValue("Message")@QueryParam("message") String message,
            @DefaultValue("subject")@QueryParam("subject") String subject) {
        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});

            log.info("name:" + name);
            //used to split the incoming query string to utilize the url
            message.replace("+", " ");
            subject.replace("+", " ");
            // -- Create a new message --
            Message msg = createMessage(session, name, email, message, subject);

            Transport.send(msg);
            log.info("Message sent.");
        } catch (MessagingException e){
            log.error("error cause: " + e);

            return Response.status(500)
                    .entity("Yeah... that didn't work. Something Broke. Have a nice day.<br>" + e.toString())
                    .build();
        }

        return Response.status(200)
                .entity("Email sent successfully!<br> Name: " + name + "<br>Email: " + email)
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
