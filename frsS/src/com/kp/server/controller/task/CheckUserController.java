package com.kp.server.controller.task;

import com.kp.entity.trans.ValidateUser;
import com.kp.server.service.UserService;
import com.kp.util.file.IOUtil;

/**
 * 检测账号可用性
 * @author Mr Wang
 *
 */
public class CheckUserController extends CSTransmissionBasic {
	
	private ValidateUser vu;//待验证账号信息

	public CheckUserController(CSTransmissionBasic basic){
		super(basic);
		this.vu = (ValidateUser) apply;
	}
	
	public void run(){
		try {
			setStart();
			try{
				boolean exist = UserService.exist(vu.getUser());
				vu.setUsing(exist);
			}catch(Exception e){
				vu.setNormal(false);
				vu.setErrorMsg("检测异常");
			}
			out.writeObject(vu);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(socket, in, out);
			setStop();
		}
	}
}
