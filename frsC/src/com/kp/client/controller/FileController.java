package com.kp.client.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;

import com.kp.client.controller.task.AllFileController;
import com.kp.client.controller.task.DeleteFileController;
import com.kp.client.controller.task.DownloadFileController;
import com.kp.client.controller.task.ReController;
import com.kp.client.controller.task.ReUDController;
import com.kp.client.controller.task.UDCSTransmissionBasic;
import com.kp.client.controller.task.UploadFileController;
import com.kp.client.other.OneTask;
import com.kp.client.other.TaskPool;
import com.kp.client.view.View;
import com.kp.client.view.tstate.TaskItem;
import com.kp.entity.db.User;
import com.kp.entity.trans.AllFile;
import com.kp.entity.trans.Apply;
import com.kp.entity.trans.DeleteFile;
import com.kp.entity.trans.DownloadFile;
import com.kp.entity.trans.UploadFile;
import com.kp.util.file.FileUtil;
import com.kp.util.view.ViewUtil;

/*import com.dicd.client.controller.task.AllFolderController;
import com.dicd.client.controller.task.CreateFolderController;
import com.dicd.client.controller.task.DeleteFileController;
import com.dicd.client.controller.task.DeleteShareController;
import com.dicd.client.controller.task.DownloadFileController;
import com.dicd.client.controller.task.EachFileController;
import com.dicd.client.controller.task.MoveFileController;
import com.dicd.client.controller.task.PresentFolderController;
import com.dicd.client.controller.task.ReController;
import com.dicd.client.controller.task.ReUDController;
import com.dicd.client.controller.task.RenameFileController;
import com.dicd.client.controller.task.SaveShareController;
import com.dicd.client.controller.task.SearchFileController;
import com.dicd.client.controller.task.ShareFileController;
import com.dicd.client.controller.task.UDCSTransmissionBasic;
import com.dicd.client.controller.task.UploadFileController;
import com.dicd.client.other.HistoryBrowseRecord;
import com.dicd.client.other.OneTask;
import com.dicd.client.other.TaskPool;
import com.dicd.client.view.View;
import com.dicd.client.view.common.FileChooser;
import com.dicd.client.view.tstate.TaskItem;
import com.dicd.entity.db.User;
import com.dicd.entity.trans.AllFolder;
import com.dicd.entity.trans.Apply;
import com.dicd.entity.trans.CreateFolder;
import com.dicd.entity.trans.DeleteFile;
import com.dicd.entity.trans.DeleteShare;
import com.dicd.entity.trans.DownloadFile;
import com.dicd.entity.trans.EachFile;
import com.dicd.entity.trans.MoveFile;
import com.dicd.entity.trans.PresentFolder;
import com.dicd.entity.trans.RenameFile;
import com.dicd.entity.trans.SaveShare;
import com.dicd.entity.trans.SearchFile;
import com.dicd.entity.trans.ShareFile;
import com.dicd.entity.trans.UploadFile;
import com.dicd.util.file.FileUtil;
import com.dicd.util.view.ViewUtil;*/

/**
 * 文件控制器
 * @author Mr Wang
 *
 */
public class FileController {

	private View view;
	private String present;//当前路径
	private TaskPool taskPool;//任务池（上传、下载）
	//private HistoryBrowseRecord hbr;//文件浏览记录
	
