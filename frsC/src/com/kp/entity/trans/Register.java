package com.kp.entity.trans;

import com.kp.util.kpabe.MK;
import com.kp.util.kpabe.PK;

/**
 * 注册账号命令
 * @author Mr Wang
 *
 */
public class Register extends Apply {

	private static final long serialVersionUID = 1L;

	private String user;//账号
	private String password;//密码
	private String policy;
	private PK pk;
	private MK mk;
	private int userid;
	
	public Register(){
		super(Apply.REGISTER);
	}
	
	public Register(String user, String password, String policy) {
		super(Apply.REGISTER);
		this.user = user;
		this.password = password;
		this.policy = policy;
	}

	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public PK getPk() {
		return pk;
	}

	public void setPk(PK pk) {
		this.pk = pk;
	}

	public MK getMk() {
		return mk;
	}

	public void setMk(MK mk) {
		this.mk = mk;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
}