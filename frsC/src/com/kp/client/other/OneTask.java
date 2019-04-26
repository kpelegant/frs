package com.kp.client.other;

import com.kp.client.controller.task.UDCSTransmissionBasic;
import com.kp.client.view.tstate.TaskItem;

/**
 * 一个上传或下载任务信息
 * @author Mr Wang
 *
 */
public class OneTask {

	private UDCSTransmissionBasic basic;	//任务控制器
	private TaskItem task;	//任务视图
	private String url;
	private String srcdst;//源文件目的地址
	
	public OneTask(){
		
	}
	
	public OneTask(UDCSTransmissionBasic basic, TaskItem task, String url, String srcdst){
		this.basic = basic;
		this.task = task;
		this.url = url;
		this.srcdst = srcdst;
	}

	
	public OneTask(UDCSTransmissionBasic basic, String url, String srcdst){
		this.basic = basic;
		this.url = url;
		this.srcdst = srcdst;
	}

	public UDCSTransmissionBasic getController() {
		return basic;
	}

	public void setBasic(UDCSTransmissionBasic basic) {
		this.basic = basic;
	}

	public TaskItem getTask() {
		return task;
	}

	public void setTask(TaskItem task) {
		this.task = task;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setSrcdst(String srcdst) {
		this.srcdst = srcdst;
	}
	
	public String getSrcdst() {
		return srcdst;
	}
}
