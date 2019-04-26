package com.kp.server.view.common;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.kp.entity.db.fileUser;
import com.kp.server.view.View;


/**
 * 显示普通文件的视图
 * @author ping
 *
 */
public class Common {
	
	private View view;
	
	//视图组件

	//private FileSearchTable fileSearchTable;  //文件名、文件属性
	private FileToolBar fileToolBar;
	//private FileOperator fileOperator;
	private FileTable fileTable;
 	//private Footer footer;
	//private boolean isSearch;
	
	public Common(View view){
		this.view = view;
		init(view.getShell());
		//isSearch = false;
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	/*public boolean isSearch(){
		return isSearch;
	}*/
	
	
	private void init(Shell shell){
		initComponent(shell);
		initListener();
	}
	
	/**
	 * 初始化组件
	 * @param shell
	 */
	private void initComponent(Shell shell){
		fileToolBar = new FileToolBar(shell);
		fileTable = new FileTable(shell);
		//fileSearchTable = new FileSearchTable(shell);
	}
	
	/**
	 * 初始化所有事件处理
	 */
	private void initListener(){
		//fileToolBar.addUploadListener(secritUploadListener);
		//fileToolBar.addDownloadListener(downloadListener);
		fileToolBar.addRefreshListener(refreshListener);
		fileToolBar.addDeleteListener(deleteListener);
		//fileToolBar.addNewCreateListener(createFolerListener);
		//fileToolBar.addMoveListener(moveListener);
		//fileToolBar.addShareFileListener(shareFileListener);

		//fileTable.addFileMoveToListener(moveListener);
		
		//fileTable.addCreateFolderListener(createFolerListener);
		
		//fileTable.addDeleteFileListener(deleteListener);
		//fileTable.addReFreshListener(refreshClickListener);
		//fileTable.addDownloadFileListener(downloadListener);
		//fileTable.addUploadFileListener(uploadListener);
		
		//fileTable.addSecritUploadFileListener(secritUploadListener);//加密上传
		
		//fileTable.addMouseDoubleClickListener(tableMouseDoubleClickListener);
		
		//fileTable.addRenameFocusOutEventListener(renameFocusOutEventListener);
		//fileTable.addSelectionListener(fileSelectionListener);
		fileTable.addShowByFileNameListener(showByFileNameListener);
		fileTable.addShowByFileLengthListener(showByFileLengthListener);
		fileTable.addShowByModifyTimeListener(showByModifyTimeListener);
		//fileTable.addShareFileListener(shareFileListener);
		
		/*fileSearchTable.addSelectionListener(fileSelectionListener);
		fileSearchTable.addRenameFocusOutEventListener(renameFocusOutEventListener);
		fileSearchTable.addMouseDoubleClickListener(tableMouseDoubleClickListener);
		fileSearchTable.addDownloadFileListener(downloadListener);
		fileSearchTable.addShareFileListener(shareFileListener);
		fileSearchTable.addFileMoveToListener(moveListener);
		fileSearchTable.addDeleteFileListener(deleteListener);

		fileOperator.addBackClickListener(backClickListener);
		fileOperator.addRefreshClickListener(refreshClickListener);
		fileOperator.addSearchModifyListener(searchModifyListener);
		fileOperator.addAheadClickListener(aheadClickListener);
		
		footer.addShareClickListener(shareClickListener);*/
	}
	
	/**
	 * 布局组件位置
	 */
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		fileToolBar.setBounds(0, arg1, arg2, 40);
		//fileOperator.setBounds(0, arg1+fileToolBar.getSize().y, arg2, 40);
		//footer.setBounds(0, arg1+arg3-35, arg2, 35);
		//fileTable.setBounds(0, arg1+fileToolBar.getSize().y+fileOperator.getSize().y, arg2-2, arg3-(footer.getSize().y+fileToolBar.getSize().y+fileOperator.getSize().y));
		//fileSearchTable.setBounds(0, arg1+fileToolBar.getSize().y+fileOperator.getSize().y, arg2-2, arg3-(footer.getSize().y+fileToolBar.getSize().y+fileOperator.getSize().y));

		fileTable.setBounds(0, arg1+fileToolBar.getSize().y, arg2-2, arg3-fileToolBar.getSize().y);
	}
	
