package com.kp.server.view;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.kp.server.controller.FileController;
import com.kp.server.controller.UserController;
import com.kp.server.controller.ViewController;
import com.kp.server.view.common.Common;
import com.kp.server.view.common.SDetail;
import com.kp.server.view.common.SState;
import com.kp.server.view.common.SToolBar;
import com.kp.server.view.common.SettingMenu;
import com.kp.server.view.regsignin.Login;
import com.kp.server.view.regsignin.Register;
import com.kp.server.view.setting.Setting;
import com.kp.util.view.ImageButton;
import com.kp.util.view.Switch.SwitchBtn;
import com.kp.util.view.ViewUtil;


/**
 * 服务端界面入口
 * @author Mr Wang
 *
 */
public class View {
	
	private Display display;
	private Shell shell;
	private CLabel name;
	
	//控制器
	private UserController userController;
	private FileController fileController;
	private ViewController viewController;

	//界面窗口视图大小
	private int height = 650;//高度
	private int width = 900;//宽度
	private int logoHeight = 80;//开始部署组件高度

	//视图组件
	private SToolBar sToolBar;
	private SDetail sDetail;
	private SState sState;
	
	private Register register;
	private Login login;
	private Setting setting;
	//private Account account;//分享页面
	private Common common;//显示文件视图

	//窗口操作按钮
	private ImageButton closeWindow;//关闭按钮	
	private ImageButton maxWindow;//最大化按钮	
	private ImageButton restoreWindow;//还原按钮
	private ImageButton minWindow;//最小化按钮
	
	//当前账户及账户图标
	private CLabel user;
	private CLabel usericon;
	private Image usericonImage;
	private Font userFont;
	private Color whiteColor;

	//设置菜单按钮
	private ImageButton showWindowMenu;	

	//设置按钮菜单
	private SettingMenu settingMenu;

	//颜色
	private Color windowBackColor;
	private Color nameColor;

	public View(){
		init();
	}
	
	public void setUserController(UserController userController) {
		this.userController = userController;
	}
	
	public UserController getUserController() {
		return userController;
	}
	
	public void setFileController(FileController fileController) {
		this.fileController = fileController;
	}
	
	public FileController getFileController() {
		return fileController;
	}
	
	public void setViewController(ViewController viewController) {
		this.viewController = viewController;
	}
	
	public ViewController getViewController() {
		return viewController;
	}
	
	public void setRegister(Register register) {
		this.register = register;
	}
	
	public Register getRegister() {
		return register;
	}
	
	public void setLogin(Login login) {
		this.login = login;
	}
	
	public Login getLogin() {
		return login;
	}
	
	/*public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}*/
	
	
	
	public Common getCommon() {
		return common;
	}

	public void setCommon(Common common) {
		this.common = common;
	}

	/**
	 * 初始化Display和Shell实例，设置窗口视图大小
	 */
	private void init(){
		display = Display.getDefault();
		shell = new Shell(display, SWT.APPLICATION_MODAL);
		shell.setText("服务端");//任务栏名称
		windowBackColor = new Color(display, 14, 142, 231);
		shell.setBackground(windowBackColor);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setSize(width, height);
		ViewUtil.centerShell(display, shell);

		shell.addListener(SWT.MouseDown, mouseMoveListener);  
		shell.addListener(SWT.MouseMove, mouseMoveListener);  
		shell.addPaintListener(new PaintListener() {			
			@Override
			public void paintControl(PaintEvent arg0) {
				setBounds();
			}
		});

		initWindowControl(shell);
		initComponent(shell);
		//initListener();
		
		//当前账户
		user = new CLabel(shell, SWT.NONE);
		user.setBackground(whiteColor);
		whiteColor = new Color(display, 255, 255, 255);
		user.setForeground(whiteColor);
		userFont = new Font(display, "", 12, 0);
		user.setFont(userFont);
		usericon = new CLabel(shell, SWT.NONE);
		user.addListener(SWT.MouseDown, mouseMoveListener);  
		user.addListener(SWT.MouseMove, mouseMoveListener);  
		
		name = new CLabel(shell, SWT.NONE);
		nameColor = new Color(display, 235, 235, 235);
		name.setForeground(nameColor);
		name.setText("服务端");
		name.setFont(new Font(display, "", 14, 0));
		setBounds();
	}

