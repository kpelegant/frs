package com.kp.client.controller.task;

import com.kp.client.dao.skDao;
import com.kp.entity.db.mySK;
import com.kp.entity.trans.Register;
import com.kp.util.file.IOUtil;
import com.kp.util.kpabe.Kpabe;
import com.kp.util.kpabe.MK;
import com.kp.util.kpabe.PK;
import com.kp.util.kpabe.impl.KpabeImpl;
import com.kp.util.secret.AESUtil;
import com.kp.util.secret.RSAUtil;

import javafx.css.PseudoClass;

/**
 * 注册控制器
 * @author Mr Wang
 *
 */
public class RegisterController extends CSTransmissionBasic {
	
	private Register reg;
	private String user;//账号
	private String password;//密码
	private String policy;
	private MK mk;
	private PK pk;
	private byte[] sk;
	private int userid;
 
	public RegisterController(ReController rc, String user, String password, String policy){
		super(rc);
		this.user = user;
		this.password = password;
		this.policy = policy;
	}
	
	public void run(){
		try {
			reg = new Register();
			super.connectServer(reg);//发送命令
			reg = (Register) in.readObject();
			if(reg.isNormal()){
				reg.setUser(user);
				reg.setPassword(password);
				reg.setPolicy(policy);
				out.writeObject(reg);
				
				reg = (Register) in.readObject();
				mk = reg.getMk();
				pk = reg.getPk();
				userid = reg.getUserid();
				//私钥生成
				sk = KpabeImpl.SK2byte(Kpabe.keyGen(policy, mk, pk));
				System.out.println("sk"+sk);
				mySK mysk = new mySK(userid, sk);
				skDao skdao = new skDao();
				skdao.insert(mysk);
						
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rc.run(reg);
			IOUtil.close(socket, in, out);
		}
	}
}
