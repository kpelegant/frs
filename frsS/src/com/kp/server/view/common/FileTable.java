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

import com.kp.entity.db.fileUser;
import com.kp.entity.db.myFile;
import com.kp.entity.file.CFile;
import com.kp.util.file.FileUtil;
import com.kp.util.view.ViewUtil;

/**
 * 文件系统表格视图
 */
public class FileTable extends Table {
	
	private static String[] attrName = {"名称","上传者","上传日期","类型","大小"};
	private static int[] attrLength = {300, 200, 150, 130, 135};

	/**当前显示在界面上所有文件和文件夹属性*/
	private List<Image> icons;

	//编辑视图
	private Text text;
	private TableEditor editor;
	private TableItem renameItem;
	private String[] rename = new String[2];
	private Listener renameListener2;

	//右键菜单
	//private TableMenu menu;

	public FileTable(Composite composite, int style){
		super(composite, style);
		checkSubclass();
		this.icons = new ArrayList<Image>();
		init();
	}

	public FileTable(Composite composite){
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
	 * 显示文件列表
	 * @param list
	 */
	public void showFilesToTable(List<fileUser> list){
		dispose();
		//list = FileUtil.sortCFile(list, menu.getSortWay());
		//list = FileUtil.sortCFile(list, 1);
		for(fileUser f : list){
			addFileToTable(f);
		}
	}
	
	/**
	 * 添加文件到列表
	 * @param f
	 */
	@SuppressWarnings("deprecation")
	private TableItem addFileToTable(fileUser f){
		TableItem item = new TableItem(this, SWT.NULL);
		item.setText(0, f.getFilelist().getFileName());
		item.setText(1, f.getUsername());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(f.getFilelist().getUploadTime());
		item.setText(2, dateString);
		item.setText(3, (FileUtil.getSystemTypeDescription(f)));
		item.setText(4, ((long)(f.getFilelist().getSize()/1024)+(f.getFilelist().getSize()%1024>0 ? 1:0))+" KB");
		
		/*ImageData imageData = FileUtil.getSystemImage(f);
		if(imageData != null){
			Image image = new Image(item.getDisplay(), imageData);
			icons.add(image);
			item.setImage(0, image);
		}*/
		return item;
	}
	
	/**
	 * 在界面添加一个文件夹并让其处于编辑状态
	 * @param f
	 */
	/*public void ceFolder(myFile f){
	//	TableItem item = addFileToTable(f);
		this.setSelection(item);
		editFileName(item);
	}
*/
	/**
	 * 表格单击双击监听
	 * */
	public void addMouseDoubleClickListener(Listener listener){
		addListener(SWT.MouseDoubleClick, listener);
		//menu.addOpenFileListener(listener);
	}

	/**
	 * 获取当前选择的文件名字
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
	 * 判断选择的是否是文件夹
	 * @throws TableException 
	 * */
	public boolean selectIsFolder() throws Exception{
		int index = getSelectionIndex();
		if(index < 0){
			throw new Exception("没有选中的表格！");
		}
		TableItem it = getItem(index);
		if(it == null){
			throw new Exception("没有选中的表格！");
		}
		return it.getText(3).compareTo("") == 0;
	}

	/**
	 * 获取当前选择的文件
	 * */
	public String[] getSelectionFiles(){
		TableItem[] ti = getSelection();
		String[] s = new String[ti.length];
		for(int i=0; i<ti.length; i++){
			s[i] = ti[i].getText(0);
		}
		return s;
	}
	
	/**
	 * 选中的文件是否包含文件夹
	 * @return
	 */
	public boolean includeFolder(){
		TableItem[] ti = getSelection();
		for(int i=0; i<ti.length; i++){
			if(ti[i].getText(3).compareTo("") == 0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 可编辑显示
	 */
	private synchronized void editFileName(TableItem t){
		if(text != null && !text.isDisposed()){
			text.setVisible(false);
			text.dispose();
		}
		editor = new TableEditor(this);
		text = new Text(this, SWT.BORDER);
		text.addListener(SWT.FocusOut, addRenameFocusOutEventListener);
		text.addListener(SWT.KeyUp, addRenameKeyUpEventListener);
		text.addListener(SWT.FocusOut, renameListener2);
		text.computeSize(SWT.DEFAULT, getItemHeight());
		//调整editor的高度和宽度
		editor.grabHorizontal = true;
		editor.minimumHeight = text.getSize().y;
		editor.minimumWidth = text.getSize().x;

		text.setText(t.getText(0));
		text.forceFocus();
		editor.setEditor(text, t, 0);
		renameItem = t;
	}
	
	/**
	 * 编辑选中的文件或文件夹
	 */
	public void editSelectFileName(){
		TableItem[] ti = getSelection();
		if(ti.length > 0){
			editFileName(ti[0]);
		}
	}

	/**
	 * 重命名监听鼠标事件前处理
	 */
	Listener addRenameFocusOutEventListener = new Listener() {
		@Override
		public synchronized void handleEvent(Event e) {
			if(!renameItem.isDisposed()){
				rename[0] = renameItem.getText(0);
				renameItem.setText(0, text.getText());
				rename[1] = renameItem.getText(0);
			}else{
				rename[0] = null;
				rename[1] = null;
			}
			text.setVisible(false);
			text.dispose();
			editor.dispose();
		}
	};

	/**
	 * 重命名监听按键事件前处理
	 */
	Listener addRenameKeyUpEventListener = new Listener() {
		@Override
		public void handleEvent(Event e) {
			if(e.keyCode == 13){
				text.setVisible(false);
			}
		}
	};

	/**
	 * 重命名监听鼠标点击事件后处理
	 */
	public void addRenameFocusOutEventListener(Listener listener){
		renameListener2 = listener;
	}

	/**
	 * 获取重命名事件的前后文件名字
	 */
	public String[] getRenameFileName(){
		return rename;
	}

	/**
	 * 文件上传监听事件处理
	 * @param arg0 事件处理
	 */
	public void addUploadFileListener(Listener listener){
		//menu.addUploadFileListener(listener);
	}
	
	/**
	 * 文件加密上传监听事件处理
	 * @param arg0 事件处理
	 */
	public void addSecritUploadFileListener(Listener listener){
		//menu.addSecritUploadFileListener(listener);
	}

	/**
	 * 新建文件夹监听事件处理
	 * @param arg0 事件处理
	 */
	public void addCreateFolderListener(Listener listener){
		//menu.addCreateFolderListener(listener);
	}

	/**
	 * 刷新监听事件处理
	 * @param arg0 事件处理
	 */
	public void addReFreshListener(Listener listener){
		//menu.addReFreshListener(listener);
	}

	/**
	 * 下载文件监听事件处理
	 * @param arg0 事件处理
	 */
	public void addDownloadFileListener(Listener listener){
		//menu.addDownloadFileListener(listener);
	}

	/**
	 * 文件删除监听事件处理
	 * @param arg0 事件处理
	 */
	public void addDeleteFileListener(Listener listener){
		//menu.addDeleteFileListener(listener);
	}

	/**
	 * 文件移动到监听事件处理
	 * @param arg0 事件处理
	 */
	public void addFileMoveToListener(Listener listener){
		//menu.addFileMoveToListener(listener);
	}
	
	public void addShareFileListener(Listener listener){
		//menu.addShareFileListener(listener);
	}
	
	/**
	 * 文件选中事件处理
	 * @param listener
	 */
	public void addSelectionListener(Listener listener){
		addListener(SWT.Selection, listener);
	}

	public void addShowByFileNameListener(Listener listener){
		//menu.addShowByFileNameListener(listener);
	}

	public void addShowByFileLengthListener(Listener listener){
		//menu.addShowByFileLengthListener(listener);
	}

	public void addShowByModifyTimeListener(Listener listener){
		//menu.addShowByModifyTimeListener(listener);
	}

	/**
	 * 重命名视图呈现编辑状态
	 */
	Listener renameListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			editSelectFileName();
		}
	};
}
