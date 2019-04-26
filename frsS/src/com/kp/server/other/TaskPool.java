package com.kp.server.other;

import java.util.ArrayList;
import java.util.List;

import com.kp.config.Config;
import com.kp.server.controller.task.CSTransmissionBasic;
import com.kp.server.view.View;


/**
 * 上传、下载任务池
 * @author Mr Wang
 *
 */
public class TaskPool implements Runnable {

	private View view;
	private Thread thread;
	private long completeNum;
	
	private List<CSTransmissionBasic> list;//上传队列

	private List<CSTransmissionBasic> running;//正在运行的线程
	private List<CSTransmissionBasic> stoping;//已经停止的线程
	
	public TaskPool(View view){
		this.view = view;
		this.completeNum = 0;
		this.list = new ArrayList<CSTransmissionBasic>();
		this.running = new ArrayList<CSTransmissionBasic>();
		this.stoping = new ArrayList<CSTransmissionBasic>();
	}
	
	@Override
	public void run() {
		while(list.size() > 0 || running.size() > 0){
			synchronized (this) {
				//暂停正在运行的程序
				pauseRunning();
				sliceUp();
				clearStopThread();
			}
			//下载
			view.setTaskNum(list.size()+running.size(), running.size(), completeNum);
			try {
				Thread.sleep(Config.sliceUpTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 切片
	 * @param list
	 * @param nextNum
	 * @param totalNum
	 * @return 下一个切片线程
	 */
	private void sliceUp(){
		for(int i=0, j=0; i<list.size() && j<Config.runningTaskNum; i++){
			CSTransmissionBasic basic = list.get(i%list.size());
			if(!basic.isStart()){
				basic.start();
				running.add(basic);
				++j;
			}else if(basic.isStop()){
				stoping.add(basic);
				view.append(basic.getName()+" 加入停止计划 ");
			}else{
				basic.goon();
				running.add(basic);
				++j;
			}
		}
		for(CSTransmissionBasic basic : running){
			list.remove(basic);
		}
	}
	
	/**
	 * 暂停所有运行的线程
	 */
	private void pauseRunning(){
		for(CSTransmissionBasic basic : running){
			if(!basic.isStop()){
				basic.pause();
				list.add(basic);
			}else{
				completeNum++;
				view.append(basic.getName()+" 移除运行计划 ");
			}
		}
		running.clear();
	}

	public void addTask(CSTransmissionBasic basic){
		synchronized (this) {
			list.add(basic);
			view.append(basic.getName()+" 加入运行计划 ");//线程
			if(thread == null || !thread.isAlive()){
				thread = new Thread(this);
				thread.start();
			}
		}
	}
	
	/**
	 * 清理停止的线程
	 * @param stoping
	 * @param list
	 */
	private void clearStopThread(){
		for(CSTransmissionBasic basic : stoping){
			list.remove(basic);
			completeNum++;
			view.append(basic.getName()+" 移除运行计划 ");
		}
		stoping.clear();
	}
	
	public boolean isRunning(){
		return list.size() > 0 || running.size() > 0;
	}
	
	public void stopAllTask(){
		for(CSTransmissionBasic basic : list){
			basic.setStop();
		}
		for(CSTransmissionBasic basic : running){
			basic.setStop();
		}
	}
}
