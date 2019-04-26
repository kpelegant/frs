package com.kp.server.controller.task.execute;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.kp.entity.file.BlockMes;
import com.kp.entity.trans.DataBlock;
import com.kp.server.controller.task.DownloadFileController;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;


/**
 * 下载文件
 * @author Mr Wang
 *
 */
public class DownloadFileExecute extends ExecutantBasic {

	private DownloadFileController dfc;
	private InputStream in;
	
	public DownloadFileExecute(DownloadFileController dfc, Socket socket){
		this.dfc = dfc;
		this.socket = socket;
	}
	
	public void run(){
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			byte[] data = new byte[8192];
			double length = 0;
			int byteread = 0;
			while(!stop){
				//BlockMes bm = dfc.getBlockMes();
				/*if(bm == null) break;
				if(bm.getSort() <= 0){
				//	out.writeObject(new DataBlock(bm.getName(), bm.getSort(), bm.getSe()[0], 0, bm.getPassword()));
					continue;//识别没有文件块情况
				}*/
				//in = FileUtil.getFSDataInputStream(bm.getPath());
				length = in.available();
				//out.writeObject(new DataBlock(bm.getName(), bm.getSort(), bm.getSe()[0], length, bm.getPassword()));
				while(!stop && length > 0){//开始发生数据
					byteread = in.read(data, 0, (int) Math.min(length, 8192));
					out.write(data, 0, byteread);
					length -= byteread;
				}
				IOUtil.close(in);
			}
			out.writeObject(new DataBlock());
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			IOUtil.close(out, in, socket);
		}
	}
}
