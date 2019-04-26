package com.kp.client.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.swt.graphics.ImageData;
/*
import com.dicd.client.controller.task.AddFriendController;
import com.dicd.client.controller.task.CheckUserController;
import com.dicd.client.controller.task.DeleteFriendController;
import com.dicd.client.controller.task.FriendsController;
import com.dicd.client.controller.task.LoginController;
import com.dicd.client.controller.task.ModifyRemarkController;
import com.dicd.client.controller.task.ModifyUserController;
import com.dicd.client.controller.task.ReController;
import com.dicd.client.controller.task.RegisterController;
import com.dicd.client.controller.task.SearchUserController;*/

import com.kp.client.controller.task.CheckUserController;
import com.kp.client.controller.task.LoginController;
import com.kp.client.controller.task.ModifyUserController;
import com.kp.client.controller.task.ReController;
import com.kp.client.controller.task.RegisterController;
import com.kp.client.view.View;
import com.kp.entity.db.User;
import com.kp.entity.trans.Apply;
import com.kp.entity.trans.Login;
import com.kp.entity.trans.ModifyUser;
import com.kp.entity.trans.Register;
import com.kp.entity.trans.ValidateUser;
import com.kp.util.view.ViewUtil;


/**
 * 用户控制器
 * @author Mr Wang
 *
 */
public class UserController {

	private View view;
	private User u;//登录用户数据
	
