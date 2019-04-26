package com.kp.util.file;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;

//import org.apache.hadoop.hbase.client.HTable;

import com.kp.entity.trans.Apply;
import com.kp.server.controller.task.CSTransmissionBasic;




/**
 * 输入流和输出流工具
 * @author ping
 *
 */
public class IOUtil {
	
	/**关闭数据流*/
	public static void close(Closeable...c){
		for(int i=0; i<c.length; i++){
			if(c[i] != null){
				try {
					c[i].close();
					c[i] = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 关闭所有SQL链接
	 * @param objs
	 */
	public static void close(AutoCloseable...objs){
		for(AutoCloseable obj : objs){
			if( obj != null){
				try{
					obj.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				obj = null;
			}
		}
	}
	
	/**清空数据ObjectOutputStream*/
	public static void clear(ObjectOutputStream out){
		try{
			if(out == null) return;
			//清空数据
			out.flush();
			out.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void reTaskFailure(CSTransmissionBasic basic, String msg){
		try {
			Apply apply = basic.getApply();
			apply.setNormal(false);
			apply.setErrorMsg(msg);
			basic.getOut().writeObject(apply);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(basic.getOut(), basic.getIn(), basic.getSocket());
		}
	}
	
	/**
	 * 关闭HTable
	 * @param table
	 */
	/*public static void close(HTable table){
		try {
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
