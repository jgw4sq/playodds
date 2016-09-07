package com.weber;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class SignUp
 */
@WebServlet("/ManagerSignUp")
public class ManagerSignUp extends HttpServlet {
	private String managercode = "manageR";
	private String assmanagercode = "assistmanageR";
	private String headcode = "headguarD";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerSignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	

        request.getRequestDispatcher("registration.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Statement stmt =null;
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		int age = Integer.parseInt(request.getParameter("age"));
		String company = request.getParameter("company");
		String position = "Manager";
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String positioncode = request.getParameter("positioncode");
		System.out.println(company);
		System.out.println(position);
		System.out.println(positioncode);
		System.out.println(firstName);
		System.out.println(lastName);

		int passwords= Login.hash(password);
		try{
			Context initContext = new InitialContext();
			 Context envContext  = (Context)initContext.lookup("java:/comp/env");
			 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
			 Connection con = ds.getConnection();
			stmt = con.createStatement();
			/**
		Class.forName("com.mysql.jdbc.Driver");  
		  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
					stmt = con.createStatement();
					/*
					 Connection con=DriverManager.getConnection(  
								"jdbc:mysql://127.0.0.1:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
								stmt = con.createStatement();
		*/
					String sql ="SELECT COUNT(email) AS user FROM GUARDS WHERE email='"+email+"'";
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next()){
					int count = rs.getInt("user");
					if (count<1){
						System.out.println("Inserting into guards table");

					 sql = "INSERT INTO GUARDS VALUES ('"+firstName+"','"+lastName+"','"+age+", '"+company+"', "+true+","+Integer.parseInt("1")+",'"+position+"', '"+email+"',"+passwords+",0);";
					stmt.executeUpdate(sql);
			        request.getRequestDispatcher("index.jsp").forward(request, response);
					}else{
						response.getWriter().append("This email already has an account. To try again please press the back button.");

					}}
					con.close();
			}catch(Exception e){
			e.printStackTrace();
		}
		
		}
		
	

}
