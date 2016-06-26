<!DOCTYPE html>
<%@ page import="java.util.ArrayList" %>
<%@ page import= "com.weber.TimeOff" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Approved Time Off</h3>
<%  
// retrieve your list from the request, with casting 
ArrayList<TimeOff> list = (ArrayList<TimeOff>) request.getAttribute("approvedtimesoff");

// print the information about every category of the list
for(TimeOff timeoff : list) {
	TimeOff timeoff1 = timeoff;
	%>
	<p>
	<% 
    out.print("Start Time: "+timeoff1.getStartTime().toString());
    out.print("    End Time: "+timeoff1.getEndTime().toString());
    %>
    <p><%
}
%>
<h3>Not Approved Time Requested Off</h3>
<%  
// retrieve your list from the request, with casting 
ArrayList<TimeOff> list2 = (ArrayList<TimeOff>) request.getAttribute("notapprovedtimesoff");

// print the information about every category of the list
for(TimeOff timeoff : list2) {
	TimeOff timeoff1 = timeoff;
	%>
	<p>
	<% 
    out.print("Start Time: "+timeoff1.getStartTime().toString());
    out.print("    End Time: "+timeoff1.getEndTime().toString());
    %>
    <p><%
}
%>





<h3>Request Off Time</h3>
	<form name="f2" method="post" action="RequestOff" id="f2">
		StartTime: <input type="text" name="year" placeholder="year"><input
			type="text" name="month" placeholder="month(01)"><input
			type="text" name="day" placeholder="day(01)"><input
			type="text" name="hour:minute" placeholder="hour:minute(10:30)"><select name="ampm">
			<option value="am">AM</option>
			<option value="pm">PM</option>
		</select><br> 
		EndTime: <input type="text" name="year2" placeholder="year"><input
			type="text" name="month2" placeholder="month(01)"><input
			type="text" name="day2" placeholder="day(01)"><input
			type="text" name="hour:minute2" placeholder="hour:minute(10:30)"><select name="ampm2">
			<option value="am" >AM</option>
			<option value="pm" >PM</option>
		</select><br> 
		<input type="submit" value="submit"> 
	</form>


</body>
</html>