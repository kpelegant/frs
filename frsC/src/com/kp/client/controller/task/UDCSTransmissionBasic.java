package com.kp.client.controller.task;

import com.kp.client.controller.task.execute.ExecutantBasic;

/**
 * 上传、下载基础
 * @author Mr Wang
 *
 */
public class UDCSTransmissionBasic extends CSTransmissionBasic {
	
	protected String src;//源文件
	protected String dst;//目的位置
	protected double transLength;//已传输长度
	protected double totalLength;//文件总长度
	
	
	protected ReUDController rc;
	
	protected ExecutantBasic[] execute;
	
	protected int pause = 1;//1正常，2暂停，0停止
	
	protected UDCSTransmissionBasic(ReUDController rc, String src, String dst){
		this.rc = rc;
		this.src = src;
		this.dst = dst;
		this.transLength = 0;
	}
	/**
	 * 报告数据长度
	 * @param byteread
	 */
	public synchronized void reBytes(int byteread) {
		transLength += byteread;
	}
	
	/**
	 * 是否开始传输
	 * @return
	 */
	public boolean isTrans(){
		return transLength > 0;
	}
	
	public double getTransLength() {
		return transLength;
	}
	
	public double getTotalLength() {
		return totalLength;
	}
	
	public boolean isPause(){
		return pause == 2;
	}
	
	public boolean isStop(){
		return pause == 0;
	}
	
	/**
	 * 暂停
	 */
	@SuppressWarnings("deprecation")
	public void pause(){
		pause = 2;
		suspend();
		if(execute != null){
			for(ExecutantBasic e : execute){
				e.suspend();
			}
		}
	}
	
	/**
	 * 继续
	 */
	@SuppressWarnings("deprecation")
	public void goon(){
		resume();
		if(execute != null){
			for(ExecutantBasic e : execute){
				e.resume();
			}
		}
		pause = 1;
	}
	
	/**
	 * 关闭
	 */
	@SuppressWarnings("deprecation")
	public void close(){
		pause = 0;
		if(execute != null){
			for(ExecutantBasic e : execute){
				e.setStop(true);
				e.resume();
			}
		}
		resume();
		if(!isAlive()){
			start();
		}
	}
}
