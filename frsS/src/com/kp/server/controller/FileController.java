package com.kp.server.controller;

import java.io.File;

import com.kp.config.Config;
import com.kp.server.controller.task.AllFileController;
import com.kp.server.controller.task.CSTransmissionBasic;
import com.kp.server.controller.task.DeleteFileController;
import com.kp.server.controller.task.DownloadFileController;
import com.kp.server.controller.task.UploadFileController;
import com.kp.server.dao.myFileDao;
import com.kp.server.other.PortPool;
import com.kp.server.other.TaskPool;
import com.kp.server.service.FileService;
import com.kp.server.view.View;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;



/**
 * 文件控制器
 * @author Mr Wang
 *
 */
public class FileController {

	private View view;
	private PortPool portPool;  //端口池：申请端口释放端口
	private TaskPool taskPool;  
	
	public FileController(){
		this.portPool = new PortPool();
	}
	
	public void setView(View view) {
		this.view = view;
		this.taskPool = new TaskPool(this.view);
	}
	
	public TaskPool getTaskPool() {
		return taskPool;
	}
	
	/**
	 * 释放端口
	 * @param port
	 */
	public void realsePort(int port){
		portPool.releasePort(port);
	}
	
	/**
	 * 客户端：获取当前文件夹内容
	 * @param basic
	 */
	/*public void presentFolder(CSTransmissionBasic basic) {
		PresentFolderController pfc = new PresentFolderController(basic);
		taskPool.addTask(pfc);
	}*/

	/**
	 * 客户端：上传文件
	 * @param basic
	 */
	public void uploadFile(CSTransmissionBasic basic) {
		int port = portPool.apply();
		if(port == PortPool.NOPORT){
			IOUtil.reTaskFailure(basic, "服务器繁忙");
		}else{//申请到端口
			UploadFileController ufc = new UploadFileController(basic, this, port);
			taskPool.addTask(ufc);
		}
	}

	/**
	 * 客户端：下载文件
	 * @param monitor
	 */
	public void downloadFile(CSTransmissionBasic basic) {
		int port = portPool.apply();
		if(port == PortPool.NOPORT){
			IOUtil.reTaskFailure(basic, "服务器繁忙");
		}else{
			DownloadFileController dfc = new DownloadFileController(basic, this, port);
			taskPool.addTask(dfc);
		}
	}

	/**
	 * 客户端：删除文件
	 * @param monitor
	 */
	public void deleteFile(CSTransmissionBasic basic) {
		DeleteFileController dfc = new DeleteFileController(basic);
		taskPool.addTask(dfc);
	}
	/**
	 * 服务端：删除文件
	 */
	public void deleteFile(String[] filenames) {
		myFileDao dao = new myFileDao();
		String  storename; 
		String  filePath; 
		for(String filename: filenames) {
			dao.delete(filename);
			if(dao.findFileByFileName(filename)!=null) {
				storename = dao.findFileByFileName(filename).getStoreName();
				filePath = Config.defaultPath + "\\" + storename;
				File file = new File(filePath);
				file.delete();
			}
			
		}
		showFiles();
	}
	/**
	 * 客户端：创建文件夹
	 * @param monitor
	 */
	/*public void createFolder(CSTransmissionBasic basic) {
		CreateFolderController cfc = new CreateFolderController(basic);
		taskPool.addTask(cfc);
	}*/

	/**
	 * 客户端：重命名文件或文件夹
	 * @param monitor
	 */
	/*public void renameFile(CSTransmissionBasic basic) {
		RenameFileController rfc = new RenameFileController(basic);
		taskPool.addTask(rfc);
	}*/

	/**
	 * 客户端：获取目录结构
	 * @param monitor
	 */
	/*public void allFolder(CSTransmissionBasic basic) {
		AllFolderController afc = new AllFolderController(basic);
		taskPool.addTask(afc);
	}*/

	/**
	 * 客户端：移动文件
	 * @param monitor
	 */
	/*public void moveFile(CSTransmissionBasic basic) {
		MoveFileController mfc = new MoveFileController(basic);
		taskPool.addTask(mfc);
	}*/

	/**
	 * 客户端：分享文件
	 * @param basic
	 */
	/*public void shareFile(CSTransmissionBasic basic) {
		ShareFileController sfc = new ShareFileController(basic);
		taskPool.addTask(sfc);
	}*/

	/**
	 * 客户端：相互之间的文件
	 * @param monitor
	 */
	/*public void getEachFile(CSTransmissionBasic basic) {
		EachFileController efc = new EachFileController(basic);
		taskPool.addTask(efc);
	}*/

	/**
	 * 客户端：删除分享文件
	 * @param monitor
	 */
	/*public void deleteShareFile(CSTransmissionBasic basic) {
		DeleteShareController dsc = new DeleteShareController(basic);
		taskPool.addTask(dsc);
	}*/

	/**
	 * 客户端：保存分享文件
	 * @param basic
	 */
	/*public void saveShareFile(CSTransmissionBasic basic) {
		SaveShareController ssc = new SaveShareController(basic);
		taskPool.addTask(ssc);
	}*/

	/**
	 * 客户端：检索文件
	 * @param monitor
	 */
	/*public void searchFile(CSTransmissionBasic basic) {
		SearchFileController sfc = new SearchFileController(basic);
		taskPool.addTask(sfc);
	}*/
	
	
	/**
	 * 客户端展示文件
	 */
	
	public void showFiles(CSTransmissionBasic basic) {
		AllFileController afc = new AllFileController(basic);
		taskPool.addTask(afc);
	}
	
	/**
	 * 服务端展示文件
	 */
	public void showFiles(){
		view.getCommon().showFilesToTable(FileUtil.getAllFiles());
	}
}
