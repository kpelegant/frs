package com.kp.client.view.setting;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.kp.client.view.View;
import com.kp.util.view.Text2;
import com.kp.util.view.ViewUtil;


public class ModifyPassword extends PageItem {

	private CLabel coldpwd;
	private Text2 toldpwd;
	private CLabel cnewpwd;
	private Text2 tnewpwd;
	private CLabel cnewpwd2;
	private Text2 tnewpwd2;
	
	private Button ok;
	
	public ModifyPassword(Composite parent, View view) {
		super(parent, "修改密码", view);
	}

	@Override
	protected void layout(Composite parent) {
		coldpwd = new CLabel(parent, SWT.RIGHT);
		coldpwd.setText("旧密码：");
		coldpwd.setBackground(clickColor);
		toldpwd = new Text2(parent, SWT.PASSWORD, new RGB(173, 214, 244));
		cnewpwd = new CLabel(parent, SWT.RIGHT);
		cnewpwd.setText("新密码：");
		cnewpwd.setBackground(clickColor);
		tnewpwd = new Text2(parent, SWT.PASSWORD, new RGB(173, 214, 244));
		cnewpwd2 = new CLabel(parent, SWT.RIGHT);
		cnewpwd2.setText("确   认：");
		cnewpwd2.setBackground(clickColor);
		tnewpwd2 = new Text2(parent, SWT.PASSWORD, new RGB(173, 214, 244));
		ok = new Button(parent, SWT.NONE);
		ok.setText("修改");
		ok.setBackground(normalColor);
		
		ok.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event arg0) {
				if(toldpwd.getText().length() == 0){
					ViewUtil.hint(v.getShell(), "请输入旧密码");
				}else if(tnewpwd.getText().length() == 0){
					ViewUtil.hint(v.getShell(), "请输入新密码");
				}else if(tnewpwd2.getText().length() == 0){
					ViewUtil.hint(v.getShell(), "请确认新密码");
				}else if(tnewpwd.getText().compareTo(tnewpwd2.getText()) != 0){
					ViewUtil.hint(v.getShell(), "新密码和确认密码不一致");
				}else{
					try {
						v.getUserController().modifyPassword(toldpwd.getText(), tnewpwd.getText());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	@Override
	protected void setBounds(int width, int height) {
		coldpwd.setBounds((width-235)/2-10, 50, 55, 40);
		toldpwd.setBounds((width-235)/2+60-10, 55, 180, 30);
		cnewpwd.setBounds((width-235)/2-10, 90, 55, 40);
		tnewpwd.setBounds((width-235)/2+60-10, 95, 180, 30);
		cnewpwd2.setBounds((width-235)/2-10, 130, 55, 40);
		tnewpwd2.setBounds((width-235)/2+60-10, 135, 180, 30);
		ok.setBounds((width-235)/2+60-10, 195, 80, 30);
	}
	
	@Override
	public void showData() {
		toldpwd.setText("");
		tnewpwd.setText("");
		tnewpwd2.setText("");
	}
	
	@Override
	public void dispose() {
		super.dispose();
		toldpwd.dispose();
		tnewpwd.dispose();
		tnewpwd2.dispose();
	}
}
