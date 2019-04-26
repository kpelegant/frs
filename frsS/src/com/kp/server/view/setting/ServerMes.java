package com.kp.server.view.setting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;

import com.kp.config.Config;
import com.kp.server.view.View;


public class ServerMes extends PageItem {

	private CLabel cserverPort;
	private CLabel cdefaultPath;
	private CLabel cminPort;
	private CLabel cmaxPort;
	private CLabel csliceUp;
	private CLabel cmaxThread;
	private CLabel cmutiBlockNum;
	
	private CLabel tserverPort;
	private CLabel tdefaultPath;
	private CLabel tminPort;
	private CLabel tmaxPort;
	private CLabel tsliceUp;
	private CLabel tmaxThread;
	private CLabel tmutiBlockNum;
	
	
	public ServerMes(Composite parent, View view) {
		super(parent, "服务器信息", view);
	}

	@Override
	protected void layout(Composite parent) {
		
		cserverPort = new CLabel(parent, SWT.RIGHT);
		cserverPort.setText("服务端口：");
		cserverPort.setBackground(clickColor);
		tserverPort = new CLabel(parent, SWT.NONE);
		tserverPort.setBackground(clickColor);
		
		cdefaultPath = new CLabel(parent, SWT.RIGHT);
		cdefaultPath.setText("默认存储路径：");
		cdefaultPath.setBackground(clickColor);
		tdefaultPath = new CLabel(parent, SWT.NONE);
		tdefaultPath.setBackground(clickColor);
		
		cminPort = new CLabel(parent, SWT.RIGHT);
		cminPort.setText("最小端口：");
		cminPort.setBackground(clickColor);
		tminPort = new CLabel(parent, SWT.NONE);
		tminPort.setBackground(clickColor);
		

		cmaxPort = new CLabel(parent, SWT.RIGHT);
		cmaxPort.setText("最大端口：");
		cmaxPort.setBackground(clickColor);
		tmaxPort = new CLabel(parent, SWT.NONE);
		tmaxPort.setBackground(clickColor);
		
		csliceUp = new CLabel(parent, SWT.RIGHT);
		csliceUp.setText("任务池切换时间(ms)：");
		csliceUp.setBackground(clickColor);
		tsliceUp = new CLabel(parent, SWT.NONE);
		tsliceUp.setBackground(clickColor);
		
		cmaxThread = new CLabel(parent, SWT.RIGHT);
		cmaxThread.setText("最大同时运行任务数量(个)：");
		cmaxThread.setBackground(clickColor);
		tmaxThread = new CLabel(parent, SWT.NONE);
		tmaxThread.setBackground(clickColor);
		
		
		cmutiBlockNum = new CLabel(parent, SWT.RIGHT);
		cmutiBlockNum.setText("启动双线程文件块数量(块)：");
		cmutiBlockNum.setBackground(clickColor);
		tmutiBlockNum = new CLabel(parent, SWT.NONE);
		tmutiBlockNum.setBackground(clickColor);
	}
	
	protected void setBounds(int width, int height) {
		cserverPort.setBounds((width-300)/2-40, 10, 180, 40);
		tserverPort.setBounds((width-300)/2+140, 15, 180, 30);
		cdefaultPath.setBounds((width-300)/2-40, 50, 180, 40);
		tdefaultPath.setBounds((width-300)/2+140, 55, 180, 30);
		cminPort.setBounds((width-300)/2-40, 90, 180, 40);
		tminPort.setBounds((width-300)/2+140, 95, 180, 30);
		cmaxPort.setBounds((width-300)/2-40, 130, 180, 40);
		tmaxPort.setBounds((width-300)/2+140, 135, 180, 30);
		csliceUp.setBounds((width-300)/2-40, 170, 180, 40);
		tsliceUp.setBounds((width-300)/2+140, 175, 180, 30);
		cmaxThread.setBounds((width-300)/2-40, 210, 180, 40);
		tmaxThread.setBounds((width-300)/2+140, 215, 180, 30);
		cmutiBlockNum.setBounds((width-300)/2-40, 250, 180, 40);
		tmutiBlockNum.setBounds((width-300)/2+140, 255, 180, 30);
	}
	
	public void showData() {
		tserverPort.setText(Config.serverPort+"");
		tdefaultPath.setText(Config.defaultPath);
		tminPort.setText(Config.minPort+"");
		tmaxPort.setText(Config.maxPort+"");
		tsliceUp.setText(Config.sliceUpTime.toString());
		tmaxThread.setText(Config.runningTaskNum.toString());
		tmutiBlockNum.setText(Config.doubleThreadBlockNum.toString());
	}
	
	public void dispose() {
		super.dispose();
	};
}
