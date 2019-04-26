package com.kp.util.file;

/**
 * 重载Bytes，解决值为空出错
 * @author Mr Wang
 *
 */
//public class Bytes extends org.apache.hadoop.hbase.util.Bytes {
public class Bytes{
	public static byte[] toBytes(String s){
		if(s == null){
			return null;
		}
		return Bytes.toBytes(s);
	}
	
	public static byte[] toBytes(short s){
		return toBytes(s+"");
	}
	
	public static byte[] toBytes(int i){
		return toBytes(i+"");
	}
	
	public static byte[] toBytes(long l){
		return toBytes(l+"");
	}
	
	public static byte[] toBytes(float f){
		return toBytes(f+"");
	}
	
	public static byte[] toBytes(double d){
		return toBytes(d+"");
	}
	
	public static byte[] toBytes(boolean b){
		return toBytes(b+"");
	}
}
