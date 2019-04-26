package com.kp.entity.db;

import java.io.Serializable;

public class setUp implements Serializable{

	private static final long serialVersionUID = 1L;
	int id;
	byte[] pk;
	byte[] mk;
	public setUp(int id, byte[] pk, byte[] mk) {
		this.id = id;
		this.pk = pk;
		this.mk = mk;
	}
	public setUp(byte[] pk, byte[] mk) {
		this.pk = pk;
		this.mk = mk;
	}
	public setUp(){}
	public byte[] getPk() {
		return pk;
	}
	public void setPk(byte[] pk) {
		this.pk = pk;
	}
	public byte[] getMk() {
		return mk;
	}
	public void setMk(byte[] mk) {
		this.mk = mk;
	}
	
}