	/**
	 * 布局组件位置
	 */
	public void setBounds(){
		
		if(common != null && common.getSize().x <= 0){
			common.setBounds(0, logoHeight, shell.getSize().x, shell.getSize().y-logoHeight);
		}
		
		//usericon.setBounds(15, 33, 55, 55);
		//name.setBounds(12, 3, 150, 22);
		name.setBounds(8, 2, 150, 50);
		//user.setBounds(75, 49, 220, 22);
		closeWindow.setBounds(shell.getSize().x-33, 0, 30, 18);
		maxWindow.setBounds(shell.getSize().x-63, 0, 30, 18);
		restoreWindow.setBounds(shell.getSize().x-63, 0, 30, 18);
		minWindow.setBounds(shell.getSize().x-93, 0, 30, 18);
		showWindowMenu.setBounds(shell.getSize().x-123, 0, 30, 18);
		
		//sToolBar.setBounds(0, logoHeight-35, shell.getSize().x, 35);
		
	/*	sState.setBounds(0, shell.getSize().y-35, shell.getSize().x, 35);
		sDetail.setBounds(0, sToolBar.getLocation().y+sToolBar.getSize().y, shell.getSize().x,
				shell.getSize().y-(sToolBar.getLocation().y+sToolBar.getSize().y)-sState.getSize().y);*/
		
		/*if(account != null){
			account.setBounds(0, logoHeight, shell.getSize().x, shell.getSize().y-logoHeight);
		}*/
	}

