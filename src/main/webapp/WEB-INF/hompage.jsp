<%@ page import="java.util.ArrayList" %>
<%@ page import= "com.weber.Login" %>
<html>
<h3>This is the Homepage</h3>

<a href = "<%=request.getContextPath()%>/Login">Logout </a>
<a href = "<%=request.getContextPath()%>/MyAccount">MyAccount </a>
<a href = "<%=request.getContextPath()%>/MySchedule">MySchedule </a>
<%if (Login.USER.getPosition().equals("Manager")){
	%>
	<a href = "<%=request.getContextPath()%>/AddShifts">Add Shifts </a>
	<a href = "<%=request.getContextPath()%>/ScheduleManager"> Schedule Manager </a>
	
	<%
}%>

</html>