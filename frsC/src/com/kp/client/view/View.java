package com.kp.client.view;

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

import com.kp.client.controller.FileController;
import com.kp.client.controller.UserController;
import com.kp.client.controller.ViewController;
import com.kp.client.regsignin.Login;
import com.kp.client.regsignin.Register;
import com.kp.client.view.common.Common;
import com.kp.client.view.common.SettingMenu;
import com.kp.client.view.setting.Setting;
import com.kp.client.view.tstate.Transmission;
import com.kp.util.view.ImageButton;
import com.kp.util.view.ViewUtil;


/**
 * 客户端视图入口
 * @author 
 *
 */
public class View {

	private Display display;//swt和操作系统之间的通信
	private Shell shell;//窗口
	private CLabel name; //带文本或图像的标签
	//控制器
	private ViewController viewController;
	private UserController userController;
	private FileController fileController;

	//界面窗口视图大小
	private int height = 650;
	private int width = 900;
	private int logoHeight = 100;

	//窗口操作按钮
	private ImageButton closeWindow;//关闭按钮	
	private ImageButton maxWindow;//最大化按钮	
	private ImageButton restoreWindow;//还原按钮
	private ImageButton minWindow;//最小化按钮
	private ImageButton showWindowMenu;	//设置菜单按钮

	//当前账户及账户图标
	private CLabel user;
	private CLabel usericon;
	private Image usericonImage;
	private Font userFont;
	private Color whiteColor;
//	private CLabel size;

	//视图组件
	private Register register;
	private Login login;
	
	private Common common;
	private Transmission transmission;
	/*private Share share;*/
	private Setting setting;

	//设置按钮菜单
	private SettingMenu settingMenu;

	//颜色
	private Color windowBackColor;
	private Color nameColor;

	public View(){
		init();
	}
	
	public void setViewcontroller(ViewController viewController) {
		this.viewController = viewController;
	}
	
	public ViewController getViewController() {
		return viewController;
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
	
	public void setCommon(Common common) {
		this.common = common;
	}
	
	public Common getCommon() {
		return common;
	}
	
	public void setTransmission(Transmission transmission) {
		this.transmission = transmission;
	}
	
	public Transmission getTransmission() {
		return transmission;
	}
	/*
	public void setShare(Share share) {
		this.share = share;
	}
	
	public Share getShare() {
		return share;
	}
	*/
	
	/**
	 * 初始化Display和Shell实例，设置窗口视图大小
	 */
	private void init(){
		display = new Display();
		shell = new Shell(display, SWT.APPLICATION_MODAL);
		shell.setText("客户端");
		windowBackColor = new Color(display, 14, 142, 231);
		shell.setBackground(windowBackColor);
		shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		shell.setSize(width, height);
		ViewUtil.centerShell(display, shell);

		shell.addListener(SWT.MouseDown, mouseMoveListener);  
		shell.addListener(SWT.MouseMove, mouseMoveListener);  
		shell.addPaintListener(new PaintListener() {			
			public void paintControl(PaintEvent arg0) {
				setBounds();
			}
		});

		initWindowControl(shell);
		settingMenu = initSettingMenu();
	}

	/**
	 * 窗口移动监听程序
	 */
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
		//当前账户
		user = new CLabel(shell, SWT.NONE);
		user.setBackground(whiteColor);
		whiteColor = new Color(display, 255, 255, 255);
		user.setForeground(whiteColor);
		userFont = new Font(display, "", 12, 0);
		user.setFont(userFont);
		usericon = new CLabel(shell, SWT.NONE);
		
		/*size = new CLabel(shell, SWT.NONE);
		size.setBackground(windowBackColor);
		size.setForeground(whiteColor);*/
		
		user.addListener(SWT.MouseDown, mouseMoveListener);  
		user.addListener(SWT.MouseMove, mouseMoveListener);
		
	/*	size.addListener(SWT.MouseDown, mouseMoveListener);  
		size.addListener(SWT.MouseMove, mouseMoveListener);  */
		
		name = new CLabel(shell, SWT.NONE);
		//nameColor = new Color(display, 235, 235, 235);
		nameColor = new Color(display, 255, 255, 255);
		name.setForeground(nameColor);
		name.setText("客户端");
		name.setFont(new Font(display, "", 14, SWT.NONE));
	}

	/**
	 * 初始化设置菜单
	 * @return
	 */
	private SettingMenu initSettingMenu(){
		SettingMenu s = new SettingMenu(shell);
		s.addMenuItem("个人信息", menu_Setting);
		s.addMenuItem("修改密码", menu_Setting);
		s.addMenuItem("退出", closeOnClickListener);
		return s;
	}

