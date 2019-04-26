package com.kp.client.controller.task;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.HashMap;

import com.kp.client.dao.skDao;
import com.kp.config.Config;
import com.kp.entity.db.User;
import com.kp.entity.trans.DataBlock;
import com.kp.entity.trans.DownloadFile;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;
import com.kp.util.kpabe.CT;
import com.kp.util.kpabe.Kpabe;
import com.kp.util.kpabe.PK;
import com.kp.util.kpabe.SK;
import com.kp.util.kpabe.impl.KpabeImpl;
import com.kp.util.secret.AESUtil;
import com.kp.util.view.ViewUtil;
import com.sun.corba.se.impl.orbutil.CacheTable;

import it.unisa.dia.gas.jpbc.Element;


/**
 * 下载控制器
 * @author Mr Wang
 *
 */
public class DownloadFileController extends UDCSTransmissionBasic {

	private DownloadFile df;
	private String filename;
	private int userid;
	private String dst;
	private byte[] spassword; //加密后的密钥
	private Element dpassword;//解密后的密钥
	private CT ctPassword = new CT();
	private SK sk = new SK();
	private PK pk = new PK();
	private byte[] bSk;
	int port;
	
	//当前线程任务是否成功完成
	protected boolean success = false;
	
	//是否停止当前线程
	protected boolean stop = false;
	
	FileOutputStream fos;
	DataInputStream dis;
	
	
	public DownloadFileController(ReUDController rc, String filename, String dst,  int userid) {
		super(rc, filename, dst);
		this.userid = userid;
		this.filename = filename;
		this.dst = dst;
	}

	public void run(){
		try {
			/*if(isStop()){
				df = new DownloadFile();
				df.setNormal(true);
				return;
			}*/
			df = new DownloadFile();//发送任务命令
			System.out.println("即将下载用户名"+filename);
			df.setFilename(filename);
			df.setUserid(userid);
			super.connectServer(df);
			df = (DownloadFile) in.readObject();
			port = df.getPort();
			totalLength = df.getSize();
			if(df.isNormal()){
				spassword = df.getSpassword();  //得到加密过的密钥
				pk = df.getPk();
				ctPassword = KpabeImpl.byte2CT(spassword);
				
				skDao dao = new skDao();
				bSk = dao.findSKByUserId(userid).getSk();
				sk = KpabeImpl.byte2SK(bSk);//得到当前用户私钥
				if(Kpabe.decrypt(ctPassword, sk, pk) == null) {
					df.setNormal(false);
					out.writeObject(df);
					df.setErrorMsg("不能解密该文件，无法下载！");
				}else {//解密下载
					df.setNormal(true);
					out.writeObject(df);
					socket = new Socket(Config.ServerIP, port);//连接服务端任务端口
					in = new ObjectInputStream(socket.getInputStream());
					dis = new DataInputStream(in);

					df = (DownloadFile) in.readObject();
					double length = df.getLength();
					System.out.println("将要下载的密文大小："+length);
					
					System.out.println("下载目的地址"+dst);
					//fos = new FileOutputStream(dst+"\\"+filename);
					//File tempFile = File.createTempFile("tmp", null);
					String tempFilePath = "F:\\Workspaces2016\\frsC\\temp";
					File file = new File(tempFilePath+"\\"+filename);
					fos = new FileOutputStream(file);
					byte[] bytes = new byte[8176];
				//	while(!isStop()){
						/*if(db.getSecrit() != null){//若是密文下载，解析出加密密钥
							password = new String(AESUtil.decrypt(AESUtil.decode(db.getSecrit()), secrit));
						}*/
						int i;
						while((i = dis.read(bytes, 0, (int)Math.min(length, 8176))) > 0){//开始接收
							fos.write(bytes, 0, i);
							fos.flush();
							length = length - i;
						}
						
						//success = true;
					//}
					//df = (DownloadFile) in.readObject();//交换状态
					/*if(!isStop()){
						throw new IOException("下载出错");
					}*/
					dis.close();
					fos.close();
					
					//解密
					dpassword = Kpabe.decrypt(ctPassword, sk, pk); //解密后的密钥
					byte[] passwordString = dpassword.toBytes();
					byte[] content = FileUtil.fileToByte(tempFilePath+"\\"+filename);
					System.out.println("解密前长度:"+content.length);
					byte[] downloadContent = AESUtil.decrypt(content, passwordString);
					System.out.println("解密后长度:"+downloadContent.length);
					double length1 = downloadContent.length;
					
					ByteArrayInputStream bais=new ByteArrayInputStream(downloadContent); //把刚才的部分视为输入流
					fos = new FileOutputStream(dst+"\\"+filename);
					
					byte[] temp = new byte[8176];
				
					while((i = bais.read(temp, 0, (int)Math.min(length1, 8176))) > 0){//开始接收
						fos.write(temp, 0, i);
						fos.flush();
						length1 = length1 - i;
					}
					bais.close();
					fos.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			df.setNormal(false);
			df.setErrorMsg("下载过程中出错");
		}finally{
			rc.run(this, df);
			IOUtil.close(socket, in, out);
		}
	}
	
	/**
	 * 检测地址并初始化
	 * @param name
	 * @param size
	 * @return
	 * @throws IOException
	 */
	/*public synchronized String checkInit(String name, double size) throws IOException{
		if(!list.containsKey(name)){
			String path = FileUtil.checkFile(dst+"\\"+name);
			RandomAccessFile out = new RandomAccessFile(path, "rw");
			out.setLength((long) size);
			IOUtil.close(out);
			list.put(name, path);
			if(df.isFile()){
				rc.updateFileName(this, path.substring(path.lastIndexOf("\\")+1));
			}
		}
		return list.get(name);
	}*/
}
