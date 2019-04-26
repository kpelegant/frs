package com.kp.client.other;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.kp.client.controller.task.CSTransmissionBasic;
import com.kp.client.view.View;
import com.kp.client.view.tstate.TaskItem;
import com.kp.client.view.tstate.Transmission;
import com.kp.util.view.ViewUtil;

/*import com.dicd.client.controller.task.CSTransmissionBasic;
import com.dicd.client.view.View;
import com.dicd.client.view.tstate.TaskItem;
import com.dicd.client.view.tstate.Transmission;
import com.dicd.entity.HistoryRecord;
import com.dicd.entity.ITaskBasicInfo.TaskStyle;
import com.dicd.util.view.ViewUtil;*/

/**
 * 上传和下载任务池
 * @author Mr Wang
 *
 */
public class TaskPool implements Runnable {
	
	//private List<HistoryRecord> historys;

	private List<OneTask> uploadList;	//上传队列
	private List<OneTask> uploadRunningList;	//上传运行队列
	
	private List<OneTask> downloadList;	//下载队列
	private List<OneTask> downloadRunningList;	//下载队列
	
	private int totalNum;	//上传和下载同一时间运行任务的数量
	private Date aheadDate;//记录前一次报告传输长度的时间
	
	private Thread thread;
	
	public TaskPool(){
		//this.historys = new ArrayList<HistoryRecord>();
		this.uploadList = new ArrayList<OneTask>();
		this.uploadRunningList = new ArrayList<OneTask>();
		this.downloadList = new ArrayList<OneTask>();
		this.downloadRunningList = new ArrayList<OneTask>();
		this.totalNum = 2;
		this.aheadDate = new Date();
	}
	
