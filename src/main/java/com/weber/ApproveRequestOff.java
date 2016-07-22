package com.weber;

import java.io.IOException;
import java.sql.Connection;
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
 * Servlet implementation class ApproveRequestOff
 */
@WebServlet("/ApproveRequestOff")
public class ApproveRequestOff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveRequestOff() {
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

		}
		
		Statement stmt = null;
		User user = (User) request.getSession().getAttribute("user");
		ArrayList<TimeOff> notapprovedtimesoff = new ArrayList<TimeOff>();

		try{  
			Context initContext = new InitialContext();
			 Context envContext  = (Context)initContext.lookup("java:/comp/env");
			 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
			 Connection con = ds.getConnection();
			stmt = con.createStatement();
			String sql = "SELECT * FROM TIMEOFF WHERE approved=false AND pool='"+user.getPool()+"';";
			ResultSet rs= stmt.executeQuery(sql);
			while(rs.next()){
				String guard=rs.getString("guard");
				int id= rs.getInt("id");
				Timestamp startTime=rs.getTimestamp("startTime");
				Timestamp endTime=rs.getTimestamp("endTime");
				String manager=rs.getString("manager");
				boolean approved=rs.getBoolean("approved");
				String email=rs.getString("email");
				String pool=rs.getString("pool");
				notapprovedtimesoff.add(new TimeOff(startTime,endTime,guard,approved,email,pool,id));
				

				
				
				
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		request.setAttribute("notapprovedtimesoff", notapprovedtimesoff);
        request.getRequestDispatcher("approverequestoff.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
