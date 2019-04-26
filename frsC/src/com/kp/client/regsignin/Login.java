package com.kp.client.regsignin;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.kp.client.view.View;
import com.kp.util.view.ImageButton;
import com.kp.util.view.Text2;
import com.kp.util.view.ViewUtil;

/**
 * 用户登录视图
 */
public class Login extends Shell {
	
	private View view;
	
	private ViewForm mainView;
	
	//关闭按钮、最小化按钮
	private ImageButton close;
	private ImageButton min;
	private CLabel title;
	
	//用户输入数据视图
	private CLabel cuser;
	private CLabel cpassword;
	private Text2 tuser;
	private Text2 tpassword;
	private ImageButton loginClickButton;
	
	private CLabel cVert;
	private Text2 tVert;
	private CLabel pVert;
	private Button nosee;
	private String vertCode;//验证码
	private Image vertImage;
	
	//用户操作情况提示
	private CLabel operateTitle;
	private Color operateTitleFont;
	private CLabel operateTitleImageShow;
	private Image operateTitleImage;
	
	//注册
	private ViewForm regArea;
	private CLabel regTitle;
	private Button regButton;
	
	//视图颜色
	private Color titleBG;
	private Color mainBG;
	
	public Login(View view){
		super(view.getShell().getDisplay(), SWT.APPLICATION_MODAL);
		checkSubclass();
		this.view = view;
		init();
		setBounds();
	}
	
	@Override
	protected void checkSubclass(){            
    }
	
	private void init(){
		setText("账号登录");
		titleBG = new Color(getDisplay(), 14, 142, 231);
		setBackground(titleBG);
		setSize(440, 300);
		ViewUtil.centerShell(getDisplay(), this);
		addListener(SWT.MouseDown, mouseMoveListener);  
		addListener(SWT.MouseMove, mouseMoveListener);
		//主面板视图
		mainView = new ViewForm(this, SWT.NONE);
		mainBG = new Color(getDisplay(), 255, 255, 255);
		mainView.setBackground(mainBG);
		
		title = new CLabel(this, SWT.NONE);
		title.setText("账号登录");
		title.setBackground(titleBG);
		title.setForeground(mainBG);
		//关闭按钮
		close = new ImageButton(this, "images/close_normal_image.png", "images/close_over_image.png", "images/close_down_image.png");
		close.setToolTipText("关闭");
		close.addClickListener(closeOnClickListener);
		
		//最小化按钮
		min = new ImageButton(this, "images/min_normal_image.png", "images/min_over_image.png", "images/min_down_image.png");		
		min.setToolTipText("最小化");
		min.addClickListener(minOnClickListener);
		
		cuser = new CLabel(mainView, SWT.RIGHT);
		cuser.setText("账号：");
		cuser.setBackground(mainBG);
		cpassword = new CLabel(mainView, SWT.RIGHT);
		cpassword.setText("密码：");
		cpassword.setBackground(mainBG);
		tuser = new Text2(mainView, SWT.NONE, new RGB(173, 214, 244));
		tuser.addListener(SWT.Modify, dataModifyListener);
		tpassword = new Text2(mainView, SWT.PASSWORD, new RGB(173, 214, 244));
		tpassword.addListener(SWT.Modify, dataModifyListener);
		loginClickButton = new ImageButton(mainView, "images/login_normal_image.png", "images/login_over_image.png", "images/login_down_image.png");
		loginClickButton.addClickListener(loginOnClickListener);  //登录事件
		
		cVert = new CLabel(mainView, SWT.RIGHT);
		cVert.setText("验证码：");
		cVert.setBackground(mainBG);
		tVert = new Text2(mainView, SWT.NONE, new RGB(173, 214, 244));
		pVert = new CLabel(mainView, SWT.NONE);
		pVert.setBackground(mainBG);
		updateVert();
		nosee = new Button(mainView, SWT.NONE);
		nosee.setText("换一张");
		nosee.setForeground(mainBG);
		nosee.setBackground(mainBG);
		nosee.addListener(SWT.Selection, noseeClickListener);
		
		
		//用户操作情况提示
		operateTitleImage = new Image(getDisplay(), "images/operateTitle_image.png");
		operateTitleImageShow = new CLabel(mainView, SWT.NONE);
		operateTitleImageShow.setBackground(operateTitleImage);
		operateTitleImageShow.setVisible(false);
		operateTitle = new CLabel(mainView, SWT.LEFT);
		operateTitle.setBackground(mainBG);
		operateTitleFont = new Color(this.getDisplay(), 255, 0, 0);
		operateTitle.setForeground(operateTitleFont);
		
		//注册
		regArea = new ViewForm(mainView, SWT.NONE);
		regArea.setBackground(mainBG);
		regTitle = new CLabel(regArea, SWT.NONE);
		regTitle.setText("还没有账号？");
		regTitle.setBackground(mainBG);
		regButton = new Button(regArea, SWT.NONE);
		regButton.setText("立即注册账号");
		regButton.setForeground(mainBG);
		regButton.setBackground(mainBG);
		regButton.addListener(SWT.Selection, regButtonListener);  //注册事件
	}
	
	/**
	 * 更新验证码
	 */
	public void updateVert(){
		if(vertImage != null){
			ViewUtil.dispose(vertImage);
		}
		vertImage = new Image(mainView.getDisplay(), getVertImage());
		pVert.setImage(vertImage);
	}
	
