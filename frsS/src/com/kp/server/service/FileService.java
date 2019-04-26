package com.kp.server.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.kp.config.Config;
import com.kp.entity.db.myFile;
import com.kp.server.controller.FileController;
import com.kp.server.dao.myFileDao;


/**
 * 文件服务
 * @author Mr Wang
 *
 */
public class FileService {

	/**
	 * 清空数据库
	 * @throws IOException
	 */
/*	public static void clear() throws IOException{
		List<Filet> fs = FileDao.getAllFilet();
		for(Filet f : fs){
			FileDao.deleteFilet(f.getKey());
			FileUtil.delete(Config.defaultPath+"/"+f.getName());
		}
	}*/
	
	/**
	 * 将临时文件加入文件库
	 * @param tmp
	 * @param real
	 * @throws IOException 
	 */
	/*public static void addFile(String tmp, String real) throws IOException{
		synchronized (FileService.class) {
			Filet f = FileDao.getFilet(real);
			if(f == null){
				f = new Filet(MD5Util.getHash(real), real, 1);
				FileUtil.rename(Config.defaultPath+"/"+tmp, Config.defaultPath+"/"+real);
				FileDao.addFilet(f);
			}else{
				f.setNum(f.getNum()+1);
				FileUtil.delete(Config.defaultPath+"/"+tmp);
				FileDao.updateFilet(f);
			}
		}
	}*/
	
	/**
	 * 加入文件库
	 * @param tmp
	 * @param real
	 * @throws IOException 
	 */
	/*public static void addFile(String...reals) throws IOException{
		synchronized (FileService.class) {
			if(reals == null) return ;
			for(String real : reals){
				Filet f = FileDao.getFilet(real);
				if(f == null){
					f = new Filet(MD5Util.getHash(real), real, 1);
					FileDao.addFilet(f);
				}else{
					f.setNum(f.getNum()+1);
					FileDao.updateFilet(f);
				}
			}
		}
	}*/

	/**
	 * 删除文件
	 * @param names
	 * @throws IOException 
	 */
	/*public static void delete(List<String> names) throws IOException {
		synchronized (FileService.class) {
			for(String name : names){
				Filet f = FileDao.getFilet(name);
				if(f != null){
					if(f.getNum() <= 1){
						FileDao.deleteFilet(f.getKey());
						FileUtil.delete(Config.defaultPath+"/"+f.getName());
					}else{
						f.setNum(f.getNum()-1);
						FileDao.updateFilet(f);
					}
				}
			}
		}
	}*/

	public static void delete(String filename) throws IOException {
		synchronized (FileService.class) {
			myFileDao dao = new myFileDao();
			String storename = dao.findFileByFileName(filename).getStoreName();
			String filePath;
			filePath = Config.defaultPath+"\\"+storename;
			if(dao.delete(filename) > 0) {
				File file = new File(filePath);
				file.delete();
				System.out.println("s删除成功！");
			}else {

				System.out.println("s删除失败！");
			}
				/*Filet f = FileDao.getFilet(name);
				if(f != null){
					if(f.getNum() <= 1){
						FileDao.deleteFilet(f.getKey());
						FileUtil.delete(Config.defaultPath+"/"+f.getName());
					}else{
						f.setNum(f.getNum()-1);
						FileDao.updateFilet(f);
					}
				}*/
		}
	}

}
