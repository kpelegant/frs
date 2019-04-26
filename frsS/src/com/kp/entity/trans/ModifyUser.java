package com.kp.entity.trans;

import com.kp.entity.db.User;

/**
 * 修改用户信息命令
 * @author Mr Wang
 *
 */
public class ModifyUser extends Apply {

	private static final long serialVersionUID = 1L;
	
	//private String nick;//昵称
	private String username;
	private String oldPassword;//旧密码
	private String newPassword;//新密码
	
	private User u;

	public ModifyUser(){
		super(MODIFYUSER);
	}
	
	public ModifyUser(String usernamme, String oldPassword, String newPassword) {
		super(MODIFYUSER);
		this.username =username;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public User getUser() {
		return u;
	}
	
	public void setUser(User u) {
		this.u = u;
	}
}
