package com.kp.client.controller;


import com.kp.client.regsignin.Login;
import com.kp.client.regsignin.Register;
import com.kp.client.view.View;
import com.kp.client.view.common.Common;
import com.kp.client.view.tstate.Transmission;

/**
 * 视图控制器
 * @author 
 *
 */
public class ViewController {
	
	private View view;//客户端视图

	public ViewController(){
		
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * 显示登录界面
	 */
	public void toLoginView(){
		Login login = new Login(view);
		view.setLogin(login);
		login.show();
	}

	/**
	 * 显示注册界面
	 */
	public void toRegView() {
		Register register = new Register(view);
		view.setRegister(register);
		register.show();
	}

	/**
	 * 显示主界面
	 */
	public void toMainView() {
		Transmission trans = new Transmission(view);
		trans.setVisible(false);
		view.setTransmission(trans);
		
		/*Share s = new Share(view);
		s.setVisible(false);
		view.setShare(s);*/
		
		Common common = new Common(view);
		view.setCommon(common);
		
		//view.getFileController().getRootFolder();
		view.getFileController().showAllFile();
		view.show();
	}


	/**
	 * 显示传输视图
	 */
	public void showTransView() {
		Transmission trans = view.getTransmission();
		trans.clearTaskRecord();
		view.getFileController().getTaskPool().showUploadList(view.getShell(), trans);
		view.getFileController().getTaskPool().showDownloadList(view.getShell(), trans);
		//view.getFileController().getTaskPool().showHistorys(view, trans);
		view.getTransmission().setVisible(true);
	}
	
	/**
	 * 显示分享视图
	 */
	
}
