package com.kp.client.controller.task;

import com.kp.entity.trans.DeleteFile;
import com.kp.util.file.IOUtil;

/**
 * 删除文件
 * @author Mr Wang
 *
 */
public class DeleteFileController extends CSTransmissionBasic {

	private String filename;
	private DeleteFile df;
	private int userid;
	private int reUserid;
	
	
	public DeleteFileController(ReController rc, String filename, int userid){
		super(rc);
		this.filename = filename;
		this.userid = userid;
	}
	
	public void run(){
		try {
			System.out.println("文件名"+filename);
			df = new DeleteFile(filename);
			super.connectServer(df);
			df = (DeleteFile) in.readObject();
			reUserid = df.getUserid();
			if(userid != reUserid){
				df.setNormal(false);
				df.setErrorMsg("不能删除其他用户上传文件！");
			}else{
				df.setNormal(true);
				System.out.println("c可以删除");
				out.writeObject(df);
				df = (DeleteFile) in.readObject();
				if(df.isNormal()) {
					df.setNormal(true);
					df.setErrorMsg("删除成功！");
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			df.setNormal(false);
			df.setErrorMsg("删除文件或文件夹异常");
		} finally{
			rc.run(df);
			IOUtil.close(socket, in, out);
		}
	}
}
