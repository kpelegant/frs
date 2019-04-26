package com.kp.entity.trans;

import java.io.Serializable;

/**
 * 传输的数据段
 * @author ping
 *
 */
public class DataBlock implements Serializable {

	private static final long serialVersionUID = -4391636666087712346L;
	
	//数据类型
	private static int HEAD = 1;//文件头
	private static int DATA = 2;//文件数据
	private static int END = 3;//任务结束

	//头数据
	private String name;//文件名或路径
	private int sort;//文件顺序或文件总长度
	private int threadsort;//线程顺序
	private Double start;//开始位置
	private Double length;//块长度
	private String secrit;//密码
	
	//传输的数据
	private byte[] data;//存储数据
	private int type;//结束
	
	/**
	 * 数据头
	 */
	public DataBlock(String name, int sort, double start, double length, String secrit){
		this.name = name;
		this.sort = sort;
		this.start = start;
		this.data = null;
		this.length = length;
		this.secrit = secrit;
		this.type = HEAD;
	}

	public DataBlock(String name, int sort, int threadsort, double start, double length, String secrit){
		this.name = name;
		this.sort = sort;
		this.threadsort = threadsort;
		this.start = start;
		this.data = null;
		this.length = length;
		this.secrit = secrit;
		this.type = HEAD;
	}
	/**
	 * 数据末端
	 */
	public DataBlock(){
		this.name = null;
		this.sort = -1;
		this.start = null;
		this.data = null;
		this.type = END;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSort() {
		return sort;
	}

	public byte[] getData() {
		return data;
	}
	
	public boolean isEnd() {
		return type == END;
	}
	
	public boolean isHead(){
		return type == HEAD;
	}
	
	public boolean isData(){
		return type == DATA;
	}
	
	public double getLength(){
		return length;
	}
	
	public double getStart() {
		return start;
	}
	
	public String getSecrit() {
		return secrit;
	}
	public int getThreadsort() {
		return threadsort;
	}
	
}
