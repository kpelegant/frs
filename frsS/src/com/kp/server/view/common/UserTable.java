package com.kp.server.view.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.kp.entity.db.User;
import com.kp.entity.db.fileUser;
import com.kp.entity.db.myFile;
import com.kp.entity.file.CFile;
import com.kp.util.file.FileUtil;
import com.kp.util.view.ViewUtil;

/**
 * 用户表格视图
 */
public class UserTable extends Table {
	
	private static String[] attrName = {"用户名","用户策略","账号状态","角色","注册时间"};
	private static int[] attrLength = {45, 150, 60, 50, 120};

	/**当前显示在界面上所有文件和文件夹属性*/
	private List<Image> icons;


	//右键菜单
	//private TableMenu menu;

	public UserTable(Composite composite, int style){
		super(composite, style);
		checkSubclass();
		this.icons = new ArrayList<Image>();
		init();
	}

	public UserTable(Composite composite){
		this(composite, SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.MULTI);
	}

	@Override
	protected void checkSubclass(){            
	}

	/**
	 * 初始化表格视图
	 */
	protected void init(){
		setHeaderVisible(true);
		///menu = new TableMenu(this);

		addListener(SWT.MeasureItem, new Listener() {//设置一行的高度
			@Override
			public void handleEvent(Event event) {
				event.height =25;
			}
		});
		
		/*addMenuDetectListener(new MenuDetectListener() {//菜单切换
			@Override
			public void menuDetected(MenuDetectEvent arg0) {
				if(getSelectionCount() > 0){
					TableItem[] tis = getSelection();
					if(tis.length == 1 && tis[0].getText(3).compareTo("") != 0){
						menu.changeToItemMenu(getSelectionCount(), false);
					}else{
						menu.changeToItemMenu(getSelectionCount(), true);
					}
				}else{
					menu.changeToBlankMenu();
				}
			}
		});
		menu.addReNameFileListener(renameListener);*/

		initColumnTitle();
	}

	/**
	 * 初始化表格列名
	 */
	private void initColumnTitle(){
		for(int i=0; i<attrName.length; i++){
			final TableColumn column = new TableColumn(this, (i == attrName.length-1) ? SWT.RIGHT:SWT.NONE);
			final int nameNum = i;
			column.addListener(SWT.Resize, new Listener() {
				@Override
				public void handleEvent(Event arg0) {
					int width = column.getWidth() - 18*attrName[nameNum].length();
					//int blankNum = width/4;
					int blankNum = width/5;
					String blankCharToLeft = "";
					for(int i=0; i<blankNum; i++){
						blankCharToLeft += " ";
					}
					column.setText(attrName[nameNum]+blankCharToLeft);
					column.setWidth(column.getWidth()-width%5);
				}
			});
			column.setText(attrName[i]);//设置列名
			column.setWidth(attrLength[i]);//设置列宽度
		}
	}

	/**
	 * 释放资源
	 */
	@Override
	public void dispose(){		
		removeAll();
		for(Image i : icons){
			ViewUtil.dispose(i);
		}
		icons.clear();
	}
	
	/**
	 * 显示用户列表
	 * @param list
	 */
	public void showUsersToTable(List<User> list){
		dispose();
		//list = FileUtil.sortCFile(list, menu.getSortWay());
		//list = FileUtil.sortCFile(list, 1);
		for(User u : list){
			addUserToTable(u);
		}
	}
	
	/**
	 * 添加用户到列表
	 * @param f
	 */
	@SuppressWarnings("deprecation")
	private TableItem addUserToTable(User u){
		TableItem item = new TableItem(this, SWT.NULL);
		item.setText(0, u.getUsername());
		item.setText(1, u.getPolicy());
		item.setText(2, u.getState()==0?"正常":"停止使用");
		item.setText(3, u.getRole()==1?"用户":"管理员");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(u.getRegTime());
		item.setText(4, dateString);
		
		/*ImageData imageData = FileUtil.getSystemImage(f);
		if(imageData != null){
			Image image = new Image(item.getDisplay(), imageData);
			icons.add(image);
			item.setImage(0, image);
		}*/
		return item;
	}
	

	/**
	 * 获取当前选择的用户名字
	 * @throws TableException 
	 * */
	public String getSelectFileName() throws Exception{
		int index = getSelectionIndex();
		if(index < 0){
			throw new Exception("没有选中的表格！");
		}
		TableItem it = getItem(index);
		if(it == null){
			throw new Exception("没有选中的表格！");
		}
		return it.getText();
	}
	

	/**
	 * 获取当前选择的用户
	 * */
	public String[] getSelectionUsers(){
		TableItem[] ti = getSelection();
		String[] s = new String[ti.length];
		for(int i=0; i<ti.length; i++){
			s[i] = ti[i].getText(0);
		}
		return s;
	}

	
	/**
	 * 用户选中事件处理
	 * @param listener
	 */
	public void addSelectionListener(Listener listener){
		addListener(SWT.Selection, listener);
	}

}
