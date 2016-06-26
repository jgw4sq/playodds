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
 * Servlet implementation class HelloWorld
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static User USER = new User();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Statement stmt=null;
		try{  
			ArrayList<Shift> myshifts = new ArrayList<Shift>();
			ArrayList<TimeOff> approvedtimesoff = new ArrayList<TimeOff>();
			ArrayList<TimeOff> notapprovedtimesoff = new ArrayList<TimeOff>();
			Class.forName("com.mysql.jdbc.Driver");  
			  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test2","jake","weber"); 
			stmt = con.createStatement();
			String sql = "SELECT * FROM Shifts WHERE guard='Jake Weber';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String guard = rs.getString("guard");
				Timestamp startTime= rs.getTimestamp("startTime");
				Timestamp endTime = rs.getTimestamp("endTime");
				String pool = rs.getString("pool");
				int length = rs.getInt("length");
				myshifts.add(new Shift(startTime,endTime,pool,length,guard));
			}
			stmt = con.createStatement();
			 sql = "SELECT * FROM TIMEOFF WHERE guard='Jake Weber';";
			 rs = stmt.executeQuery(sql);
			while (rs.next()){
				String guard = rs.getString("guard");
				Timestamp startTime2 = rs.getTimestamp("startTime");
				Timestamp endTime2 = rs.getTimestamp("endTime");
				boolean approved = rs.getBoolean("approved");
				if(approved==true){
					approvedtimesoff.add(new TimeOff(startTime2,endTime2,0,guard,true));
				}
				else{
					notapprovedtimesoff.add(new TimeOff(startTime2,endTime2,0,guard,false));
				}
				
			}
			stmt = con.createStatement();
			 sql = "SELECT * FROM GUARDS WHERE name='Jake Weber';";
			 rs = stmt.executeQuery(sql);
			if(rs.next()){
				int age =rs.getInt("age");
				String pool=rs.getString("mainPool");
				boolean otherpools=rs.getBoolean("otherPools");
				int rank=rs.getInt("rank");
				String position =rs.getString("position");
				USER = new User( "Jake Weber",  position,  pool,  myshifts,
						 approvedtimesoff, notapprovedtimesoff,  age,  rank,  otherpools);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
        request.getRequestDispatcher("/WEB-INF/hompage.jsp").forward(request, response);

	}

}
