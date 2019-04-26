package com.kp.server.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.entity.db.myFile;

public class myFileDao extends BaseDao{
	/**
	 * 添加文件信息
	 */
	public int insert(myFile file) {
		String sql = "insert file (userid, filename, storename, uploadtime, size, spassword) values(?,?,?,?,?,?)";
		Object[] params = {file.getUserId(), file.getFileName(), file.getStoreName(), file.getUploadTime(), file.getSize(), file.getSpassword()};
		return this.executeUpdateFile(sql, params);
	}

	/**
	 * 根据文件名删除记录
	 */
	public int delete(String filename) {
		String sql = "delete from file where filename = ?";
		Object[] params = {filename};
		return this.executeUpdate(sql, params);
	}
	
	/**
	 * 根据文件名查找文件信息
	 */
	public myFile findFileByFileName(String filename) {
		String sql = "select * from file where filename = ? ";
		Object[] params = {filename};

		RSProcessor getFileByFileNameProcessor = new RSProcessor(){

			public Object process(ResultSet rs) throws SQLException {
				myFile file = null;
				
				if(rs != null) {
					if(rs.next()) {
						int id = rs.getInt("id");
						int userId = rs.getInt("userid");
						String fileName = rs.getString("filename");
						String storeName = rs.getString("storename");
						//Date uploadTime = rs.getDate("uploadtime");
						Date uploadTime = rs.getTimestamp("uploadtime");
						Double size = rs.getDouble("size");
						Blob spwd_blob = rs.getBlob("spassword");
						byte[] spassword = spwd_blob.getBytes((long) 1,(int) spwd_blob.length());
						file = new myFile(id, userId, fileName, storeName, uploadTime, size, spassword);
					}
				}
				return file;
			}
		};
		return (myFile)this.executeQuery(getFileByFileNameProcessor, sql, params);
	}
	/**
	 * 根据id查找文件
	 */
	public myFile findFileById(int id) {
		String sql = "select * from file where id = ? ";
		Object[] params = {id};

		RSProcessor getFileByIdProcessor = new RSProcessor(){

			public Object process(ResultSet rs) throws SQLException {
				myFile file = null;
				
				if(rs != null) {
					if(rs.next()) {
						int id = rs.getInt("id");
						int userId = rs.getInt("userid");
						String fileName = rs.getString("filename");
						String storeName = rs.getString("storename");
						//Date uploadTime = rs.getDate("uploadtime");
						Date uploadTime = rs.getTimestamp("uploadtime");
						Double size = rs.getDouble("size");
						Blob spwd_blob = rs.getBlob("spassword");
						byte[] spassword = spwd_blob.getBytes((long) 1,(int) spwd_blob.length());
						file = new myFile(id, userId, fileName, storeName, uploadTime, size, spassword);
					}
				}
				return file;
			}
		};
		return (myFile)this.executeQuery(getFileByIdProcessor, sql, params);
	}
	
	/**
	 * 根据userid查找文件
	 */
	public myFile findFileByUserId(int userId) {
		String sql = "select * from file where userid = ? ";
		Object[] params = {userId};

		RSProcessor getFileByUserIdProcessor = new RSProcessor(){

			public Object process(ResultSet rs) throws SQLException {
				myFile file = null;
				
				if(rs != null) {
					if(rs.next()) {
						int id = rs.getInt("id");
						int userId = rs.getInt("userid");
						String fileName = rs.getString("filename");
						String storeName = rs.getString("storename");
						//Date uploadTime = rs.getDate("uploadtime");
						Date uploadTime = rs.getTimestamp("uploadtime");
						Double size = rs.getDouble("size");
						Blob spwd_blob = rs.getBlob("spassword");
						byte[] spassword = spwd_blob.getBytes((long) 1,(int) spwd_blob.length());
						file = new myFile(id, userId, fileName, storeName, uploadTime, size, spassword);
					}
				}
				return file;
			}
		};
		return (myFile)this.executeQuery(getFileByUserIdProcessor, sql, params);
	}
	
	/**
	 * 获取所有文件信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<myFile> getAllFiles() {
		//final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		//获取所有文件
		String sql = "select * from file";
		
		RSProcessor filesProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				List<myFile> listFiles = new ArrayList<myFile>();
				
				while(rs != null && rs.next()) {
					int id = rs.getInt("id");
					int userId = rs.getInt("userid");
					String fileName = rs.getString("filename");
					String storeName = rs.getString("storename");
					//Date uploadTime = rs.getDate("uploadtime");
					Date uploadTime = rs.getTimestamp("uploadtime");
					Double size = rs.getDouble("size");
					Blob spwd_blob = rs.getBlob("spassword");
					byte[] spassword = spwd_blob.getBytes((long) 1,(int) spwd_blob.length());
					myFile file = new myFile(id, userId, fileName, storeName, uploadTime, size, spassword);
					listFiles.add(file);
				}
				return listFiles;
			}
			
		};
		Object result = executeQuery(filesProcessor, sql, null);
		
		return (List<myFile>)result;
	}
	
	
	
}
