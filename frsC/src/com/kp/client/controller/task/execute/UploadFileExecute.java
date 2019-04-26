/*package com.kp.client.controller.task.execute;

import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import com.kp.client.controller.task.UploadFileController;
import com.kp.config.Config;
import com.kp.entity.file.BlockMes;
import com.kp.entity.trans.DataBlock;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;
import com.kp.util.secret.AESUtil;

*//**
 * 上传文件
 * @author Mr Wang
 *
 *//*
public class UploadFileExecute extends ExecutantBasic {
	
	private UploadFileController ufc;//上传控制器
	private int port;//任务端口
	private String password;//加密文件密钥
	private RandomAccessFile in;
	private int flag;//第几个线程

	public UploadFileExecute(UploadFileController ufc, int port, String password){
		this.ufc = ufc;
		this.port = port;
		this.password = password;
	}
	public UploadFileExecute(Integer flag, UploadFileController ufc, int port, String password){
		this.flag = flag;
		this.ufc = ufc;
		this.port = port;
		this.password = password;
	}
	
	public void run(){
		try {
			socket = new Socket(Config.ServerIP, port);//连接服务端任务端口
			out = new ObjectOutputStream(socket.getOutputStream());
			int byteread = 0;
			while(!stop){
				BlockMes bm = ufc.getBlockMes();//申请任务块
				if(bm == null) break;
				if(in == null){
					in = new RandomAccessFile(bm.getPath(), "r");
				}
				in.seek((long) bm.getSe()[0]);
				byte[] bytes = new byte[8176];
				double length = bm.getSe()[1] - bm.getSe()[0];  //结束位置-开始位置
				out.writeObject(new DataBlock(null, bm.getSort(), flag, 0, password==null?length:FileUtil.getSecritLength(length, 8176), null));
				byte[] temp = null;
				while(!stop && length > 0){//开始向服务端传输
					byteread = in.read(bytes, 0, (int)Math.min(length, 8176));
					if(password == null){
						out.write(bytes, 0, byteread);
					}else{
						if(byteread < 8176){
							temp = new byte[byteread];
							System.arraycopy(bytes, 0, temp, 0, byteread);
						}else{
							temp = bytes;
						}
						byte[] re = AESUtil.encrypt(temp, password);
						out.write(re, 0, re.length);
					}
					length -= byteread;
					ufc.reBytes(byteread);
					IOUtil.clear(out);
				}
			}
			out.writeObject(new DataBlock());//发送结束标志
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			IOUtil.close(in, out, socket);
		}
	}
}
*/