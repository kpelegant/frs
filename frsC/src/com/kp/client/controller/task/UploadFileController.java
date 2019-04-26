package com.kp.client.controller.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import org.eclipse.ui.internal.keys.MacKeyFormatter;

import com.kp.config.Config;
import com.kp.entity.file.BlockMes;
import com.kp.entity.trans.DataBlock;
import com.kp.entity.trans.UploadFile;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;
import com.kp.util.kpabe.Kpabe;
import com.kp.util.kpabe.MK;
import com.kp.util.kpabe.PK;
import com.kp.util.kpabe.impl.KpabeImpl;
import com.kp.util.secret.AESUtil;
import com.kp.util.secret.MD5Util;
import com.kp.util.view.ViewUtil;

import it.unisa.dia.gas.jpbc.Element;


/**
 * 上传控制器
 * @author Mr Wang
 *
 */
public class UploadFileController extends UDCSTransmissionBasic {
	
	private UploadFile uf;
	private String secret;//是否加密
	private int nextTrans = 0;//下一个数据块
	private PK pk = new PK();
	int[] gama={1,2,3,4,5,6,7,8};
	Element m;
	private double spasswordsize;//加密后的密钥长度,长度8字节
	/*private byte[] head;
	
	private RandomAccessFile rafin;
	private double length;*/
	
	private String username;
	private String filename;
	private int port;
	byte[] password;
	byte[] spassword;
	
	public UploadFileController(ReUDController rc, String src, String username, String filename, String secret){
		super(rc, src, "");
		this.secret = secret;  //传过来用户密码，判断是否加密
		this.totalLength = new File(src).length();
		this.username = username;
		this.filename = filename;
	}
	
	public void run(){
		try {
			uf = new UploadFile();//上传文件命令
			super.connectServer(uf);
			uf = (UploadFile) in.readObject();
			
			pk = uf.getPk();
			System.out.println("公钥g:"+pk.g);
			m = pk.pairing.getGT().newRandomElement();
			System.out.println("m值："+m);
			password = m.toBytes();//应用KP-ABE选取明文用于文件加密
			//将对称密钥password加密了（应用KP-ABE）
			spassword = KpabeImpl.CT2byte(Kpabe.encrypt(m, gama, pk));

			System.out.println("m加密后的值："+Kpabe.encrypt(m, gama, pk).e);
			//spasswordsize = spassword.getBytes().length;  //加密文件的密钥经过KP-ABE加密后的长度
			//head = FileUtil.double2Bytes(spasswordsize);  //密钥长度
			//System.out.println("文件头长度（密钥大小）"+head.length);

			System.out.println("文件名"+src);
			//FileUtil.insert(src, 0, head);  //插入8字节密钥长度
			//FileUtil.insert(src, 8, spassword.getBytes());  //插入加密后的密钥
			uf = new UploadFile(username, filename, totalLength, secret!=null?spassword:null);
			//out.writeObject(uf);//将加密文件的对称密钥加密后,文件长度传给服务器
			//uf = (UploadFile) in.readObject();//接受服务端状态
			out.writeObject(uf);
			uf = (UploadFile) in.readObject();
			port = uf.getPort();
			if(uf.isNormal()){//开始上传
				try {
					byte[] content = FileUtil.fileToByte(src);
					System.out.println("加密前长度:"+content.length);
					byte[] enfile = AESUtil.encrypt(content, password);  //加密文件
					double length = enfile.length;
					uf.setLength(length);
					uf.setNormal(true);
					out.writeObject(uf);
					
					System.out.println("加密后长度:"+enfile.length);
					System.out.println("c开始上传");
					
					socket = new Socket(Config.ServerIP, port);//连接服务端任务端口
					out = new ObjectOutputStream(socket.getOutputStream());
					
					ByteArrayInputStream bais=new ByteArrayInputStream(enfile); //把刚才的部分视为输入流
					DataOutputStream dos=new DataOutputStream(out);
					/* // 文件名和长度  
					 */
	                // 开始传输文件  
	                byte[] bytes = new byte[8176];  
	                int i = 0;   
	                while((i = bais.read(bytes, 0, (int)Math.min(length, 8176))) != -1) {  
	                    dos.write(bytes, 0, i);  
	                    dos.flush();  
	                    length = length - i;
	                }  
	                bais.close();
	                dos.close();
					if(!uf.isNormal()){
						throw new Exception();
					}
				} catch (Exception e) {
					e.printStackTrace();
					uf.setNormal(false);
					uf.setErrorMsg("上传过程中出现错误");
				}
				//out.writeObject(uf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			rc.run(this, uf);
			IOUtil.close(socket, in, out);
		}
	}
	
	/**
	 * 申请任务块
	 * @return
	 */
	public synchronized BlockMes getBlockMes(){
		totalLength = totalLength+8+spasswordsize;
		if(nextTrans*uf.getBlock() < totalLength){
			double[] se = new double[2];
			se[0] = nextTrans*uf.getBlock();
			se[1] = (nextTrans+1)*uf.getBlock() > totalLength ? totalLength : (nextTrans+1)*uf.getBlock();
			BlockMes bm = new BlockMes(src, nextTrans, se);
			nextTrans++;
			return bm;
		}
		return null;
	}
}
