package com.kp.client.controller.task;

import com.kp.entity.trans.AllFile;
import com.kp.util.file.IOUtil;

/**
 * 获取当前文件夹内的所有文件和文件夹
 * @author Mr Wang
 *
 */
public class AllFileController extends CSTransmissionBasic {

	private AllFile af;
	
	public AllFileController(ReController rc){
		super(rc);
	}
	
	public void run(){
		try {
			af = new AllFile();
			super.connectServer(af);
			af = (AllFile) in.readObject();
			System.out.println("返回af"+af);
		} catch (Exception e) {
			e.printStackTrace();
			af.setNormal(false);
			af.setErrorMsg("获取目录结构异常");
		}finally{
			rc.run(af);
			IOUtil.close(socket, in, out);
		}
	}
}
