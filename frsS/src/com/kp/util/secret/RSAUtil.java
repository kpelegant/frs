package com.kp.util.secret;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * RSA加密算法
 * @author Mr Wang
 *
 */
public class RSAUtil {

	/**
	 * 生成密钥对
	 * @return [0]公钥，[1]私钥
	 * @throws Exception
	 */
	public static String[] genKeyPair() throws Exception{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		KeyPair kp = kpg.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
		String[] key = new String[2];
		key[0] = (new BASE64Encoder()).encodeBuffer(publicKey.getEncoded());
		key[1] = (new BASE64Encoder()).encodeBuffer(privateKey.getEncoded());
		return key;
	}
	
	/**
	 * 用公钥对数据加密
	 * @param content 数据
	 * @param key 公钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] content, String key) throws Exception{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for(int i=0; i<content.length; i=i+117){
			byte[] tmp = new byte[i+117>content.length?content.length-i:117];
			System.arraycopy(content, i, tmp, 0, tmp.length);
			byte[] t = encrypt2(tmp, key);
			out.write(t);
		}
		return out.toByteArray();
	}
	
	/**
	 * 用私钥对数据解密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] content, String key) throws Exception{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for(int i=0; i<content.length; i=i+128){
			byte[] tmp = new byte[i+128>content.length?content.length-i:128];
			System.arraycopy(content, i, tmp, 0, tmp.length);
			byte[] t = decrypt2(tmp, key);
			out.write(t);
		}
		return out.toByteArray();
	}
	
	private static byte[] encrypt2(byte[] content, String key) throws Exception {
		//对公钥解密
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		//取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		Key publicKey = kf.generatePublic(x509KeySpec);
		//对数据加密
		Cipher cipher = Cipher.getInstance(kf.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(content);
	}
	
	private static byte[] decrypt2(byte[] content, String key) throws Exception{
		//对私钥解密
		byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		//取得私钥
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		Key privateKey = kf.generatePrivate(pkcs8EncodedKeySpec);
		//对数据解密
		Cipher cipher = Cipher.getInstance(kf.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(content);
	}
}























