package com.Mcsi;

public class Student extends UserBean{
	private int points;
	public Student(String username, String password, int points) {
		super(username, password);
		this.setRole("student");
		this.points = points;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
}
