package com.kp.client.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class BaseDao {
	/**
	 * 获取数据库连接对象。
	 */
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/frsc?useUnicode=true&characterEncoding=utf-8";
	private static String user = "root";
	private static String password = "root";
	
	protected Connection conn;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}

	/**
	 * 关闭数据库连接。
	 * 
	 * @param conn
	 *            数据库连接
	 * @param stmt
	 *            Statement对象
	 * @param rs
	 *            结果集
	 */
	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		// 若结果集对象不为空，则关闭
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 若Statement对象不为空，则关闭
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 若数据库连接对象不为空，则关闭
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void closeAll(Connection conn, Statement stmt) {
		closeAll(conn, stmt, null);
	}

	public void closeAll() {
		closeAll(this.conn, this.pstmt, this.rs);
	}
	/**
	 * 增、删、改操作
	 * 
	 * @param sql
	 *            sql语句
	 * @param params
	 *            参数数组
	 * @return 执行结果
	 */
	public int executeUpdate(String sql, int userid, byte[] sk) {
		int result = 0;
		try {
			conn = this.getConnection();
			if (conn != null && !conn.isClosed()) {
				pstmt = conn.prepareStatement(sql);  //把sql语句中的变量抽出来了
				pstmt.setInt(1, userid);
				
				Blob sk_blob = conn.createBlob();
				sk_blob.setBytes(1, sk);
				pstmt.setBlob(2, sk_blob);
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return result;
	}

	public Object executeQuery(RSProcessor processor, String sql,
			Object... params) {
		Object result = null;
		try {
			conn = this.getConnection();  //建立连接
			if (conn != null && conn.isClosed() == false) {
				pstmt = conn.prepareStatement(sql);
				if(params != null) {
					for (int i = 0; i < params.length; i++) {
						pstmt.setObject(i + 1, params[i]);
					}
				}
				rs = pstmt.executeQuery();
				result = processor.process(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return result;
	}

	
}
