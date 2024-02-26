package com.Mcsi;

public class UserBean {
	private int id;
	private String username;
	private String password;
	private String role;
	private UnitBean unit;
	private int points;
	
	public UserBean(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Boolean validate() {
		int status = DataAccessObject.authenticate(this);
		if (status == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public UnitBean getUnit() {
		return unit;
	}

	public void setUnit(UnitBean unit) {
		this.unit = unit;
	}

}
