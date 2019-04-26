package com.kp.client.controller.task;

import com.kp.entity.db.User;
import com.kp.entity.trans.Login;
import com.kp.util.file.IOUtil;
import com.kp.util.secret.AESUtil;
import com.kp.util.secret.RSAUtil;

/**
 * 登录控制器
 * @author Mr Wang
 *
 */
public class LoginController extends CSTransmissionBasic {
	
	private Login login;     //登录命令
	private String user;		//账号
	private String password;	//密码

	public LoginController(ReController rc, String user, String password){
		super(rc);
		this.user = user;
		this.password = password;
	}
	
	public void run(){
		try {
			login = new Login();
			super.connectServer(login);
			login = (Login) in.readObject();  //读取任务是否正常
			if(login.isNormal()){
				login.setUser(user);
				login.setPassword(password);
				out.writeObject(login);
				
				login = (Login) in.readObject();//包括服务器状态和用户信息
				if(login.isNormal()){
					//login.getU().setPassword(password);
					User u = login.getU();
					System.out.println("logincontrol(c):用户名："+u.getUsername());
					System.out.println("logincontrol(c):密码："+u.getPassword());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			login.setNormal(false);
			login.setErrorMsg("登录时出现异常");
		}finally{
			IOUtil.close(socket, in, out);
			rc.run(login);  //调用回调函数转向主页面
		}
	}
}