	/**
	 * 生成验证码图片流
	 * @return
	 */
	private InputStream getVertImage(){
		BufferedImage bi = new BufferedImage(84,28,BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		java.awt.Color c = new java.awt.Color(200,150,255);
		g.setColor(c);
		g.fillRect(0, 0, 84, 28);

		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random r = new Random();
		int len=ch.length,index;
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<4; i++){
			index = r.nextInt(len);
			g.setColor(new java.awt.Color(r.nextInt(88),r.nextInt(188),r.nextInt(255)));
			g.setFont(new java.awt.Font("宋体", 0, 20));
			g.drawString(ch[index]+"", (i*20)+8, 21);
			sb.append(ch[index]);
		}
		ByteArrayOutputStream bs = new ByteArrayOutputStream();  
		try {
			ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
			ImageIO.write(bi, "png",imOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.vertCode = sb.toString().toLowerCase();
		return new ByteArrayInputStream(bs.toByteArray()); 
	}
	
	/**
	 * 验证码更新事件处理
	 */
	Listener noseeClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			updateVert();
		}
	};
	
	/**打开窗口*/
	public void show(){
		super.open();
		while (!isDisposed()) {
			if (!getDisplay().readAndDispatch()){
				getDisplay().sleep();
			}
		}
	}
	
	/**
	 * 设置视图的位置及大小
	 * @param arg0 X点
	 * @param arg1 Y点
	 * @param arg2 长度
	 * @param arg3 宽度
	 */
	public void setBounds(){
		title.setBounds(5, 4, 80, 25);
		close.setBounds(getSize().x-33, 0, 30, 18);
		min.setBounds(getSize().x-63, 0, 30, 18);
		mainView.setBounds(0, 33, getSize().x, getSize().y-33);
		
		operateTitleImageShow.setBounds((mainView.getSize().x-260)/2+50, 15, 14, 14);
		operateTitle.setBounds((mainView.getSize().x-260)/2+60+5, 15, 200, 14);
		
		cuser.setBounds((mainView.getSize().x-260)/2-10, 40, 60, 30);
		tuser.setBounds((mainView.getSize().x-260)/2+50, 40, 200, 30);
		cpassword.setBounds((mainView.getSize().x-260)/2-10, 85, 60, 30);
		tpassword.setBounds((mainView.getSize().x-260)/2+50, 85, 200, 30);
		
		cVert.setBounds((mainView.getSize().x-260)/2-10, 130, 60, 30);
		tVert.setBounds((mainView.getSize().x-260)/2+50, 130, 60, 30);
		pVert.setBounds((mainView.getSize().x-260)/2+110, 130, 90, 30);
		nosee.setBounds((mainView.getSize().x-260)/2+200, 132, 50, 25);
		loginClickButton.setBounds((mainView.getSize().x-260)/2+40, 175, 210, 35);
		
		regArea.setBounds(1, mainView.getSize().y-46, mainView.getSize().x-2, 45);
		regTitle.setBounds(1+(mainView.getSize().x-190)/2, 0, 80, 35);
		regButton.setBounds(91+(mainView.getSize().x-190)/2, 5, 100, 25);
	}
	
	/**
	 * 释放资源
	 */
	@Override
	public void dispose(){
		ViewUtil.dispose(titleBG, mainBG, vertImage);
		ViewUtil.dispose(operateTitleFont, operateTitleImage);
		ViewUtil.dispose(loginClickButton);
		tuser.dispose();
		tpassword.dispose();
		tVert.dispose();
		super.dispose();
	}
	
	/**
	 * 关闭窗口
	 */
	public void close(){
		dispose();
	}
	
	//窗口关闭命令
	Listener closeOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			dispose();
			
		}
	};
	
	//窗口最小化命令
	Listener minOnClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			setMinimized(true);
		}
	};
	//窗口移动
	Listener mouseMoveListener = new Listener() {  
	    int startX, startY;
	    Boolean move = false;
	    @Override
	    public void handleEvent(Event e) {  
	        if (e.type == SWT.MouseDown && e.button == 1) {
	        	startX = e.x;  
            	startY = e.y;
	        	move = true;
	        }  
	        if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
	        	if(move){
		            Point p = toDisplay(e.x, e.y);  
		            p.x -= startX;  
		            p.y -= startY;  
		            setLocation(p); 
	        	}
	        }  
	    } 
	};
	
	//登录事件处理
	Listener loginOnClickListener = new Listener() {
		public void handleEvent(Event arg0) {
			//获取账号密码
			String user = tuser.getText();
			String password = tpassword.getText();
			//判断用户输入
			if(user.trim().compareTo("") == 0){
				hint("用户名不能为空");
			}else if(password.trim().compareTo("") == 0){
				hint("密码不能为空");
			}else if(tVert.getText().trim().length() == 0){
				hint("请输入验证码");
			}else if(tVert.getText().trim().toLowerCase().compareTo(vertCode) != 0){
				hint("验证码错误");
			}else{
				loginClickButton.setDisabled(true);
				System.out.println("login(regsigin)准备登录");
				view.getUserController().login(user, password);
			}
		}
	};
	
	/**
	 * 注册按钮响应事件
	 */
	Listener regButtonListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			dispose();
			view.getViewController().toRegView();
		}
	};
	
	/**
	 * 提示信息
	 * @param text
	 */
	public void hint(String text){
		loginClickButton.setDisabled(false);
		updateVert();
		operateTitleImageShow.setVisible(true);
		operateTitle.setText(text);
	}
	
	/**
	 * 用户修改数据监听事件处理
	 */
	Listener dataModifyListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			clearhint();
		}
	};
	
	/**
	 * 清空错误提示信息
	 */
	protected void clearhint(){
		operateTitleImageShow.setVisible(false);
		operateTitle.setText("");
	}
}
