package com.weber;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        request.getRequestDispatcher("/WEB-INF/addshifts.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pool = request.getParameter("pool");
		int numberofGuards = Integer.parseInt(request.getParameter("numberofguards"));
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String hoursminutes = request.getParameter("hour:minute");
		String ampm = request.getParameter("ampm");
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
		String hoursminutes2 = request.getParameter("hour:minute2");
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
			 stmt =null;

			 Connection con=DriverManager.getConnection(  
						"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
						stmt = con.createStatement();
						/*
						 Connection con=DriverManager.getConnection(  
									"jdbc:mysql://127.0.0.1:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
									stmt = con.createStatement();
			*/
		for(int i=0;i<numberofGuards;i++){

		 String sql = "INSERT INTO SHIFTS  (startTime, endTime,pool,length)VALUES ('"+startTime+"', '"+endTime+"','"+pool+"',"+length+");";
		int rs2 = stmt.executeUpdate(sql);
		}
        request.getRequestDispatcher("/WEB-INF/addshifts.jsp").forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