	public FileController(){
		this.present = "";
		this.taskPool = new TaskPool();
		//this.hbr = new HistoryBrowseRecord();
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public TaskPool getTaskPool() {
		return taskPool;
	}
	
	/**
	 * 获取路径的文件夹内容
	 * @param present
	 */
/*	public void getPresentFoler(String present){
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final PresentFolder folder = (PresentFolder) apply;
				if(folder.isNormal()){
					if(folder.getPresent().length() > FileController.this.present.length()){
						hbr.addChildren(folder.getPresent().substring(folder.getPresent().lastIndexOf("\\")+1));
					}else if(folder.getPresent().length() < FileController.this.present.length()){
						hbr.toParent();
					}
					FileController.this.present = folder.getPresent();
					folder.getUser().setPassword(view.getUserController().getUser().getPassword());
					if(folder.getUser() != null && folder.getUser().getHeadImageBytes() != null){
						folder.getUser().setHeadImage(new ImageData(new ByteArrayInputStream(folder.getUser().getHeadImageBytes())));
						folder.getUser().setHeadImageBytes(null);
					}
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							folder.getUser().setPassword(view.getUserController().getUser().getPassword());
							view.getUserController().setUser(folder.getUser());
							view.getCommon().showFilesToTable(folder.getList());
							view.getCommon().setUrl("全部文件"+FileController.this.present);
							view.getCommon().setBackDisabled(folder.getPresent().length() == 0);
							view.getCommon().setAheadDisabled(!hbr.hasChildren());
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), folder.getErrorMsg());
				}
			}
		};
		User u = view.getUserController().getUser();
		PresentFolderController pfc = new PresentFolderController(rc, present, u.getKey(), u.getCode());
		pfc.start();
	}*/

	/**
	 * 获取根文件夹内容
	 */
	public void getRootFolder() {
		//getPresentFoler("");
	}
	
	/**
	 * 获取子文件夹内容
	 * @param name
	 */
/*	public void getChlidren(String name, boolean isSearch) {
		getPresentFoler(isSearch?name:present+"\\"+name);
	}*/
	
	/**
	 * 获取历史孩子结点
	 */
	/*public void getHistoryChlidren(){
		try {
			getPresentFoler(present+"\\"+hbr.getChildren());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 获取父文件夹
	 */
	/*public void getParent() {
		getPresentFoler(present.substring(0, present.lastIndexOf("\\")));
	}*/
	
	/**
	 * 刷新文件夹内容
	 */
	/*public void refreshFolder(){
		getPresentFoler(present);
	}*/
	
	/**
	 * 上传文件
	 * @param s是否加密
	 */
	public void uploadFile(boolean b){
		FileDialog dialog = new FileDialog(view.getShell(), SWT.MULTI);
		dialog.setText(b?"加密上传文件":"明文上传文件");
		dialog.open();
		String[] names = dialog.getFileNames();//选中文件名（加后缀）
		System.out.println("上传文件名(filecontroller)："+names[0]);
	//	System.out.println("上传文件名(filecontroller)："+names[1]);
		String path = dialog.getFilterPath();//文件全路径
		System.out.println("上传文件路径(filecontroller)："+path);
		
		if(path != null && !"".equals(path)){//路径不为空
			ReUDController rc = new ReUDController() {  //实现回调接口
				public void run(UDCSTransmissionBasic basic, Apply apply) {
					UploadFile uf = (UploadFile) apply;
					if(!uf.isNormal()){
						System.out.println("filecontroller(c)错误  ！isnormol");
						ViewUtil.hint(view.getShell(), uf.getErrorMsg());
					}
					refreshFolder();
					final OneTask oneTask = taskPool.removeTask(basic, uf.isNormal());
					if(view.getTransmission() != null){
						ViewUtil.asyncExec(new Runnable() {
							public void run() {
								view.getTransmission().deleteUploadTask(oneTask.getTask());
								//taskPool.showHistorys(view, view.getTransmission());
							}
						});
					}
				}
				public void refreshFolder() {
					/*if(!view.getCommon().isSearch()){
						FileController.this.refreshFolder();
					}*/
					System.out.println("上传完成，更新列表");
					FileController.this.showAllFile();
				}
				public void updateFileName(UDCSTransmissionBasic basic, String name) {
					
				}
			};
			
			List<String> ls = FileUtil.getFilePath(path, names);//得到文件路径
			User u = view.getUserController().getUser();//获取上传用户
			for(int i=0; i<ls.size(); i++){//遍历所有选中文件路径
				//回调函数，原路径，当前路径，是否加密
				//final UploadFileController ufc = new UploadFileController(rc, ls.get(i), present+"\\"+names[i], b?view.getUserController().getUser().getPassword():null);

				final UploadFileController ufc = new UploadFileController(rc, ls.get(i), view.getUserController().getUser().getUsername(), names[i], b?view.getUserController().getUser().getPassword():null);
				TaskItem task = null;
				if(view.getTransmission() != null){
					task = view.getTransmission().addUploadTask(names[i]);
					task.addListeners(ViewUtil.getTaskListener(view.getShell(), ufc, task, ls.get(i).substring(0, ls.get(i).lastIndexOf("\\"))));
				}
				this.taskPool.addUploadTask(new OneTask(ufc, task, names[i], ls.get(i)));
			}//for
		}
	}
	
	/**
	 * 下载文件
	 * @param names 文件名
	 */
	public void downloadFile(String[] names){
		if(names.length == 0) return;
		for(int i=0; i<names.length; i++){
		//	names[i] = present + "\\" + names[i];
			names[i] = names[i];
		}
		downloadFile(names, null);
	}
	
	/**
	 * 下载文件
	 * @param names 文件名
	 */
	public void downloadFile(String[] names, String sname){
		if(names.length == 0) return;
		DirectoryDialog dialog = new DirectoryDialog(view.getShell(), SWT.SAVE);
		String dst = dialog.open();
		if(dst != null){
			ReUDController rc = new ReUDController() {
				public void run(UDCSTransmissionBasic basic, Apply apply) {
					DownloadFile df = (DownloadFile) apply;
					if(!df.isNormal()){
						ViewUtil.hint(view.getShell(), df.getErrorMsg());
					}
					final OneTask oneTask = taskPool.removeTask(basic, df.isNormal());
					if(view.getTransmission() != null){
						ViewUtil.asyncExec(new Runnable() {
							public void run() {
								view.getTransmission().deleteDownloadTask(oneTask.getTask());
								//taskPool.showHistorys(view, view.getTransmission());
							}
						});
					}
				}
				public void refreshFolder() {
					/*if(!view.getCommon().isSearch()){
						FileController.this.refreshFolder();
					}*/
					
				}
				public void updateFileName(UDCSTransmissionBasic basic, final String name) {
					final TaskItem task = taskPool.getTaskItem(basic);
					taskPool.updateFileName(basic, name);
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getTransmission() != null && task != null){
								task.updateFileName(name);
							}
						}
					});
				}
			};
			User u = view.getUserController().getUser();
			for(String name : names){
				System.out.println("文件名"+name);
				System.out.println("下载路径"+dst);
				DownloadFileController dfc = new DownloadFileController(rc, name, dst, view.getUserController().getUser().getId());
				TaskItem task = null;
				if(view.getTransmission() != null){ 
					task = view.getTransmission().addDownloadTask(sname!=null ? sname : name.substring(name.lastIndexOf("\\")+1));
					task.addListeners(ViewUtil.getTaskListener(view.getShell(), dfc, task, dst));
				}
				this.taskPool.addDownloadTask(new OneTask(dfc, task, sname!=null ? sname : name.substring(name.lastIndexOf("\\")+1), dst));
			}
		}
	}
	
	/**
	 * 删除文件
	 * @param names
	 */
	public void deleteFile(String filename){
		ReController rc = new ReController() {
			public void run(Apply apply) {
				DeleteFile df = (DeleteFile) apply;
				if(df.isNormal()){
					/*if(!view.getCommon().isSearch()){
						//refreshFolder();
					}else{*/
					view.getFileController().showAllFile();
					ViewUtil.hint(view.getShell(), df.getErrorMsg());
						ViewUtil.asyncExec(new Runnable() {
							public void run() {
								//searchFile(view.getCommon().getSearchText());
							}
						});
					//}
				}else{
					ViewUtil.hint(view.getShell(), df.getErrorMsg());
				}
			}
		};
		/*if(!isSearch){
			//重新生成路径
			for(int i=0; i<names.length; i++){
				if(hbr.deleteChildren(names[i])){
					view.getCommon().setAheadDisabled(!hbr.hasChildren());
				}
				names[i] = present+"\\"+names[i];
			}
		}*/
		User u = view.getUserController().getUser();
		System.out.println("c想删除的文件名："+filename+"目前用户id"+u.getId());
		DeleteFileController dfc = new DeleteFileController(rc, filename, u.getId());
		dfc.start();
	}

	/**
	 * 创建文件夹
	 */
	/*public void createFoler() {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final CreateFolder cf = (CreateFolder) apply;
				if(cf.isNormal()){
					if(present.compareTo(cf.getFolder())==0){
						ViewUtil.asyncExec(new Runnable() {
							public void run() {
								view.getCommon().createFolder(cf.getCFile());
							}
						});
					}
				}else{
					ViewUtil.hint(view.getShell(), cf.getErrorMsg());
				}
			}
		};
		User u = view.getUserController().getUser();
		CreateFolderController cfc = new CreateFolderController(rc, u.getKey(), u.getCode(), present);
		cfc.start();
	}*/

	/**
	 * 重命名文件或文件夹
	 * @param old
	 * @param nwe
	 */
	/*public void renameFile(String old, String nwe, boolean isSearch) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				RenameFile rf = (RenameFile) apply;
				if(!view.getCommon().isSearch()){
					refreshFolder();
				}else{
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							searchFile(view.getCommon().getSearchText());
						}
					});
				}
				if(!rf.isNormal()){
					ViewUtil.hint(view.getShell(), rf.getErrorMsg());
				}
			}
		};
		if(isSearch?old.substring(old.lastIndexOf("\\")+1).compareTo(nwe)==0:old.compareTo(nwe)==0){
			if(!view.getCommon().isSearch()){
				refreshFolder();
			}else{
				searchFile(view.getCommon().getSearchText());
			}
			return;
		}
		User u = view.getUserController().getUser();
		RenameFileController rfc = new RenameFileController(rc, u.getKey(),
				u.getCode(), isSearch?old:present+"\\"+old, nwe);
		rfc.start();
	}*/

	/**
	 * 获取数据库所有文件
	 */
	public void showAllFile() {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final AllFile af = (AllFile) apply;
				if(af.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							System.out.println("准备显示文件");
							System.out.println(af.getFileuserList());
							view.getCommon().showFilesToTable(af.getFileuserList());
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), af.getErrorMsg());
				}
			}
		};
		System.out.println("准备前往allfilecontroller");
		AllFileController afc = new AllFileController(rc);
		afc.start();
	}

	/**
	 * 移动文件
	 * @param names
	 * @param path
	 */
	/*public void moveFile(String[] names, String path, boolean isSearch) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				MoveFile mf = (MoveFile) apply;
				if(mf.isNormal()){
					if(!view.getCommon().isSearch()){
						refreshFolder();
					}else{
						ViewUtil.asyncExec(new Runnable() {
							public void run() {
								searchFile(view.getCommon().getSearchText());
							}
						});
					}
				}else{
					ViewUtil.hint(view.getShell(), mf.getErrorMsg());
				}
			}
		};
		if(!isSearch){
			for(int i=0; i<names.length; i++){
				names[i] = present+"\\"+names[i];
			}
		}
		User u = view.getUserController().getUser();
		MoveFileController mfc = new MoveFileController(rc, u.getKey(), u.getCode(), names, path);
		mfc.start();
	}
*/
	/**
	 * 分享文件
	 * @param path
	 * @param names
	 */
	/*public void sharePFile(String[] fuser, String[] names) {
		for(int i=0; i<names.length; i++){
			names[i] = present+"\\"+names[i];
		}
		shareFile(fuser, names);
	}*/
	
	/**
	 * 分享文件
	 * @param path
	 * @param names
	 */
	/*public void shareFile(String[] fuser, String[] paths) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				ShareFile sf = (ShareFile) apply;
				ViewUtil.hint(view.getShell(), sf.isNormal()?"已分享给好友":sf.getErrorMsg());
			}
		};
		User u = view.getUserController().getUser();
		ShareFileController sfc = new ShareFileController(rc, u.getKey(), u.getCode(), fuser, paths);
		sfc.start();
	}*/

	/**
	 * 获取我与好友之间分享的文件
	 */
	/*public void getEachFile(String key) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final EachFile ef = (EachFile) apply;
				if(ef.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getShare() != null){
								view.getShare().showEachFile(ef.getFkey(), ef.getShare());
							}
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), ef.getErrorMsg());
				}
			}
		};
		User u = view.getUserController().getUser();
		EachFileController efc = new EachFileController(rc, u.getKey(), u.getCode(), key);
		efc.start();
	}*/

	/**
	 * 获取所有文件结构
	 */
	/*public void getAllFile() {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final AllFolder af = (AllFolder) apply;
				if(af.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getShare() != null){
								view.getShare().shareFile(af.getCFile());
							}
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), af.getErrorMsg());
				}
			}
		};
		User u = view.getUserController().getUser();
		AllFolderController afc = new AllFolderController(rc, u.getKey(), u.getCode());
		afc.start();
	}*/

	/**
	 * 删除分享文件
	 * @param key
	 */
	/*public void deleteShareFile(String key) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				DeleteShare ds = (DeleteShare) apply;
				if(ds.isNormal()){
					if(view.getShare() != null){
						view.getShare().updateFileku();
					}
				}else{
					ViewUtil.hint(view.getShell(), ds.getErrorMsg());
				}
			}
		};
		User u = view.getUserController().getUser();
		DeleteShareController dsc = new DeleteShareController(rc, u.getKey(), u.getCode(), key);
		dsc.start();
	}
*/
	/**
	 * 保存文件到云盘
	 * @param key
	 */
	/*public void saveFileToYun(final String key) {
		final User u = view.getUserController().getUser();
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final AllFolder af = (AllFolder) apply;
				if(af.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							af.getCFile().clearFile();
							FileChooser dialog = new FileChooser(view.getShell(), "浏览文件夹", SWT.BORDER|SWT.ARROW);
							dialog.createTree(af.getCFile());
							String path = dialog.open();
							if(path != null){
								ReController rc = new ReController() {
									public void run(Apply apply) {
										SaveShare ss = (SaveShare) apply;
										if(!ss.isNormal()){
											ViewUtil.hint(view.getShell(), ss.getErrorMsg());
										}else{
											if(!view.getCommon().isSearch()){
												refreshFolder();
											}else{
												ViewUtil.asyncExec(new Runnable() {
													public void run() {
														searchFile(view.getCommon().getSearchText());
													}
												});
											}
										}
									}
								};
								SaveShareController ssc = new SaveShareController(rc, u.getKey(), u.getCode(), key, path);
								ssc.start();
							}
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), af.getErrorMsg());
				}
			}
		};
		AllFolderController afc = new AllFolderController(rc, u.getKey(), u.getCode());
		afc.start();
	}*/

	/**
	 * 搜索文件
	 * @param key
	 */
	/*public void searchFile(String key) {
		if(key.length() == 0) return;
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final SearchFile sf = (SearchFile) apply;
				if(sf.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							view.getCommon().showSearchFile(sf.getWkey(), sf.getCFiles());
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), sf.getErrorMsg());
				}
			}
		};
		User u = view.getUserController().getUser();
		SearchFileController sfc = new SearchFileController(rc, u.getKey(), u.getCode(), present, key);
		sfc.start();
	}*/

	/**
	 * 判断是否还有线程运行
	 * @return
	 */
	public boolean isRunning() {
		return taskPool.hasRunning();
	}

	/**
	 * 强制关闭所有任务
	 */
	public void cancelAllTask() {
		taskPool.cancelAllTask();
	}
}