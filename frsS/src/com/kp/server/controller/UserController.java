package com.kp.server.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.MessageBox;

import com.kp.entity.db.User;
import com.kp.server.controller.task.CSTransmissionBasic;
import com.kp.server.controller.task.CheckUserController;
import com.kp.server.controller.task.LoginController;
import com.kp.server.controller.task.ModifyUserController;
import com.kp.server.controller.task.RegisterController;
import com.kp.server.service.UserService;
import com.kp.server.view.View;
import com.kp.util.secret.MD5Util;
import com.kp.util.view.ViewUtil;

/*import com.dicd.entity.db.User;
import com.dicd.server.controller.task.AddFriendController;
import com.dicd.server.controller.task.CSTransmissionBasic;
import com.dicd.server.controller.task.CheckUserController;
import com.dicd.server.controller.task.DeleteFriendController;
import com.dicd.server.controller.task.FriendsController;
import com.dicd.server.controller.task.LoginController;
import com.dicd.server.controller.task.ModifyRemarkController;
import com.dicd.server.controller.task.ModifyUserController;
import com.dicd.server.controller.task.RegisterController;
import com.dicd.server.controller.task.SearchUserController;
import com.dicd.server.service.UserService;
import com.dicd.server.view.View;
import com.dicd.util.scriet.MD5Util;
import com.dicd.util.view.ViewUtil;*/

/**
 * 用户管理控制器
 * @author Mr Wang
 *
 */
public class UserController {
	
	private View view;
	//private User u;//管理员账号
	private Monitor monitor;

