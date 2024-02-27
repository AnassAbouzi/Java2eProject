package com.Mcsi;

public class Teacher extends UserBean{
	private String unit_name;
	public Teacher(String username, String password, String unit_name) {
		super(username, password);
		this.setRole("teacher");
		this.unit_name = unit_name;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
}
