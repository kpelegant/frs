package com.kp.entity.trans;

import java.util.List;

import com.kp.entity.db.fileUser;

/**
 * 获取所有文件夹命令
 * @author Mr Wang
 *
 */
public class AllFile extends Apply {
	
	private static final long serialVersionUID = 1L;
	private List<fileUser> fileuserList;
	
	
	public AllFile(){
		super(ALLFILE);
	}


	public List<fileUser> getFileuserList() {
		return fileuserList;
	}


	public void setFileuserList(List<fileUser> fileuserList) {
		this.fileuserList = fileuserList;
	}
	
	
}
