package com.kp.entity.trans;

import com.kp.util.kpabe.PK;
import com.kp.util.kpabe.SK;

/**
 * 上传文件命令
 * @author Mr Wang
 *
 */
public class UploadFile extends ApplyPort {

	private static final long serialVersionUID = 1L;

	private String username;
	private long block;
	private double size;//文件长度
	private double length; //加密后的文件长度
	private String filename;//存储位置
	private byte[] spassword;//加密密钥
	private PK pk = new PK(); //公钥
	private SK sk = new SK();//私钥
	
	public UploadFile(){
		super(Apply.UPLOADFILE);
	}
	
	public UploadFile(String username, String filename, double size, byte[] spassword) {
		super(Apply.UPLOADFILE);
		this.username = username;
		this.filename = filename;
		this.size = size;
		this.spassword = spassword;
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setBlock(long block) {
		this.block = block;
	}
	
	public long getBlock() {
		return block;
	}
	
	public double getSize() {
		return size;
	}
	
	public byte[] getSpassword() {
		return spassword;
	}
	
	public String getFilename() {
		return filename;
	}

	public PK getPk() {
		return pk;
	}

	public void setPk(PK pk) {
		this.pk = pk;
	}
	
	public SK getSk() {
		return sk;
	}

	public void setSk(SK sk) {
		this.sk = sk;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
	
	
}
