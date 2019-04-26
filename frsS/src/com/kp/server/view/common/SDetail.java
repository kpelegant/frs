package com.kp.server.view.common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.kp.util.view.ViewUtil;


/**
 * 服务端运行详细详细
 * @author ping
 *
 */
public class SDetail extends ViewForm {
	
	//颜色
	private Color backColor;
	//提示语
	private Label title;
	//清空文本
	private Button clear;
	//显示详细信息
	private Text detail;
	
	public SDetail(Composite composite){
		super(composite, SWT.NONE);
		init();
	}
	
	/**
	 * 初始化组件
	 * */
	private void init(){
		backColor = new Color(getDisplay(), 255, 255, 255);
		setBackground(backColor);
		
		title = new Label(this, SWT.NONE);
		title.setText("详细信息 : ");
		
		clear = new Button(this, SWT.NONE);
		clear.setText("清空");
		clear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				detail.setText("");
			}
		});
		
		detail = new Text(this, SWT.MULTI|SWT.BORDER|SWT.WRAP|SWT.V_SCROLL|SWT.READ_ONLY);
		detail.setBackground(backColor);
	}
	
	/**
	 * 设置组件位置大小
	 */
	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		super.setBounds(arg0, arg1, arg2, arg3);
		title.setBounds(4, 6, 60, 17);
		clear.setBounds(arg2-80, 3, 60, 23);
		detail.setBounds(4, 29, arg2-10, arg3-29);
	}
	
	/**
	 * 添加文本
	 */
	public void append(String arg0){
		if(!isDisposed()){
			detail.append(arg0);
		}
	}
	
	/**
	 * 释放资源
	 */
	@Override
	public void dispose(){
		ViewUtil.dispose(backColor);
	}
}
