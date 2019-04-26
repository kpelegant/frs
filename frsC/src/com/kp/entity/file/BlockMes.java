package com.kp.entity.file;
import java.lang.String;
/**
 * 块信息
 * @author Mr Wang
 *
 */
public class BlockMes {
	
	private String path;//文件路径
	private int sort;//顺序标记
	private double[] se;//开始位置和结束位置
	
	public BlockMes(String path, int sort, double[] se) {
		this.path = path;
		this.sort = sort;
		this.se = se;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSort() {
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
