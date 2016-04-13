import com.monthlyEmail.MainEmail;
import org.apache.log4j.Logger;
import org.junit.Test;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
/**
 * Created by Student on 4/11/2016.
 */
public class MainEmailTest {

    private org.apache.log4j.Logger log = Logger.getLogger(this.getClass());
    MainEmail me = new MainEmail();
    private final String username = "timothyjm70@gmail.com";//
    private final String password = "98917tim";
    private final String fromEmailAddress = "timothyjm70@gmail.com";
    Properties properties = new Properties();


    @Test
    public void testCreateMessage() throws Exception {
        Session session = null;
        try {
            session = Session.getDefaultInstance(properties,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
        } catch (Exception e) {

        }
        Message msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress(fromEmailAddress));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("test@test.com",false));
        msg.setSubject("Hello");
        msg.setText("How are you " + "testName" + " " + 1);
        msg.setSentDate(new Date());

        assertEquals(msg.getLineCount(), me.createMessage(session, "testName", "test@test.com", "test", "tester").getLineCount());
    }

    @Test
    public void testLoadProperties() throws Exception {

        try {
            properties.load(this.getClass().getResourceAsStream(
                    "/email.properties"));
        } catch (IOException ioException) {
            log.error("IOException", ioException);
        } catch (Exception exception) {
            log.error("Some kind of exception - keep reading.", exception);
        }

        assertEquals(me.loadProperties(), properties);
    }

    @Test
    public void testParamEmail() {

    }


}