package com.kp.client.view.tstate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

import com.kp.util.view.ViewUtil;


/**自定义分页控件元素**/
public class PageItem {
	//点击
	private CLabel click;
	//正常界面
	private Composite view;
	private CLabel normal;
	//界面
	private ScrolledComposite scrolledComposite;
	private List<TaskItem> st;
	private Color color1, color2;
	
	public PageItem(Composite parent, String text){
		init(parent, text);
	}
	
	public void init(Composite parent, String text){
		normal = new CLabel(parent, SWT.CENTER);
		color1 = new Color(parent.getDisplay(), 169, 169, 169);
		normal.setBackground(color1);
		normal.setText(text);
		click = new CLabel(parent, SWT.CENTER);
		color2 = new Color(parent.getDisplay(), 255, 255, 255);
		click.setBackground(color2);
		click.setText(text);
		click.setVisible(false);
		scrolledComposite = new ScrolledComposite(parent, SWT.BORDER|SWT.V_SCROLL);
		scrolledComposite.setVisible(false);
		view = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(view);
		scrolledComposite.setBackground(color2);
		view.setBackground(color2);
		view.setToolTipText(text);
		st = new ArrayList<TaskItem>();
	}
	
	/**添加任务*/
	public void addEmelent(TaskItem item){
		st.add(item);
		setViewBounds(scrolledComposite.getLocation().x, scrolledComposite.getLocation().y+1, scrolledComposite.getSize().x-1, scrolledComposite.getSize().y-2);	
	}
	
	public CLabel getClick() {
		return click;
	}

	public CLabel getNormal() {
		return normal;
	}
	
	public Composite getComposite(){
		return view;
	}
	
	/**点击该页面时*/
	public void click(){
		click.setVisible(true);
		normal.setVisible(false);
		scrolledComposite.setVisible(true);
	}
	
	/**取消选择*/
	public void reset(){
		click.setVisible(false);
		normal.setVisible(true);
		scrolledComposite.setVisible(false);
	}
	
	/**设置表头位置和大小*/
	public void setHeaderBounds(int arg0, int arg1, int arg2, int arg3){
		normal.setBounds(arg0, arg1, arg2, arg3);
		click.setBounds(arg0, arg1, arg2, arg3);
	}
	
	/**设置view位置和大小*/
	public void setViewBounds(int arg0, int arg1, int arg2, int arg3){
		scrolledComposite.setBounds(arg0, arg1-1, arg2+1, arg3+2);
		view.setBounds(0, 0, scrolledComposite.getSize().x-1, 0);
		for(int i=0; i<st.size(); i++){
			st.get(i).setBounds(0, i*60+(i+1)*3, view.getSize().x, 60);
		}
	}
	
	/**点击事件处理*/
	public void addOnClickListener(Listener listener){
		normal.addListener(SWT.MouseUp, listener);
	}
	
	/**释放资源*/
	public void dispose(){
		for(int i=0; i<st.size(); i++){
			st.get(i).dispose();
		}
		ViewUtil.dispose(color1, color2);
	}
	
	/**删除View中的一个视图*/
	public void deleteItem(TaskItem item){
		st.remove(item);
		item.dispose();
		setViewBounds(scrolledComposite.getLocation().x, scrolledComposite.getLocation().y+1, scrolledComposite.getSize().x-1, scrolledComposite.getSize().y-2);	
	}

	public void clearTaskRecord() {
		for(TaskItem ti : st){
			ti.dispose();
		}
		st.clear();
		setViewBounds(scrolledComposite.getLocation().x, scrolledComposite.getLocation().y+1, scrolledComposite.getSize().x-1, scrolledComposite.getSize().y-2);	
	}
}