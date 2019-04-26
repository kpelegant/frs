package com.kp.server.view.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.kp.util.view.ImageButton;
import com.kp.util.view.ViewUtil;

public class SState extends ViewForm {
	
	//当前任务数
	private Label rask;
	private Label running;
	private Label complete;
	
	private ImageButton user;
	
	//颜色
	private Color backColor;
	
	public SState(Composite composite){
		super(composite, SWT.NONE);
		init();
	}
	
	/**
	 * 初始化组件
	 * @param composite
	 */
	private void init(){
		backColor = new Color(getDisplay(), 236, 242, 247);
		setBackground(backColor);
		
		rask = new Label(this, SWT.NONE);
		running = new Label(this, SWT.NONE);
		complete = new Label(this, SWT.NONE);
		
		user = new ImageButton(this, "images/friend_icon.png");
		setTaskNum(0, 0, 0);
	}
	
	/**
	 * 设置组件位置大小
	 */
	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		super.setBounds(arg0, arg1, arg2, arg3);		
		rask.setBounds(5, (arg3-17)/2, 170, 17);
		running.setBounds(180, (arg3-17)/2, 170, 17);
		complete.setBounds(355, (arg3-17)/2, 170, 17);
		user.setBounds(arg2-45, 6, 21, 21);
	}
	
	/**
	 * 任务数添加或减少
	 */
	public void setTaskNum(long task, long run, long complete){
		if(!isDisposed()){
			this.rask.setText("当前请求任务："+task);
			this.running.setText("当前运行任务："+run);
			this.complete.setText("当前完成任务："+complete);
		}
	}
	
	/**
	 * 释放资源
	 */
	@Override
	public void dispose(){
		ViewUtil.dispose(backColor);
	}
	
	public void addUserClickListener(Listener l){
		user.addClickListener(l);
	}
}
