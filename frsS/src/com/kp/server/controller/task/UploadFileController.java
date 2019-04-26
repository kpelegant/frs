package com.kp.server.controller.task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kp.config.Config;
import com.kp.entity.db.User;
import com.kp.entity.db.myFile;
import com.kp.entity.file.CFile;
import com.kp.entity.trans.UploadFile;
import com.kp.server.controller.FileController;
import com.kp.server.controller.task.execute.UploadFileExecute;
import com.kp.server.dao.myFileDao;
import com.kp.server.dao.userDao;
import com.kp.server.service.UserService;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;
import com.kp.util.secret.AESUtil;
import com.kp.util.secret.MD5Util;
import com.kp.util.view.ViewUtil;


/**
 * 上传文件
 * @author Mr Wang
 *
 */
public class UploadFileController extends UDCSTransmissionBasic {

	private FileController fc;  //申请端口，将UploadFileController对象加入任务池
	private UploadFile uf;  //上传文件命令:文件长度，存储位置，端口，线程数量
	private double size;
	private byte[] spassword;
	private String filename;
	private String storename;
	private int userid;
	
	
	//当前线程任务是否成功完成
	protected boolean success = false;
	
	//是否停止当前线程
	protected boolean stop = false;
	
	FileOutputStream fos;
	DataOutputStream dos;
	User u;
	int port;
	
	public UploadFileController(CSTransmissionBasic basic, FileController fc, int port){
		super(basic);
		this.fc = fc;
		this.uf = (UploadFile) apply;//交互命令
		this.uf.setPort(port);//服务器申请到的端口
	}
	
	public void run(){
		try {
			setStart();//开始
			port = uf.getPort();
			System.out.println("s返回pk");
			uf.setPk(Config.pk);
			out.writeObject(uf);//返回pk，端口，通知客户端状态
			uf = (UploadFile) in.readObject();
			
			spassword = uf.getSpassword();
			filename = uf.getFilename();
			System.out.println("filename"+filename);
			userDao dao = new userDao();
			u = dao.findUserByName(uf.getUsername());
			userid = u.getId();
			size = uf.getSize();
			uf.setPort(port);
			uf.setNormal(true);
			
			out.writeObject(uf);
			try{
				double length;//接收长度
				uf = (UploadFile) in.readObject();
				length = uf.getLength(); //密文长度
				System.out.println("密文长度："+length);
				if(uf.isNormal()) {
					System.out.println("开始上传接收");
					serverSocket = new ServerSocket(port);
					Socket socket = serverSocket.accept();
					System.out.println("s监听");
					
					in = new ObjectInputStream(socket.getInputStream());
					DataInputStream dis = new DataInputStream(in);
					storename = "t"+MD5Util.getHash(uf.getFilename()+"#"+new Date().toString()+"#"+FileUtil.getRandomString());
					fos = new FileOutputStream(Config.defaultPath+"\\"+storename);
					//fos = new FileOutputStream(Config.defaultPath+"\\"+filename);
					int i = 0;
					byte[] bytes = new byte[8176];
					while((i = dis.read(bytes, 0, (int)Math.min(length, 8176))) > 0){//开始接收数据块
						fos.write(bytes, 0, i);
						fos.flush();
						length = length - i;
						System.out.println("接收");
					}
					dis.close();
					fos.close();
					System.out.println("接收完成");
					//uf = (UploadFile) in.readObject();//交换上传状态
					if(!uf.isNormal()){
						throw new Exception();
					}
					Date uploadTime = new Date();
					myFile fileinfo = new myFile();
					fileinfo.setUserId(userid);
					fileinfo.setStoreName(storename);
					fileinfo.setFileName(filename);
					fileinfo.setUploadTime(uploadTime);
					fileinfo.setSize(size);
					fileinfo.setSpassword(spassword);
					myFileDao fileDao = new myFileDao();
					fileDao.insert(fileinfo);  //插入文件信息
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				//UserService.deleteFile(uf.getKey(), uf.getCode(), new String[]{uf.getL()});
				uf.setNormal(false);
				uf.setErrorMsg("上传过程中出现错误");
			}
			//out.writeObject(uf);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fc.realsePort(port);
			IOUtil.close(socket, in, out);
			System.gc();
			setStop();
		}
	}
	
	/**
	 * 添加上传的文件块顺序和文件名
	 * @param sort
	 * @param name
	 */
	/*public synchronized String addUploadBlockName(int sort){
		//calculate(false);
		String name = "t"+MD5Util.getHash("#"+uf.getLocation()+"#"+sort+"#"+new Date().toString()+"#"+FileUtil.getRandomString());
		list.put(sort, name);
		return name;
	}*/
	
	/**
	 * 计算hash值
	 * @param num
	 */
	/*public void calculate(boolean isFinal){
		if(isFinal && list.size() - nextCalculate > 0){
			String[] paths = new String[list.size() - nextCalculate + 1];
			paths[0] = randomOutput+"/"+hashs.size();
			for(int i=nextCalculate; i<list.size(); i++){
				paths[i-nextCalculate+1] = Config.defaultPath+"/"+list.get(i);
			}
			nextCalculate = list.size();
			CalculateHash h = new CalculateHash(paths);
			hashs.add(h);
			h.start();
		}else if(!isFinal && list.size() - nextCalculate >= Config.calcuteHash){
			String[] paths = new String[Config.calcuteHash+1];
			paths[0] = randomOutput+"/"+hashs.size();
			for(int i=nextCalculate; i<nextCalculate+Config.calcuteHash; i++){
				paths[i-nextCalculate+1] = Config.defaultPath+"/"+list.get(i);
			}
			nextCalculate = nextCalculate+Config.calcuteHash;
			CalculateHash h = new CalculateHash(paths);
			hashs.add(h);
			h.start();
		}
	}*/
}
