package com.kp.util.file;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
}
