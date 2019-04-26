package com.kp.server.dao;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.entity.db.myFile;
import com.kp.entity.db.setUp;

public class setUpDao extends BaseDao{
	public int insert(setUp setup) {
		String sql = "insert setup (pk, mk) values(?,?)";
		return this.executeUpdateSetup(sql, setup.getPk(), setup.getMk());
	}
	
	
	/**
	 * 获取信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public setUp getSetUp() {
		String sql = "select * from setup";
		
		RSProcessor setupProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				setUp setup = null;
				
				while(rs != null && rs.next()) {
					int id = rs.getInt("id");
					Blob pk_blob = rs.getBlob("pk");
					byte[] pk = pk_blob.getBytes((long) 1,(int) pk_blob.length());
					
					Blob mk_blob = rs.getBlob("mk");
					byte[] mk = mk_blob.getBytes((long) 1,(int) mk_blob.length());
					
					setup = new setUp(id, pk, mk);
				}
				return setup;
			}
			
		};
		Object result = executeQuery(setupProcessor, sql, null);
		
		return (setUp)result;
	}
}
