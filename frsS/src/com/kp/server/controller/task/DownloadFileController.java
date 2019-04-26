package com.kp.server.controller.task;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.kp.config.Config;
import com.kp.entity.db.myFile;
import com.kp.entity.file.BlockMes;
import com.kp.entity.file.CFile;
import com.kp.entity.trans.DataBlock;
import com.kp.entity.trans.DownloadFile;
import com.kp.server.controller.FileController;
import com.kp.server.controller.task.execute.DownloadFileExecute;
import com.kp.server.dao.myFileDao;
import com.kp.util.file.FileUtil;
import com.kp.util.file.IOUtil;
import com.kp.util.secret.AESUtil;
import com.kp.util.view.ViewUtil;

/**
 * 下载文件控制器
 * @author Mr Wang
 *
 */
public class DownloadFileController extends UDCSTransmissionBasic{
	
	private FileController fc;
	private DownloadFile df;
	private List<myFile> list;
	
	private int userid;
	private String src;//文件原地址
	private String filename;
	private String storename;
	
	private DataOutputStream dos;
	private FileInputStream fis;
	private int port;
	private byte[] spassword;

	public DownloadFileController(CSTransmissionBasic basic,
			FileController fc, int port) {
		super(basic);
		this.fc = fc;
		this.df = (DownloadFile) apply;;
		this.port = port;
	}

	public void run(){
		try {
			setStart();
			try {
				filename = df.getFilename();
				System.out.println("端口"+port);
				System.out.println("接收到即将下载的文件名"+filename);
				userid = df.getUserid();
				myFileDao fileDao = new myFileDao();
				myFile f = fileDao.findFileByFileName(filename);
				if(f == null){//文件无法下载
					df.setNormal(false);
					df.setErrorMsg("文件不存在");
				}else{//可正常下载
					storename = f.getStoreName();
					spassword = f.getSpassword();
					df.setFile(true);
					df.setPort(port);
					df.setSpassword(spassword);
					df.setPk(Config.pk);
					df.setSize(f.getSize());//返回端口，报告状态
					df.setNormal(true);
					out.writeObject(df);
					df = (DownloadFile) in.readObject();
					if(!df.isNormal()) {
						df.setNormal(false);
						df.setErrorMsg("用户不能解密，取消下载！");
					}else {
						serverSocket = new ServerSocket(port);
						Socket socket = serverSocket.accept();
						
						out = new ObjectOutputStream(socket.getOutputStream());
						dos = new DataOutputStream(out);
						byte[] bytes = new byte[8176];
					//	while(!isStop()){
							File file =new File(Config.defaultPath+"\\"+storename);
							fis = new FileInputStream(file);
							double length = file.length();
							df.setLength(length);
							out.writeObject(df);
							System.out.println("要下载的文件字节数："+length);
							
							int i;
			                while((i = fis.read(bytes, 0, (int)Math.min(length, 8176))) != -1) {  
			                    dos.write(bytes, 0, i);  
			                    dos.flush(); 
			                    length = length - i;
			                } 
			                fis.close();
			                dos.close();
			                System.out.println("发送完成");
							//IOUtil.close(in);
					//	}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				df.setNormal(false);
				df.setErrorMsg("下载文件异常");
			}
			//out.writeObject(df);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fc.realsePort(df.getPort());
			IOUtil.close(socket, in, out);
			System.gc();
			setStop();
		}
	}
	
	/**
	 * 申请数据块
	 * @return
	 * @throws IOException 
	 */
	/*public synchronized BlockMes getBlockMes() throws IOException{
		if(nextCFile < list.size()){
			//可识别文件为空情况
			double[] se = new double[2];
			se[0] = nextTrans*(list.get(nextCFile).getPassword()!=null?Config.block-AESUtil.addLength:Config.block);
			if(nextTrans < list.get(nextCFile).getHName().length-1){
				se[1] = (nextTrans+1)*(list.get(nextCFile).getPassword()!=null?Config.block-AESUtil.addLength:Config.block);
			}else{
				se[1] = list.get(nextCFile).getSize();
			}
			BlockMes bm = new BlockMes(list.get(nextCFile).getHName().length > 0 ? Config.defaultPath+"/"+list.get(nextCFile).getHName()[nextTrans] : null,
					df.isShare()?"\\"+list.get(nextCFile).getName():list.get(nextCFile).getAbsolutePath().substring(df.getL().substring(0, df.getL().lastIndexOf("\\")).length()),
					list.get(nextCFile).getPassword()==null?null:AESUtil.encode(AESUtil.encrypt(list.get(nextCFile).getPassword().getBytes(), df.getCode())),
					list.get(nextCFile).getSize(), se);
			nextTrans++;
			if(nextTrans >= list.get(nextCFile).getHName().length){
				nextCFile++;
				nextTrans = 0;
			}
			//return bm;
		}
		return null;
	}*/
}