	public UserController(){
		
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	/*public User getUser() {
		return u;
	}*/
	
	/**
	 * 启动监听器
	 */
	public boolean startMonitor(){
		try {
			monitor = new Monitor(view);
			monitor.startServer();
		} catch (Exception e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 关闭监听器
	 */
	public boolean closeMonitor(){
		if(monitor == null || !monitor.isRunning()) return true;
		/*if(view.getFileController().getTaskPool().isRunning()){
			MessageBox dialog=new MessageBox(view.getShell(), SWT.YES|SWT.NO);
			dialog.setText("温馨提示");
			dialog.setMessage("正在运行任务，是否强制关闭？");
			if(dialog.open() != 64){//64返回确定，128返回取消
				return false;
			}
			view.getFileController().getTaskPool().stopAllTask();
		}*/
		boolean flag = monitor.closeServer();
		if(flag) monitor = null;
		return flag;
	}
	
	/**
	 * 客户端：检测用户账号
	 */
	public void checkUser(CSTransmissionBasic basic){
		CheckUserController cuc = new CheckUserController(basic);
		view.getFileController().getTaskPool().addTask(cuc);
	}
	
	/**
	 * 客户端：注册账号
	 * @param basic
	 */
	public void register(CSTransmissionBasic basic){
		RegisterController rc = new RegisterController(basic);
		view.getFileController().getTaskPool().addTask(rc);
	}
	
	/**
	 * 客户端：登录
	 * @param basic
	 */
	public void login(CSTransmissionBasic basic){
		LoginController lc = new LoginController(basic);
		view.getFileController().getTaskPool().addTask(lc);
	}

	/**
	 * 客户端：获取好友
	 * @param basic
	 */
	/*public void getFriends(CSTransmissionBasic basic) {
		FriendsController fc = new FriendsController(basic);
		view.getFileController().getTaskPool().addTask(fc);
	}*/

	/**
	 * 客户端：搜索用户
	 * @param basic
	 */
	/*public void searchUser(CSTransmissionBasic basic) {
		SearchUserController suc = new SearchUserController(basic);
		view.getFileController().getTaskPool().addTask(suc);
	}*/

	/**
	 * 客户端：添加好友
	 */
	/*public void addFriend(CSTransmissionBasic basic) {
		AddFriendController afc = new AddFriendController(basic);
		view.getFileController().getTaskPool().addTask(afc);
	}*/

	/**
	 * 客户端：修改备注
	 * @param monitor2
	 */
	/*public void modifyRemark(CSTransmissionBasic basic) {
		ModifyRemarkController mrc = new ModifyRemarkController(basic);
		view.getFileController().getTaskPool().addTask(mrc);
	}*/

	/**
	 * 客户端：删除好友
	 * @param monitor2
	 */
	/*public void deleteFriend(CSTransmissionBasic basic) {
		DeleteFriendController dfc = new DeleteFriendController(basic);
		view.getFileController().getTaskPool().addTask(dfc);
	}*/

	/**
	 * 客户端：修改用户信息
	 * @param monitor2
	 */
	public void modifyUser(CSTransmissionBasic basic) {
		ModifyUserController muc = new ModifyUserController(basic);
		view.getFileController().getTaskPool().addTask(muc);
	}

	/**
	 * 服务端：注册管理员
	 * @param text
	 * @param text2
	 * @param text3
	 */
	/*public void register(String user, String password, String nick) {
		try {
			Date d = new Date();
			User u = new User();
			u.setKey(MD5Util.getHash(user));
			u.setUser(user);
			u.setPassword(password);
			u.setNick(nick);
			u.setEmail(user);
			u.setState(0);//正常
			u.setRole(2);//管理员
			u.setCode("");
			u.setRegTime(d);
			u.setActTime(d);
			UserService.register(u);
			ViewUtil.hint(view.getShell(), "成功注册，请登录！");
			if(view.getRegister() != null){
				view.getRegister().close();
				view.setRegister(null);
				view.getViewController().toLoginView();
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(view.getRegister() != null){
				view.getRegister().updateVert();
				view.getRegister().setEUser(e.getMessage());
			}
		}
	}*/

	/**
	 * 服务端：管理员登陆
	 * @param user
	 * @param password
	 */
	/*public void login(String user, String password) {
		try {
			User u = UserService.validateAdmin(user, password);
			if(u != null){
				this.u = u;
				if(this.u.getHeadImageBytes() != null){
					this.u.setHeadImage(new ImageData(new ByteArrayInputStream(u.getHeadImageBytes())));
					this.u.setHeadImageBytes(null);
				}
				if(view.getLogin() != null){
					view.getLogin().close();
					view.show();
				}
			}else{
				if(view.getLogin() != null){
					view.getLogin().hint("用户名或密码错误");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(view.getLogin() != null){
				view.getLogin().hint(e.getMessage());
			}
		}
	}*/

	/**
	 * 服务端：修改昵称
	 * @param text
	 */
	/*public void modifyNick(String nick) {
		try {
			UserService.modifyNick(u.getKey(), nick);
			updateUser();
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), e.getMessage());
		}
	}*/
	
	/**
	 * 更新账号信息
	 * @throws IOException 
	 */
	/*public void updateUser() {
		try {
			User u = UserService.getUser(this.u.getUser());
			if(u != null){
				this.u = u;
				if(this.u.getHeadImageBytes() != null){
					this.u.setHeadImage(new ImageData(new ByteArrayInputStream(u.getHeadImageBytes())));
					this.u.setHeadImageBytes(null);
				}
				view.updateSetting();
				view.updateView();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}*/

	/**
	 * 服务端：修改头像
	 * @param bytes
	 */
	/*public void modifyHeadImage(byte[] bytes) {
		try {
			UserService.modifyHeadImage(u.getKey(), bytes);
			updateUser();
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), e.getMessage());
		}
	}*/

	/**
	 * 服务端：修改密码
	 * @param text
	 * @param text2
	 */
	/*public void modifyPassword(String old, String nwe) {
		try {
			UserService.modifyPassword(u.getKey(), old, nwe);
			updateUser();
			ViewUtil.hint(view.getShell(), "修改密码成功");
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), e.getMessage());
		}
	}*/

	/**
	 * 获取所有用户
	 */
	/*public void getUsers() {
		try {
			List<User> us = UserService.getAllUsers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 修改最大容量空间
	 * @param key
	 * @param max
	 */
	/*public void modifyMaxVolume(String key, double max) {
		try {
			UserService.modifyMaxVolume(key, max);
			if(view.getAccount() != null){
				view.getAccount().updateMaxVolume(key, max);
			}
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), "修改最大存储容量异常");
		}
	}*/

	/**
	 * 设置账号的状态
	 * @param key
	 * @param state
	 */
	/*public void setUserState(String key, int state) {
		try {
			if(u.getKey().compareTo(key) == 0 && state == 1){
				ViewUtil.hint(view.getShell(), "不能停用自己的账号");
				return;
			}
			UserService.setUserState(key, state);
			if(view.getAccount() != null){
				view.getAccount().updateUserState(key, state);
			}
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), "修改账号状态异常");
		}
	}*/

	/**
	 * 删除账号
	 * @param key
	 */
	/*public void deleteUser(String key) {
		try {
			if(u.getKey().compareTo(key) == 0){
				ViewUtil.hint(view.getShell(), "不能删除自己的账号");
				return;
			}
			UserService.deleteUser(key);
			if(view.getAccount() != null){
				getUsers();
			}
		} catch (IOException e) {
			e.printStackTrace();
			ViewUtil.hint(view.getShell(), "删除账号异常");
		}
	}*/

	/**
	 * 搜索用户
	 * @param search
	 */
	/*public void searchUser(String search) {
		try {
			List<User> us = UserService.searchUser(search);
			for(User u : us){
				if(u.getHeadImageBytes() != null){
					u.setHeadImage(new ImageData(new ByteArrayInputStream(u.getHeadImageBytes())));
					u.setHeadImageBytes(null);
				}
			}
			if(view.getAccount() != null){
				view.getAccount().showUsers(us);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}






