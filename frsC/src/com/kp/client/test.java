package com.kp.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.kp.util.file.FileUtil;
import com.kp.util.kpabe.CT;
import com.kp.util.kpabe.Kpabe;
import com.kp.util.kpabe.MK;
import com.kp.util.kpabe.PK;
import com.kp.util.kpabe.SK;
import com.kp.util.kpabe.impl.KpabeImpl;
import com.kp.util.secret.AESUtil;

import it.unisa.dia.gas.jpbc.Element;

public class test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Element m;
		int[] gama={1,2,3,4,5,6,7,8};
		byte[] pwdString; //加密
		byte[] dpwdString; //加密
		PK pk = new PK();
		MK mk = new MK();
		String policy = "1 2 2of2 3 4 2of2 2of2";
		SK sk = new SK();
		CT cm = new CT();
		Kpabe.setup(pk, mk);
		m = pk.pairing.getGT().newRandomElement(); //生成密钥
		cm = Kpabe.encrypt(m, gama, pk);
		sk = Kpabe.keyGen(policy, mk, pk);
		//pwdString = KpabeImpl.byte2Hex(m.toBytes());//加密
		pwdString = m.toBytes();//加密
		byte[] content = FileUtil.fileToByte("F:\\test.txt");
		System.out.println("加密前长度:"+content.length);
		byte[] enfile = AESUtil.encrypt(content, pwdString);
		int length = enfile.length;
		System.out.println("加密后长度:"+enfile.length);

		ByteArrayInputStream bais=new ByteArrayInputStream(enfile); 
		FileOutputStream fos = new FileOutputStream("F:\\ct.txt");
		int i = 0;
		byte[] temp = new byte[8176];
		while((i = bais.read(temp, 0, (int)Math.min(length, 8176))) != -1){//开始接收
			fos.write(temp, 0, i);
			fos.flush();
			length = length - i;
		}
		bais.close();
		fos.close();
		
		
		byte[] ct = FileUtil.fileToByte("F:\\ct.txt");
		
		
		
		//dpwdString = KpabeImpl.byte2Hex(Kpabe.decrypt(cm, sk, pk).toBytes());
		dpwdString = Kpabe.decrypt(cm, sk, pk).toBytes();
		byte[] defile = AESUtil.decrypt(ct, dpwdString);
		int length1 = defile.length;
		ByteArrayInputStream bais1=new ByteArrayInputStream(defile); //把刚才的部分视为输入流
		FileOutputStream fos1 = new FileOutputStream("F:\\test1.txt");
	
		while((i = bais1.read(temp, 0, (int)Math.min(length1, 8176))) != -1){//开始接收
			fos1.write(temp, 0, i);
			fos1.flush();
			length1 = length1 - i;
		}
		bais1.close();
		fos1.close();
		
	}

}