	/**
	 * 窗口视图设计，循环监听消息
	 */
	private void open(){
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	
	/**
	 * 开始显示视图
	 */
	public void show(){
		open();
		
	}
	
	public void updateSetting(){
		if(setting != null && !setting.isDisposed()){
			setting.update();
		}
	}

	/**
	 * 释放资源
	 */
	private void dispose(){
		ViewUtil.dispose(sToolBar, sDetail, sState, closeWindow, maxWindow, restoreWindow, minWindow, showWindowMenu);
		ViewUtil.dispose(windowBackColor, userFont, usericonImage, whiteColor, nameColor);
		shell.dispose();		
	}

	/**
	 * 初始化视图右上角按钮
	 */
	private void initWindowControl(Shell shell){
		//关闭按钮
		closeWindow = new ImageButton(shell, "images/close_normal_image.png", "images/close_over_image.png", "images/close_down_image.png");
		closeWindow.setToolTipText("关闭");
		closeWindow.addClickListener(closeOnClickListener);
		//最大化按钮
		maxWindow = new ImageButton(shell, "images/max_normal_image.png", "images/max_over_image.png", "images/max_down_image.png");		
		maxWindow.setToolTipText("最大化");
		maxWindow.addClickListener(maxOnClickListener);
		//还原界面大小
		restoreWindow = new ImageButton(shell, "images/minmax_normal_image.png", "images/minmax_over_image.png", "images/minmax_down_image.png");		
		restoreWindow.setToolTipText("还原");
		restoreWindow.addClickListener(restoreOnClickListener);
		restoreWindow.setVisible(false);
		//最小化按钮
		minWindow = new ImageButton(shell, "images/min_normal_image.png", "images/min_over_image.png", "images/min_down_image.png");		
		minWindow.setToolTipText("最小化");
		minWindow.addClickListener(minOnClickListener);
		//设置菜单按钮
		showWindowMenu = new ImageButton(shell, "images/set_normal_image.png", "images/set_over_image.png", "images/set_down_image.png");		
		showWindowMenu.setToolTipText("设置");
		showWindowMenu.addClickListener(setClickListener);
	}

	/**
	 * 初始化组件
	 * @param shell
	 */
	private void initComponent(Shell shell){
		//account = new Account(this);
		//account.setVisible(false);
		
		sToolBar = new SToolBar(shell);		
		sDetail = new SDetail(shell);
		sState = new SState(shell);
		settingMenu = initSettingMenu();
	}

	/**
	 * 初始化设置菜单
	 * @return
	 */
	private SettingMenu initSettingMenu(){
		SettingMenu s = new SettingMenu(shell);
		s.addMenuItem("服务器信息", menu_Setting);
		s.addMenuItem("服务端设置", menu_Setting);
		s.addMenuItem("用户信息", menu_Setting);
		s.addMenuItem("退出", closeOnClickListener);
		return s;
	}
	
	public Shell getShell(){
		return shell;
	}
	
	/**
	 * 初始化监听器
	 */
	/*private void initListener(){
		sToolBar.addSwitchListener(serverOpenOnClickListener, serverCloseOnClickListener);
		sState.addUserClickListener(userClickListener);
	}*/
	
	/**
	 * 分享按钮事件处理
	 */
	/*Listener userClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			getViewController().showUserView();
		}
	};*/
	
	//服务器启动监听事件处理
	Listener serverOpenOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			MessageBox dialog=new MessageBox(shell, SWT.YES|SWT.NO);
		    dialog.setText("温馨提示");
		    dialog.setMessage("是否启动服务器？");
		    if(dialog.open() == 64){//64返回确定，128返回取消
		    	if(userController.startMonitor()){
		    		sToolBar.switchBtn(SwitchBtn.OPEN);
		    	}
		    }
		}
	};
	
	//服务器关闭监听事件处理
	Listener serverCloseOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			MessageBox dialog=new MessageBox(shell, SWT.YES|SWT.NO);
		    dialog.setText("温馨提示");
		    dialog.setMessage("是否关闭服务器？");
		    if(dialog.open() == 64){//64返回确定，128返回取消
		    	if(userController.closeMonitor()){
		    		sToolBar.switchBtn(SwitchBtn.CLOSE);
		    	}
		    }
		}
	};

	/**
	 * 添加服务器运行中的一些信息
	 */
	public synchronized void append(final String text){
		ViewUtil.asyncExec(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				sDetail.append(text+new Date().toLocaleString()+"\n");
			}
		});
	}

	/**
	 * 设置服务器任务数
	 */
	public void setTaskNum(final long task, final long run, final long complete){
		ViewUtil.asyncExec(new Runnable() {
			@Override
			public void run() {
				sState.setTaskNum(task, run, complete);
			}
		});
	}

	//窗口关闭命令
	Listener closeOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			MessageBox dialog=new MessageBox(shell, SWT.YES|SWT.NO);
		    dialog.setText("温馨提示");
		    dialog.setMessage("是否关闭服务器？");
		    if(dialog.open() == 64){//64返回确定，128返回取消
		    	if(userController.closeMonitor()){
		    		sToolBar.switchBtn(SwitchBtn.CLOSE);
		    		dispose();
		    	}
		    }
		}
	};

	//窗口最小化命令
	Listener minOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			shell.setMinimized(true);
		}
	};

	//窗口最大化命令
	Listener maxOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			restoreWindow.setVisible(true);
			maxWindow.setVisible(false);
			shell.setMaximized(true);
		}
	};

	//窗口还原命令
	Listener restoreOnClickListener = new Listener() {	
		@Override
		public void handleEvent(Event arg0) {
			restoreWindow.setVisible(false);
			maxWindow.setVisible(true);
			shell.setMaximized(false);
		}
	};

	//窗口移动监听程序
	Listener mouseMoveListener = new Listener() {  
		int startX, startY;
		Boolean move = false;
		@Override
		public void handleEvent(Event e) {  
			if (e.type == SWT.MouseDown && e.button == 1) {
				if(e.y < logoHeight){
					startX = e.x;  
					startY = e.y; 
					move = true;
				}else{
					move = false;
				}
			}  
			if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
				if(move){
					Point p = shell.toDisplay(e.x, e.y);  
					p.x -= startX;  
					p.y -= startY;  
					shell.setLocation(p); 
				}
			}  
		} 
	};
	
	/**
	 * 清空数据库
	 */
	/*Listener clearServer = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			MessageBox dialog=new MessageBox(shell, SWT.YES|SWT.NO);
		    dialog.setText("温馨提示");
		    dialog.setMessage("数据库一旦清空，系统所有数据将无法找回。");
		    if(dialog.open() == 64){//64返回确定，128返回取消
		    	if(viewController.clear()){
		    		dispose();
		    	}
		    }
		}
	};*/

	//设置按钮事件处理，显示菜单
	Listener setClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			settingMenu.setLocation(showWindowMenu.getLocation().x+shell.getLocation().x, 
					showWindowMenu.getLocation().y+shell.getLocation().y+20);
			settingMenu.setVisible(true);
		}
	};
	
	//菜单设置按钮处理时间
		Listener menu_Setting = new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				int w = 500, h = 350;
				setting = new Setting(View.this);
				setting.setBounds(shell.getLocation().x+(shell.getSize().x-w)/2, shell.getLocation().y+(shell.getSize().y-h)/2, w, h);
				if(arg0.widget.toString().compareTo("MenuItem {服务器信息}") == 0){
					setting.show(0);
				}else if(arg0.widget.toString().compareTo("MenuItem {服务端设置}") == 0){
					setting.show(1);
				}else if(arg0.widget.toString().compareTo("MenuItem {用户信息}") == 0){
					setting.show(2);
				}
			}
		};
}
