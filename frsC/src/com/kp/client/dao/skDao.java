package com.kp.client.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.kp.entity.db.mySK;

public class skDao extends BaseDao{
	/**
	 * 添加私钥
	 */
	public int insert(mySK mysk) {
		String sql = "insert sk (userid, sk) values(?,?)";
		return this.executeUpdate(sql, mysk.getUserid(), mysk.getSk());
	}
	
	/**
	 * 根据userid查找sk
	 */
	public mySK findSKByUserId(int userid) {
		String sql = "select * from sk where userid = ? ";
		Object[] params = {userid};

		RSProcessor getSKByUserIdProcessor = new RSProcessor(){

			public Object process(ResultSet rs) throws SQLException {
				mySK mysk = null;
				
				if(rs != null) {
					if(rs.next()) {
						int id = rs.getInt("id");
						int userid = rs.getInt("userid");
						Blob sk_blob = rs.getBlob("sk");
						byte[] sk = sk_blob.getBytes((long) 1,(int) sk_blob.length());
						mysk = new mySK(id, userid, sk);
					}
				}
				return mysk;
			}
		};
		return (mySK)this.executeQuery(getSKByUserIdProcessor, sql, params);
	}
}
