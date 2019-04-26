package com.kp.entity.trans;

import com.kp.util.kpabe.PK;

/**
 * 下载文件命令
 * @author Mr Wang
 *
 */
public class DownloadFile extends ApplyPort {

	private static final long serialVersionUID = 1L;
	
	private String location;//文件位置
	private double size;//长度
	private double length;//密文长度
	private boolean file;//是否是文件
	//private boolean isShare;//是否是分享文件
	protected String username;//用户名字
	protected String filename;//文件名字
	protected int userid;//用户id
	protected byte[] spassword;//加密后的密钥
	protected PK pk;

	public DownloadFile(){
		super(Apply.DOWNLOADFILE);
	}
	
	public DownloadFile(String location) {
		super(Apply.DOWNLOADFILE);
		this.location = location;
	}
	
	public void setFile(boolean file) {
		this.file = file;
	}
	
	public boolean isFile() {
		return file;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public byte[] getSpassword() {
		return spassword;
	}

	public void setSpassword(byte[] spassword) {
		this.spassword = spassword;
	}

	public PK getPk() {
		return pk;
	}

	public void setPk(PK pk) {
		this.pk = pk;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
	
}
