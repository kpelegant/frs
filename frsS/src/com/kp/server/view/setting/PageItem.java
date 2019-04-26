package com.kp.server.view.setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

import com.kp.server.view.View;
import com.kp.util.view.ViewUtil;

/**
 * 设置视图的每一页设计
 */
public class PageItem {
	
	protected View v;
	
	//点击
	private CLabel click;
	//正常界面
	private CLabel normal;
	//界面
	private Composite  view;
	protected Color normalColor, clickColor;
	
	public PageItem(Composite parent, String text, View v){
		this.v = v;
		normal = new CLabel(parent, SWT.CENTER);
		normalColor = new Color(parent.getDisplay(), 229, 245, 255);
		normal.setBackground(normalColor);
		normal.setText(text);
		click = new CLabel(parent, SWT.CENTER);
		clickColor = new Color(parent.getDisplay(), 255, 255, 255);
		click.setBackground(clickColor);
		click.setText(text);
		click.setVisible(true);
		view = new Composite(parent, SWT.NONE);
		view.setBackground(clickColor);
		view.setVisible(false);
		
		layout(view);
	}
	
	/**
	 * 设置视图位置
	 * @param arg0 按钮X点
	 * @param arg1 按钮Y点
	 * @param arg2 按钮长度
	 * @param arg3 按钮宽度
	 * @param arg4 主视图X点
	 * @param arg5 主视图Y点
	 * @param arg6 主视图长度
	 * @param arg7 主视图宽度
	 */
	public void setBounds(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7){
		click.setBounds(arg0, arg1, arg2, arg3);
		normal.setBounds(arg0, arg1, arg2, arg3);
		view.setBounds(arg4, arg5, arg6, arg7);
		setBounds(view.getSize().x, view.getSize().y);
	}
	
	/**
	 * 点击事件处理
	 */
	public void click(){
		click.setVisible(true);
		normal.setVisible(false);
		view.setVisible(true);
		showData();
	}
	
	/**
	 * 还原事件处理
	 */
	public void reset(){
		click.setVisible(false);
		normal.setVisible(true);
		view.setVisible(false);
	}
	
	/**
	 * 设置界面布局
	 */
	protected void layout(Composite parent){
		
	}
	
	/**
	 * 设置扩展对象的位置
	 * @param width 宽度
	 * @param height 高度
	 */
	protected void setBounds(int width, int height){
		
	}
	
	public void showData(){
		
	}

	/**
	 * 获取设置操作界面颜色
	 * @return
	 */
	public Color getFormColor(){
		return clickColor;
	}
	
	/**
	 * 释放资源
	 */
	public void dispose(){
		ViewUtil.dispose(normalColor, clickColor);
	}
	
	/**
	 * 点击事件处理
	 * @param listener 处理方式
	 */
	public void addOnClickListener(Listener listener){
		normal.addListener(SWT.MouseUp, listener);
	}
}
