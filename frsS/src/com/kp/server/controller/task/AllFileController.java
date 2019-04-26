package com.kp.server.controller.task;

import java.util.ArrayList;
import java.util.List;

import com.kp.entity.db.User;
import com.kp.entity.db.fileUser;
import com.kp.entity.db.myFile;
import com.kp.entity.trans.AllFile;
import com.kp.server.dao.myFileDao;
import com.kp.server.dao.userDao;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;

/**
 * 获取目录结构
 * @author Mr Wang
 *
 */
public class AllFileController extends CSTransmissionBasic {

	private AllFile af;
	private List<fileUser> fUserList = new ArrayList<fileUser>();
	
	public AllFileController(CSTransmissionBasic basic){
		super(basic);
		this.af = (AllFile) apply;
	}
	
	public void run(){
		try {
			setStart();
			try {
				/*myFileDao filedao = new myFileDao();
				List<myFile> myfile = filedao.getAllFiles();
				//System.out.println("数据库获取文件链表"+myfile);
				for(myFile mf:myfile){
					fileUser fUser = new fileUser();
					userDao uDao = new userDao();
					User user = new User();
					user = uDao.findUserById(mf.getUserId());
					String username = user.getUsername();
					fUser.setFilelist(mf);
					fUser.setUsername(username);
					//System.out.println("fuser"+fUser);
					fUserList.add(fUser);
				}*/
				fUserList = FileUtil.getAllFiles();
				af.setFileuserList(fUserList);
				af.setNormal(true);
				out.writeObject(af);
			} catch (Exception e) {
				e.printStackTrace();
				af.setNormal(false);
				af.setErrorMsg("获取目录结构异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			IOUtil.close(in, out, socket);
			setStop();
		}
	}
}
