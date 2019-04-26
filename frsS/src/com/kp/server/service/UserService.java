package com.kp.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.entity.db.User;
import com.kp.entity.db.myFile;
import com.kp.entity.file.CFile;
import com.kp.server.dao.userDao;
import com.kp.util.file.FileUtil;
import com.kp.util.secret.AESUtil;
import com.kp.util.secret.MD5Util;

/**
 * 用户服务
 * @author Mr Wang
 *
 */
public class UserService {

	/**
	 * 判断账号是否存在
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public static boolean exist(String user) throws IOException {
		try {
			User u = new userDao().findUserByName(user);
			return u != null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("服务器操作异常");
		}
	}
	
	/**
	 * 注册
	 * @param u
	 * @throws IOException
	 */
	public static void register(User u) throws IOException{
		if(exist(u.getUsername())){
			throw new IOException("账号已经被注册");
		}
		try {
			userDao userDao = new userDao();
			userDao.insert(u);
			System.out.println("注册成功（insert）");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("服务器操作异常");
		}
	}
	
	/**
	 * 清空数据库
	 * @throws IOException
	 */
	/*public static void clear() throws IOException{
		List<User> us = UserDao.getAllUsers();
		for(User u : us){
			UserDao.deleteUser(u.getKey());
		}
	}*/

	/**
	 * 验证用户名和密码
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public static User validateUser(String user, String password) throws Exception {
		try {
			userDao userDao = new userDao();
			User u = userDao.findUserByName(user);
			if(u != null && u.getState()==0 && u.getRole()==1 && u.getPassword().compareTo(password)==0){
				return u;
			}else if(u != null && u.getState()==1){
				throw new Exception("账号已被暂停使用");
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("登陆异常");
		}
	}

	/**
	 * 获取用户资料
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public static User getUser(String user) throws IOException {
		userDao userDao = new userDao();
		User u = userDao.findUserByName(user);
		return u;
	}
	
	public static User getUserByKey(int id) throws IOException{
		userDao userDao = new userDao();
		User u = userDao.findUserById(id);
		return u;
	}
	
	/**
	 * 获取该用户当前目录下所有文件信息
	 * @param key
	 * @param code
	 * @param present
	 * @return
	 * @throws IOException 
	 */
	/*public static List<CFile> getList(String key, String code, String present) throws IOException {
		try {
			User u = UserDao.getUserByKey(key);
			byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
			CFile f = (CFile) FileUtil.getObjectByByte(bytes);
			if(f != null){
				CFile file = f.getCFile(present);
				if(file.isDirectory()){
					List<CFile> list = file.listCFilesByList();
					for(int i=0; i<list.size(); i++){
						list.get(i).clear();
					}
					return list;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new IOException("获取目录信息失败");
	}*/

	/**
	 * 添加文件信息
	 * @param key
	 * @param code
	 * @param l
	 * @param size 
	 * @param password
	 * @return false文件已存在，true添加成功
	 * @throws IOException 
	 */
	/*public static String addCFile(String l, myFile cf) throws IOException {
		synchronized (UserService.class) {
			User u = UserDao.getUserByKey(key);
			if(u.getUseVolume()+(cf.getSize()/1024) > u.getMaxVolume()) return "云存储容量不够";
			byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
			CFile f = (CFile) FileUtil.getObjectByByte(bytes);
			if(f.getCFile(l) == null){
				f.addCFile(l.substring(0, l.lastIndexOf("\\")), cf);
				u.setUseVolume(u.getUseVolume()+(cf.getSize()/1024));
				u.setFileString(AESUtil.encode(AESUtil.encrypt(FileUtil.getBytesByObject(f), code)));
				UserDao.updateUser(u);
				return null;
			}
			return "文件已存在";
		}
	}*/
	
