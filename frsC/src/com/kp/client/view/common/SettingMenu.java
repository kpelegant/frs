package com.kp.client.view.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import com.kp.util.view.ViewUtil;


/**
 * 设置按钮显示的菜单
 */
public class SettingMenu {
	
	//显示的菜单
	private Menu menu;
	
	//存储所有的MenuItem
	private List<MenuItem> menuItem;
	
	public SettingMenu(Shell parent, int style){		
		menu = new Menu(parent, style);
		menuItem = new ArrayList<MenuItem>();
	}
	
	public SettingMenu(Shell parent){
		this(parent, SWT.POP_UP);
	}
	
	/**
	 * 在菜单中添加一项
	 * @param text 该项显示的文本
	 * @param selectionListener 该项的监听函数
	 */
	public void addMenuItem(String text, Listener listener){
		MenuItem it = ViewUtil.addMenuItem(menu, text);
		it.addListener(SWT.Selection, listener);
		menuItem.add(it);
	}
	
	/**
	 * 设置菜单是否显示
	 */
	public void setVisible(Boolean visible){
		menu.setVisible(visible);
	}
	
	/**
	 * 设置菜单位置
	 */
	public void setLocation(int arg0, int arg1){
		menu.setLocation(arg0, arg1);
	}
	
	/**
	 * 设置菜单位置
	 */
	public void setLocation(Point arg0){
		menu.setLocation(arg0);
	}
}
