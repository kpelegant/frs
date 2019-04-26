package com.kp.server.other;

import com.kp.config.Config;
import com.kp.entity.port.Port;

/**
 * 端口池
 * @author ping
 *
 */
public class PortPool {
	
	public static int NOPORT = -1;//表示没有申请到端口

	//所有的端口
	private Port[] ports;
	
	//下一个可判断申请的端口
	private int nextPort;
	
	//端口最小值和最大值
	private int min;
	private int max;
	
	public PortPool(){
		this.nextPort = 0;
		this.min = Config.minPort;
		this.max = Config.maxPort;
		init();
	}

	/**
	 * 初始化端口
	 */
	private void init(){
		ports = new Port[max - min + 1];
		for(int i=0; i<ports.length; i++){
			ports[i] = new Port(i+min);
		}
	}
	
	/**
	 * 申请端口
	 * @return -1表示已经没有端口
	 */
	public synchronized int apply() {
		for(int i=nextPort, j=min; j<=max; i=(++i)%ports.length, j++){
			nextPort = (i + 1)%ports.length;
			if(!ports[i].isUsed()){
				ports[i].setUsed(true);
				return ports[i].getPortNum();
			}
		}
		return NOPORT;
	}
	
	/**
	 * 释放端口
	 * @param port
	 */
	public void releasePort(int portNum){
		ports[portNum-min].setUsed(false);
	}
}