	/**
	 * 设置正在上传文件的信息
	 * @param key
	 * @param code
	 * @param l
	 * @param hname
	 * @param size
	 * @throws IOException 
	 */
	/*public static void setUploadingFile(String key, String code, String l, List<String> hname, Double size) throws IOException{
		synchronized (UserService.class) {
			User u = UserDao.getUserByKey(key);
			byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
			CFile f = (CFile) FileUtil.getObjectByByte(bytes);
			CFile upload = f.getCFile(l);
			if(upload == null){
				FileService.delete(hname);
				throw new IOException("正在上传的文件不存在");
			}else{
				upload.setOther(hname);
				u.setFileString(AESUtil.encode(AESUtil.encrypt(FileUtil.getBytesByObject(f), code)));
				UserDao.updateUser(u);
			}
		}
	}*/

	/**
	 * 获取该路径下的文件信息
	 * @param key
	 * @param code
	 * @param l
	 * @return
	 * @throws IOException 
	 */
/*	public static myFile getCFile(String location) throws IOException {
		User u = UserDao.getUserByKey(key);
		byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
		myFile f = (CFile) FileUtil.getObjectByByte(bytes);
		return f.getCFile(l);
	}
*/
	/**
	 * 删除文件
	 * @param key
	 * @param code
	 * @param paths
	 * @return
	 * @throws IOException 
	 */
	/*public static List<String> deleteFile(String key, String code, String[] paths) throws Exception {
		synchronized (UserService.class) {
			User u = UserDao.getUserByKey(key);
			byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
			CFile f = (CFile) FileUtil.getObjectByByte(bytes);
			List<String> ls = new ArrayList<String>();
			for(String p : paths){
				CFile ff = f.getCFile(p);
				if(ff == null) continue;
				ShareService.deleteShareFile(key, code, ff);
				List<CFile> tmps = ff.getAllCFile();
				for(CFile tmp : tmps){
					if(tmp.getHName() != null){
						for(String name : tmp.getHName()){
							ls.add(name);
						}
					}
				}
				ff.delete();
				u.setUseVolume(u.getUseVolume()-(ff.getSize()/1024));
			}
			u.setFileString(AESUtil.encode(AESUtil.encrypt(FileUtil.getBytesByObject(f), code)));
			UserDao.updateUser(u);
			return ls;
		}
	}*/

	/**
	 * 创建文件夹
	 * @param key
	 * @param code
	 * @throws IOException 
	 */
	/*public static CFile createFolder(String key, String code, String folder) throws IOException {
		synchronized (UserService.class) {
			User u = UserDao.getUserByKey(key);
			byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
			CFile f = (CFile) FileUtil.getObjectByByte(bytes);
			CFile ff = f.getCFile(folder);
			String name = "新建文件夹";
			if(ff.exist(name)){
				for(int i=2; ;i++){
					name = "新建文件夹("+i+")";
					if(!ff.exist(name)){
						break;
					}
				}
			}
			ff.addCFile(name);
			u.setFileString(AESUtil.encode(AESUtil.encrypt(FileUtil.getBytesByObject(f), code)));
			UserDao.updateUser(u);
			return ff.getCFile("\\"+name);
		}
	}*/

	/**
	 * 重命名文件或文件夹
	 * @param key
	 * @param code
	 * @param old
	 * @param nwe
	 * @throws IOException 
	 */
	/*public static boolean renameFile(String key, String code, String old,
			String nwe) throws Exception {
		synchronized (UserService.class) {
			User u = UserDao.getUserByKey(key);
			byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
			CFile f = (CFile) FileUtil.getObjectByByte(bytes);
			if(f.getCFile(old.substring(0, old.lastIndexOf("\\"))+"\\"+nwe) == null){
				CFile ff = f.getCFile(old);
				if(!ff.isUploading()){
					ShareService.rupdateShareFile(key, code, ff, nwe);
					ff.delete();
					ff.setName(nwe);
					f.addCFile(old.substring(0, old.lastIndexOf("\\")), ff);
					u.setFileString(AESUtil.encode(AESUtil.encrypt(FileUtil.getBytesByObject(f), code)));
					UserDao.updateUser(u);
				}
				return true;
			}
			return false;
		}
	}*/

	/**
	 * 获取目录结构
	 * @param key
	 * @param code
	 * @return
	 * @throws IOException 
	 */
	/*public static CFile getAllFolder(String key, String code) throws IOException {
		User u = UserDao.getUserByKey(key);
		byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
		CFile f = (CFile) FileUtil.getObjectByByte(bytes);
		return f;
	}*/

