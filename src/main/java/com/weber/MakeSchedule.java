package com.weber;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class MakeSchedule
 */
@WebServlet("/MakeSchedule")
public class MakeSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeSchedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("makeschedule.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pool = ((User)request.getSession().getAttribute("user")).getPool();
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		Timestamp startDate = new Timestamp(0,0,0,0,0,0,0);
		startDate.setYear(Integer.parseInt(year)-1900);
		startDate.setMonth(Integer.parseInt(month));
		startDate.setDate(Integer.parseInt(day));
		
	
		String year2 = request.getParameter("year2");
		String month2 = request.getParameter("month2");
		String day2 = request.getParameter("day2");
		Timestamp endDate = new Timestamp(0,0,0,0,0,0,0);
		endDate.setYear(Integer.parseInt(year2)-1900);
		endDate.setMonth(Integer.parseInt(month2));
		endDate.setDate(Integer.parseInt(day2));
		System.out.println(startDate.toString());
		System.out.println(endDate.toString());
		makeScheduleBasedOnRank(pool,startDate, endDate);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	public static void makeScheduleBasedOnRank(String pool,Timestamp startDate, Timestamp endDate){
		ArrayList<Shift> shift = populateShifts(pool,startDate,endDate);
		Collections.sort(shift);
		ArrayList<User> guards = populateUsers(pool,startDate,endDate);
		Collections.sort(guards, User.SortUserTimeRank);
		for(int i=0; i<shift.size();i++){
			Collections.sort(guards, User.SortUserTimeRank);
			System.out.println("Checking Shift");
			if(shift.get(i).isManagerRequired()){
				for(int j=0; j<guards.size();j++){
					System.out.println("Checking Guard"+guards.get(j).getName());

					if(guards.get(j).isAvailable(shift.get(i))&&(guards.get(j).getPosition().equals("Manager")||guards.get(j).getPosition().equals("Assistant Manager")||guards.get(j).getPosition().equals("Head Guard"))){
						System.out.println("Changing Shift"+shift.get(i).getId());
						
						guards.get(j).addShift(shift.get(i));
						guards.get(j).getShifts().add(shift.get(i));
						shift.remove(i);
						i-=1;
						break;
					}
				}
			}else{
			for(int j=0; j<guards.size();j++){
				System.out.println("Checking Guard"+guards.get(j).getName());

				if(guards.get(j).isAvailable(shift.get(i))){
					System.out.println("Changing Shift"+shift.get(i).getId());
					guards.get(j).addShift(shift.get(i));
					guards.get(j).getShifts().add(shift.get(i));
					shift.remove(i);
					i-=1;
					break;

				}
			}
			}
		}
		
		
	}
	
	public static ArrayList<Shift> populateShifts(String pool,Timestamp startDate, Timestamp endDate){
		System.out.println(startDate.toString());
		System.out.println(endDate.toString());
		ArrayList<Shift> shifts = new ArrayList<Shift>();
		Statement stmt = null;
		try{
			Context initContext = new InitialContext();
			 Context envContext  = (Context)initContext.lookup("java:/comp/env");
			 DataSource ds = (DataSource)envContext.lookup("jdbc/MySQLDS");
			 Connection con = ds.getConnection();
			stmt = con.createStatement();
						String sql = "SELECT * FROM SHIFTS WHERE pool='"+pool+"' AND startTime >= '"+startDate.toString()+"' AND endTime<= '"+endDate.toString()+"';";
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()){
							int id = rs.getInt("id");
							String guard = rs.getString("guard");
							Timestamp startTime= rs.getTimestamp("startTime");
							Timestamp endTime = rs.getTimestamp("endTime");
							String poolshift = rs.getString("pool");
							int length = rs.getInt("length");
							String emailOfShift = rs.getString("email");
							boolean managerRequired = rs.getBoolean("managerRequired");
							shifts.add(new Shift(startTime,endTime,poolshift,length,guard,id,emailOfShift,managerRequired));

							
						}
						con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return shifts;
		
		
	}
	
	public static ArrayList<User> populateUsers(String pool,Timestamp startdate, Timestamp endDate){
		Statement stmt = null;
		ArrayList<User> poolEmployees = new ArrayList<User>();
		
		try{
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
					stmt = con.createStatement();
					/*
					 Connection con=DriverManager.getConnection(  
								"jdbc:mysql://127.0.0.1:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
								stmt = con.createStatement();
		*/
					String sql = "SELECT * FROM GUARDS WHERE mainPool='"+pool+"';";
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()){
						ArrayList<Shift> shifts = new ArrayList<Shift>();
						ArrayList<TimeOff> approvedtimesoff = new ArrayList<TimeOff>();
						ArrayList<TimeOff> notapprovedtimesoff = new ArrayList<TimeOff>();

						String name = rs.getString("name");
						String email = rs.getString("email");
						System.out.println(email);
						int age = rs.getInt("age");
						String mainPool = rs.getString("mainPool");
						boolean otherPools = rs.getBoolean("otherPools");
						int rank = rs.getInt("rank");
						int managerMinHours= rs.getInt("managerMinHours");
						String position = rs.getString("position");
						Statement stmt2 = con.createStatement();
						sql = "SELECT * FROM SHIFTS WHERE email='"+email+"';";
						ResultSet rs2 = stmt2.executeQuery(sql);
						while(rs2.next()){
							String guard = rs2.getString("guard");
							int id = rs2.getInt("id");
							Timestamp startTime= rs2.getTimestamp("startTime");
							Timestamp endTime = rs2.getTimestamp("endTime");
							String poolshift = rs2.getString("pool");
							int length = rs2.getInt("length");
							String emailOfShift = rs2.getString("email");
							boolean managerRequired = rs2.getBoolean("managerRequired");
							shifts.add(new Shift(startTime,endTime,poolshift,length,guard,id,emailOfShift,managerRequired));
						}
						stmt = con.createStatement();
						 sql = "SELECT * FROM TIMEOFF WHERE email='"+email+"';";
						 ResultSet rs3 = stmt.executeQuery(sql);
						while (rs3.next()){
							String guard = rs3.getString("guard");
							Timestamp startTime2 = rs3.getTimestamp("startTime");
							Timestamp endTime2 = rs3.getTimestamp("endTime");
							boolean approved = rs3.getBoolean("approved");
							String reason = rs3.getString("reason");
							int id= rs3.getInt("id");
							if(approved==true){
								approvedtimesoff.add(new TimeOff(startTime2,endTime2,guard,true,email,pool,id,reason));
							}
							else{
								notapprovedtimesoff.add(new TimeOff(startTime2,endTime2,guard,false,email,pool,id,reason));
							}
						


					}
						User user = new User( name,  position,  mainPool, shifts,
								approvedtimesoff,notapprovedtimesoff,  age,  rank, otherPools,email,managerMinHours);
						
						poolEmployees.add(user);
						System.out.println("Added employee"+user.getName());
					
		}
					
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return poolEmployees;
		
	}
	public static ArrayList<User> populateUsers(String pool){
		Statement stmt = null;
		ArrayList<User> poolEmployees = new ArrayList<User>();
		
		try{
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
					stmt = con.createStatement();
					/*
					 Connection con=DriverManager.getConnection(  
								"jdbc:mysql://127.0.0.1:3306/jake","adminnHxi4B8","fWUk7PSKVlcV"); 
								stmt = con.createStatement();
		*/
					String sql = "SELECT * FROM GUARDS WHERE mainPool='"+pool+"';";
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()){
						ArrayList<Shift> shifts = new ArrayList<Shift>();
						ArrayList<TimeOff> approvedtimesoff = new ArrayList<TimeOff>();
						ArrayList<TimeOff> notapprovedtimesoff = new ArrayList<TimeOff>();

						String name = rs.getString("name");
						String email = rs.getString("email");
						System.out.println(email);
						int age = rs.getInt("age");
						String mainPool = rs.getString("mainPool");
						boolean otherPools = rs.getBoolean("otherPools");
						int rank = rs.getInt("rank");
						int managerMinHours= rs.getInt("managerMinHours");
						String position = rs.getString("position");
						Statement stmt2 = con.createStatement();
						sql = "SELECT * FROM SHIFTS WHERE email='"+email+"';";
						ResultSet rs2 = stmt2.executeQuery(sql);
						while(rs2.next()){
							String guard = rs2.getString("guard");
							int id = rs2.getInt("id");
							Timestamp startTime= rs2.getTimestamp("startTime");
							Timestamp endTime = rs2.getTimestamp("endTime");
							String poolshift = rs2.getString("pool");
							int length = rs2.getInt("length");
							String emailOfShift = rs2.getString("email");
							boolean managerRequired = rs2.getBoolean("managerRequired");
							shifts.add(new Shift(startTime,endTime,poolshift,length,guard,id,emailOfShift,managerRequired));
						}
						stmt = con.createStatement();
						 sql = "SELECT * FROM TIMEOFF WHERE email='"+email+"';";
						 ResultSet rs3 = stmt.executeQuery(sql);
						while (rs3.next()){
							String guard = rs3.getString("guard");
							Timestamp startTime2 = rs3.getTimestamp("startTime");
							Timestamp endTime2 = rs3.getTimestamp("endTime");
							boolean approved = rs3.getBoolean("approved");
							String reason = rs3.getString("reason");
							int id= rs3.getInt("id");
							if(approved==true){
								approvedtimesoff.add(new TimeOff(startTime2,endTime2,guard,true,email,pool,id,reason));
							}
							else{
								notapprovedtimesoff.add(new TimeOff(startTime2,endTime2,guard,false,email,pool,id,reason));
							}
						


					}
						User user = new User( name,  position,  mainPool, shifts,
								approvedtimesoff,notapprovedtimesoff,  age,  rank, otherPools,email,managerMinHours);
						
						poolEmployees.add(user);
						System.out.println("Added employee"+user.getName());
					
		}
					
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return poolEmployees;
		
	}
	
	
	

}
