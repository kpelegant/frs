package com.kp.server.controller.task;

import com.kp.entity.db.User;
import com.kp.entity.trans.Login;
import com.kp.server.service.UserService;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;
import com.kp.util.secret.AESUtil;
import com.kp.util.secret.RSAUtil;

/**
 * 登录控制器
 * @author Mr Wang
 *
 */
public class LoginController extends CSTransmissionBasic {

	private Login login;
	
	public LoginController(CSTransmissionBasic basic){
		super(basic);
		this.login = (Login) apply;
	}
	
	public void run(){
		try {
			setStart();
			try{
				login.setNormal(true);
				out.writeObject(login);//返回服务是否正常
				login = (Login) in.readObject();
				User u = UserService.validateUser(login.getUser(),login.getPassword());
				login.setNormal(u!=null);//判断成功返回true
				login.setPassword(null);
				if(u == null){
					login.setErrorMsg("用户名或密码不正确");
				}else{
					System.out.println("logincontroller(s):服务器判断用户名密码成功");
					login.setU(u);
				}
			}catch(Exception e){
				e.printStackTrace();
				login.setNormal(false);
				login.setErrorMsg(e.getMessage());
			}
			out.writeObject(login);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			IOUtil.close(socket, in, out);
			setStop();
		}
	}
}
