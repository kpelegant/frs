package com.kp.client.view.setting;

import java.text.SimpleDateFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.kp.client.view.View;
import com.kp.entity.db.User;
import com.kp.util.view.ImageButton;
import com.kp.util.view.Text2;


public class PersonMes extends PageItem {

	private CLabel cuser;
	private CLabel cregTime;
	
	private CLabel tuser;
	private CLabel tregTime;
	
	public PersonMes(Composite parent, View view) {
		super(parent, "个人信息", view);
	}

	@Override
	protected void layout(Composite parent) {
		cuser = new CLabel(parent, SWT.RIGHT);
		cuser.setText("账号：");
		cuser.setBackground(clickColor);
		tuser = new CLabel(parent, SWT.NONE);
		tuser.setBackground(clickColor);
		
		
		cregTime = new CLabel(parent, SWT.RIGHT);
		cregTime.setText("注册时间：");
		cregTime.setBackground(clickColor);
		tregTime = new CLabel(parent, SWT.NONE);
		tregTime.setBackground(clickColor);

	}
	
	@Override
	protected void setBounds(int width, int height) {
		cuser.setBounds((width-220)/2, 60, 70, 40);
		tuser.setBounds((width-220)/2+70, 60, 150, 40);
		cregTime.setBounds((width-220)/2, 110, 70, 40);
		tregTime.setBounds((width-220)/2+70, 110, 150, 40);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void showData() {
		User u = v.getUserController().getUser();
		tuser.setText(u.getUsername());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tregTime.setText(formatter.format(u.getRegTime()));
	}
	
	@Override
	public void dispose() {
		super.dispose();
	};
}
