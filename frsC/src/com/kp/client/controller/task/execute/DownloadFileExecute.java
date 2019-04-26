/*package com.kp.client.controller.task.execute;

import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import com.kp.client.controller.task.DownloadFileController;
import com.kp.config.Config;
import com.kp.entity.trans.DataBlock;
import com.kp.util.file.IOUtil;
import com.kp.util.secret.AESUtil;

*//**
 * 下载文件
 * @author Mr Wang
 *
 *//*
public class DownloadFileExecute extends ExecutantBasic {

	private DownloadFileController dfc;//下载控制器
	private int port;//任务端口
	private String secrit;//口令
	private RandomAccessFile out;
	private String preFileName = "";//上一个传输的文件名
	
	public DownloadFileExecute(DownloadFileController dfc, int port, String secrit){
		this.dfc = dfc;
		this.port = port;
		this.secrit = secrit;
	}
	
	public void run(){
		try {
			socket = new Socket(Config.ServerIP, port);//连接服务端任务端口
			in = new ObjectInputStream(socket.getInputStream());
			DataBlock db = null;
			double length = 0;
			byte[] bytes = new byte[8192];
			int byteread = 0;
			String password = null;
			//Cache cache = new Cache(8192);//数据缓存池
			byte[] temp = null;
			while(!stop){
				db = (DataBlock) in.readObject();
				if(db.isEnd()) break;
				else if(db.isHead()){//头数据
					if(preFileName.compareTo(db.getName())!=0){
						IOUtil.close(out);
						//String path = dfc.checkInit(db.getName(), db.getSort());
						//out = new RandomAccessFile(path, "rw");
						preFileName = db.getName();
						if(db.getSecrit() != null){//若是密文下载，解析出加密密钥
							password = new String(AESUtil.decrypt(AESUtil.decode(db.getSecrit()), secrit));
						}
					}
					out.seek((long) db.getStart());
					length = db.getLength();
					while(length > 0 && !stop){//开始接收数据块
						byteread = in.read(bytes, 0, (int)Math.min(length, 8192));
						if(byteread == -1) throw new Exception("数据传输异常");
						if(db.getSecrit() == null){
							out.write(bytes, 0, byteread);
						}else{
							cache.write(bytes, 0, byteread);
							if(cache.isFull()){
								temp = AESUtil.decrypt(cache.read(), password);
								out.write(temp, 0, temp.length);
								cache.reset();
							}
						}
						dfc.reBytes(byteread);
						length -= byteread;
					}
					if(db.getSecrit()!=null && !cache.isEmpty()){
						temp = AESUtil.decrypt(cache.read(), password);
						out.write(temp, 0, temp.length);
						cache.reset();
					}
				}
			}
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			IOUtil.close(in, out, socket);
		}
	}
}
*/