package com.weber;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class MySchedule
 */
@WebServlet("/MySchedule")
public class MySchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MySchedule() {
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
			Statement stmt = null;
			ArrayList<Shift> myshifts = new ArrayList<Shift>();

			try{
				String email = ((User)request.getSession().getAttribute("user")).getEmail();
				Context initContext = new InitialContext();
				 Context envContext  = (Context)initContext.lookup("java:/comp/env");
				 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
				 Connection con = ds.getConnection();
				stmt = con.createStatement();
				 String sql = "SELECT * FROM SHIFTS WHERE email='"+email+"';";
				 ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					int id = rs.getInt("id");
					String guard = rs.getString("guard");
					Timestamp startTime= rs.getTimestamp("startTime");
					Timestamp endTime = rs.getTimestamp("endTime");
					String poolshift = rs.getString("pool");
					int length = rs.getInt("length");
					boolean managerRequired = rs.getBoolean("managerRequired");
					String shiftPosition = rs.getString("position");

					myshifts.add(new Shift(startTime,endTime,poolshift,length,guard,id,email,managerRequired,shiftPosition));
				}
				
				
				request.setAttribute("shifts", myshifts);
				}catch(Exception e){
					e.printStackTrace();
				}
	        request.getRequestDispatcher("myschedule.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
