package com.kp.util.secret;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加密解密
 * @author ping
 *
 */
public class AESUtil {
	
	public static long addLength = 131072;//源数据长度64M减去此长度加密后为64M
	
	/**
	 * 初始化密码
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static SecretKeySpec initKey(byte[] password) throws NoSuchAlgorithmException{
		SecureRandom random = null;
		random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password);
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, random);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		return key;
	}
	
	/**AES加密
	 * @throws IOException */
	public static byte[] encrypt(byte[] content, byte[] password) throws IOException{
		try {
			SecretKeySpec key = initKey(password);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}catch (InvalidKeyException e) {
			e.printStackTrace();
		}catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		throw new IOException("AES加密出错");
	}
	
	/**AES解密
	 * @throws IOException */
	public static byte[] decrypt(byte[] content, byte[] password) throws IOException {  
        try {  
        	SecretKeySpec key = initKey(password);  
           // Cipher cipher = Cipher.getInstance("AES");// 创建密码器   DES/ECB/NoPadding
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器   
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
            byte[] result = cipher.doFinal(content);
            return result; // 加密   
        } catch (NoSuchAlgorithmException e) {  
                e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
                e.printStackTrace();  
        } catch (InvalidKeyException e) {  
                e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
                e.printStackTrace();  
        } catch (BadPaddingException e) {  
                e.printStackTrace();  
        }  
        throw new IOException("AES解密出错");
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
