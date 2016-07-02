package com.weber;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Statement stmt =null;
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String pool = request.getParameter("poolSelect");
		String position = request.getParameter("position");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		int passwords= Login.hash(password);
		try{
		Class.forName("com.mysql.jdbc.Driver");  
		  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
					stmt = con.createStatement();
					String sql = "INSERT INTO GUARDS VALUES ('"+name+"',"+age+", '"+pool+"', "+true+","+Integer.parseInt("1")+",'"+position+"', '"+email+"',"+passwords+");";
					stmt.executeUpdate(sql);
			        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

			}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
