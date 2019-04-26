package com.kp.server.view.regsignin;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.kp.server.view.View;
import com.kp.util.view.ImageButton;
import com.kp.util.view.Text2;
import com.kp.util.view.ViewUtil;


/**
 * 注册视图
 * @author Mr Wang
 *
 */
public class Register extends Shell {
	
	private View view;
	private ViewForm mainView;
	private String vertCode;//验证码
	
	//关闭按钮
	private ImageButton close;
	private CLabel title;
	
	//输入控件
	private CLabel cTitle;
	private ViewForm _cTitle;
	private CLabel cUser;
	private CLabel cPassword;
	private CLabel cPassword2;
	private CLabel cNick;
	private CLabel cVert;
	private CLabel eUser;
	private CLabel ePassword;
	private CLabel ePassword2;
	private CLabel eNick;
	private CLabel eVert;
	private Text2 tUser;
	private Text2 tPassword;
	private Text2 tPassword2;
	private Text2 tNick;
	private Text2 tVert;
	private CLabel pVert;
	private Button nosee;
	private ImageButton registerButton;
	
	//视图颜色
	private Color titleBG;
	private Color mainBG;
	private Color redColor;
	private Font cTitleFont;
	private Font cFont;
	private Image vertImage;
	
	public Register(View view){
		super(view.getShell().getDisplay(), SWT.APPLICATION_MODAL);
		this.view = view;
		checkSubclass();
		init();
		setBounds();
	}
	
	private void init() {
		setText("注册管理员账号");
		titleBG = new Color(getDisplay(), 14, 142, 231);
		setBackground(titleBG);
		setSize(570, 400);
		ViewUtil.centerShell(getDisplay(), this);
		addListener(SWT.MouseDown, mouseMoveListener);  
		addListener(SWT.MouseMove, mouseMoveListener);
		
		//主面板视图
		mainView = new ViewForm(this, SWT.NONE);
		mainBG = new Color(getDisplay(), 255, 255, 255);
		mainView.setBackground(mainBG);
		
		//关闭按钮
		close = new ImageButton(this, "/images/close_normal_image.png", "/images/close_over_image.png", "/images/close_down_image.png");
		close.setToolTipText("关闭");
		close.addClickListener(closeOnClickListener);
		
		title = new CLabel(this, SWT.NONE);
		title.setText("注册管理员账号");
		title.setBackground(titleBG);
		title.setForeground(mainBG);
		
		cTitleFont = new Font(mainView.getDisplay(), "宋体", 15, SWT.NORMAL);
		cTitle = new CLabel(mainView, SWT.NONE);
		cTitle.setText("邮箱注册");
		cTitle.setBackground(mainBG);
		cTitle.setFont(cTitleFont);
		
		_cTitle = new ViewForm(mainView, SWT.NONE);
		redColor = new Color(mainView.getDisplay(), 255, 0, 0);
		
		cFont = new Font(mainView.getDisplay(), "宋体", 13, SWT.NORMAL);
		cUser = new CLabel(mainView, SWT.RIGHT);
		cUser.setText("邮箱");
		cUser.setBackground(mainBG);
		cUser.setFont(cFont);
		tUser = new Text2(mainView, SWT.NONE, new RGB(173, 214, 244));
		eUser = new CLabel(mainView, SWT.NONE);
		eUser.setForeground(redColor);
		eUser.setBackground(mainBG);
		tUser.addListener(SWT.FocusOut, tUserCheckListener);
		
		cPassword = new CLabel(mainView, SWT.RIGHT);
		cPassword.setText("密码");
		cPassword.setBackground(mainBG);
		cPassword.setFont(cFont);
		tPassword = new Text2(mainView, SWT.PASSWORD, new RGB(173, 214, 244));
		ePassword = new CLabel(mainView, SWT.NONE);
		ePassword.setForeground(redColor);
		ePassword.setBackground(mainBG);
		tPassword.addListener(SWT.FocusOut, tPasswordCheckListener);
		
		cPassword2 = new CLabel(mainView, SWT.RIGHT);
		cPassword2.setText("确认");
		cPassword2.setBackground(mainBG);
		cPassword2.setFont(cFont);
		tPassword2 = new Text2(mainView, SWT.PASSWORD, new RGB(173, 214, 244));
		ePassword2 = new CLabel(mainView, SWT.NONE);
		ePassword2.setForeground(redColor);
		ePassword2.setBackground(mainBG);
		tPassword2.addListener(SWT.FocusOut, tPassword2CheckListener);
		cNick = new CLabel(mainView, SWT.RIGHT);
		cNick.setText("昵称");
		cNick.setBackground(mainBG);
		cNick.setFont(cFont);
		tNick = new Text2(mainView, SWT.NONE, new RGB(173, 214, 244));
		eNick = new CLabel(mainView, SWT.NONE);
		eNick.setForeground(redColor);
		eNick.setBackground(mainBG);
		tNick.addListener(SWT.FocusOut, tNickCheckListener);
		
		cVert = new CLabel(mainView, SWT.RIGHT);
		cVert.setText("验证码");
		cVert.setBackground(mainBG);
		cVert.setFont(cFont);
		tVert = new Text2(mainView, SWT.NONE, new RGB(173, 214, 244));
		pVert = new CLabel(mainView, SWT.NONE);
		pVert.setBackground(mainBG);
		updateVert();
		nosee = new Button(mainView, SWT.NONE);
		nosee.setText("换一张");
		nosee.addListener(SWT.Selection, noseeClickListener);
		eVert = new CLabel(mainView, SWT.NONE);
		eVert.setForeground(redColor);
		eVert.setBackground(mainBG);
		tVert.addListener(SWT.FocusOut, tVertCheckListener);
		registerButton = new ImageButton(mainView, "/images/register_normal_image.png");
		//registerButton.addClickListener(registerClickListener);
	}
	
