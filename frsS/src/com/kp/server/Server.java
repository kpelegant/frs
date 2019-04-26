package com.kp.server;

import java.io.IOException;



import com.kp.config.Config;
import com.kp.server.controller.FileController;
import com.kp.server.controller.UserController;
import com.kp.server.controller.ViewController;
import com.kp.server.view.View;

/**
 * 服务端入口
 * @author Mr Wang
 *
 */
public class Server {
	public static void main(String[] args) throws ClassNotFoundException {
		try {
			Config.init();
			//初始化组件
			
			View view = new View();
			UserController userController = new UserController();
			FileController fileController = new FileController();
			ViewController viewController = new ViewController();
			
			view.setUserController(userController);
			view.setFileController(fileController);
			view.setViewController(viewController);
			
			userController.setView(view);
			fileController.setView(view);
			viewController.setView(view);
			
			userController.startMonitor();  //开启服务
			//开始显示界面
			//viewController.toLoginView();
			viewController.toMainView();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器配置文件异常");
		}
	}
}
