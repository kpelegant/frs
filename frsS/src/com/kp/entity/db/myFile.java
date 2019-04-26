package com.kp.entity.db;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件模型
 * @author Mr Wang
 *
 */
public class myFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;//主键
	private int userId;//上传者，与用户id一致
	private String fileName;//当前文件或文件夹的名字
	private String storeName;//存储名字
	private Date uploadTime;//上传时间
	private Double size;//如果是文件，该文件的容量
	private byte[] spassword;//加密后的密钥
	
	
	/**
	 * File构造
	 * @param name
	 */
	
	public myFile(int userId, String fileName, String storeName, Date uploadTime, Double size, byte[] spassword){
		this.userId = userId;
		this.fileName = fileName;
		this.storeName = storeName;
		this.uploadTime = uploadTime;
		this.size = size;
		this.spassword = spassword;
	}
	
	public myFile(int id, int userId, String fileName, String storeName, Date uploadTime, Double size, byte[] spassword){
		this.id = id;
		this.userId = userId;
		this.fileName = fileName;
		this.storeName = storeName;
		this.uploadTime = uploadTime;
		this.size = size;
		this.spassword = spassword;
	}
	public byte[] getSpassword() {
		return spassword;
	}

	public void setSpassword(byte[] spassword) {
		this.spassword = spassword;
	}

	public myFile(){}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Double getSize() {
		return size;
	}

	public void setSize(Double size) {
		this.size = size;
	}
	
	
	
}