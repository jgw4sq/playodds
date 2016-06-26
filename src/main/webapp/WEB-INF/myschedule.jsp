<%@ page import="java.util.ArrayList" %>
<%@ page import= "com.weber.Shift" %>
<body>
<p>
<%  
// retrieve your list from the request, with casting 
ArrayList<Shift> list = (ArrayList<Shift>) request.getAttribute("shifts");

// print the information about every category of the list
for(Shift shift : list) {
	Shift shift1 = shift;
	%>
	<p>
	<% 
    out.println(shift.getPool());
    out.println(shift.getGuard());
    out.println(shift.getStartTime().toString());
    out.println(shift.getEndTime().toString());
    %>
    <p><%
}
%>


<a href = "/Testwebapp/ScheduledShifts">View my Scheduled Shifts</a>
<a href = "/Testwebapp/RequestOff">View/Enter Time Requested Off</a>
</body>