	/**
	 * 移动文件
	 * @param key
	 * @param code
	 * @param paths
	 * @param dst
	 * @throws IOException 
	 */
	/*public static boolean moveFile(String key, String code, String[] paths,
			String dst) throws Exception {
		synchronized (UserService.class) {
			User u = UserDao.getUserByKey(key);
			byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
			CFile f = (CFile) FileUtil.getObjectByByte(bytes);
			for(String p : paths){
				if(f.getCFile(dst+"\\"+p.substring(p.lastIndexOf("\\")+1))==null && p.compareTo(dst) != 0){
					CFile ff = f.getCFile(p);
					if(ff.isUploading()) continue;
					//检测分享文件
					ShareService.updateShareFile(key, code, ff, dst);
					ff.delete();
					f.addCFile(dst, ff);
				}else{
					return false;
				}
			}
			u.setFileString(AESUtil.encode(AESUtil.encrypt(FileUtil.getBytesByObject(f), code)));
			UserDao.updateUser(u);
			return true;
		}
	}*/

	/**
	 * 保存分享文件
	 * @param key
	 * @param code
	 * @param skey
	 * @param path
	 * @throws Exception 
	 */
	/*public static boolean saveShareFile(String key, String code, String skey,
			String path2) throws Exception {
		synchronized (UserService.class) {
			Share s = ShareDao.getShareByKey(skey);
			User u = UserDao.getUserByKey(key);
			byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
			CFile f = (CFile) FileUtil.getObjectByByte(bytes);
			String pri = new String(AESUtil.decrypt(AESUtil.decode(u.getPriKey()), code));
			if(s.getSharer().getKey().compareTo(u.getKey()) == 0){
				String path = new String(AESUtil.decrypt(AESUtil.decode(s.getMyshare()), pri)) ;
				CFile cf = f.getCFile(path);
				if(cf != null){
					ShareFile sf = new ShareFile(cf.getName(), cf.getHName(), cf.getSize(), cf.getPassword());
					s.setToShare(sf);
				}else{
					throw new Exception("分享文件异常");
				}
			}else{
				ShareFile sf = (ShareFile) FileUtil.getObjectByByte(RSAUtil.decrypt(AESUtil.decode(s.getToShareString()), pri));
				s.setToShare(sf);
			}
			if(f.getCFile(path2+"\\"+s.getToShare().getName()) == null){
				if(u.getUseVolume()+(s.getToShare().getSize()/1024) > u.getMaxVolume()) return false;
				CFile ff = new CFile(s.getToShare().getName(), s.getToShare().getHname(), s.getToShare().getSize(), s.getToShare().getPassword());
				FileService.addFile(ff.getHName());
				f.addCFile(path2, ff);
				u.setFileString(AESUtil.encode(AESUtil.encrypt(FileUtil.getBytesByObject(f), code)));
				u.setUseVolume(u.getUseVolume()+(s.getToShare().getSize()/1024));
				UserDao.updateUser(u);
				return true;
			}
			return false;
		}
	}
*/
	/**
	 * 检索文件
	 * @param key
	 * @param code
	 * @param present
	 * @param wkey
	 * @return 
	 * @throws IOException 
	 */
	/*public static List<CFile> searchFile(String key, String code, String present,
			String wkey) throws IOException {
		User u = UserDao.getUserByKey(key);
		byte[] bytes = AESUtil.decrypt(AESUtil.decode(u.getFileString()), code);
		CFile f = (CFile) FileUtil.getObjectByByte(bytes);
		return f.getCFile(present).getAllCFileByName(wkey);
	}*/

