package com.kp.server.controller.task;

import java.util.Date;

import com.kp.config.Config;
import com.kp.entity.db.User;
import com.kp.entity.trans.Register;
import com.kp.server.dao.userDao;
import com.kp.server.service.UserService;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;
import com.kp.util.kpabe.MK;
import com.kp.util.kpabe.PK;
import com.kp.util.secret.AESUtil;
import com.kp.util.secret.MD5Util;
import com.kp.util.secret.RSAUtil;


/**
 * 注册控制器
 * @author Mr Wang
 *
 */
public class RegisterController extends CSTransmissionBasic {

	private Register reg;
	public RegisterController(CSTransmissionBasic basic){
		super(basic);
		this.reg = (Register) apply;
	}
	
	public void run(){
		try {
			setStart();
			try{
				reg.setNormal(true);
				out.writeObject(reg); //返回任务状态正常
				reg = (Register) in.readObject();//读取账号信息
				Date d = new Date();
				User u = new User();
				u.setUsername(reg.getUser());
				u.setPassword(reg.getPassword());
				u.setPolicy(reg.getPolicy());
				u.setState(0);//正常
				u.setRole(1);//用户
				u.setRegTime(d);
				UserService.register(u);//注册，存入数据库

				reg.setPk(Config.pk);
				reg.setMk(Config.mk);
				reg.setUserid(new userDao().findUserByName(reg.getUser()).getId());
				System.out.println("注册返回id"+reg.getUserid());
				out.writeObject(reg); //返回pk,mk,userid
				
				reg.setNormal(true);
				reg.setPassword(null);
			}catch(Exception e){
				e.printStackTrace();
				reg.setNormal(false);
				reg.setErrorMsg(e.getMessage());
			}
			out.writeObject(reg);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//IOUtil.close(socket, in, out);
			//setStop();
		}
	}
}
