package com.weber;

import java.util.ArrayList;

public class User {
private String name;
private String position;
private String pool;
private ArrayList<Shift> shifts;
private ArrayList<TimeOff> approvedtimeoff;
private ArrayList<TimeOff> notapprovedtimeoff;

private int age;
private int rank;
private boolean otherpools;
public User(String name, String position, String pool, ArrayList<Shift> shifts,
		ArrayList<TimeOff> approvedtimeoff,ArrayList<TimeOff> notapprovedtimeoff, int age, int rank, boolean otherpools) {
	super();
	this.name = name;
	this.position = position;
	this.pool = pool;
	this.shifts = shifts;
	this.approvedtimeoff = approvedtimeoff;
	this.notapprovedtimeoff = notapprovedtimeoff;
	this.age = age;
	this.rank = rank;
	this.otherpools = otherpools;
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


	
}
