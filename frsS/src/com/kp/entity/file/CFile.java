package com.kp.entity.file;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * 文件模型
 * @author Mr Wang
 *
 */
public class CFile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//父节点
	private CFile parent;
	
	//本节点属性
	private HashMap<String, CFile> folder;
	private String name;//当前文件或文件夹的名字
	private String[] hname;//当是文件时，文件对应的hash值文件名字
	private Double size;//如果是文件，该文件的容量
	private String password;//文件加密密码
	private Date createDate;//创建时间
	private Date modifyDate;//修改时间
	
	/**
	 * 创建文件夹
	 * @param name
	 */
	public CFile(String name){
		this.name = name;
		this.folder = new HashMap<String, CFile>();
		this.createDate = new Date();
		this.modifyDate = this.createDate;
	}
	
	/**
	 * 创建文件，密码password为空时即不加密
	 * @param name
	 * @param hname
	 * @param size
	 * @param password
	 */
	public CFile(String name, String[] hname, Double size, String password){
		this.folder = null;
		this.name = name;
		this.hname = hname;
		this.size = size;
		this.password = password;
		this.createDate = new Date();
		this.modifyDate = this.createDate;
	}
	
	public CFile(String name, List<String> hname, Double size, String password){
		this.folder = null;
		this.name = name;
		this.hname = new String[hname.size()];
		for(int i=0; i<hname.size(); i++){
			this.hname[i] = hname.get(i);
		}
		this.size = size;
		this.password = password;
		this.createDate = new Date();
		this.modifyDate = this.createDate;
	}
	
	/**
	 * 创建正在上传文件
	 * @param name
	 * @param size
	 */
	public CFile(String name, String password, Double size){
		this.folder = null;
		this.name = name;
		this.hname = null;
		this.size = size;
		this.password = password;
		this.createDate = new Date();
		this.modifyDate = this.createDate;
	}
	
	/**
	 * 添加上传文件的其他信息
	 * @param hname
	 * @param password
	 */
	public void setOther(List<String> hname){
		this.hname = new String[hname.size()];
		for(int i=0; i<hname.size(); i++){
			this.hname[i] = hname.get(i);
		}
	}
	
	/**
	 * 判断文件是否存在
	 * @param name
	 * @return
	 */
	public boolean exist(String name){
		return folder.get(name) != null;
	}
	
	/**
	 * 获取文件数量
	 * @return
	 */
	public int getBlockCount(){
		if(isFile()){
			return hname.length;
		}else{
			int num = 0;
			for(CFile f : folder.values()){
				num += f.getBlockCount();
			}
			return num;
		}
	}
	
	/**
	 * 获取输入路径的文件
	 * @param path
	 * @return
	 */
	public CFile getCFile(String path) {
		if(path.length() > 0){
			path = path.substring(1);
			int nextObliqueLine = path.indexOf("\\");
			String name = nextObliqueLine>0 ? path.substring(0, nextObliqueLine) : path;//此层文件夹的名字
			CFile file = folder.get(name);
			if(file != null && isDirectory()){
				return file.getCFile(nextObliqueLine>0 ? path.substring(nextObliqueLine) : "");
			}
			return null;
		}else {
			return this;
		}
	}
	
	/**
	 * 将file添加到path路径的文件夹中
	 * @param path 存储文件夹路径
	 * @param cfile 待存储的文件或文件夹
	 * @throws FileNotFoundException 
	 */
	public void addCFile(String path, CFile cfile) throws FileNotFoundException{
		if(path.length() > 0){
			path = path.substring(1);
			int nextObliqueLine = path.indexOf("\\");
			String name = nextObliqueLine>0 ? path.substring(0, nextObliqueLine) : path;//此层文件夹的名字
			CFile file = folder.get(name);
			if(file != null && file.isDirectory()){
				file.addCFile(nextObliqueLine>0 ? path.substring(nextObliqueLine) : "", cfile);
			}else{
				throw new FileNotFoundException("文件夹不存在");
			}
		}else {
			cfile.setParent(this);
			cfile.modifyDate = new Date();
			folder.put(cfile.getName(), cfile);
		}
	}
	
	/**
	 * 在本文件夹中创建一个文件夹
	 * @param name
	 */
	public void addCFile(String name){
		CFile file = new CFile(name);
		file.setParent(this);
		folder.put(file.getName(), file);
	}
	
	/**
	 * 在本文件夹中创建一个文件
	 * @param name
	 */
	public void addCFile(String name, String[] hname, Double size, String password){
		CFile file = new CFile(name, hname, size, password);
		file.setParent(this);
		folder.put(file.getName(), file);
	}
	
	/**
	 * 获取当前文件夹下所有文件和文件夹
	 * @return
	 */
	public CFile[] listCFilesByArray(){
		CFile[] files = new CFile[folder.size()];
		int nextCFileNum = 0;
		Iterator<Entry<String, CFile>> iter = folder.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, CFile> entry =  iter.next();
			files[nextCFileNum++] = entry.getValue();
		}
		return files;
	}
	
	/**
	 * 获取当前文件夹下所有文件和文件夹
	 * @return
	 */
	public List<CFile> listCFilesByList(){
		CFile[] files = listCFilesByArray();
		List<CFile> list = new ArrayList<CFile>();
		for(CFile f : files){
			list.add(f);
		}
		return list;
	}

	/**
	 * 获取当前文件夹下所有文件(递归）
	 * @return
	 */
	public List<CFile> getAllCFile(){
		List<CFile> files = new ArrayList<CFile>();
		if(isFile()){
			files.add(this);
		}else{
			Iterator<Entry<String, CFile>> iter = folder.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, CFile> entry =  iter.next();
				CFile file = entry.getValue();
				if(file.isDirectory()){
					files.addAll(file.getAllCFile());
				}else{
					files.add(file);
				}
			}
		}
		return files;
	}

	/**
	 * 获取当前文件夹下所有的名字是name的文件和文件夹
	 * @param name
	 * @return
	 */
	public List<CFile> getAllCFileByName(String name){
		List<CFile> files = new ArrayList<CFile>();
		Iterator<Entry<String, CFile>> iter = folder.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, CFile> entry =  iter.next();
			CFile file = entry.getValue();
			if(file.getName().contains(name)){
				files.add(file);
			}
			if(file.isDirectory()){
				files.addAll(file.getAllCFileByName(name));
			}
		}
		return files;
	}

	/**
	 * 获取当前CFile的路径
	 * @return
	 */
	public String getAbsolutePath(){
		if(parent == null) return "";
		return parent.getAbsolutePath()+"\\"+name;
	}

	/**
	 * 删除此文件或文件夹
	 */
	public void delete(){
		parent.delete(name);
	}

	/**
	 * 删除此文件夹中的文件或文件夹
	 * @param name
	 */
	private void delete(String name){
		folder.remove(name);
	}
	
	/**
	 * 判断是否是文件
	 * @return 当时是文件时返回true，否则为false
	 */
	public boolean isFile(){
		if(folder == null) return true;
		return false;
	}

	/**
	 * 判断是否是文件夹
	 * @return 当时是文件夹时返回true，否则为false
	 */
	public boolean isDirectory(){
		return !isFile();
	}
	
	/**
	 * 判断是否正在上传
	 * @return
	 */
	public boolean isUploading(){
		return hname == null && isFile();
	}
	
	/**
	 * 数据传输到客户端格式化
	 */
	public void clear(){
		this.parent = null;
		if(this.folder != null){
			this.folder.clear();
		}
		if(this.hname != null){
			this.hname = new String[]{};
		}
		if(this.password != null){
			this.password = "";
		}
	}
	
	/**
	 * 清空文件
	 */
	public void clearFile() {
		Iterator<Entry<String, CFile>> iter = folder.entrySet().iterator();
		List<String> names = new ArrayList<String>();
		while (iter.hasNext()) {
			Entry<String, CFile> entry =  iter.next();
			CFile file = entry.getValue();
			if(file.isDirectory()){
				file.clearFile();
			}else{
				names.add(entry.getKey());
			}
		}
		for(String name : names){
			folder.remove(name);
		}
	}
	
	public void setParent(CFile parent) {
		this.parent = parent;
	}

	public CFile getParent() {
		return parent;
	}

	public HashMap<String, CFile> getFolder() {
		return folder;
	}
	
	public void setName(String name) {
		this.name = name;
		this.modifyDate = new Date();
	}

	public String getName() {
		return name;
	}

	public String[] getHName() {
		return hname;
	}

	public Double getSize() {
		if(isFile()) return size;
		Double size = 0.0;
		List<CFile> fs = getAllCFile();
		for(CFile f : fs){
			size += f.getSize();
		}
		return size;
	}

	public String getPassword() {
		return password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}
}
