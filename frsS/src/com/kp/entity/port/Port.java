package com.kp.entity.port;

/**
 * 端口
 * @author ping
 *
 */
public class Port {
	
	//端口号
	private int portNum;
	
	//是否已使用
	private boolean used;
	
	public Port(int portNum){
		this.portNum = portNum;
		this.used = false;
	}

	public int getPortNum() {
		return portNum;
	}

	public void setPortNum(int portNum) {
		this.portNum = portNum;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
