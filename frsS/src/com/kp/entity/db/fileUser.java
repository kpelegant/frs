package com.kp.entity.db;

import java.io.Serializable;

public class fileUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private myFile myfile; 
	private String username;
	public fileUser(myFile myfile, String username) {
		super();
		this.myfile = myfile;
		this.username = username;
	}
	public fileUser(){}
	public myFile getFilelist() {
		return myfile;
	}
	public void setFilelist(myFile myfile) {
		this.myfile = myfile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
