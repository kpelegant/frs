package com.kp.server.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.entity.db.User;

public class userDao extends BaseDao {
	/**
	 * 添加用户
	 */
	public int insert(User user) {
		String sql = "insert user (username, password, policy, state, role, regtime) values(?,?,?,?,?,?)";
		Object[] params = {user.getUsername(), user.getPassword(), user.getPolicy(), user.getState(), user.getRole(), user.getRegTime()};
		return this.executeUpdate(sql, params);
	}

	/**
	 * 根据用户名查找用户
	 */
	public User findUserByName(String name) {
		String sql = "select * from user where username = ? ";
		Object[] params = {name};

		RSProcessor getUserByNameProcessor = new RSProcessor(){

			public Object process(ResultSet rs) throws SQLException {
				User user = null;
				
				if(rs != null) {
					if(rs.next()) {
						int id = rs.getInt("id");
						String username = rs.getString("username");
						String password = rs.getString("password");
						String policy = rs.getString("policy");
						int state = rs.getInt("state");
						int role = rs.getInt("role");
						Date regTime = rs.getDate("regtime");
						user = new User(id, username, password, policy, state, role, regTime);
					}
				}
				
				return user;
				
			}

		};
		
		return (User)this.executeQuery(getUserByNameProcessor, sql, params);
	}
	/**
	 * 根据id查找用户
	 */
	public User findUserById(int id) {
		String sql = "select * from user where id = ? ";
		Object[] params = {id};

		RSProcessor getUserByNameProcessor = new RSProcessor(){

			public Object process(ResultSet rs) throws SQLException {
				User user = null;
				
				if(rs != null) {
					if(rs.next()) {
						int id = rs.getInt("id");
						String username = rs.getString("username");
						String password = rs.getString("password");
						String policy = rs.getString("policy");
						int state = rs.getInt("state");
						int role = rs.getInt("role");
						Date regTime = rs.getDate("regtime");
						user = new User(id, username, password, policy, state, role, regTime);
					}
				}
				
				return user;
				
			}

		};
		
		return (User)this.executeQuery(getUserByNameProcessor, sql, params);
	}
	
	/**
	 * 更新密码
	 * @param u
	 * @return
	 * @throws IOException 
	 */
	public int update(String username, String password) {
		String sql = "update user set  password = ? where username = ? ";
		Object[] params = {password, username};
		return this.executeUpdate(sql, params);
	}
	
	/**
	 * 获取所有用户信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		//final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		//获取所有文件
		String sql = "select * from user";
		
		RSProcessor usersProcessor = new RSProcessor() {

			public Object process(ResultSet rs) throws SQLException {
				List<User> listUsers = new ArrayList<User>();
				
				while(rs != null && rs.next()) {
					int id = rs.getInt("id");
					String userName = rs.getString("username");
					String passWord = rs.getString("password");
					String policy = rs.getString("policy");
					int state = rs.getInt("state");
					int role = rs.getInt("role");
					Date regTime = rs.getTimestamp("regtime");
					User u = new User(id, userName, passWord, policy, state, role, regTime);
					listUsers.add(u);
				}
				return listUsers;
			}
			
		};
		Object result = executeQuery(usersProcessor, sql, null);
		
		return (List<User>)result;
	}
}
