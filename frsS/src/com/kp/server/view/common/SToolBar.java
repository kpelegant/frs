package com.kp.server.view.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

import com.kp.util.view.Switch;
import com.kp.util.view.Switch.SwitchBtn;
import com.kp.util.view.ViewUtil;


/**
 * 视图工具栏
 * @author ping
 *
 */
public class SToolBar extends ViewForm {

	//颜色
	private Color backColor;
	
	//服务器开关
	private Switch openclose;
	
	public SToolBar(Composite composite){
		super(composite, SWT.NONE);
		init();
	}
	
	/**
	 * 初始化组件
	 */
	private void init(){
		backColor = new Color(getDisplay(), 236, 242, 247);
		//backColor = new Color(getDisplay(), 14, 142, 231);
		setBackground(backColor);
		openclose = new Switch(this, "images/close_server_image.png", "images/open_server_image.png");
		openclose.setToolTipText("打开服务器", "关闭服务器");
	}
	
	/**
	 * 设置组件位置大小
	 */
	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		super.setBounds(arg0, arg1, arg2, arg3);
		openclose.setBounds(arg2-90, (arg3-29)/2, 70, 29);
	}
	
	/**
	 * 释放资源
	 */
	@Override
	public void dispose(){
		openclose.dispose();
		ViewUtil.dispose(backColor);
	}
	
	/**
	 * 设置服务器启动、关闭监听事件处理
	 */
	public void addSwitchListener(Listener listener1, Listener listener2){
		openclose.addSwitchListener(listener1, listener2);
	}
	
	/**
	 * 开关切换事件处理
	 */
	public void switchBtn(SwitchBtn s){
		openclose.switchBtn(s);
	}
}
