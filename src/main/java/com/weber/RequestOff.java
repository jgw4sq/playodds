package com.weber;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestOff
 */
@WebServlet("/RequestOff")
public class RequestOff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestOff() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Statement stmt =null;
		

	
			request.setAttribute("approvedtimesoff", Login.USER.getApprovedtimeoff());
			request.setAttribute("notapprovedtimesoff", Login.USER.getNotapprovedtimeoff());

	        request.getRequestDispatcher("/WEB-INF/requestoff.jsp").forward(request, response);

			

		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		try{
			 stmt =null;

			 Connection con=DriverManager.getConnection(  
						"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
						stmt = con.createStatement();
			
				
				request.setAttribute("approvedtimesoff", Login.USER.getApprovedtimeoff());
				request.setAttribute("notapprovedtimesoff", Login.USER.getNotapprovedtimeoff());

		 String sql = "INSERT INTO TIMEOFF VALUES ('Jake Weber', '"+startTime+"', '"+endTime+"', 'Cory Baldwin', false);";
		int rs2 = stmt.executeUpdate(sql);
        request.getRequestDispatcher("/WEB-INF/requestoff.jsp").forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
		}
		}
	}



