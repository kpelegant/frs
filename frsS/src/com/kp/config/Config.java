package com.kp.config;

import java.io.IOException;

import com.kp.entity.db.setUp;
import com.kp.server.dao.setUpDao;
import com.kp.util.file.FileUtil;
import com.kp.util.kpabe.Kpabe;
import com.kp.util.kpabe.MK;
import com.kp.util.kpabe.PK;
import com.kp.util.kpabe.impl.KpabeImpl;


/**
 * 配置文件
 * @author Mr Wang
 *
 */
public class Config {

	public static int serverPort;//服务端口
	public static String defaultPath = "F:\\Workspaces2016\\store";//文件存储路径
	public static int minPort;//最小端口值
	public static int maxPort;//最大端口值
	public static long block;//文件分块大小，单位B
	public static Long sliceUpTime;//任务池切换时间，0.1秒
	public static Integer runningTaskNum;//任务池同时执行线程最大数量
	public static Integer calcuteHash;//计算哈希值块数条件
	public static Integer doubleThreadBlockNum;//启动双线程传输的块数条件
	
	//public static boolean isSetUp = false;
	public static MK mk;
	public static PK pk;
	
	
	public static void init() throws IOException, ClassNotFoundException{
		serverPort = 10000;
		minPort = 10001;
		maxPort = 11000;
		block = 67108864;
		sliceUpTime = (long) 100;
		runningTaskNum = 20;
		calcuteHash = 9;
		doubleThreadBlockNum = 16;
		
		
		if(!isSetup()) {
			System.out.println("没有公钥");
			mk = new MK();
			pk = new PK();
			Kpabe.setup(pk, mk);
			byte[] b_pk = KpabeImpl.PK2byte(pk);
			byte[] b_mk = KpabeImpl.MK2byte(mk);
			setUpDao dao = new setUpDao();
			setUp setup = new setUp(b_pk, b_mk);
			dao.insert(setup);
		}else {
			System.out.println("有公钥");
			setUpDao dao = new setUpDao();
			setUp setup;
			setup = dao.getSetUp();
			byte[] b_pk = setup.getPk();
			pk = KpabeImpl.byte2PK(b_pk);
			byte[] b_mk = setup.getMk();
			mk = KpabeImpl.byte2MK(b_mk);
		}
		/*if(!FileUtil.exists(defaultPath)){//判断数据块文件夹是否存在
			FileUtil.createFolder(defaultPath);
		}*/
		
	}
	
	public static boolean isSetup(){
		setUpDao dao = new setUpDao();
		if(dao.getSetUp() == null) {
			return false;
		}else {
			return true;
		}
	} 
	
	public static int getServerPort() {
		return serverPort;
	}

	public static void setServerPort(int serverPort) {
		Config.serverPort = serverPort;
	}

	public static String getDefaultPath() {
		return defaultPath;
	}

	public static void setDefaultPath(String defaultPath) {
		Config.defaultPath = defaultPath;
	}

	public static int getMinPort() {
		return minPort;
	}

	public static void setMinPort(int minPort) {
		Config.minPort = minPort;
	}

	public static int getMaxPort() {
		return maxPort;
	}

	public static void setMaxPort(int maxPort) {
		Config.maxPort = maxPort;
	}

	public static long getBlock() {
		return block;
	}

	public static void setBlock(long block) {
		Config.block = block;
	}

	public static Long getSliceUpTime() {
		return sliceUpTime;
	}

	public static void setSliceUpTime(Long sliceUpTime) {
		Config.sliceUpTime = sliceUpTime;
	}

	public static Integer getRunningTaskNum() {
		return runningTaskNum;
	}

	public static void setRunningTaskNum(Integer runningTaskNum) {
		Config.runningTaskNum = runningTaskNum;
	}

	public static Integer getDoubleThreadBlockNum() {
		return doubleThreadBlockNum;
	}

	public static void setDoubleThreadBlockNum(Integer doubleThreadBlockNum) {
		Config.doubleThreadBlockNum = doubleThreadBlockNum;
	}

	public static void update(String defaultPath, int minPort, int maxPort, long sliceUpTime,
			int runningTaskNum, int doubleThreadBlockNum) throws IOException {
		setDefaultPath(defaultPath);
		setMinPort(minPort);
		setMaxPort(maxPort);
		setSliceUpTime(sliceUpTime);
		setRunningTaskNum(runningTaskNum);
		setDoubleThreadBlockNum(doubleThreadBlockNum);
	}
}
