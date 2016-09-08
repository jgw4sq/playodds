package com.weber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class User implements Comparable {
private String name;
private String position;
private String pool;
private String email;
private ArrayList<Shift> shifts;
private ArrayList<TimeOff> approvedtimeoff;
private ArrayList<TimeOff> notapprovedtimeoff;
private int managerMinHours;
private int hours;


public int getHours() {
	return hours;
}

public void setHours(int hours) {
	this.hours = hours;
}

public int getManagerMinHours() {
	return managerMinHours;
}

public void setManagerMinHours(int managerMinHours) {
	this.managerMinHours = managerMinHours;
}

public User(int rank){
	this.rank=rank;
}

public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int compareTo(User user1) {
	// TODO Auto-generated method stub
	if(this.getRank()<user1.getRank()){
		return 1;
	}else if (user1.getRank()<this.getRank()){
		return -1;
	}else{
	return 0;
	}
}

private int age;
private int rank;
private boolean otherpools;
private String firstName;
private String lastName;
public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public User(String firstName,String lastName, String position, String pool, ArrayList<Shift> shifts,
		ArrayList<TimeOff> approvedtimeoff,ArrayList<TimeOff> notapprovedtimeoff, int age, int rank, boolean otherpools,String email,int managerMinHours) {
	super();
	this.firstName = firstName;
	this.lastName= lastName;
	this.position = position;
	this.pool = pool;
	this.shifts = shifts;
	this.approvedtimeoff = approvedtimeoff;
	this.notapprovedtimeoff = notapprovedtimeoff;
	this.age = age;
	this.rank = rank;
	this.otherpools = otherpools;
	this.email=email;
	this.managerMinHours=managerMinHours;
}
public ArrayList<TimeOff> getApprovedtimeoff() {
	return approvedtimeoff;
}
public void setApprovedtimeoff(ArrayList<TimeOff> approvedtimeoff) {
	this.approvedtimeoff = approvedtimeoff;
}
public ArrayList<TimeOff> getNotapprovedtimeoff() {
	return notapprovedtimeoff;
}
public void setNotapprovedtimeoff(ArrayList<TimeOff> notapprovedtimeoff) {
	this.notapprovedtimeoff = notapprovedtimeoff;
}
public User() {
	// TODO Auto-generated constructor stub
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPosition() {
	return position;
}
public void setPosition(String position) {
	this.position = position;
}
public String getPool() {
	return pool;
}
public void setPool(String pool) {
	this.pool = pool;
}
public ArrayList<Shift> getShifts() {
	return shifts;
}
public void setShifts(ArrayList<Shift> shifts) {
	this.shifts = shifts;
}

public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public int getRank() {
	return rank;
}
public void setRank(int rank) {
	this.rank = rank;
}
public boolean isOtherpools() {
	return otherpools;
}
public void setOtherpools(boolean otherpools) {
	this.otherpools = otherpools;
}
public boolean isAvailable(Shift shift){
	for(int i=0; i<this.getApprovedtimeoff().size();i++){
		if((this.getApprovedtimeoff().get(i).getEndTime().equals(shift.getEndTime()))||(this.getApprovedtimeoff().get(i).getStartTime().equals(shift.getStartTime()))||(this.getApprovedtimeoff().get(i).getStartTime().before(shift.getStartTime())&&this.getApprovedtimeoff().get(i).getEndTime().after(shift.getStartTime()))||(this.getApprovedtimeoff().get(i).getStartTime().before(shift.getEndTime())&&this.getApprovedtimeoff().get(i).getEndTime().after(shift.getEndTime()))){
			return false;

		}
	}
	for(int i=0; i<this.getShifts().size();i++){
		if((this.getShifts().get(i).getEndTime().equals(shift.getEndTime()))||(this.getShifts().get(i).getStartTime().equals(shift.getStartTime()))||(this.getShifts().get(i).getStartTime().before(shift.getStartTime())&&this.getShifts().get(i).getEndTime().after(shift.getStartTime()))||(this.getShifts().get(i).getStartTime().before(shift.getEndTime())&&this.getShifts().get(i).getEndTime().after(shift.getEndTime()))){
			return false;

		}
	}
	return true;

}
public void addShift(Shift shift){
	Statement stmt = null;
	//this.getShifts().add(shift);
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
		String sql = "UPDATE SHIFTS SET guard='"+this.getFirstName()+" "+this.getLastName()+"', email='"+this.getEmail()+"' WHERE id="+shift.getId()+";";
		stmt.executeUpdate(sql);
		con.close();
	}catch (Exception e){
		e.printStackTrace();
	}
	this.setHours(this.getHours()+shift.getLength());
}
public static Comparator<User> SortUserTimeRank = new Comparator<User>() {

	public int compare(User user1, User user2) {
		int hourDifference1= user1.getManagerMinHours()-user1.getHours();
		int hourDifference2= user2.getManagerMinHours()-user2.getHours();
if(hourDifference1>hourDifference2){
	return -1;
}
else if (hourDifference2>hourDifference1){
	return 1;
}
else{
		
		int rank1 = user1.getRank();
	   int rank2 = user2.getRank();

	   /*For ascending order*/
	   return rank1-rank2;

	   /*For descending order*/
	   //rollno2-rollno1;
  }}};

public static void main(String[] args){
	ArrayList<User> users = new ArrayList<User>();
	for(int i=10;i>0;i--){
		users.add(new User(i));
	}
	for(int i=0; i<users.size();i++){
		System.out.println(users.get(i).getRank());
	}
	Collections.sort(users, User.SortUserTimeRank);
	for(int i=0; i<users.size();i++){
		System.out.println(users.get(i).getRank());
	}
}

@Override
public int compareTo(Object arg0) {
	// TODO Auto-generated method stub
	return 0;
}


	
}
