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
 * Servlet implementation class EditShift
 */
@WebServlet("/EditShift")
public class EditShift extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditShift() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int id = Integer.parseInt(request.getParameter("id"));
		Statement stmt =null;
		String guard = null;
		Timestamp startTime= null;
		Timestamp endTime = null;
		String poolshift = null;
		try{
			Context initContext = new InitialContext();
			 Context envContext  = (Context)initContext.lookup("java:/comp/env");
			 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
			 Connection con = ds.getConnection();
			stmt = con.createStatement();
			String sql= "SELECT * FROM SHIFTS WHERE id="+id+";";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				 id = rs.getInt("id");
				 guard = rs.getString("guard");
				 startTime= rs.getTimestamp("startTime");
				 endTime = rs.getTimestamp("endTime");
				 poolshift = rs.getString("pool");
				int length = rs.getInt("length");
				String emailOfShift = rs.getString("email");
				boolean managerRequired = rs.getBoolean("managerRequired");
				String shiftPosition = rs.getString("position");
				request.setAttribute("editShift",new Shift(startTime,endTime,poolshift,length,guard,id,emailOfShift,managerRequired,shiftPosition));
			}
			ArrayList<User> availableUsers = MakeSchedule.populateUsers(poolshift, startTime, endTime);
			for(int i=0; i<availableUsers.size(); i++){
				if(!availableUsers.get(i).isAvailable((Shift)request.getAttribute("editShift"))){
					availableUsers.remove(i);
					i-=1;
				}
			}
			request.setAttribute("avaiableUsers", availableUsers);
			request.getRequestDispatcher("editShift.jsp").forward(request, response);
			stmt.close();
			con.close();
		}catch(Exception e){
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