	public void setBounds(){
		title.setBounds(5, 4, 120, 25);
		close.setBounds(getSize().x-33, 0, 30, 18);
		mainView.setBounds(0, 33, getSize().x, getSize().y-33);
		
		cTitle.setBounds(60, 20, 150, 25);
		_cTitle.setBounds(30, 45, getSize().x-60, 1);
		
		cUser.setBounds(100, 70, 60, 30);
		tUser.setBounds(100+80, 70, 200, 30);
		eUser.setBounds(100+280, 70, 100, 30);
		cPassword.setBounds(100, 110, 60, 30);
		tPassword.setBounds(100+80, 110, 200, 30);
		ePassword.setBounds(100+280, 110, 100, 30);
		cPassword2.setBounds(100, 150, 60, 30);
		tPassword2.setBounds(100+80, 150, 200, 30);
		ePassword2.setBounds(100+280, 150, 100, 30);
		cNick.setBounds(100, 190, 60, 30);
		tNick.setBounds(100+80, 190, 200, 30);
		eNick.setBounds(100+280, 190, 100, 30);
		cVert.setBounds(100, 230, 60, 30);
		tVert.setBounds(100+80, 230, 60, 30);
		pVert.setBounds(100+140, 230, 90, 30);
		nosee.setBounds(100+230, 232, 50, 25);
		registerButton.setBounds(100+80, 280, 107, 32);
		eVert.setBounds(100+280, 230, 100, 30);
	}

	@Override
	protected void checkSubclass(){            
    }
	
	@Override
	public void dispose(){
		ViewUtil.dispose(close, registerButton);
		ViewUtil.dispose(titleBG, mainBG, redColor, cTitleFont, cFont, vertImage);
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

	public void show() {
		super.open();
		while (!isDisposed()) {
			if (!getDisplay().readAndDispatch()){
				getDisplay().sleep();
			}
		}
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
	
	/**
	 * 注册事件处理
	 */
	/*Listener registerClickListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			if(checkTUser()&&checkTPassword(ePassword, tPassword)&&checkTPassword2(ePassword2, tPassword2, tPassword)&&checkTNick()&&checkTVert()){
				view.getUserController().register(tUser.getText(), tPassword.getText(), tNick.getText());
			}
		}
	};*/
	
	/**
	 * 检测邮箱内容是否正确
	 * @return
	 */
	private boolean checkTUser(){
		eUser.setText("");
		String email = tUser.getText();
		if(email.compareTo("") == 0){
			eUser.setText("请输入常用邮箱");
			return false;
		}else{
			Pattern p = Pattern.compile("^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
			Matcher m = p.matcher(email);
			if(!m.matches()){
				eUser.setText("邮箱格式错误");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 设置用户提示内容
	 * @param using
	 */
	public void setEUser(String s){
		eUser.setText(s);
	}
	
	/**
	 * 账号输入框检测事件处理
	 */
	Listener tUserCheckListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			checkTUser();
		}
	};
	
	/**
	 * 检测密码
	 * @return
	 */
	private boolean checkTPassword(CLabel e, Text2 t){
		e.setText("");
		String password = t.getText();
		if(password.compareTo("")==0){
			e.setText("密码不能为空");
			return false;
		}else if(password.length() < 6){
			e.setText("长度大于等于6");
			return false;
		}
		return true;
	}
	
	/**
	 * 密码输入框检测事件处理
	 */
	Listener tPasswordCheckListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			checkTPassword(ePassword, tPassword);
		}
	};
	
	/**
	 * 检测确认密码
	 * @return
	 */
	private boolean checkTPassword2(CLabel e2, Text2 t2, Text2 t){
		e2.setText("");
		String password = t2.getText();
		if(password.compareTo("")==0){
			e2.setText("再次确认密码");
			return false;
		}else if(password.compareTo(t.getText())!=0){
			e2.setText("密码不一致");
			return false;
		}
		return true;
	}
	
	/**
	 * 密码确认输入框检测事件处理
	 */
	Listener tPassword2CheckListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			checkTPassword(ePassword, tPassword);
			checkTPassword2(ePassword2, tPassword2, tPassword);
		}
	};
	
	/**
	 * 检测昵称
	 */
	private boolean checkTNick(){
		eNick.setText("");
		String nick = tNick.getText();
		if(nick.compareTo("")==0){
			eNick.setText("昵称不能为空");
			return false;
		}else if(nick.length() > 50){
			eNick.setText("昵称过长");
			return false;
		}
		return true;
	}
	
	/**
	 * 昵称输入框检测事件处理
	 */
	Listener tNickCheckListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			checkTNick();
		}
	};
	
	/**
	 * 检测验证码
	 * @return
	 */
	private boolean checkTVert(){
		eVert.setText("");
		String vert = tVert.getText().trim();
		if(vert.compareTo("") == 0){
			eVert.setText("验证码不能为空");
			return false;
		}else if(vert.trim().toLowerCase().compareTo(vertCode)!=0){
			eVert.setText("验证码输入错误");
			return false;
		}
		return true;
	}
	
	/**
	 * 验证码输入框检测事件处理
	 */
	Listener tVertCheckListener = new Listener() {
		@Override
		public void handleEvent(Event arg0) {
			checkTVert();
		}
	};
}
