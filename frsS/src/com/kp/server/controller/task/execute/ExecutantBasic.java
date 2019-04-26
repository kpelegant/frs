package com.kp.server.controller.task.execute;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 执行线程基础
 * @author ping
 *
 */
public class ExecutantBasic extends Thread {
	
	//数据交互组件
	protected Socket socket;	
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	
	//当前线程任务是否成功完成
	protected boolean success = false;
	
	//是否停止当前线程
	protected boolean stop = false;

	public boolean isSuccess() {
		return success&&!stop;
	}

	public void setSuccess(boolean isSuccess) {
		this.success = isSuccess;
	}
	
	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