	public UserController(){
		
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public User getUser(){
		return u;
	}
	
	public void setUser(User u) {
		this.u = u;
		view.updateView();
	}
	
	/**
	 * 检测账号是否可用
	 * @param user
	 */
	public void checkUser(String user){
		ReController rc = new ReController() {
			public void run(Apply apply) {
				ValidateUser vu = (ValidateUser) apply;
				if(vu.isUsing()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getRegister() != null){
								view.getRegister().setEUser("账号已使用");
							}
						}
					});
				}
			}
		};
		CheckUserController controller = new CheckUserController(rc, user);
		controller.start();
	}
	
	/**
	 * 账号注册
	 * @param user
	 * @param password
	 * @param nick
	 */
	public void register(String user, String password, String policy){
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final Register reg = (Register) apply;
				if(!reg.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getRegister() != null){
								view.getRegister().updateVert();
								view.getRegister().setEUser(reg.getErrorMsg());
							}
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), "成功注册，请登录！");
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getRegister() != null){
								view.getRegister().close();
								view.setRegister(null);
								view.getViewController().toLoginView();
							}
						}
					});
				}
			}
		};
		RegisterController rc2 = new RegisterController(rc, user, password, policy);
		rc2.start();
	}
	/**
	 * 登录
	 * @param user
	 * @param password
	 */
	public void login(String user, String password){
		ReController rc = new ReController() {  //实现回调接口
			public void run(Apply apply) {
				final Login login = (Login) apply;
				if(login.isNormal()){
					u = login.getU();
					System.out.println("usercontroller(login):返回用户："+u.getUsername()+", "+u.getPassword());
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getLogin() != null){
								view.getLogin().close();
								view.setLogin(null);
								System.out.println("想去主页面");
								view.getViewController().toMainView();
							}
						}
					});
				}else{
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getLogin() != null){
								view.getLogin().hint(login.getErrorMsg());
							}
						}
					});
				}
			}
		};
		LoginController lc = new LoginController(rc, user, password);
		lc.start();
	}

	/**
	 * 获取好友列表
	 */
	/*public void getFriends() {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final Friends f = (Friends) apply;
				if(f.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getShare() != null){
								for(Friend ff : f.getFriends()){
									if(ff.getFriend() != null && ff.getFriend().getHeadImageBytes() != null){
										ff.getFriend().setHeadImage(new ImageData(new ByteArrayInputStream(ff.getFriend().getHeadImageBytes())));
										ff.getFriend().setHeadImageBytes(null);
									}
								}
								view.getShare().showFriends(f.getFriends());
							}
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), f.getErrorMsg());
				}
			}
		};
		FriendsController fc = new FriendsController(rc, u.getKey(), u.getCode());
		fc.start();
	}
*/
	/**
	 * 搜索用户
	 * @param text
	 */
	/*public void searchUser(String search) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final SearchUser su = (SearchUser) apply;
				if(su.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							try {
								if(view.getShare() != null){
									if(su.getUser() != null && su.getUser().getHeadImageBytes() != null){
										su.getUser().setHeadImage(new ImageData(new ByteArrayInputStream(su.getUser().getHeadImageBytes())));
										su.getUser().setHeadImageBytes(null);
									}
									view.getShare().reSearchUser(su.getUser(), su.getShip());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), su.getErrorMsg());
				}
			}
		};
		SearchUserController suc = new SearchUserController(rc, u.getKey(), u.getCode(), search);
		suc.start();
	}
*/
	/**
	 * 添加好友
	 * @param f
	 */
	/*public void addFriend(String f) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				AddFriend af = (AddFriend) apply;
				if(af.isNormal()){
					searchUser(af.getFriend());
					getFriends();
				}else{
					ViewUtil.hint(view.getShell(), af.getErrorMsg());
				}
			}
		};
		AddFriendController afc = new AddFriendController(rc, u.getKey(), u.getCode(), f);
		afc.start();
	}*/

	/**
	 * 修改备注
	 * @param key
	 * @param remark
	 */
	/*public void modifyRemark(String key, String remark) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final ModifyRemark mr = (ModifyRemark) apply;
				if(mr.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							if(view.getShare() != null){
								view.getShare().updateRemark(mr.getFkey(), mr.getRemark());
							}
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), mr.getErrorMsg());
				}
			}
		};
		ModifyRemarkController mrc = new ModifyRemarkController(rc, u.getKey(), u.getCode(), key, remark);
		mrc.start();
	}
*/
	/**
	 * 删除好友
	 * @param key
	 */
	/*public void deleteFriend(String key) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				DeleteFriend df = (DeleteFriend) apply;
				if(df.isNormal()){
					if(view.getShare() != null){
						getFriends();
					}
				}else{
					ViewUtil.hint(view.getShell(), df.getErrorMsg());
				}
			}
		};
		DeleteFriendController dfc = new DeleteFriendController(rc, u.getKey(), u.getCode(), key);
		dfc.start();
	}*/

	/**
	 * 获取我的好友
	 */
	/*public void getMyFriends() {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				final Friends f = (Friends) apply;
				if(f.isNormal()){
					ViewUtil.asyncExec(new Runnable() {
						public void run() {
							for(Friend ff : f.getFriends()){
								if(ff.getFriend() != null && ff.getFriend().getHeadImageBytes() != null){
									ff.getFriend().setHeadImage(new ImageData(new ByteArrayInputStream(ff.getFriend().getHeadImageBytes())));
									ff.getFriend().setHeadImageBytes(null);
								}
							}
							view.getCommon().shareFile(f.getFriends());
						}
					});
				}else{
					ViewUtil.hint(view.getShell(), f.getErrorMsg());
				}
			}
		};
		FriendsController fc = new FriendsController(rc, u.getKey(), u.getCode());
		fc.start();
	}*/

	/**
	 * 修改昵称
	 * @param text
	 */
	/*public void modifyNick(String nick) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				ModifyUser mu = (ModifyUser) apply;
				if(mu.isNormal()){
					mu.getUser().setPassword(u.getPassword());
					u = mu.getUser();
				}else{
					ViewUtil.hint(view.getShell(), mu.getErrorMsg());
				}
				ViewUtil.asyncExec(new Runnable() {
					public void run() {
						if(u != null && u.getHeadImageBytes() != null){
							u.setHeadImage(new ImageData(new ByteArrayInputStream(u.getHeadImageBytes())));
							u.setHeadImageBytes(null);
						}
						view.updateSetting();
						view.updateView();
					}
				});
			}
		};
		ModifyUserController muc = new ModifyUserController(rc, u.getKey(), u.getCode(), nick);
		muc.start();
	}*/
	
	/**
	 * 修改密码
	 * @param text
	 */
	public void modifyPassword(String oldPassword, final String newPassword) throws IOException {
		User user = view.getUserController().getUser();
		String username = user.getUsername();
		System.out.println("用户名："+username);
		ReController rc = new ReController() {
			public void run(Apply apply) {
				ModifyUser mu = (ModifyUser) apply;
				if(mu.isNormal()){
					u = mu.getUser();
					u.setPassword(newPassword);
					ViewUtil.hint(view.getShell(), "修改成功");
				}else{
					ViewUtil.hint(view.getShell(), mu.getErrorMsg());
				}
				ViewUtil.asyncExec(new Runnable() {
					public void run() {
						view.updateSetting();
					}
				});
			}
		};
		ModifyUserController muc = new ModifyUserController(rc, username,oldPassword, newPassword);
		muc.start();
	}
	
	/**
	 * 修改头像
	 * @param text
	 */
	/*public void modifyHeadImage(byte[] headImage) {
		ReController rc = new ReController() {
			public void run(Apply apply) {
				ModifyUser mu = (ModifyUser) apply;
				if(mu.isNormal()){
					mu.getUser().setPassword(u.getPassword());
					u = mu.getUser();
				}else{
					ViewUtil.hint(view.getShell(), mu.getErrorMsg());
				}
				ViewUtil.asyncExec(new Runnable() {
					public void run() {
						if(u != null && u.getHeadImageBytes() != null){
							u.setHeadImage(new ImageData(new ByteArrayInputStream(u.getHeadImageBytes())));
							u.setHeadImageBytes(null);
						}
						view.updateSetting();
						view.updateView();
					}
				});
			}
		};
		ModifyUserController muc = new ModifyUserController(rc, u.getKey(), u.getCode(), headImage);
		muc.start();
	}*/
}
