package com.weber;

import java.sql.Timestamp;

public class Shift implements Comparable<Shift> {
	private Timestamp startTime;
	private Timestamp endTime;
	private String pool;
	private int length;
	private String guard;
	private String email;
	private String position;
	private int Id;
	private boolean managerRequired;
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isManagerRequired() {
		return managerRequired;
	}

	public void setManagerRequired(boolean managerRequired) {
		this.managerRequired = managerRequired;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Shift(Timestamp startTime, Timestamp endTime, String pool, int length, String guard,int id, String email, boolean managerRequired, String position){
		this.startTime= startTime;
		this.endTime= endTime;
		this.pool=pool;
		this.length=length;
		this.guard=guard;
		this.email=email;
		this.Id = id;
		this.managerRequired=managerRequired;
		
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getGuard() {
		return guard;
	}

	public void setGuard(String guard) {
		this.guard = guard;
	}

	@Override
	public int compareTo(Shift shift2) {
		if(this.managerRequired==true&&shift2.managerRequired==false){
			return -1;
		}
		else if(this.managerRequired==false&&shift2.managerRequired==true){
			return 1;
		}
		return 0;
	}
	
}
