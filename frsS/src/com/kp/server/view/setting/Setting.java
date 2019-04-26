package com.kp.server.view.setting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.kp.server.view.View;
import com.kp.util.view.ImageButton;
import com.kp.util.view.ViewUtil;


/**
 * 设置界面入口，主界面视图
 */
public class Setting {
	//Shell实例
	private Shell shell;
	private CLabel title;
	//关闭按钮
	private ImageButton close;
	
	private ViewForm form;
	
	//分页视图
	private List<PageItem> setting;
	private PageItem now;
	
	private Color formColor, shellColor, whiteColor;
	
	/**
	 * 创建一个设置界面对象
	 * @param shell Shell实例
	 */
	public Setting(View view){
		this.shell = new Shell(view.getShell(), SWT.APPLICATION_MODAL);
		this.shell.setText("设置");
		shellColor = new Color(this.shell.getDisplay(), 14, 142, 231);
		this.shell.setBackground(shellColor);
		this.shell.addListener(SWT.MouseDown, mouseMoveListener);  
		this.shell.addListener(SWT.MouseMove, mouseMoveListener); 
		title = new CLabel(this.shell, SWT.NONE);
		title.setBackground(shellColor);
		title.addListener(SWT.MouseDown, mouseMoveListener);  
		title.addListener(SWT.MouseMove, mouseMoveListener);
		whiteColor = new Color(this.shell.getDisplay(), 255, 255, 255);
		title.setForeground(whiteColor);
		title.setText("设置");
		close = new ImageButton(this.shell, "images/close_normal_image.png", "images/close_over_image.png", "images/close_down_image.png");
		close.setToolTipText("关闭");
		close.addClickListener(closeOnClickListener);
		
		form = new ViewForm(this.shell, SWT.NONE);
		formColor = new Color(form.getDisplay(), 229, 245, 255);
		form.setBackground(formColor);
		setting = new ArrayList<PageItem>();
		addPageItem(new ServerMes(form, view));
		addPageItem(new ServerSet(form, view));
		addPageItem(new UserMes(form, view));
	}
	
	/**
	 * 设置视图的位置及大小
	 * @param arg0 X点
	 * @param arg1 Y点
	 * @param arg2 长度
	 * @param arg3 宽度
	 */
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		shell.setBounds(arg0, arg1, arg2, arg3);
		title.setBounds(5, 4, 50, 24);
		close.setBounds(shell.getSize().x-33, 0, 30, 18);
		
		form.setBounds(0, 33, arg2, shell.getSize().y-33);
		
		for(int i=0; i<setting.size(); i++){
			//setting.get(i).setBounds(0, i*40, 80, 40, 80, 0, form.getSize().x-80, form.getSize().y);
			setting.get(i).setBounds(0, i*40, 80, 40, 80, 0, form.getSize().x-40, form.getSize().y);
		}
	}
	
	public void show(int id){
		if(setting.size() > id){
			setting.get(id).click();
			now = setting.get(id);
			setVisible(true);
		}
	}
	
	/**
	 * 设置视图是否可见
	 * @param arg0 true为可见，false为不可见
	 */
	public void setVisible(boolean arg0){
		shell.setVisible(arg0);
	}
	
	/**
	 * 添加一个视图
	 * @param item 视图
	 */
	public void addPageItem(final PageItem item){
		item.addOnClickListener(new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				item.click();
				if(now != null){
					now.reset();
				}
				now = item;
			}		
		});
		setting.add(item);
	}
	
	/**
	 * 释放资源
	 */
	public void dispose(){
		ViewUtil.dispose(close);
		ViewUtil.dispose(formColor, shellColor, whiteColor);
		for(int i=0; i<setting.size(); i++){
			setting.get(i).dispose();
		}
		shell.dispose();
	}
	
	public boolean isDisposed(){
		return shell.isDisposed();
	}
	
	/**
	 * 关闭按钮点击事件处理
	 */
	Listener closeOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			dispose();
		}
	};
	
	/**
	 * 窗口移动监听程序
	 */
	Listener mouseMoveListener = new Listener() {  
	    int startX, startY;
	    @Override
	    public void handleEvent(Event e) {  
	        if (e.type == SWT.MouseDown && e.button == 1) {
	        	if(e.y < 20000){
	        		startX = e.x;  
	            	startY = e.y; 
	        	}
	        }  
	        if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
		    	Point p = shell.toDisplay(e.x, e.y);  
		        p.x -= startX;  
		        p.y -= startY;  
		        shell.setLocation(p); 
	        }  
	    } 
	};

	/**
	 * 更新界面
	 */
	public void update() {
		if(now != null){
			now.click();
		}
	}
}
