package com.weber;

import java.sql.Timestamp;

public class TimeOff {
	private Timestamp startTime;
	private Timestamp endTime;
	private int length;
	private String guard;
	private boolean approved;
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public TimeOff(Timestamp startTime, Timestamp endTime, int length,
			String guard, boolean approved, String email) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.length = length;
		this.guard = guard;
		this.approved = approved;
		this.email=email;
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
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	
	
}