	public void run(){
		while(uploadList.size() > 0 || downloadList.size() > 0){
			Date now = new Date();
			if(now.getTime() - aheadDate.getTime() >= 1000){//隔一秒报告
				aheadDate = now;
				ViewUtil.asyncExec(new Runnable() {
					public void run() {
						synchronized (this) {
							for(OneTask o : uploadRunningList){
								if(o.getTask() != null && !o.getTask().isDisposed() && o.getController().isTrans() && !o.getController().isPause()){
									o.getTask().addCompleteByte(new BigDecimal(o.getController().getTransLength()),
											new BigDecimal(o.getController().getTotalLength()));
								}
							}
							for(OneTask o : downloadRunningList){
								if(o.getTask() != null && !o.getTask().isDisposed() && o.getController().isTrans() && !o.getController().isPause()){
									o.getTask().addCompleteByte(new BigDecimal(o.getController().getTransLength()),
											new BigDecimal(o.getController().getTotalLength()));
								}
							}
						}
					}
				});
				if(uploadRunningList.size() == 0 || downloadRunningList.size() == 0){//防止程序出现异常导致任务池不能继续运行
					startUploadRunning();
					startDownloadRunning();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 开始运行上传线程任务
	 */
	private synchronized void startUploadRunning(){
		for(int i=0; i<uploadList.size(); i++){
			if(uploadRunningList.size() >= totalNum) break;
			OneTask oneTask = uploadList.get(i);
			if(!oneTask.getController().isStop() && !oneTask.getController().isPause() && !oneTask.getController().isAlive()){
				oneTask.getController().start();
				uploadRunningList.add(oneTask);
			}
		}
		if(thread == null || !thread.isAlive()){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	/**
	 * 开始运行下载线程任务
	 */
	private synchronized void startDownloadRunning(){
		for(int i=0; i<downloadList.size(); i++){
			if(downloadRunningList.size() >= totalNum) break;
			OneTask oneTask = downloadList.get(i);
			if(!oneTask.getController().isStop() && !oneTask.getController().isPause() && !oneTask.getController().isAlive()){
				oneTask.getController().start();
				downloadRunningList.add(oneTask);
			}
		}
		if(thread == null || !thread.isAlive()){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public boolean hasRunning(){
		return uploadList.size() > 0 || downloadList.size() > 0;
	}
	
	public void addUploadTask(OneTask task){
		synchronized (this) {
			this.uploadList.add(task);
			startUploadRunning();
		}
	}
	
	public void addDownloadTask(OneTask task){
		synchronized (this) {
			this.downloadList.add(task);
			startDownloadRunning();
		}
	}
	
	public void showUploadList(Shell shell, Transmission trans) {
		synchronized (this) {
			for(OneTask task : uploadList){
				TaskItem taskItem = trans.addUploadTask(task.getUrl());
				taskItem.addListeners(ViewUtil.getTaskListener(shell, task.getController(), taskItem, task.getSrcdst().substring(0, task.getSrcdst().lastIndexOf("\\"))));
				if(task.getController().isTrans()){
					taskItem.addCompleteByte(new BigDecimal(task.getController().getTransLength()),
							new BigDecimal(task.getController().getTotalLength()));
					if(task.getController().isPause()){
						taskItem.showStartButton();
					}
				}
				task.setTask(taskItem);
			}
		}
	}
	
	public void showDownloadList(Shell shell, Transmission trans) {
		synchronized (this) {
			for(OneTask task : downloadList){
				TaskItem taskItem = trans.addDownloadTask(task.getUrl());
				taskItem.addListeners(ViewUtil.getTaskListener(shell, task.getController(), taskItem, task.getSrcdst().substring(0, task.getSrcdst().lastIndexOf("\\"))));
				if(task.getController().isTrans()){
					taskItem.addCompleteByte(new BigDecimal(task.getController().getTransLength()),
							new BigDecimal(task.getController().getTotalLength()));
					if(task.getController().isPause()){
						taskItem.showStartButton();
					}
				}
				task.setTask(taskItem);
			}
		}
	}
	
	/*public void showHistorys(View view, final Transmission trans) {
		synchronized (this) {
			trans.clearTaskRecord(3);
			for(final HistoryRecord h : historys){
				final TaskItem item = trans.addTaskRecord(h);
				item.addListeners(new Listener(){
					public void handleEvent(Event arg0) {
						historys.remove(h);
						trans.deleteTaskRecord(item);
					}
				});
			}
		}
	}*/
	
	/**
	 * 根据任务控制器获取任务视图
	 * @param basic
	 * @return
	 */
	public TaskItem getTaskItem(CSTransmissionBasic basic){
		for(OneTask oneTask : uploadList){
			if(oneTask.getController() == basic){
				return oneTask.getTask();
			}
		}
		for(OneTask oneTask : downloadList){
			if(oneTask.getController() == basic){
				return oneTask.getTask();
			}
		}
		return null;
	}
	
	/**
	 * 移除任务
	 * @param basic
	 * @return
	 */
	public OneTask removeTask(CSTransmissionBasic basic, boolean isH){
		synchronized (this) {
			for(OneTask task : uploadList){//在上传队列中查找
				if(task.getController() == basic){
					uploadList.remove(task);
					boolean flag = uploadRunningList.remove(task);
					if(isH && flag && !task.getController().isStop()){
						//historys.add(new HistoryRecord(task.getSrcdst(), task.getController().getTotalLength(), TaskStyle.UPLOAD));
					}
					startUploadRunning();
					return task;
				}
			}
			for(OneTask task : downloadList){//在下载队列中查找
				if(task.getController() == basic){
					downloadList.remove(task);
					boolean flag = downloadRunningList.remove(task);
					if(isH && flag && !task.getController().isStop()){
						//historys.add(new HistoryRecord(task.getSrcdst(), task.getController().getTotalLength(), TaskStyle.DOWNLOAD));
					}
					startDownloadRunning();
					return task;
				}
			}
			return null;
		}
	}
	
	/**
	 * 更新文件名
	 * @param basic
	 * @param name
	 */
	public void updateFileName(CSTransmissionBasic basic, String name){
		for(OneTask oneTask : uploadList){
			if(oneTask.getController() == basic){
				oneTask.setUrl(name);
				return;
			}
		}
		for(OneTask oneTask : downloadList){
			if(oneTask.getController() == basic){
				oneTask.setUrl(name);
				oneTask.setSrcdst(oneTask.getSrcdst()+"\\"+name);
				return;
			}
		}
	}

	/**
	 * 关闭所有任务
	 */
	public void cancelAllTask() {
		synchronized (this) {
			for(OneTask oneTask : uploadList){
				oneTask.getController().close();
			}
			for(OneTask oneTask : downloadList){
				oneTask.getController().close();
			}
		}
	}
}
