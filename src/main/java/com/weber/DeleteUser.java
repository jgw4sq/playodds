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
 * Servlet implementation class DeleteUser
 */
@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(((User)request.getSession().getAttribute("user"))==null){
			response.sendRedirect(request.getContextPath()+"/Login");

		}
		String name = request.getParameter("name");
		System.out.println(name);
		//name=name.replace("Edit ", "");
		String [] splitname = name.split(" ");
		String firstname = splitname[0];
		String lastname=null;
		if (splitname.length>1){
			lastname=splitname[1];
		}
		Statement stmt =null;
		try{
			Context initContext = new InitialContext();
			 Context envContext  = (Context)initContext.lookup("java:/comp/env");
			 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
			 Connection con = ds.getConnection();
			stmt = con.createStatement();
			String sql= "DELETE FROM GUARDS WHERE firstName='"+firstname+"' AND lastName='"+lastname+"';";
			int rs = stmt.executeUpdate(sql);
			stmt.close();
			con.close();
response.sendRedirect(request.getContextPath()+"/ManageUsers");	
	}catch (Exception e){
		//request.getRequestDispatcher("edituser.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/ManageUsers");	

	}

}
}
