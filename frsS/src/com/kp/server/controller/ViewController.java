package com.kp.server.controller;
import com.kp.server.view.View;
import com.kp.server.view.common.Common;
/**
 * 视图控制器
 * @author Mr Wang
 *
 */
public class ViewController {
	
	private View view;
	
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * 清空数据库
	 */
	/*public boolean clear(){
		try {
			//UserService.clear();
		    //ShareService.clear();
			//FriendService.clear();
			//FileService.clear();
			ViewUtil.hint(view.getShell(), "清空数据库完成，退出系统");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), "清空数据库出错");
			return false;
		}
	}*/

	/**
	 * 显示登陆界面
	 */
	/*public void toLoginView() {
		try {
			if(UserService.isFristUseSystem()){
				Register register = new Register(view);
				view.setRegister(register);
				register.show();
			}else{
				Login login = new Login(view);
				view.setLogin(login);
				login.show();
			}
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), "服务器异常");
		}
	}*/

	/**
	 * 显示用户管理界面
	 */
	/*public void showUserView() {
		view.getAccount().setVisible(true);
	}*/
	
	public void toMainView() {
		
		/*Share s = new Share(view);
		s.setVisible(false);
		view.setShare(s);*/
		
		Common common = new Common(view);
		view.setCommon(common);
		
		view.getFileController().showFiles();
		
		view.show();
	}

}