	public Point getSize(){
		Point p = new Point(0, 0);
		/*p.x = fileToolBar.getSize().x+fileOperator.getSize().x+footer.getSize().x+fileTable.getSize().x;
		p.y = fileToolBar.getSize().y+fileOperator.getSize().y+footer.getSize().y+fileTable.getSize().y;*/
		
		p.x = fileToolBar.getSize().x+fileTable.getSize().x;
		p.y = fileToolBar.getSize().y+fileTable.getSize().y;
		return p;
	}
	
	/**
	 * 释放资源
	 */
	public void dispose(){
		fileToolBar.dispose();
		//fileOperator.dispose();
		fileTable.dispose();
		//fileSearchTable.dispose();
		//footer.dispose();
	}
	
	/**
	 * 显示文件到表格中
	 */
	public void showFilesToTable(List<fileUser> list){
		//isSearch = false;
		//fileOperator.clearSearchText();
		fileTable.showFilesToTable(list);
		//fileSelectionListener.handleEvent(null);
	}

	public void setUrl(String url){
		//fileOperator.setUrl(url);
	}

	/**
	 * 设置返回按钮是否可用
	 * @param isDisabled
	 */
	/*public void setBackDisabled(boolean isDisabled){
		fileOperator.setBackDisabled(isDisabled);
	}*/

	/**
	 * 设置前进按钮是否可用
	 * @param isDisabled
	 */
	/*public void setAheadDisabled(boolean isDisabled){
		fileOperator.setAheadDisabled(isDisabled);
	}*/
	
	/**
	 * 在界面创建一个文件夹
	 * @param name
	 */
	/*public void createFolder(CFile f) {
		fileTable.ceFolder(f);
		fileSelectionListener.handleEvent(null);
	}*/
	
	/**
	 * 移动文件
	 * @param cFile
	 */
	/*public void moveFile(CFile f) {
		String[] names = isSearch?fileSearchTable.getSelectionFiles():fileTable.getSelectionFiles();
		if(names.length > 0){
			FileChooser dialog = new FileChooser(view.getShell(), "浏览文件夹", SWT.BORDER|SWT.ARROW);
			dialog.createTree(f);
			String path = dialog.open();
			if(path != null){
				view.getFileController().moveFile(names, path, isSearch);
			}
		}
	}*/

	//重命名视图呈现编辑状态
	/*Listener renameListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			if(isSearch){
				fileSearchTable.editSelectFileName();
			}else{
				fileTable.editSelectFileName();
			}
		}
	};*/
	
	//明文上传事件处理
	/*Listener uploadListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			view.getFileController().uploadFile(false);
		}
	};*/
	
	//加密上传事件处理
	/*Listener secritUploadListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			view.getFileController().uploadFile(true);
		}
	};*/

	//下载事件处理
	/*Listener downloadListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			String[] names = isSearch?fileSearchTable.getSelectionFiles():fileTable.getSelectionFiles();
			if(isSearch){
				view.getFileController().downloadFile(names, null);
			}else{
				view.getFileController().downloadFile(names);
			}
		}
	};
*/
	//表格单机双击事件处理
	/*Listener tableMouseDoubleClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			try {
				if(isSearch?fileSearchTable.selectIsFolder():fileTable.selectIsFolder()){
					String name = isSearch?fileSearchTable.getSelectFileName():fileTable.getSelectFileName();
					view.getFileController().getChlidren(name, isSearch);
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	};*/

	//后退按钮事件处理
	/*Listener backClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			view.getFileController().getParent();
		}
	};*/

	/*Listener aheadClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			view.getFileController().getHistoryChlidren();
		}
	};*/

