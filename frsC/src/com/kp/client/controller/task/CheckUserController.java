package com.kp.client.controller.task;

import com.kp.entity.trans.ValidateUser;
import com.kp.util.file.IOUtil;

/**
 * 检测用户账号是否可用
 * @author Mr Wang
 *
 */
public class CheckUserController extends CSTransmissionBasic {
	
	private ValidateUser vu;
	private String user;//待检测账号

	public CheckUserController(ReController rc, String user){
		super(rc);
		this.user = user;
	}
	
	public void run(){
		try {
			vu = new ValidateUser(user);
			super.connectServer(vu);
			vu = (ValidateUser) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			rc.run(vu);
			IOUtil.close(socket, in, out);
		}
	}
}
