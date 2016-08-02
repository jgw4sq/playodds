package com.weber;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class EditUser
 */
@WebServlet("/EditUser")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("edit");
		name=name.replace("Edit ", "");
		Statement stmt =null;
		try{
			Context initContext = new InitialContext();
			 Context envContext  = (Context)initContext.lookup("java:/comp/env");
			 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
			 Connection con = ds.getConnection();
			stmt = con.createStatement();
			String sql= "SELECT * FROM GUARDS WHERE name='"+name+"';";
			ResultSet rs = stmt.executeQuery(sql);
			int rank=0;
			int managerMinHours=0;
			if(rs.next()){
				 rank = rs.getInt("rank");
				 managerMinHours = rs.getInt("managerMinHours");
			}
			request.setAttribute("rank", rank);
			request.setAttribute("managerMinHours", managerMinHours);
			request.setAttribute("name", name);
			stmt.close();
			con.close();
			request.getRequestDispatcher("edituser.jsp").forward(request, response);
			
		}catch (Exception e){
			e.printStackTrace();
		}
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int rank=Integer.parseInt(request.getParameter("rank"));
		int managerMinHours= Integer.parseInt(request.getParameter("managerMinHours"));
		Statement stmt=null;
		try{
			Context initContext = new InitialContext();
			 Context envContext  = (Context)initContext.lookup("java:/comp/env");
			 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
			 Connection con = ds.getConnection();
			stmt = con.createStatement();
			String sql= "UPDATE GUARDS SET rank="+rank+",managerMinHours="+managerMinHours+" WHERE name='"+name+"';";
			int rs = stmt.executeUpdate(sql);
			stmt.close();
			con.close();
			response.sendRedirect("ManageUsers");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
