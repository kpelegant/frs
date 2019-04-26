package com.kp.client.view.tstate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;

import com.kp.util.file.FileUtil;
import com.kp.util.view.ImageButton;
import com.kp.util.view.ViewUtil;

/**
 * 单个任务运行状态视图
 * @author ping
 *
 */
public class RunningTaskItem extends ViewForm implements TaskItem {

	private Label icon;//图标	
	private CLabel name;//文件名	
	private Label length;//文件大小和已下载的大小	
	private Label time;//时间	
	private ProgressBar bar;//进度条	
	private Label speed;//速度	
	private ImageButton pause;//暂停	
	private ImageButton start;//开始	
	private ImageButton close;//关闭	
	private ImageButton openFolder;//打开所在文件夹	
	
	private Image iconimage;//图标	
	private String sName;//文件路径
	private String style;
	private Date aheadDate;//上一次设置进度时的时间	
	private BigDecimal aheadcompletelength;//上一次已完成的长度	
	
	public RunningTaskItem(Composite composite, String sName, String style) {
		super(composite, SWT.NONE);
		this.sName = sName;
		this.style = style;
		this.aheadDate = new Date(new Date().getTime()-1000);
		this.aheadcompletelength = new BigDecimal(0);
		init();
	}

	private void init(){
		iconimage = new Image(getDisplay(), FileUtil.getMaxImageData(sName));
		icon = new Label(this, SWT.CENTER);
		icon.setImage(iconimage);
		name = new CLabel(this, SWT.NONE);
		name.setText(sName);		
		this.length = new Label(this, SWT.NONE);		
		time = new Label(this, SWT.CENTER);
		bar = new ProgressBar(this, SWT.NONE);
		bar.setMaximum(100);
		speed = new Label(this, SWT.NONE);
		speed.setText("等待"+style+"...");
		pause = new ImageButton(this, "images/ppause_normal_image.png", "images/ppause_over_image.png", "images/ppause_over_image.png");
		pause.setToolTipText("暂停");
		start = new ImageButton(this, "images/pstart_normal_image.png", "images/pstart_over_image.png", "images/pstart_over_image.png");
		start.setToolTipText("开始");
		close = new ImageButton(this, "images/pclose_normal_image.png", "images/pclose_over_image.png", "images/pclose_over_image.png");
		close.setToolTipText("取消");
		openFolder = new ImageButton(this, "images/pfile_normal_image.png", "images/pfile_over_image.png", "images/pfile_over_image.png");
		openFolder.setToolTipText("打开所在文件夹");
	}
	
	/**设置组件位置*/
	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		super.setBounds(arg0, arg1, arg2, arg3);
		openFolder.setBounds(getSize().x-50, (getSize().y-12)/2, 14, 12);
		close.setBounds(openFolder.getLocation().x-40, (getSize().y-11)/2, 11, 11);
		pause.setBounds(close.getLocation().x-40, (getSize().y-12)/2, 9, 12);
		start.setBounds(close.getLocation().x-40, (getSize().y-12)/2, 9, 12);
		icon.setBounds(5, 10/2, getSize().y-10, getSize().y-10);
		name.setBounds(icon.getLocation().x+icon.getSize().x, (getSize().y-16*2-3)/2, (int)((start.getLocation().x-40-(icon.getLocation().x+icon.getSize().x))*0.3), 16);
		length.setBounds(icon.getLocation().x+icon.getSize().x, name.getLocation().y+name.getSize().y+3, (int)((start.getLocation().x-40-(icon.getLocation().x+icon.getSize().x))*0.3), 16);
		time.setBounds(name.getLocation().x+name.getSize().x, (getSize().y-16)/2, (int)((start.getLocation().x-40-(icon.getLocation().x+icon.getSize().x))*0.3), 16);
		bar.setBounds(time.getLocation().x+time.getSize().x, (getSize().y-15-16-3)/2, (int)((start.getLocation().x-40-(icon.getLocation().x+icon.getSize().x))*0.4), 15);
		speed.setBounds(time.getLocation().x+time.getSize().x, bar.getLocation().y+bar.getSize().y+3, (int)((start.getLocation().x-40-(icon.getLocation().x+icon.getSize().x))*0.4), 16);
		getParent().setSize(getParent().getSize().x, getLocation().y+getSize().y+5);
	}
	
	/**设置已完成长度*/
	public synchronized void addCompleteByte(BigDecimal completelength, BigDecimal alllength){
		if(isDisposed()) return;
		Date date = new Date();
		long time = date.getTime()-aheadDate.getTime();
		//文件传输完成后
		if(completelength.subtract(alllength).doubleValue() >= 0){
			this.time.setText(ViewUtil.toTime(0));
			this.length.setText(ViewUtil.switchToPut(completelength.doubleValue())+"/"+ViewUtil.switchToPut(alllength.doubleValue()));
			bar.setSelection(completelength.multiply(new BigDecimal(100)).divide(alllength, 10, RoundingMode.HALF_DOWN).intValue());
			speed.setText("请等待...");
		}else if(time >= 1000){
			this.length.setText(ViewUtil.switchToPut(completelength.doubleValue())+"/"+ViewUtil.switchToPut(alllength.doubleValue()));
			bar.setSelection(completelength.multiply(new BigDecimal(100)).divide(alllength, 10, RoundingMode.HALF_DOWN).intValue());
			BigDecimal s = completelength.subtract(aheadcompletelength).multiply(new BigDecimal(1000)).divide(new BigDecimal(time), 10, RoundingMode.HALF_DOWN);
			speed.setText(("正在"+style+"文件：")+ViewUtil.switchToPut(s.doubleValue())+"/s");
			this.time.setText(s.intValue()==0?"99 : 99 : 99":ViewUtil.toTime(alllength.subtract(completelength).divide(s, 10, RoundingMode.HALF_DOWN).longValue()));
			aheadcompletelength = completelength;
			aheadDate = date;
		}
	}
	
	/**释放资源*/
	@Override
	public void dispose(){
		if(isDisposed()){
			return;
		}
		setVisible(false);
		ViewUtil.dispose(iconimage);
		super.dispose();
	}
	
	public void addListeners(Listener...listeners){
		if(listeners == null) return;
		pause.addClickListener(listeners.length>0?listeners[0]:null);
		start.addClickListener(listeners.length>1?listeners[1]:null);
		close.addClickListener(listeners.length>2?listeners[2]:null);
		openFolder.addClickListener(listeners.length>3?listeners[3]:null);
	}
	
	public void showStartButton(){
		start.setVisible(true);
		pause.setVisible(false);
		speed.setText("暂停中...");
	}
	
	public void showPause(){
		start.setVisible(false);
		pause.setVisible(true);
		if(bar.getSelection() == 100){
			speed.setText("请等待...");
		}else{
			speed.setText("等待"+style+"...");
		}
	}

	public void updateFileName(String name) {
		this.name.setText(name);
	}
}
