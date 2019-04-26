package com.kp.server.view.setting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.kp.entity.db.User;
import com.kp.server.dao.userDao;
import com.kp.server.view.View;
import com.kp.server.view.common.FileTable;
import com.kp.server.view.common.UserTable;


public class UserMes extends PageItem {
	private UserTable userTable;
	
	public UserMes(Composite parent, View view) {
		super(parent, "用户信息", view);
	}

	@Override
	protected void layout(Composite parent) {
		userTable = new UserTable(parent);
		
	}
	
	protected void setBounds(int width, int height) {
		/*cserverPort.setBounds((width-300)/2-40, 10, 180, 40);
		tserverPort.setBounds((width-300)/2+140, 15, 180, 30);
		cdefaultPath.setBounds((width-300)/2-40, 50, 180, 40);
		tdefaultPath.setBounds((width-300)/2+140, 55, 180, 30);
		cminPort.setBounds((width-300)/2-40, 90, 180, 40);
		tminPort.setBounds((width-300)/2+140, 95, 180, 30);
		cmaxPort.setBounds((width-300)/2-40, 130, 180, 40);
		tmaxPort.setBounds((width-300)/2+140, 135, 180, 30);
		csliceUp.setBounds((width-300)/2-40, 170, 180, 40);
		tsliceUp.setBounds((width-300)/2+140, 175, 180, 30);
		cmaxThread.setBounds((width-300)/2-40, 210, 180, 40);
		tmaxThread.setBounds((width-300)/2+140, 215, 180, 30);
		cmutiBlockNum.setBounds((width-300)/2-40, 250, 180, 40);
		tmutiBlockNum.setBounds((width-300)/2+140, 255, 180, 30);*/
		userTable.setBounds(0, 0, width, height);
	}
	
	public void showData() {
		List<User> list = new ArrayList<User>();
		userDao dao = new userDao();
		list = dao.getAllUsers();
		userTable.showUsersToTable(list);
	}
	
	public void dispose() {
		super.dispose();
	};
}
