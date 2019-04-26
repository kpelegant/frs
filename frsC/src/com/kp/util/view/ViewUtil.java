package com.kp.util.view;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

import com.kp.client.controller.task.UDCSTransmissionBasic;
import com.kp.client.view.tstate.TaskItem;

/*import com.dicd.client.controller.task.UDCSTransmissionBasic;
import com.dicd.client.view.tstate.TaskItem;
import com.dicd.util.file.FileUtil;*/

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
	 * 添加树节点
	 * @param parent
	 * @param text
	 * @return
	 */
	/*public static TreeItem addTreeItem(TreeItem parent, String text){
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setText(text);
		item.setImage(FileUtil.getFolderImage());
		return item;
	}*/
	
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
				box.setMessage(msg==null?"未知错误":msg);
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
	public static int wait(com.kp.client.controller.task.execute.ExecutantBasic...t){
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
	 * 获取任务监听事件处理
	 * @param basic
	 * @param item
	 * @return
	 */
	public static Listener[] getTaskListener(final Shell shell, final UDCSTransmissionBasic basic, final TaskItem item, final String url){
		Listener[] listeners = new Listener[4];
		listeners[0] = new Listener() {//暂停
			public void handleEvent(Event arg0) {
				basic.pause();
				item.showStartButton();
			}
		};
		listeners[1] = new Listener() {//开始
			@Override
			public void handleEvent(Event arg0) {
				basic.goon();
				item.showPause();
			}
		};
		listeners[2] = new Listener() {//关闭
			@Override
			public void handleEvent(Event arg0) {
				MessageBox dialog=new MessageBox(item.getShell(), SWT.YES|SWT.NO);
				dialog.setText("温馨提示");
				dialog.setMessage("确认要取消吗？");
				if(dialog.open() == 64){//64返回确定，128返回取消
					basic.close();
				}
			}
		};
		listeners[3] = new Listener() {//关闭
			@Override
			public void handleEvent(Event arg0) {
				try {
					Runtime.getRuntime().exec("cmd /c start explorer "+url);
				} catch (IOException e) {
					e.printStackTrace();
					ViewUtil.hint(shell, "文件夹打开失败");
				}
			}
		};
		return listeners;
	}
	
	/**
	 * 添加边框
	 * @param c
	 * @param rgb
	 */
	public static void addBorder(final Control c, final RGB rgb){
		PaintListener viewPaintListener = new PaintListener() {
			@Override
			public void paintControl(PaintEvent arg0) {
				GC gc_container_1 = new GC(c);  
				Color color = new Color(Display.getDefault(), rgb);
				gc_container_1.setForeground(color); 
	            gc_container_1.setLineWidth(1);  
	            gc_container_1.drawRoundRectangle(0, 0, c.getSize().x-1, c.getSize().y-1, 3, 3);  
	            gc_container_1.dispose();
	            color.dispose();
			}
		};
		c.addPaintListener(viewPaintListener);
	}
}
