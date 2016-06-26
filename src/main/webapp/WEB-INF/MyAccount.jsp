<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import= "com.weber.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<p>
<%  
User user = (User) request.getAttribute("user");
String name = user.getName();

String position = user.getPosition();
String pool = user.getPool();

int age = user.getAge();
boolean otherpools = user.isOtherpools();
out.println(name);
out.println(position);
out.println(age);
out.println(pool);
%>


</p>


</body>
</html>