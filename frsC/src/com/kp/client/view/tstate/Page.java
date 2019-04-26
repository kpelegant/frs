package com.kp.client.view.tstate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**自定义分页控件*/
public class Page {
	private List<PageItem> page;
	//位置和大小
	private int left = 0,
				 top = 0,
				 width = 0,
				 height = 0;
	private PageItem nowshow = null;
	
	public Page(Composite parent){
		initPage();
	}
	
	/**初始化*/
	public void initPage(){
		page = new ArrayList<PageItem>();
	}
	
	/**添加一个页面*/
	public void addPageItem(final PageItem item){
		item.addOnClickListener(new Listener(){
			public void handleEvent(Event arg0) {
				item.click();
				if(nowshow != null){
					nowshow.reset();
				}
				nowshow = item;
			}		
		});
		page.add(item);
		if(!(left == 0 && top == 0 && width == 0 && height == 0)){
			setBounds(left, top, width, height);
		}
	}
	
	/**设置显示界面*/
	public void setItemToShow(int index){
		nowshow = page.get(index);
		nowshow.click();
	}
	
	/**设置位置和大小*/
	public void setBounds(int arg0, int arg1, int arg2, int arg3){
		for(int i=0; i<page.size(); i++){
			page.get(i).setHeaderBounds(arg0+i*arg2/page.size(), 0, arg2/page.size()+1, 40);
		}
		for(int j=0; j<page.size(); j++){
			page.get(j).setViewBounds(arg0, 40, arg2, arg3-40);
		}
	}
	
	/**获取Page中元素*/
	public PageItem getEmelent(int index){
		return page.get(index);
	}
	
	/**释放资源*/
	public void dispose(){
		for(int i=0; i<page.size(); i++){
			page.get(i).dispose();
		}
	}
}
