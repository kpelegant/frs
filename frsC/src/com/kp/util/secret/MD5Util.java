package com.kp.util.secret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kp.util.file.FileUtil;



/**
 * 数据安全
 * @author Mr Wang
 *
 */
public class MD5Util {
	
	/**
	 * 计算DATA的哈希
	 */
	public static String getHash(String data){
		return getHash(data.getBytes());
	}

	/**
	 * 计算hash值
	 */
	public static String getHash(byte[] b){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(b); 
			byte[] newByte = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < newByte.length; j++){ 
				if ((newByte[j] & 0xff) < 0x10) {
					sb.append("0");  
				}   
				sb.append(Long.toString(newByte[j] & 0xff, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取随机密码
	 * @return
	 */
	public static String getRandomPassword(){
		String source = FileUtil.getRandomString()+FileUtil.getRandomString();
		return getHash(source);
	}
}
