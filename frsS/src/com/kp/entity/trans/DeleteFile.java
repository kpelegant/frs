package com.kp.entity.trans;

/**
 * 删除文件命令
 * @author Mr Wang
 *
 */
public class DeleteFile extends Apply {

	private static final long serialVersionUID = 1L;
	
	private String filename;//删除文件路径
	private int userid;

	public DeleteFile(){
		super(DELETEFILE);
	}
	
	public DeleteFile(String filename) {
		super(DELETEFILE);
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
}
