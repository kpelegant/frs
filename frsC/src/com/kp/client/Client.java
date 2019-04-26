package com.kp.client;

import com.kp.client.controller.FileController;
import com.kp.client.controller.UserController;
import com.kp.client.controller.ViewController;
import com.kp.client.view.View;

/**
 * 客户端入口
 * @author Mr Wang
 *
 */
public class Client {

	public static void main(String[] args) {
		//初始化组件
		View view = new View();
		ViewController viewController = new ViewController();
		UserController userController = new UserController();
		FileController fileController = new FileController();
		
		viewController.setView(view);
		userController.setView(view);
		fileController.setView(view);
		
		view.setViewcontroller(viewController);
		view.setUserController(userController);
		view.setFileController(fileController);
		
		//开始显示界面
		viewController.toLoginView();
		//viewController.toMainView();
	}
}
