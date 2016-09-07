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
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private String managercode = "manageR";
	private String assmanagercode = "assistmanageR";
	private String headcode = "headguarD";
	
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
	

        request.getRequestDispatcher("registration.jsp").forward(request, response);
	
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
		String position = request.getParameter("mySelect");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String positioncode = request.getParameter("positioncode");
		System.out.println(pool);
		System.out.println(position);
		System.out.println(positioncode);
		int passwords= Login.hash(password);
		if((position.equals("Manager")&&positioncode.equals(managercode))||(position.equals("Assistant Manager")&&positioncode.equals(assmanagercode))||(position.equals("Head Guard")&&positioncode.equals(headcode))||position.equals(("Guard"))){
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

					 sql = "INSERT INTO GUARDS VALUES ('"+name+"',"+age+", '"+pool+"', "+true+","+Integer.parseInt("1")+",'"+position+"', '"+email+"',"+passwords+");";
					stmt.executeUpdate(sql);
			        request.getRequestDispatcher("index.jsp").forward(request, response);
					}else{
						response.getWriter().append("This email already has an account. To try again please press the back button.");

					}}
					con.close();
			}catch(Exception e){
			e.printStackTrace();
		}
		
		}else{
			System.out.println("wrong position code");
			response.getWriter().append("You entered the wrong position code. Please press the back button to try again");
		}
		
	}

}
