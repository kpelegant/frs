package com.kp.entity.trans;

import com.kp.entity.db.User;

/**
 * 登录信息命令
 * @author kangping
 *
 */
public class Login extends Apply {

	private static final long serialVersionUID = 1L;
	private String user;//账号
	private String password;//密码
	private User u;//账号信息
	
	public Login(){
		super(LOGIN);
	}
	
	public Login(String user, String password) {
		super(Apply.LOGIN);
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setU(User u) {
		this.u = u;
	}
	
	public User getU() {
		return u;
	}
}
