package com.kp.server.controller.task;

import com.kp.entity.db.User;
import com.kp.entity.trans.ModifyUser;
import com.kp.server.service.UserService;
import com.kp.util.file.IOUtil;

/**
 * 修改用户信息
 * @author Mr Wang
 *
 */
public class ModifyUserController extends CSTransmissionBasic {

	private ModifyUser mu;
	
	public ModifyUserController(CSTransmissionBasic basic) {
		super(basic);
		this.mu = (ModifyUser) apply;
	}

	public void run(){
		try {
			setStart();
			try {
				User u = null;
				if(mu.getOldPassword() != null && mu.getOldPassword().length() > 0
					&& mu.getNewPassword() != null && mu.getNewPassword().length() > 0){
					u = UserService.modifyPassword(mu.getUsername(), mu.getOldPassword(), mu.getNewPassword());
					mu.setUser(u);
					mu.setNormal(true);
				}else{
					mu.setNormal(false);
					mu.setErrorMsg("无法识别操作");
				}
			} catch (Exception e) {
				e.printStackTrace();
				mu.setNormal(false);
				mu.setErrorMsg(e.getMessage());
			}
			out.writeObject(mu);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			IOUtil.close(socket, in, out);
			setStop();
		}
	}
}
