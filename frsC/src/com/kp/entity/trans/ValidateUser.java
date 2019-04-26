package com.kp.entity.trans;

/**
 * 验证账号命令
 * @author Mr Wang
 *
 */
public class ValidateUser extends Apply {

	private static final long serialVersionUID = 1L;
	
	private String user;//待验证的账号
	private boolean using;//是否已经使用

	public ValidateUser(String user) {
		super(Apply.VALIDATEUSER);
		this.user = user;
	}

	public String getUser(){
		return this.user;
	}
	
	public void setUsing(boolean using) {
		this.using = using;
	}
	
	public boolean isUsing() {
		return using;
	}
}
