package com.monthlyEmail;

import org.apache.log4j.Logger;
import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Student on 3/23/2016.
 */
@Path("/helloworld")
public class MainEmail {

    private org.apache.log4j.Logger log = Logger.getLogger(this.getClass());
    private final String username = "timothyjm70@gmail.com";//
    private final String password = "98917tim";

    // The Java method will process HTTP GET requests
    @POST
    @Path("/send")
    public Response addUser(
            @FormParam("id") int id,
            @FormParam("name") String name,
            @FormParam("email") String email) {

        // Get a Properties object

        Properties props = loadProperties();

        /*Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");*/

        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});

            // -- Create a new message --
            Message msg = new MimeMessage(session);

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("timothyjm70@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email,false));
            msg.setSubject("Hello");
            msg.setText("How are you " + name + " " + id);
            msg.setSentDate(new Date());
            Transport.send(msg);
            log.info("Message sent.");
        }catch (MessagingException e){
            log.error("error cause: " + e);

            return Response.status(500)
                    .entity("Yeah... that didn't work. Something Broke. Have a nice day.")
                    .build();
        }

        return Response.status(200)
                .entity("Email sent successfuly!<br> Id: "+id+"<br> Name: " + name + "<br>Email: " + email)
                .build();


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

    @Test
    public void testThat() {
        Properties yup = loadProperties();
    }
}
