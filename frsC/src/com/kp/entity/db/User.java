package com.kp.entity.db;

import java.io.Serializable;
import java.util.Date;




/**
 * 用户实例
 * @author kangping
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;//主键
	private String username;//账号,用户名
	private String password;//密码
	private String policy;//策略
	private int state;//账号状态
	private int role;//角色
	private Date regTime;//注册时间
	
	public User(){
		
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

	
	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
	
}
