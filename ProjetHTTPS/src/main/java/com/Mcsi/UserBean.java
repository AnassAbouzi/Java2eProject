	package com.Mcsi;

public class UserBean {
	private int id;
	private String username;
	private String password;
	private String salt;
	private String role;
	
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

	public void setPassword(String hashedPassword) {
		this.password = hashedPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
