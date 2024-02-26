package com.Mcsi;


public class UnitBean {
	private int id;
	private String unitName;
	private UserBean prof;
	private int hours;
	
	public UnitBean(String unitName, UserBean prof, int hours) {
		this.unitName = unitName;
		this.prof = prof;
		this.hours = hours;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public UserBean getProf() {
		return prof;
	}

	public void setProf(UserBean prof) {
		this.prof = prof;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}
	
}
