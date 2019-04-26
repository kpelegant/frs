package com.kp.client.controller.task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.kp.config.Config;


/**
 * 客户端与服务端数据交互基础
 * @author ping
 *
 */
public class CSTransmissionBasic extends Thread {
	
	//数据交互组件
	protected Socket socket;	
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	
	//用户信息
	/*protected String key;
	protected String code;//安全码
*/	
	protected ReController rc;//回调类

	public CSTransmissionBasic() {
		
	}
	
	public CSTransmissionBasic(ReController rc) {
		this.rc = rc;
	}
	
	/*public CSTransmissionBasic(String key, String code){
		this.key = key;
		this.code = code;
	}
	
	public CSTransmissionBasic(ReController rc, String key, String code){
		this.rc = rc;
		this.key = key;
		this.code = code;
	}
	
	/**
	 * 连接服务端并发送任务请求
	 * @param msg
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	protected void getServerTaskPort(Object msg) throws UnknownHostException, IOException{
		connectServer(msg);
		//task = (IApplyForTaskPort) in.readObject();
		//task.setTaskPort(taskPort);
	}
	
	/**
	 * 链接服务端，发送任务请求
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	protected void connectServer(Object msg) throws UnknownHostException, IOException{
		socket = new Socket(Config.ServerIP, Config.ServerPort);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		out.writeObject(msg);
	}
}