	/**
	 * 修改昵称
	 * @param key
	 * @param code
	 * @param nick
	 * @throws IOException 
	 */
	/*public static void modifyNick(String key, String nick) throws IOException {
		try {
			User u = UserDao.getUserByKey(key);
			u.setNick(nick);
			UserDao.updateUser(u);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("修改昵称异常");
		}
	}
*/
	/**
	 * 修改密码
	 * @param key
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws IOException
	 */
	public static User modifyPassword(String username, String oldPassword, String newPassword) throws IOException {
		try {
			userDao userdao = new userDao();
			User u = userdao.findUserByName(username);
			if(oldPassword.compareTo(u.getPassword()) == 0){
				u.setPassword(newPassword);
				userdao.update(username, newPassword);
				return u;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("修改密码异常");
		}
		throw new IOException("原密码错误");
	}

	/**
	 * 设置头像
	 * @param key
	 * @param headImage
	 * @throws IOException
	 */
	/*public static void modifyHeadImage(String key, byte[] headImage) throws IOException {
		try {
			User u = UserDao.getUserByKey(key);
			u.setHeadImageBytes(headImage);
			UserDao.updateUser(u);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("修改头像异常");
		}
	}*/

	/**
	 * 检查账号、随机码合法性
	 * @param key
	 * @param code
	 * @return
	 * @throws IOException 
	 */
	/*public static String checkCode(String key, String code) throws IOException {
		User u = UserDao.getUserByKey(key);
		if(u == null) return null;
		String password = new String(AESUtil.decrypt(AESUtil.hex2Byte(code), u.getCode()));
		if(u.getState()==0 && u.getPassword().compareTo(MD5Util.getHash(password))==0){
			return password;
		}
		return null;
	}*/

	/**
	 * 判断是否是第一次使用系统
	 * @return
	 * @throws IOException 
	 */
	/*public static boolean isFristUseSystem() throws IOException {
		return UserDao.getAllUsers().size() == 0;
	}*/

	/**
	 * 验证管理员账号
	 * @param user
	 * @param password
	 * @throws IOException 
	 */
	/*public static User validateAdmin(String user, String password) throws IOException {
		try {
			User u = UserDao.getUserByUser(user);
			if(u != null && u.getState()==0 && u.getRole()==2 && u.getPassword().compareTo(MD5Util.getHash(password))==0){
				u.setActTime(new Date());
				UserDao.updateUser(u);
				u.setPassword(null);
				if(u.getHeadImageBytes() == null){
					u.setHeadImageBytes(FileUtil.getDefaultHeadImage());
				}
				return u;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("登陆异常");
		}
	}*/

	/**
	 * 获取所有用户
	 * @return
	 * @throws IOException 
	 */
	/*public static List<User> getAllUsers() throws IOException {
		List<User> us = UserDao.getAllUsers();
		for(User u : us){
			u.setPassword(null);
			if(u.getHeadImageBytes() == null){
				//u.setHeadImageBytes(FileUtil.getDefaultHeadImage());
			}
		}
		return us;
	}*/

	/**
	 * 修改最大容量
	 * @param key
	 * @param max
	 * @throws IOException 
	 */
	/*public static void modifyMaxVolume(String key, double max) throws IOException {
		User u = UserDao.getUserByKey(key);
		u.setMaxVolume(max);
		UserDao.updateUser(u);
	}*/

	/**
	 * 设置账号状态
	 * @param key
	 * @param state
	 * @throws IOException 
	 */
	/*public static void setUserState(String key, int state) throws IOException {
		User u = UserDao.getUserByKey(key);
		u.setState(state);
		UserDao.updateUser(u);
	}*/

	/**
	 * 删除账号
	 * @param key
	 * @throws IOException 
	 */
	/*public static void deleteUser(String key) throws IOException {
		List<Share> ss = ShareDao.getMyShares(key);
		ss.addAll(ShareDao.getToShares(key));
		for(Share s : ss){
			ShareDao.deleteShare(s.getKey());
		}
		for(Friend f : FriendDao.getMyFriends(key)){
			FriendDao.deleteFriend(f.getKey());
		}
		UserDao.deleteUser(key);
	}
*/
	/**
	 * 搜索用户
	 * @param search
	 * @return
	 * @throws IOException 
	 */
	/*public static List<User> searchUser(String search) throws IOException {
		List<User> us = UserDao.searchUsers(search);
		for(User u : us){
			u.setPassword(null);
			if(u.getHeadImageBytes() == null){
				u.setHeadImageBytes(FileUtil.getDefaultHeadImage());
			}
		}
		return us;
	}*/
}
