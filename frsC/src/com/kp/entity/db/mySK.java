package com.kp.entity.db;

import java.io.Serializable;


public class mySK implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;//主键
	private int  userid;//用户id
	private byte[] sk;//私钥
	
	public mySK(int userid, byte[] sk) {
		this.userid = userid;
		this.sk = sk;
	}

	public mySK(int id, int userid, byte[] sk) {
		this.id = id;
		this.userid = userid;
		this.sk = sk;
	}
	public mySK(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public byte[] getSk() {
		return sk;
	}

	public void setSk(byte[] sk) {
		this.sk = sk;
	}
	
	
}
