package com.kp.util.view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

import com.kp.server.controller.task.execute.ExecutantBasic;
import com.kp.server.controller.task.execute.UploadFileExecute;
/*
import com.dicd.server.controller.task.execute.CalculateHash;
import com.dicd.server.controller.task.execute.ExecutantBasic;*/

/**
 * 视图界面工具
 * @author Mr Wang
 *
 */
public class ViewUtil {
	
	/**
	 * 将窗口居中
	 * @param display
	 * @param shell
	 */
	public static void centerShell(Display display,Shell shell){ 
		Rectangle displayBounds = display.getPrimaryMonitor().getBounds(); 
		Rectangle shellBounds = shell.getBounds();
		int x = displayBounds.x + (displayBounds.width - shellBounds.width)>>1; 
		int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1; 
		shell.setLocation(x, y);
	}
	
	/**
	 * 释放视图资源
	 * @param c
	 */
	public static void dispose(Resource...r){
		for(int i=0; i<r.length; i++){
			if(r[i] != null){
				r[i].dispose();
			}
		}
	}
	
	/**
	 * 释放图片按钮资源
	 * @param m
	 */
	public static void dispose(Widget...w){
		for(int i=0; i<w.length; i++){
			if(w[i] != null){
				w[i].dispose();
			}
		}
	}
	
	/**
	 * 在工具栏中添加一个空白的、无法操作的Item来分割两个可操作的Item
	 * @param tool
	 * @param length 空白长度
	 */
	public static void addBlankToolItem(ToolBar tool, int length){
		if(length <= 0) return;
		ToolItem item = new ToolItem(tool, SWT.NONE);
		String text = new String("");
		for(int i=0; i<length; i++){
			text += " ";
		}
		item.setText(text);
		item.setEnabled(false);
	}
	
	/**
	 * 在工具栏中添加一个可操作按钮
	 * @param tool
	 * @param picture
	 * @param text
	 * @param tipText
	 * @return
	 */
	public static ToolItem addToolItem(ToolBar tool, String picture, String text, String tipText){
		ToolItem item = new ToolItem(tool, SWT.NONE);
		item.setImage(new Image(tool.getDisplay(), picture));
		item.setText(text);
		item.setToolTipText(tipText);
		return item;
	}
	
	/**
	 * 数据单位转换成String输出
	 */
	public static String switchToPut(double num){
		return switchToPut( num, 0);
	}
	
	/**
	 * 循环自动转换--数据单位转换成String输出
	 */
	public static String switchToPut(double num, int flag){
		String[] dw = {"B", "KB", "MB", "GB"};
		if(num >=1024 && flag < dw.length){
			num = num/1024;
			return switchToPut(num, flag+1);
		}else{
			return String.format("%.2f", num)+dw[flag];
		}
	}
	
	/**
	 * 将秒转换成时间
	 */
	public static String toTime(long mm){
		Integer h = 0, m = 0, s = 0;
		s = (int) (mm % 60);
		mm = mm / 60;
		m = (int) (mm % 60);
		mm = mm / 60;
		h = (int) mm;
		return toBit(h, 2)+" : "+toBit(m, 2)+" : "+toBit(s, 2);
	}
	
	/**
	 * 数字转换成字符串时显示的最少位数
	 */
	public static String toBit(Integer num, int bit){
		String strNum = num.toString();
		for(int i=strNum.length(); i<bit; i++){
			strNum = "0" + strNum;
		}
		return strNum;
	}
	
	/**
	 * 添加菜单Item
	 * @param menu
	 * @param text
	 * @return
	 */
	public static MenuItem addMenuItem(Menu menu, String text){
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText(text);
		return item;
	}
	
	/**
	 * 提示窗口
	 * @return
	 */
	public static void hint(final Shell shell, final String msg){
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				MessageBox box = new MessageBox(shell);
				box.setText("温馨提示");
				box.setMessage(msg);
				box.open();
			}
		});
	}
	
	/**
	 * 异步执行
	 * @param run
	 */
	public static void asyncExec(Runnable run){
		Display.getDefault().asyncExec(run);
	}
	
	/**
	 * 线程等待
	 */
	public static int wait(ExecutantBasic...t){
		int flag = 0;
		for(int i=0; i<t.length; i++){
			if(t[i] != null){
				try {
					t[i].join(0);
					if(t[i].isSuccess()){
						flag++;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	
	
	public static int wait(UploadFileExecute...t){
		int flag = 0;
		for(int i=0; i<t.length; i++){
			if(t[i] != null){
				try {
					t[i].join(0);
					if(t[i].isSuccess()){
						flag++;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	/**
	 * 线程等待
	 */
	/*public static int wait(List<CalculateHash> t){
		ExecutantBasic[] basic = new ExecutantBasic[t.size()];
		for(int i=0; i<t.size(); i++){
			basic[i] = t.get(i);
		}
		return wait(basic);
	}*/
}
