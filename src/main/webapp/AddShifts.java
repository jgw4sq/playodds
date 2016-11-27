package com.weber;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class AddShifts
 */
@WebServlet("/AddShifts")
public class AddShifts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddShifts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(((User)request.getSession().getAttribute("user"))==null){
			response.sendRedirect(request.getContextPath()+"/Login");

		}else{
        request.getRequestDispatcher("addshifts.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int numberofEmployees = Integer.parseInt(request.getParameter("#ofEmployees"));
		String pool = ((User)request.getSession().getAttribute("user")).getPool();
		String shiftPosition = request.getParameter("shiftPosition");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String hour = request.getParameter("hour");
		String minute = request.getParameter("minutes");
		String hoursminutes = hour+":"+minute;
		String ampm = request.getParameter("ampm");
		String reason = request.getParameter("reason");
		String repeat = request.getParameter("repeat");
		if(ampm.equals("pm")){
			String hours = hoursminutes.substring(0,2);
			int inthours = Integer.parseInt(hours);
			inthours+=12;
			hoursminutes= String.valueOf(inthours)+hoursminutes.substring(2,hoursminutes.length());
			System.out.println(hoursminutes);
		}
		String year2 = request.getParameter("year2");
		String month2 = request.getParameter("month2");
		String day2 = request.getParameter("day2");
		String hour2 = request.getParameter("hour2");
		String minute2 = request.getParameter("minutes2");
		String hoursminutes2 = hour2+":"+minute2;
		String ampm2 = request.getParameter("ampm2");

		if(ampm2.equals("pm")){
			String hours = hoursminutes2.substring(0,2);
			int inthours = Integer.parseInt(hours);
			inthours+=12;
			hoursminutes2= String.valueOf(inthours)+hoursminutes2.substring(2,hoursminutes2.length());
			System.out.println(hoursminutes);
		}
		String startTime = year+"-"+month+"-"+day+" "+hoursminutes+":00";
		String endTime = year2+"-"+month2+"-"+day2+" "+hoursminutes2+":00";
		Statement stmt= null;
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		Timestamp start = new Timestamp(timestamp.getTime());
		Timestamp end = new Timestamp(timestamp.getTime());
		start.setYear(Integer.parseInt(year)-1900);
		start.setMonth(Integer.parseInt(month));
		start.setDate(Integer.parseInt(day));
		start.setHours(Integer.parseInt(hoursminutes.substring(0,2)));
		start.setMinutes(Integer.parseInt(hoursminutes.substring(3, hoursminutes.length())));
		end.setYear(Integer.parseInt(year2)-1900);
		end.setMonth(Integer.parseInt(month2));
		end.setDate(Integer.parseInt(day2));
		end.setHours(Integer.parseInt(hoursminutes2.substring(0,2)));
		end.setMinutes(Integer.parseInt(hoursminutes2.substring(3, hoursminutes2.length())));
		
		int length = ((int) (end.getTime()-start.getTime()))/(1000*60*60);
		
		try{
			int numberofRepeat=1;
			if(repeat.equals("Yes")){
				numberofRepeat=52;
			}
			 stmt =null;
			 Context initContext = new InitialContext();
			 Context envContext  = (Context)initContext.lookup("java:/comp/env");
			 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
			 Connection con = ds.getConnection();
			stmt = con.createStatement();
						
		for(int i=0;i<numberofEmployees;i++){
			for(int j=0;j<numberofRepeat; j++){
		 String sql = "INSERT INTO SHIFTS  (startTime, endTime,pool,length,position)VALUES ('"+startTime+"', '"+endTime+"','"+pool+"',"+length+",'"+shiftPosition+"');";
		int rs2 = stmt.executeUpdate(sql);
		start.setDate(start.getDate()+7);
		end.setDate(end.getDate()+7);
		 startTime = (start.getYear()+1900)+"-"+(start.getMonth()+1)+"-"+start.getDate()+" "+start.getMinutes()+":00";
		 endTime = (end.getYear()+1900)+"-"+(end.getMonth()+1)+"-"+end.getDate()+" "+end.getMinutes()+":00";
		}}
        request.getRequestDispatcher("addshifts.jsp").forward(request, response);
        con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
