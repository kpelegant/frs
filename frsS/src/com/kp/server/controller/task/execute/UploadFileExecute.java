package com.kp.server.controller.task.execute;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.kp.config.Config;
import com.kp.entity.trans.DataBlock;
import com.kp.server.controller.task.UploadFileController;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;


/**
 * 文件上传
 * @author Mr Wang
 *
 */
public class UploadFileExecute extends ExecutantBasic {
	
	private UploadFileController ufc;
	private OutputStream out;
	private String storeL;
	private int flag;//第几个线程
	private int sort;//第几个数据块

	public UploadFileExecute(){
	}
	public UploadFileExecute(UploadFileController ufc, Socket socket){
		this.ufc = ufc;
		this.socket = socket;
	}
	public UploadFileExecute(UploadFileController ufc, Socket socket, String storeL){
		this.ufc = ufc;
		this.socket = socket;
		this.storeL = storeL;
	}
	
	public void run(){
		try {
			in = new ObjectInputStream(socket.getInputStream());
			DataBlock db = null;
			double length = 0;//接收文件长度
			int byteread = 0;
			byte[] bytes = new byte[8176];
			while(!stop){
				db = (DataBlock) in.readObject();
				flag = db.getThreadsort();
				sort = db.getSort(); //数据块顺序
				if(db.isEnd()){//文件传输结束
					break;
				}else if(db.isHead()){//单个块传输开始
					//IOUtil.close(out);
					//String name = ufc.addUploadBlockName((int) db.getSort());  //根据MD5命名文件
		        	//String name = ufc.addUploadBlockName((int) db.getSort());  //根据MD5命名文件
				   // out = FileUtil.getFSDataOutputStream(Config.defaultPath+"/"+name);
					out =new FileOutputStream(Config.defaultPath+this.storeL, true);
					FileOutputStream sout =new FileOutputStream(Config.defaultPath+"/"+sort, true);
					
					length = db.getLength();//数据块长度
					while(length > 0 && !stop){//开始接收数据块
						byteread = in.read(bytes, 0, (int)Math.min(length, 8176));
						//byteread = in.read(bytes, db.getThreadsort()*db.getLength(), (int)Math.min(length, 8176));
						if(byteread == -1) throw new Exception("数据传输异常");
						sout.write(bytes, 0, byteread);
						length -= byteread;
					}
					int size=0;
		            //定义一个字节缓冲区,该缓冲区的大小根据需要来定义
		            byte[] buffer=new byte[1024];
		            FileInputStream fis=new FileInputStream(Config.defaultPath+"/"+sort);
		            //循环来读取该文件中的数据
		            while((size=fis.read(buffer))!=-1){
		            	out.write(buffer);
		            }
				}
			}
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			IOUtil.close(out, in, socket);
		}
	}
}
