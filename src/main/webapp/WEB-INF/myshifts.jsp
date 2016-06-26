<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
<%@ page import= "com.weber.Shift" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>My Shifts</h3>
<%  
// retrieve your list from the request, with casting 
ArrayList<Shift> list2 = (ArrayList<Shift>) request.getAttribute("myshifts");

// print the information about every category of the list
for(Shift shift : list2) {
	Shift shift1 = shift;
	%>
	<p>
	<% 
    out.print("Pool: "+shift1.getStartTime().toString());
    out.print("Start Time: "+shift1.getStartTime().toString());
    out.print("    End Time: "+shift1.getEndTime().toString());
    %>
    <p><%
}
%>

</body>
</html>