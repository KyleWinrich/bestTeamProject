
Form input

1. its simple you input your Name, the recipients email, the subject of the email and the message body as text.

2. then you click send.

3. you will be sent to a confirm page if successful. It'll look like.

(Email sent successfully!
 Name: "name"
 Email: "email")
 
4. if unsuccessful
(Yeah... that didn't work. Something Broke. Have a nice day.)




URL email input
1. To use this method you'll need to use the URL formula below

tomcat-timothyjm.rhcloud.com/emailService/{email-required}?name={name-not required}&
message={message-not required}&subject{subject-not required}

2. The email is the recipients email and is obviously required to send it.

3. The name is for the success message only and not required for the email. if not entered it defaults to "No-Name".
Cannot have spaces or &/?.

4. the message is also not required, if not entered it defaults to "Message". To enter it via the URL you'll have to use
the + symbol instead of a space for your message.
    example

    "hello world from the email service"

    would be

    "hello+world+from+the+email+service"

5. the same goes for the subject line to add multiple words they need to be separated by a plus sign and not a space.
if nothing is entered for the subject it'll default to "Subject".