	//刷新按钮事件处理
	/*Listener refreshClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			if(!isSearch()){
				fileTable.forceFocus();
				view.getFileController().refreshFolder();
			}else{
				view.getFileController().searchFile(getSearchText());
			}
		}
	};*/

	
	//刷新事件处理
		Listener refreshListener = new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				view.getFileController().showFiles();
			}
		};
	
	
	//删除事件处理
	Listener deleteListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			String[] filenames = fileTable.getSelectionFiles();
			if(filenames.length > 0){
				MessageBox dialog=new MessageBox(view.getShell(), SWT.YES|SWT.NO);
				dialog.setText("温馨提示");
				dialog.setMessage("确认要删除“"+filenames[0]+"”"+(filenames.length>1 ? "等"+filenames.length+"个文件或文件夹" : "")+"吗？");
				if(dialog.open() == 64){//64返回确定，128返回取消
					view.getFileController().deleteFile(filenames);
				}
			}
		}
	};
	
	//新建文件夹事件处理
	/*Listener createFolerListener = new Listener() {
		Date ahead = new Date(new Date().getTime()-1000);
		@Override
		public synchronized void handleEvent(Event arg0) {
			Date now = new Date();
			if(now.getTime()-ahead.getTime()>300){
				ahead = now;
				fileTable.forceFocus();
				view.getFileController().createFoler();
			}
		}
	};
*/
	//重命名监听鼠标点击事件后处理
	/*Listener renameFocusOutEventListener = new Listener() {	
		@Override
		public void handleEvent(Event e) {
			String[] rename = isSearch?fileSearchTable.getRenameFileName():fileTable.getRenameFileName();
			if(rename[0] != null && rename[1] != null){
				view.getFileController().renameFile(rename[0], rename[1], isSearch);
			}
		}
	};*/

	//移动到事件处理
	/*Listener moveListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			String[] names = isSearch?fileSearchTable.getSelectionFiles():fileTable.getSelectionFiles();
			if(names.length > 0){
				view.getFileController().getAllFolder();
			}
		}
	};*/

	//文件搜索事件处理
	/*Listener searchModifyListener = new Listener() {
		@Override
		public void handleEvent(Event e) {
			String key = fileOperator.getSearchText();
			isSearch = key.length() > 0;
			if(key.length() > 0){
				view.getFileController().searchFile(key);
				fileSearchTable.setVisible(true);
				fileTable.setVisible(false);
			}else{
				view.getFileController().refreshFolder();
				fileSearchTable.setVisible(false);
				fileTable.setVisible(true);
				fileSelectionListener.handleEvent(null);
			}
		}
	};*/

	//文件选中事件chul
	/*Listener fileSelectionListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			int selectedFileNum = isSearch?fileSearchTable.getSelectionCount():fileTable.getSelectionCount();
			if(selectedFileNum > 0){
				footer.setSelectedFileNum("已选择 "+selectedFileNum+" 个项");
			}else{
				footer.setSelectedFileNum((isSearch?fileSearchTable.getItemCount():fileTable.getItemCount())+" 个对象");
			}
			fileToolBar.setToolDisabled(selectedFileNum, isSearch);
		}
	};
	*/
	//文件显示的排序方式
	Listener showByFileNameListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			//view.getFileController().refreshFolder();
		}
	};

	Listener showByFileLengthListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			//view.getFileController().refreshFolder();
		}
	};

	Listener showByModifyTimeListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			//view.getFileController().refreshFolder();
		}
	};
	
	/**
	 * 分享按钮事件处理
	 */
	/*Listener shareClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			view.getViewController().showShareView();
		}
	};*/
	
	/**
	 * 分享文件
	 */
	/*Listener shareFileListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			view.getUserController().getMyFriends();
		}
	};*/

	/**
	 * 生成好友选择界面
	 * @param friends
	 */
	/*public void shareFile(List<Friend> friends) {
		String[] names = isSearch?fileSearchTable.getSelectionFiles():fileTable.getSelectionFiles();
		if(names.length > 0){
			FriendsChooser dialog = new FriendsChooser(view.getShell(), "分享文件");
			dialog.createTree(friends);
			dialog.open();
			String[] paths = dialog.getNames();
			if(paths != null && paths.length > 0){
				if(isSearch){
					view.getFileController().shareFile(paths, names);
				}else{
					view.getFileController().sharePFile(paths, names);
				}
			}
		}
	}*/

	/**
	 * 显示搜索到的文件
	 * @param wkey
	 * @param cFiles
	 */
	/*public void showSearchFile(String wkey, List<CFile> list) {
		if(fileOperator.getSearchText().compareTo(wkey) == 0){
			fileSearchTable.showFilesToTable(list);
			fileSelectionListener.handleEvent(null);
		}
	}*/
}
