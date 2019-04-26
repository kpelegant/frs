package com.kp.client.controller.task;

import com.kp.entity.trans.ModifyUser;
import com.kp.util.file.IOUtil;

/**
 * 修改用户信息
 * @author Mr Wang
 *
 */
public class ModifyUserController extends CSTransmissionBasic {

	private ModifyUser mu;
	//private String nick;//昵称
	private String username;//
	private String oldPassword;//老密码
	private String newPassword;//新密码
	
	/**
	 * 修改昵称
	 * @param rc
	 * @param key
	 * @param code
	 * @param nick
	 */
	public ModifyUserController(ReController rc){
		super(rc);
		this.oldPassword = null;
		this.newPassword = null;
	}
	
	/**
	 * 修改密码
	 * @param rc
	 * @param key
	 * @param code
	 * @param nick
	 */
	public ModifyUserController(ReController rc, String username, String oldPassword, String newPassword){
		super(rc);
		this.username = username;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	public void run(){
		try {
			mu = new ModifyUser(username, oldPassword, newPassword);
			super.connectServer(mu);
			mu = (ModifyUser) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			mu.setNormal(false);
			mu.setErrorMsg("修改用户信息异常");
		} finally{
			rc.run(mu);
			IOUtil.close(socket, in, out);
		}
	}
}
