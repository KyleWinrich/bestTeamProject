<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 3/21/2016
  Time: 1:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form action="helloworld/send" method="post">
    Enter Name:<input type="text" name="name"/><br/><br/>
    Enter Recipient Email:<input type="text" name="email"/><br/><br/>
    Enter Subject Line:<input type="text" name="subject"/><br/><br/>
    Enter Message:<input type="text" name="userMessage"/><br/><br/>
    <input type="submit" value="Send Email"/>
  </form>
  </body>
</html>
