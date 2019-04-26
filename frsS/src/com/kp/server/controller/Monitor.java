package com.kp.server.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.kp.config.Config;
import com.kp.entity.trans.Apply;
import com.kp.server.controller.task.CSTransmissionBasic;
import com.kp.server.view.View;
import com.kp.util.file.IOUtil;

/**
 * 监听器
 * @author Mr Wang
 *
 */
public class Monitor extends CSTransmissionBasic {

	private View view;
	private boolean running;//监听器是否正在运行
	
	public Monitor(View view){
		this.view = view;
		this.running = false;
	}
	
	public void run(){
		try {
			this.serverSocket = new ServerSocket(Config.serverPort);
			this.running = true;
			accept();//开始进行监听
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(serverSocket);
		}
	}

	/**
	 * 监听客户端申请
	 */
	private void accept() {
		while(running){
			try{
				socket = serverSocket.accept();
				if(!running){
					break;
				}
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				apply = (Apply) in.readObject();
				startTask();//启动任务
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 启动任务
	 */
	private void startTask() {
		//System.out.println("apply"+apply);
		if(apply.getType() == Apply.VALIDATEUSER){
			view.getUserController().checkUser(this);
		}else
			if(apply.getType() == Apply.REGISTER){
			view.getUserController().register(this);
		}else if(apply.getType() == Apply.LOGIN){
			view.getUserController().login(this);
		}else{
			if(apply.getType() == Apply.PRESENTFOLDER){
				//view.getFileController().presentFolder(this);
			}else if(apply.getType() == Apply.UPLOADFILE){
				view.getFileController().uploadFile(this);
			}else if(apply.getType() == Apply.DOWNLOADFILE){
				view.getFileController().downloadFile(this);
			}else if(apply.getType() == Apply.DELETEFILE){
				view.getFileController().deleteFile(this);
			}else if(apply.getType() == Apply.CREATEFOLDER){
				//view.getFileController().createFolder(this);
			}else if(apply.getType() == Apply.RENAMEFILE){
				//view.getFileController().renameFile(this);
			}else if(apply.getType() == Apply.ALLFILE){
				view.getFileController().showFiles(this);;
			}else if(apply.getType() == Apply.MOVEFILE){
				//view.getFileController().moveFile(this);
			}else if(apply.getType() == Apply.FRIEND){
				//view.getUserController().getFriends(this);
			}else if(apply.getType() == Apply.SEARCHUSER){
				//view.getUserController().searchUser(this);
			}else if(apply.getType() == Apply.ADDFRIEND){
				//view.getUserController().addFriend(this);
			}else if(apply.getType() == Apply.MODIFYREMARK){
				//view.getUserController().modifyRemark(this);
			}else if(apply.getType() == Apply.DELETEFRIEND){
				//view.getUserController().deleteFriend(this);
			}else if(apply.getType() == Apply.SHAREFILE){
				//view.getFileController().shareFile(this);
			}else if(apply.getType() == Apply.SHARE){
				//view.getFileController().getEachFile(this);
			}else if(apply.getType() == Apply.DELETESHARE){
				//view.getFileController().deleteShareFile(this);
			}else if(apply.getType() == Apply.SAVESHARE){
				//view.getFileController().saveShareFile(this);
			}else if(apply.getType() == Apply.SEARCHFILE){
				//view.getFileController().searchFile(this);
			}else if(apply.getType() == Apply.MODIFYUSER){
				view.getUserController().modifyUser(this);
			}
		}
	}

	/**
	 * 开启服务器
	 * @throws Exception 
	 */
	public boolean startServer() {
		start();
		//等待服务器开启成功或失败在返回
		while(!running && isAlive()){}
		return running;
	}
	
	/**
	 * 关闭服务端
	 * @return
	 * @throws SocketException 
	 */
	public boolean closeServer() {
		Socket socket = null;
		try {
			this.running = false;
			socket = new Socket("127.0.0.1", Config.serverPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(socket);
		}
		return true;
	}
	
	/**
	 * 判断是否在运行
	 * @return
	 */
	public boolean isRunning(){
		return this.running;
	}
}
