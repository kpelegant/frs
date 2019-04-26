package com.kp.server.controller.task;

import java.util.List;

import com.kp.entity.trans.DeleteFile;
import com.kp.server.dao.myFileDao;
import com.kp.server.service.FileService;
import com.kp.util.file.IOUtil;


/**
 * 删除文件
 * @author Mr Wang
 *
 */
public class DeleteFileController extends CSTransmissionBasic {
	
	private DeleteFile df;
	private String filename;
	private int userid;

	public DeleteFileController(CSTransmissionBasic basic){
		super(basic);
		this.df = (DeleteFile) apply;
	}
	
	public void run(){
		try {
			setStart();
			try {
				filename = df.getFilename();
				System.out.println("(s)想删文件名:"+filename);
				myFileDao dao = new myFileDao();
				userid = dao.findFileByFileName(filename).getUserId();
				df.setUserid(userid);
				out.writeObject(df); //返回userid
				df = (DeleteFile) in.readObject();
				if(df.isNormal()){
					System.out.println("s准备删除");
					FileService.delete(filename);
					df.setNormal(true);
					
				}
				out.writeObject(df);
			//	List<String> names = UserService.deleteFile(df.getKey(), df.getCode(), df.getPaths());
				//FileService.delete(names);
				//df.setNormal(true);
			} catch (Exception e) {
				e.printStackTrace();
				df.setNormal(false);
				df.setErrorMsg("获取目录结构异常");
			}
			//out.writeObject(df);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			IOUtil.close(socket, in, out);
			setStop();
		}
	}
}
