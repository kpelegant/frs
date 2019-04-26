package com.kp.entity.trans;

/**
 * 申请端口命令
 * @author Mr Wang
 *
 */
public class ApplyPort extends Apply {

	private static final long serialVersionUID = 1L;

	private int port;//任务端口
	private int threadNum;//线程数量
	
	public ApplyPort(int type) {
		super(type);
	}

	public void setPort(int port){
		this.port = port;
	}
	
	public int getPort(){
		return this.port;
	}
	
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}
	
	public int getThreadNum() {
		return threadNum;
	}
}
