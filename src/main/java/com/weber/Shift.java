package com.weber;

import java.sql.Timestamp;

public class Shift implements Comparable<Shift> {
	private Timestamp startTime;
	private Timestamp endTime;
	private String pool;
	private int length;
	private String guard;
	
	public Shift(Timestamp startTime, Timestamp endTime, String pool, int length, String guard){
		this.startTime= startTime;
		this.endTime= endTime;
		this.pool=pool;
		this.length=length;
		this.guard=guard;
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
		if(this.startTime.before(shift2.startTime)){
			return -1;
		}
		else if(this.startTime.after(shift2.startTime)){
			return 1;
		}
		return 0;
	}
	
}
