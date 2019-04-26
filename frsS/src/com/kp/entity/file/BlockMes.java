package com.kp.entity.file;

/**
 * 块信息
 * @author Mr Wang
 *
 */
public class BlockMes {

	private String path;//块路径
	private String name;//文件路径
	private String password;//密码
	private double sort;//顺序标记或文件长度
	private double se[];//开始位置和结束位置
	
	public BlockMes(String path, String name, String password, double sort, double[] se) {
		this.path = path;
		this.name = name;
		this.password = password;
		this.sort = sort;
		this.se = se;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public double getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public double[] getSe() {
		return se;
	}

	public void setSe(double[] se) {
		this.se = se;
	}
}
