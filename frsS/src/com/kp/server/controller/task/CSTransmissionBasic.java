package com.kp.server.controller.task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.kp.entity.trans.Apply;
import com.kp.server.service.UserService;



/**
 * 客户端与服务端数据交互基础
 * @author ping
 *
 */
public class CSTransmissionBasic extends Thread {
	
	//数据交互组件
	protected ServerSocket serverSocket;
	protected Socket socket;	
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	
	protected Apply apply;
	
	protected int state;//0还未启动，1正在运行，2正在暂停，3已停止
	
	public CSTransmissionBasic() {
		super();
		state = 0;
	}

	public CSTransmissionBasic(CSTransmissionBasic basic) {
		super();
		this.socket = basic.socket;
		this.in = basic.in;
		this.out = basic.out;
		this.apply = basic.apply;
		state = 0;
	}
	
	/**
	 * 检测随机码
	 * @throws IOException
	 */
	/*protected void checkCode() throws Exception{
		try {
			if(apply instanceof Account){
				Account a = (Account) apply;
				String password = UserService.checkCode(a.getKey(), a.getCode());
				if(password != null){
					a.setCode(password);
					apply = a;
					return;
				}
			}
			throw new IOException("随机码错误");
		} catch (IOException e) {
			e.printStackTrace();
			apply.setNormal( false);
			apply.setErrorMsg("账号验证出现问题，请重新登陆");
			out.writeObject(apply);
			throw new IOException(e.getMessage());
		}
	}*/
	
	public Apply getApply() {
		return apply;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public ObjectInputStream getIn() {
		return in;
	}
	
	public ObjectOutputStream getOut() {
		return out;
	}
	
	public boolean isStart(){
		return state != 0;
	}
	
	public boolean isStop(){
		return state == 3;
	}
	
	/**
	 * 暂停
	 */
	@SuppressWarnings("deprecation")
	public void pause(){
		state = 2;
		suspend();
	}
	
	/**
	 * 继续
	 */
	@SuppressWarnings("deprecation")
	public void goon(){
		resume();
		state = 1;
	}
	
	public void setStop(){
		state = 3;
	}
	
	public void setStart(){
		state = 1;
	}
}
