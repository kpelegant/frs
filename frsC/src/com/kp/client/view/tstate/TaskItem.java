package com.kp.client.view.tstate;

import java.math.BigDecimal;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * 单个任务状态视图
 * @author ping
 *
 */
public interface TaskItem {
	
	public void setBounds(int arg0, int arg1, int arg2, int arg3);
	
	public void addCompleteByte(BigDecimal completelength, BigDecimal alllength);
	
	public void dispose();
	
	public void addListeners(Listener...listeners);
	
	public void showStartButton();
	
	public void showPause();
	
	public void updateFileName(String name);
	
	public Shell getShell();
	
	public boolean isDisposed();
}
