package com.kp.util.file;

import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.kp.entity.db.User;
import com.kp.entity.db.fileUser;
import com.kp.entity.db.myFile;
import com.kp.entity.file.CFile;
import com.kp.server.dao.myFileDao;
import com.kp.server.dao.userDao;
import com.kp.util.secret.MD5Util;

//import sun.awt.shell.ShellFolder;

/**
 * 文件操作工具
 * @author ping
 *
 */
public class FileUtil {
	private static FileSystemView fsv = FileSystemView.getFileSystemView();
	private static Image folder;//文件夹Image
	private static Image uploading;//正在上传
	
	/**
	 * 获取文件图标
	 * @param f
	 * @return
	 */
	/*public static ImageData getSystemImage(File f){
		if(f.isDirectory()){
			return getFolderImage().getImageData();
		}else if(f.isUploading()){
			return getUploadingImage().getImageData();
		}else{
			try {
				File file = File.createTempFile("aaa", f.getName());
				return getSWTImageFromSwing((ImageIcon) fsv.getSystemIcon(file));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}*/
	
	/**
	 * 获取文件描述
	 * @return
	 */
	public static String getSystemTypeDescription(fileUser f){
		try {
			File file = File.createTempFile("aaa", f.getFilelist().getFileName());
			return fsv.getSystemTypeDescription(file);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取文件夹图标
	 * @param display
	 * @return
	 */
	public static Image getFolderImage(){
		if(folder == null){
			folder = new Image(Display.getDefault(), "images/folder_image.png");
		}
		return folder;
	}
	
	/**
	 * 获取文件夹图标
	 * @param display
	 * @return
	 */
	public static Image getUploadingImage(){
		if(uploading == null){
			uploading = new Image(Display.getDefault(), "images/uploading.png");
		}
		return uploading;
	}
	
	/**
	 * 获取文件大图标
	 */
	/*public static ImageData getMaxImageData(String name){
		try {
			File file = File.createTempFile("aaa", name);
			ShellFolder shellFolder = ShellFolder.getShellFolder(file);
			ImageData id = getSWTImageFromSwing(new ImageIcon(shellFolder.getIcon(true)));
			file.delete();
			return id;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}	
	}*/
	
	/**
	 * 转换icon成imagedata
	  */
	private static ImageData getSWTImageFromSwing(ImageIcon imageIcon){	 
	    if (imageIcon.getImage() instanceof BufferedImage)	 {
	    	BufferedImage bufferedImage = (BufferedImage) imageIcon.getImage();
	    	DirectColorModel colorModel = (DirectColorModel)bufferedImage.getColorModel();
	    	PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(), colorModel.getBlueMask());
	    	ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
	    	for(int x=0; x<bufferedImage.getWidth(); x++){
	    		for(int y=0; y<bufferedImage.getHeight(); y++){
			    	int rgb = bufferedImage.getRGB(x, y);
			    	int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));
			    	data.setPixel(x, y, pixel);
			    	if (colorModel.hasAlpha()) {
			    		data.setAlpha(x, y, (rgb>>24) & 0xFF);
			    	}
	    		}
	    	}
	    	return data;
	    }else{	    	 	    
	    	return null;
	    }
	}
	
	public static byte[] getBytesByName(String path) throws IOException{
		File f = new File(path);
		if(f.exists()){
			RandomAccessFile in = new RandomAccessFile(f, "r");
			byte[] bytes = new byte[(int) in.length()];
			in.readFully(bytes);
			return bytes;
		}
		throw new FileNotFoundException("FileNotFoundException:"+path);
	}
	
	/**
	 * 打开文件或文件夹
	 */
	public static void openFile(Shell shell, String url, String title){
		try {
			if(!new File(url).exists()){
				throw new IOException();
			}
			Runtime.getRuntime().exec("cmd /c start explorer "+url);
		} catch (IOException e) {
			e.printStackTrace();
			MessageBox box = new MessageBox(shell);
			box.setText("温馨提示");
			box.setMessage(title);
			box.open();
		}
	}
	
	/**创建文件
	 * @throws IOException */
	public static File createFile(String pathname) throws IOException{
		createFolder(new File(pathname.substring(0, pathname.lastIndexOf("\\"))));
		File file = new File(pathname);
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	
	/**
	 * 初始化文件
	 * @param f
	 * @param length
	 * @throws IOException
	 */
	public static void initFile(File f, long length) throws IOException{
		RandomAccessFile out = new RandomAccessFile(f, "rw");
		out.setLength(length);
		IOUtil.close(out);
	}
	
	/**
	 * 检查文件是否存在，存在则重命名文件，并创建文件夹
	 * @param path
	 * @return
	 */
	public static String checkFile(String path){
		File f = new File(path);
		String path2 = path;
		if(f.exists()){
			for(int i=2; ; i++){
				path2 = path.substring(0, path.lastIndexOf("."))+"("+i+")"+path.substring(path.lastIndexOf("."));
				if(!new File(path2).exists()){
					break;
				}
			}
		}
		createFolder(path2.substring(0, path2.lastIndexOf("\\")));
		return path2;
	}
	
	/**
	 * 判断该路径是否是文件
	 * @param path
	 * @return
	 */
	public static boolean isFile(String path){
		return new File(path).isFile();
	}
	
	/**
	 * 判断该路径是否是文件夹
	 * @param path
	 * @return
	 */
	public static boolean isDirectory(String path){
		return new File(path).isDirectory();
	}
	
	/**
	 * 创建文件夹
	 */
	public static void createFolder(File file){
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 创建文件夹
	 */
	public static void createFolder(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 判断文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean exists(String path){
		return new File(path).exists();
	}
	
	/**
	 * 获取所有文件路径
	 * @param path
	 * @param names
	 * @return
	 */
	public static List<String> getFilePath(String path, String[] names){
		List<String> l = new ArrayList<String>();
		if(names == null){
			File f = new File(path);
			File[] fs = f.listFiles();
			for(File fl : fs){
				if(fl.isFile()){
					l.add(fl.getAbsolutePath());
				}else{
					l.addAll(getFilePath(fl.getAbsolutePath(), null));
				}
			}
		}else{
			for(int i=0; i<names.length; i++){
				File f = new File(path+"\\"+names[i]);
				if(f.isFile()){
					l.add(f.getAbsolutePath());
				}else{
					l.addAll(getFilePath(f.getAbsolutePath(), null));
				}
			}
		}
		return l;
	}
	
	/**
	 * 将对象转换成字节
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesByObject(Object object) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(object);
		byte[] bytes = out.toByteArray();
		IOUtil.close(oos, out);
		return bytes;
	}
	
	/**
	 * 根据文件名获取目录文件
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Object getObject(byte[] bytes) throws Exception{
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object object = ois.readObject();
		IOUtil.close(ois, bis);
		return object;
	}
	
	/**
	 * 获取分块上传方案
	 * @return
	 */
	public static double[][] getBlocks(double length, int num, long block){
		int total = (int)(length/block) + (length%block>0 ? 1 : 0);
		int tn = total/num;
		int tn2 = total%num;
		double[][] blocks = new double[num][2];
		for(int i=0; i<num; i++){
			if(i==0){
				blocks[i][0] = i*block*(tn+(tn2>i?1:0));
			}else{
				blocks[i][0] = blocks[i-1][1];
			}
			if(i==num-1){
				blocks[i][1] = length;
			}else{
				blocks[i][1] = blocks[i][0]+block*(tn+(tn2>i?1:0));
			}
		}
		return blocks;
	}
	
	/**
	 * 获取下载方案
	 * @param list
	 * @param num
	 * @return
	 */
	public static List<List<String>> getBlocks(String[] list, int num){
		List<List<String>> ls = new ArrayList<List<String>>();
		for(int i=0, j=0; i<num; i++){
			List<String> l = new ArrayList<String>();
			for(int z=0; j<list.length && z<(int)(list.length/num)+(list.length%num>i?1:0); j++,z++){
				l.add(list[j]);
			}
			ls.add(l);
		}
		return ls;
	}
	
	/**
	 * 预估需要启动线程数
	 * @param length
	 * @param block
	 * @return
	 */
	public static int getThreadNum(double length, long block){
		int total = (int)(length/block) + (length%block>0 ? 1 : 0);
		int num = total>50?3:total>20?2:1;
		return num;
	}
	
	/**
	 * 预估需要启动线程数
	 * @param length
	 * @param block
	 * @return
	 */
	public static int getThreadNum(int num){
		return num>50?3:num>20?2:1;
	}
	
	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getRandomString(){
		Random r = new Random();
		String s = "";
		for(int i=0; i<100; i++){
			s += "#" + r.nextInt();
		}
		return s;
	}
	
	/**
	 * 修改文件名
	 * @param tmp
	 * @param real
	 */
	public static boolean rename(String tmp, String real){
		File f = new File(tmp);
		return f.renameTo(new File(real));
	}

	/**
	 * 删除文件
	 * @param string
	 */
	public static void delete(String tmp) {
		new File(tmp).delete();
	}
	
	/**
	 * 获取文件的hash值
	 * @return
	 * @throws IOException 
	 */
	public static String getHash(String path) throws IOException{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		RandomAccessFile in = new RandomAccessFile(path, "r");
		long length = in.length();
		byte[] bytes = new byte[4096];
		while(length > 0){
			int byteread = in.read(bytes, 0, (int) Math.min(length, 4096));
			out.write(bytes, 0, byteread);
			length -= byteread;
		}
		String hash = MD5Util.getHash(out.toByteArray());
		IOUtil.close(in, out);
		return hash;
	}
	
/*	*//**
	 * 排序显示CFile
	 * @param fs
	 * @param way 排序方式：1文件名，2时间，3文件大小
	 * @return
	 *//*
	public static List<File> sortCFile(List<File> fs, final int way){
		if(fs.size() > 1){
			Collections.sort(fs, new Comparator<File>() {
				Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
				@SuppressWarnings("deprecation")
				@Override
				public int compare(File f1, File f2) {
					if(way == 1){
						if((f1.isDirectory() && f2.isDirectory()) || (f1.isFile() && f2.isFile())){
							return cmp.compare(f1.getName(), f2.getName());
						}else{
							return f1.isDirectory() ? -1 : 1;
						}
					}else if(way == 2){
						if((f1.isDirectory() && f2.isDirectory()) || (f1.isFile() && f2.isFile())){
							return (-1)*cmp.compare(f1.getModifyDate().toLocaleString(), f2.getModifyDate().toLocaleString());
						}else{
							return f1.isDirectory() ? -1 : 1;
						}
					}else if(way == 3){
						if(f1.isDirectory() && f2.isDirectory()){
							return cmp.compare(f1.getName(), f2.getName());
						}if(f1.isFile() && f2.isFile()){
							return f1.getSize() > f2.getSize() ? -1 : 1;
						}else{
							return f1.isDirectory() ? -1 : 1;
						}
					}
					return 0;
				}
			});
		}
		return fs;
	}*/
	
	/**
	 * 获取加密后长度
	 * @param length数据长度
	 * * @param b加密长度
	 * @return
	 */
	public static double getSecritLength(double length, long b){
		int num = (int) (length/b);
		int num2 = (int) (length%b);
		return num*(b+(16-b%16))+(num2==0?0:num2+(16-num2%16));
	}
	
	/**
	 * 根据文件路径得到文件名
	 */
	
	/*public myFile getMFile(String path) {
		if(path.length() > 0){
			//path = path.substring(1);
			int nextObliqueLine = path.indexOf("\\"); //第一次出现
			String name = nextObliqueLine>0 ? path.substring(0, nextObliqueLine) : path;//此层文件夹的名字
			return this;
		}else {
			return null;
		}
	}*/
	
	/**
	 * 得到数据库文件供展示
	 */
	public static List<fileUser> getAllFiles(){
		List<fileUser> fUserList = new ArrayList<fileUser>();
		myFileDao filedao = new myFileDao();
		List<myFile> myfile = filedao.getAllFiles();
		for(myFile mf:myfile){
			fileUser fUser = new fileUser();
			userDao uDao = new userDao();
			User user = new User();
			user = uDao.findUserById(mf.getUserId());
			String username = user.getUsername();
			fUser.setFilelist(mf);
			fUser.setUsername(username);
			fUserList.add(fUser);
		}
		return fUserList;
	}
}

