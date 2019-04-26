package com.kp.entity.trans;

import java.io.Serializable;

/**
 * 提交服务端申请
 * @author Mr Wang
 *
 */
public class Apply implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//任务类型
	public static int LOGIN = 1;//登录
	public static int REGISTER = 2;//注册
	public static int VALIDATEUSER = 3;//验证账号
	public static int PRESENTFOLDER = 4;//获取当前文件夹内容
	public static int UPLOADFILE = 5;//上传文件
	public static int DOWNLOADFILE = 6;//下载文件
	public static int DELETEFILE = 7;//删除文件
	public static int CREATEFOLDER = 8;//创建文件夹
	public static int RENAMEFILE = 9;//重命名文件或文件夹
	public static int MOVEFILE = 10;//移动文件或文件夹
	public static int ALLFILE = 11;//获取所有文件
	public static int FRIEND = 12;//获取好友列表
	public static int SHARE = 13;//获取相互之间的分享
	public static int SEARCHUSER = 14;//搜索用户
	public static int ADDFRIEND = 15;//添加好友
	public static int MODIFYREMARK = 16;//修改备注
	public static int DELETEFRIEND = 17;//删除好友
	public static int SHAREFILE = 18;//分享文件
	public static int DELETESHARE = 19;//删除分享文件
	public static int SAVESHARE = 20;//保存分享文件
	public static int SEARCHFILE = 21;//搜索文件
	public static int MODIFYUSER = 22;//修改用户信息
	
	//本次任务类型
	private int type;//命令类型
	private boolean normal;//任务状态
	private String errorMsg;//错误原因
	
	public Apply(int type){
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}
	
	public void setNormal(boolean normal) {
		this.normal = normal;
	}
	
	public boolean isNormal() {
		return normal;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
}
