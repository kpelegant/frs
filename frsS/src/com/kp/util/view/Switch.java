package com.kp.util.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;


/**
 * 显示不同界面以及不同的响应
 * @author ping
 *
 */
public class Switch {

	//第一种
	private ImageButton button1;
	
	//第二种
	private ImageButton button2;
	
	public enum SwitchBtn{OPEN, CLOSE};
	
	/**
	 * 自定义设置按钮一张图片
	 * @param parent
	 * @param normalImage1
	 * @param normalImage2
	 */
	public Switch(Composite parent,String normalImage1,String normalImage2){
		this(parent,normalImage1, null, null, normalImage2, null, null);
	}
	
	/**
	 * @param parent
	 * @param normalImage1     按钮1
	 * @param mouseOverImage1
	 * @param mouseDownImage1
	 * @param normalImage2     按钮2
	 * @param mouseOverImage2
	 * @param mouseDownImage2
	 */
	public Switch(Composite parent, String normalImage1,String mouseOverImage1,String mouseDownImage1,String normalImage2,String mouseOverImage2,String mouseDownImage2){
		button1 = new ImageButton(parent, normalImage1, mouseOverImage1, mouseDownImage1);
		button2 = new ImageButton(parent, normalImage2, mouseOverImage2, mouseDownImage2);
		switchBtn(SwitchBtn.CLOSE);
	}
	
	/**
	 * 设置组件的位置及大小
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		button1.setBounds(arg0, arg1, arg2, arg3);
		button2.setBounds(arg0, arg1, arg2, arg3);
	}
	
	/***
	 * 视图监听函数
	 * @param listener1 按钮1监听事件处理
	 * @param listener2 按钮2监听事件处理
	 */
	public void addSwitchListener(Listener listener1, Listener listener2){
		button1.addClickListener(listener1);
		button2.addClickListener(listener2);
	}
	
	/***
	 * 设置组件提示信息
	 * @param arg0 第一个按钮提示信息
	 * @param arg1 第二个按钮提示信息
	 */
	public void setToolTipText(String arg0, String arg1){
		button1.setToolTipText(arg0);
		button2.setToolTipText(arg1);
	}
	
	/**
	 * 视图切换事件处理
	 */
	public void switchBtn(SwitchBtn s){
		if(s == SwitchBtn.OPEN){
			button1.setVisible(false);
			button2.setVisible(true);
		}else if(s == SwitchBtn.CLOSE){
			button1.setVisible(true);
			button2.setVisible(false);
		}
	}
	
	/**
	 * 释放资源
	 * */
	public void dispose(){
		ViewUtil.dispose(button1, button2);
	}
}
