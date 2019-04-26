package com.kp.client.view.tstate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.kp.client.view.View;
import com.kp.util.view.ImageButton;
import com.kp.util.view.ViewUtil;


/**
 * 下载界面管理
 */
public class Transmission extends ViewForm {
	
	private Page page;
	
	//Page元素
	private PageItem upload;
	private PageItem download;
	private PageItem complete;
	
	private ImageButton trans;
	private Color backColor;
	
	public Transmission(View view){
		super(view.getShell(), SWT.NO_TRIM);
		checkSubclass();
		init();
	}
	
	@Override
	protected void checkSubclass(){            
    }
	
	private void init(){
		backColor = new Color(getDisplay(), 10, 10, 10);
		setBackground(backColor);
		trans = new ImageButton(this, "images/transback_normal_image.png", "images/transback_over_image.png", "images/transback_down_image.png");
		trans.addClickListener(transbackListener);
		page = new Page(this);
		upload = new PageItem(this, "正在上传");
		page.addPageItem(upload);
		download = new PageItem(this, "正在下载");
		page.addPageItem(download);
		complete = new PageItem(this, "完成传输");
		page.addPageItem(complete);
		page.setItemToShow(0);
	}
	
	/**设置位置与大小*/
	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3) {
		super.setBounds(arg0, arg1, arg2, arg3);
		trans.setBounds((int)(arg2*0.15)-80, 0, 80, 29);
		page.setBounds((int)(arg2*0.15), 0, arg2-(int)(arg2*0.15), arg3);		
	}
	
	/**关闭该界面事件处理*/
	Listener transbackListener = new Listener() {
		public void handleEvent(Event arg0) {
			Transmission.this.setVisible(false);
			clearTaskRecord();
		}
	};
	
	/**上传任务*/
	public TaskItem addUploadTask(String name){
		RunningTaskItem item = new RunningTaskItem(upload.getComposite(), name, "上传");
		upload.addEmelent(item);
		return item;
	}
	
	/**下载任务*/
	public TaskItem addDownloadTask(String name){
		RunningTaskItem item = new RunningTaskItem(download.getComposite(), name, "下载");
		download.addEmelent(item);
		return item;
	}
	
	/**任务记录*/
	/*public TaskItem addTaskRecord(HistoryRecord history){
		TaskItem item = new HistoryTaskItem(complete.getComposite(), history);
		complete.addEmelent(item);
		return item;
	}*/
	
	/**释放资源*/
	@Override
	public void dispose(){
		page.dispose();
		ViewUtil.dispose(trans);
		ViewUtil.dispose(backColor);
		super.dispose();
	}
	
	/**删除上传SingleTrans*/
	public void deleteUploadTask(TaskItem item){
		upload.deleteItem(item);
	}
	
	/**删除下载SingleTrans*/
	public void deleteDownloadTask(TaskItem item){
		download.deleteItem(item);
	}
	
	/**删除任务记录StoryTrans*/
	public void deleteTaskRecord(TaskItem item){
		complete.deleteItem(item);
	}
	
	public void clearTaskRecord(){
		upload.clearTaskRecord();
		download.clearTaskRecord();
		complete.clearTaskRecord();
	}
	
	public void clearTaskRecord(int num){
		if(num == 1){
			upload.clearTaskRecord();
		}else if(num == 2){
			download.clearTaskRecord();
		}else if(num == 3){
			complete.clearTaskRecord();
		}
	}
}