	/**
	 * 窗口关闭命令
	 */
	Listener closeOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			if(fileController.isRunning()){
				MessageBox dialog=new MessageBox(shell, SWT.YES|SWT.NO);
				dialog.setText("温馨提示");
				dialog.setMessage("正在运行上传或下载任务，是否取消？");
				if(dialog.open() != 64){//64返回确定，128返回取消
					return;
				}
				fileController.cancelAllTask();
			}
			dispose();
		}
	};

	/**
	 * 窗口最小化命令
	 */
	Listener minOnClickListener = new Listener() {
		public void handleEvent(Event arg0) {
			shell.setMinimized(true);
		}
	};

	/**
	 * 窗口最大化命令
	 */
	Listener maxOnClickListener = new Listener() {
		public void handleEvent(Event arg0) {
			restoreWindow.setVisible(true);
			maxWindow.setVisible(false);
			shell.setMaximized(true);
		}
	};

	/**
	 * 窗口还原命令
	 */
	Listener restoreOnClickListener = new Listener() {
		public void handleEvent(Event arg0) {
			restoreWindow.setVisible(false);
			maxWindow.setVisible(true);
			shell.setMaximized(false);
		}
	};

	/**
	 * 设置按钮事件处理，显示菜单
	 */
	Listener setClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			settingMenu.setLocation(showWindowMenu.getLocation().x+shell.getLocation().x, showWindowMenu.getLocation().y+shell.getLocation().y+20);
			settingMenu.setVisible(true);
		}
	};

	/**
	 * 布局组件位置
	 */
	public void setBounds(){
		if(common != null && common.getSize().x <= 0){
			common.setBounds(0, logoHeight, shell.getSize().x, shell.getSize().y-logoHeight);
		}
		if(transmission != null){
			transmission.setBounds(0, logoHeight, shell.getSize().x, shell.getSize().y-logoHeight);
		}
		/*if(share != null){
			share.setBounds(0, logoHeight, shell.getSize().x, shell.getSize().y-logoHeight);
		}*/
		closeWindow.setBounds(shell.getSize().x-33, 0, 30, 18);
		maxWindow.setBounds(shell.getSize().x-63, 0, 30, 18);
		restoreWindow.setBounds(shell.getSize().x-63, 0, 30, 18);//还原窗口
		minWindow.setBounds(shell.getSize().x-93, 0, 30, 18);
		showWindowMenu.setBounds(shell.getSize().x-123, 0, 30, 18);//设置
		
		//usericon.setBounds(15, 33, 55, 55);
		name.setBounds(8, 2, 150, 50);//左上角显示
		user.setBounds(10, 50, 220, 22);
		//size.setBounds(75, 61, 220, 22);
	}

	/**
	 * 显示视图
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
		updateView();
		setBounds();
		open();
	}
	
	/**
	 * 更新界面信息
	 */
	public void updateView() {
		//user.setText(userController.getUser().getNick());
		//user.setText("用户，您好");
		if((userController.getUser().getUsername())==null) {
			user.setText("未登录");
		}else {
			user.setText(userController.getUser().getUsername()+",您好！");
		}
		//usericonImage = new Image(display, userController.getUser().getHeadImage().scaledTo(55, 55));//头像
		//usericonImage = new Image(display, userController.getUser().getHeadImage().scaledTo(55, 55));//头像
		
		//usericon.setBackground(usericonImage);
		
		//空间大小
		//size.setText(ViewUtil.switchToPut(userController.getUser().getUseVolume(), 1)+"/"+ViewUtil.switchToPut(userController.getUser().getMaxVolume(), 1));
	}

	/**
	 * 释放资源
	 */
	public void dispose(){
		ViewUtil.dispose(windowBackColor, usericonImage, userFont, whiteColor, nameColor);
		ViewUtil.dispose(closeWindow, maxWindow, restoreWindow, minWindow, showWindowMenu);

		common.dispose();
		if(transmission != null){
			transmission.dispose();
		}
		/*
		if(share != null){
			share.dispose();
		}*/
		shell.dispose();
		
	}

	public Shell getShell() {
		return shell;
	}
	
	public void updateSetting(){
		if(setting != null && !setting.isDisposed()){
			setting.update();
		}
	}

	//菜单设置按钮处理时间
	Listener menu_Setting = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			int w = 500, h = 350;
			setting = new Setting(View.this);
			setting.setBounds(shell.getLocation().x+(shell.getSize().x-w)/2, shell.getLocation().y+(shell.getSize().y-h)/2, w, h);
			if(arg0.widget.toString().compareTo("MenuItem {个人信息}") == 0){
				setting.show(0);
			}else if(arg0.widget.toString().compareTo("MenuItem {修改密码}") == 0){
				setting.show(1);
			}
		}
	};
}
