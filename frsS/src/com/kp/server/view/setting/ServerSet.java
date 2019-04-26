package com.kp.server.view.setting;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.kp.config.Config;
import com.kp.server.view.View;
import com.kp.util.view.Text2;
import com.kp.util.view.ViewUtil;


/**
 * 服务端设置
 * @author Mr Wang
 *
 */
public class ServerSet extends PageItem {

	private CLabel cdefaultPath;
	private CLabel cminPort;
	private CLabel cmaxPort;
	private CLabel csliceUp;
	private CLabel cmaxThread;
	private CLabel cmutiBlockNum;
	
	private Text2 tdefaultPath;
	private Text2 tminPort;
	private Text2 tmaxPort;
	private Text2 tsliceUp;
	private Text2 tmaxThread;
	private Text2 tmutiBlockNum;
	
	private Button ok;
	
	public ServerSet(Composite parent, View view) {
		super(parent, "服务端设置", view);
	}

	@Override
	protected void layout(Composite parent) {
		/**
		 * 只能输入数字
		 */
		Listener writeNumListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				for(char c : e.text.toCharArray()){
					if("0123456789".indexOf(c) < 0){
						e.doit = false;
						return;
					}
				}
				e.doit = true;
			}
		};
		
		Listener writeStringListener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				for(char c : e.text.toCharArray()){
					if("abcdefghijklmopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ:\\/".indexOf(c) < 0){
						e.doit = false;
						return;
					}
				}
				e.doit = true;
			}
		};
		
		cdefaultPath = new CLabel(parent, SWT.RIGHT);
		cdefaultPath.setText("默认存储路径：");
		cdefaultPath.setBackground(clickColor);
		tdefaultPath = new Text2(parent, SWT.NONE, new RGB(173, 214, 244));
		tdefaultPath.addListener(SWT.Verify, writeStringListener);
		
		cminPort = new CLabel(parent, SWT.RIGHT);
		cminPort.setText("最小端口：");
		cminPort.setBackground(clickColor);
		tminPort = new Text2(parent, SWT.NONE, new RGB(173, 214, 244));
		tminPort.addListener(SWT.Verify, writeNumListener);

		cmaxPort = new CLabel(parent, SWT.RIGHT);
		cmaxPort.setText("最大端口：");
		cmaxPort.setBackground(clickColor);
		tmaxPort = new Text2(parent, SWT.NONE, new RGB(173, 214, 244));
		tmaxPort.addListener(SWT.Verify, writeNumListener);
		
		csliceUp = new CLabel(parent, SWT.RIGHT);
		csliceUp.setText("任务池切换时间(ms)：");
		csliceUp.setBackground(clickColor);
		tsliceUp = new Text2(parent, SWT.NONE, new RGB(173, 214, 244));
		tsliceUp.addListener(SWT.Verify, writeNumListener);
		
		cmaxThread = new CLabel(parent, SWT.RIGHT);
		cmaxThread.setText("最大同时运行任务数量(个)：");
		cmaxThread.setBackground(clickColor);
		tmaxThread = new Text2(parent, SWT.NONE, new RGB(173, 214, 244));
		tmaxThread.addListener(SWT.Verify, writeNumListener);
		
		
		cmutiBlockNum = new CLabel(parent, SWT.RIGHT);
		cmutiBlockNum.setText("启动双线程文件块数量(块)：");
		cmutiBlockNum.setBackground(clickColor);
		tmutiBlockNum = new Text2(parent, SWT.NONE, new RGB(173, 214, 244));
		tmutiBlockNum.addListener(SWT.Verify, writeNumListener);
		
		ok = new Button(parent, SWT.NONE);
		ok.setText("保存");
		ok.setBackground(normalColor);
		ok.addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if(cdefaultPath.getText().length() == 0){
					ViewUtil.hint(v.getShell(), "路径不能为空");
				}else if(tsliceUp.getText().length() == 0 || Double.parseDouble(tsliceUp.getText())<=0){
					ViewUtil.hint(v.getShell(), "任务池切换时间必填且大于0");
				}else if(tmaxThread.getText().length() == 0 || Double.parseDouble(tmaxThread.getText())<=0){
					ViewUtil.hint(v.getShell(), "最大同时运行任务数量必填且大于0");
				}else if(cminPort.getText().length() == 0 || Double.parseDouble(tminPort.getText())<=0){
					ViewUtil.hint(v.getShell(), "最小端口必填且大于0");
				}else if(cmaxPort.getText().length() == 0 || Double.parseDouble(tmaxPort.getText())<=0){
					ViewUtil.hint(v.getShell(), "最大端口必填且大于0");
				}else if(tmutiBlockNum.getText().length() == 0 || Double.parseDouble(tmutiBlockNum.getText())<=1){
					ViewUtil.hint(v.getShell(), "启动双线程文件块数量且大于1");
				}else{
					String defaultPath = tdefaultPath.getText();
					int minPort = Integer.parseInt(tminPort.getText());
					int maxPort = Integer.parseInt(tmaxPort.getText());
					long sliceUpTime = Long.parseLong(tsliceUp.getText());
					int runningTaskNum = Integer.parseInt(tmaxThread.getText());
					int doubleThreadBlockNum = Integer.parseInt(tmutiBlockNum.getText());
					
					try {
						Config.update(defaultPath, minPort, maxPort, sliceUpTime, runningTaskNum, doubleThreadBlockNum);
						ViewUtil.hint(v.getShell(), "保存成功");
					} catch (IOException e) {
						e.printStackTrace();
						ViewUtil.hint(v.getShell(), "保存出现异常");
					}
				}
			}
		});
	}
	
	@Override
	protected void setBounds(int width, int height) {
		cdefaultPath.setBounds((width-300)/2-20, 10, 180, 30);
		tdefaultPath.setBounds((width-300)/2+160, 15, 120, 30);
		cminPort.setBounds((width-300)/2-20, 50, 180, 30);
		tminPort.setBounds((width-300)/2+160, 55, 120, 30);
		cmaxPort.setBounds((width-300)/2-20, 90, 180, 30);
		tmaxPort.setBounds((width-300)/2+160, 95, 120, 30);
		csliceUp.setBounds((width-300)/2-20, 130, 180, 30);
		tsliceUp.setBounds((width-300)/2+160, 135, 120, 30);
		cmaxThread.setBounds((width-300)/2-20, 170, 180, 30);
		tmaxThread.setBounds((width-300)/2+160, 175, 120, 30);
		cmutiBlockNum.setBounds((width-300)/2-20, 210, 180, 30);
		tmutiBlockNum.setBounds((width-300)/2+160, 215, 120, 30);
		ok.setBounds((width-300)/2+100, 255, 80, 30);
	}
	
	@Override
	public void showData() {
		tdefaultPath.setText(Config.defaultPath);
		tminPort.setText(Config.minPort+"");
		tmaxPort.setText(Config.maxPort+"");
		tsliceUp.setText(Config.sliceUpTime.toString());
		tmaxThread.setText(Config.runningTaskNum.toString());
		tmutiBlockNum.setText(Config.doubleThreadBlockNum.toString());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		tdefaultPath.dispose();
		tminPort.dispose();
		tmaxPort.dispose();
		tsliceUp.dispose();
		tmaxThread.dispose();
		tmutiBlockNum.dispose();
	}
}
