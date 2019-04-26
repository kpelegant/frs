package com.kp.util.kpabe.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.kp.util.kpabe.CT;
import com.kp.util.kpabe.MK;
import com.kp.util.kpabe.PK;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class KpabeImpl {
	/*
	 * 序列化CT
	 */
	public static byte[] CT2byte(CT ct) throws IOException {
		byte[] ret = null;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(baos);
	    out.writeObject(ct);  //
	    ret = baos.toByteArray();
	    out.close();
	    baos.close();
	    return ret;
	}
	/*
	 * 反序列化
	 */
	public static CT byte2CT(byte[] bytes) throws IOException, ClassNotFoundException {
			CT ret = null;
		    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		    ObjectInputStream in = new ObjectInputStream(bais);
		    ret = (CT)in.readObject();
		    bais.close();
		    in.close();
		    return ret;
	}
	
	/*
	 * 序列化PK
	 */
	public static byte[] PK2byte(PK pk) throws IOException {
		byte[] ret = null;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(baos);
	    out.writeObject(pk);  //
	    ret = baos.toByteArray();
	    out.close();
	    baos.close();
	    return ret;
	}
	/*
	 * 反序列化
	 */
	public static PK byte2PK(byte[] bytes) throws IOException, ClassNotFoundException {
			PK ret = null;
		    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		    ObjectInputStream in = new ObjectInputStream(bais);
	
		    ret = (PK)in.readObject();
		    bais.close();
		    in.close();
		    return ret;
	}
	
	/*
	 * 序列化MK
	 */
	public static byte[] MK2byte(MK mk) throws IOException {
		byte[] ret = null;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(baos);
	    out.writeObject(mk);  //
	    ret = baos.toByteArray();
	    out.close();
	    baos.close();
	    return ret;
	}
	/*
	 * 反序列化
	 */
	public static MK byte2MK(byte[] bytes) throws IOException, ClassNotFoundException {
			MK ret = null;
		    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		    ObjectInputStream in = new ObjectInputStream(bais);
		    ret = (MK)in.readObject();
		    bais.close();
		    in.close();
		    return ret;
	}
	
	public static String encode(byte[] bytes){
		BASE64Encoder base64encoder = new BASE64Encoder(); 
		return base64encoder.encode(bytes);
	}
	
	public static byte[] decode(String str) throws IOException{
		BASE64Decoder base64decoder = new BASE64Decoder(); 
		return base64decoder.decodeBuffer(str); 
	}
	
	/**
	 * 将byte转换成字符串
	 * @param buf 字节数据
	 * @return 字符串
	 */
	public static String byte2Hex(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
	}
	
	public static byte[] hex2Byte(String b) {  
		if ((b.length() % 2) != 0)  
			throw new IllegalArgumentException("长度不是偶数");  
		byte[] b2 = new byte[b.length() / 2];  
		for (int n = 0; n < b.length(); n += 2) {  
			String item = b.substring(n, n+2);  
			b2[n / 2] = (byte) Integer.parseInt(item, 16);  
		}  
		return b2;  
	}
}
