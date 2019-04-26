package com.kp.client.view.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.kp.util.view.ImageButton;
import com.kp.util.view.ViewUtil;


/**
 * 视图工具栏
 * @author ping
 *
 */
public class FileToolBar extends ViewForm {
	
	private ToolBar toolBar;
	
	//Item与工具栏边框之间的距离
	private int toolBarLength = 31;
	
	//操作按钮之间的距离
	//private int lengthBetweenItems = 1;
	private int lengthBetweenItems = 2;
	
	//操作按钮
	private ToolItem uploadFile;
	private ToolItem downloadFile;
	//private ToolItem shareFile;
	private ToolItem deleteFile;
	private ToolItem refresh;
	//private ToolItem createFolder;
	//private ToolItem moveFileTo;
	
	//打开文件传输视图按钮
	private ImageButton transmission;//右上角传输列表
	
	//颜色
	private Color toolBarBackColor;
	
	public FileToolBar(Composite composite, int style) {
		super(composite, style);
		init(composite);
	}
	
	public FileToolBar(Composite composite) {		
		this(composite, SWT.NONE);
	}
	
	/**
	 * 初始化工具栏
	 */
	private void init(Composite composite){
		toolBarBackColor = new Color(this.getDisplay(), 236,242,247);//工具条
		
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setBackground(toolBarBackColor);
		
		toolBar = new ToolBar(this, SWT.FLAT|SWT.WRAP|SWT.RIGHT);
		toolBar.setBackground(toolBarBackColor);
		
		addItem();
	}
	
	/**
	 * 初始化工具栏按钮
	 */
	private void addItem(){
		new ToolItem(toolBar, SWT.NONE).setText(" ");

		ViewUtil.addBlankToolItem(toolBar, lengthBetweenItems);
		uploadFile = ViewUtil.addToolItem(toolBar, "images/upload_image.png", "上传 ", "加密上传文件");
		//System.out.println("上传按钮");
		
		ViewUtil.addBlankToolItem(toolBar, lengthBetweenItems);
		downloadFile = ViewUtil.addToolItem(toolBar, "images/download_image.png", "下载 ", "下载文件");
		
	/*	ViewUtil.addBlankToolItem(toolBar, lengthBetweenItems);
		shareFile = ViewUtil.addToolItem(toolBar, "images/share_normal2.png", "分享 ", "分享文件");*/
		
		ViewUtil.addBlankToolItem(toolBar, lengthBetweenItems);
		//deleteFile = ViewUtil.addToolItem(toolBar, "images/delete_image.png", "删除 ", "删除文件或文件夹");
		deleteFile = ViewUtil.addToolItem(toolBar, "images/delete_image.png", "删除 ", "删除文件");
		
		ViewUtil.addBlankToolItem(toolBar, lengthBetweenItems);
		refresh = ViewUtil.addToolItem(toolBar, "images/refresh_over_image.png", "刷新 ", "刷新页面");
		
		
		/*ViewUtil.addBlankToolItem(toolBar, lengthBetweenItems);
		createFolder = ViewUtil.addToolItem(toolBar, "images/newfolder_normal.png", "新建文件夹 ", "新建文件夹");*/
		
		/*ViewUtil.addBlankToolItem(toolBar, lengthBetweenItems);
		moveFileTo = ViewUtil.addToolItem(toolBar, "images/move_image.png", "移动到 ", "移动到指定的文件夹内");*/
			
		transmission = new ImageButton(this, "images/trans_normal_image.png", "images/trans_over_image.png", "images/trans_down_image.png");
	}
	
	/**
	 * 设置FileToolBar位置大小
	 */
	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		super.setBounds(arg0, arg1, arg2, arg3);
		toolBar.setBounds(-15, (arg3-toolBarLength)/2, arg2-100, toolBarLength);
		transmission.setBounds(arg2-80, 0, 80, 34);
	}
	
	@Override
	protected void checkSubclass(){            
    }
	
	/**
	 * 根据文件的选择个数，决定按钮的可见性
	 * @param num
	 */
	public void setToolDisabled(int num, boolean isSearch){
		if(num >= 1){
			uploadFile.setEnabled(true);
			downloadFile.setEnabled(true);
			//shareFile.setEnabled(true);
			deleteFile.setEnabled(true);
			refresh.setEnabled(true);
			//moveFileTo.setEnabled(true);
		}else{
			uploadFile.setEnabled(false);
			downloadFile.setEnabled(false);
			//shareFile.setEnabled(false);
			deleteFile.setEnabled(false);
			refresh.setEnabled(false);
			//moveFileTo.setEnabled(false);
		}
		if(isSearch){
			uploadFile.setEnabled(false);
			//createFolder.setEnabled(false);
		}else{
			uploadFile.setEnabled(true);
			//createFolder.setEnabled(true);
		}
	}
	
	/**
	 * 释放资源
	 */
	@Override
	public void dispose(){
		ViewUtil.dispose(toolBarBackColor);
		ViewUtil.dispose(transmission);
	}
	
	/**
	 * 添加点击transmission出现传输视图
	 * @param arg0
	 */
	public void addClickListener(Listener arg0){
		transmission.addClickListener(arg0);
	}
	
	/**
	 * 添加上传文件事件处理
	 */
	public void addUploadListener(Listener listener){
		uploadFile.addListener(SWT.Selection, listener);
	}	
	
	/**
	 * 添加下载文件事件处理
	 */
	public void addDownloadListener(Listener listener){
		downloadFile.addListener(SWT.Selection, listener);
	}
	
	/**
	 * 添加分享文件事件处理
	 * @param listener
	 */
	/*public void addShareFileListener(Listener listener){
		shareFile.addListener(SWT.Selection, listener);
	}
	*/
	/**
	 * 添加删除文件事件处理
	 */
	public void addDeleteListener(Listener listener){
		deleteFile.addListener(SWT.Selection, listener);
	}

	/**
	 * 添加刷新事件处理
	 */
	public void addRefreshListener(Listener listener){
		refresh.addListener(SWT.Selection, listener);
	}
	/**
	 * 添加新建文件夹事件处理
	 */
	/*public void addNewCreateListener(Listener listener){
		createFolder.addListener(SWT.Selection, listener);
	}*/
	
	/**
	 * 添加移动文件到一个路径事件处理
	 */
	/*public void addMoveListener(Listener listener){
		moveFileTo.addListener(SWT.Selection, listener);
	}*/
}
