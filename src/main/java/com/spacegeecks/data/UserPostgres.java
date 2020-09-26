package com.spacegeecks.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.spacegeecks.beans.Role;
import com.spacegeecks.beans.Transaction;
import com.spacegeecks.beans.User;
import com.spacegeecks.utils.ConnectionUtil;

public class UserPostgres implements UserDAO {

	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	public int createUser(User u) {
		Integer id = 0;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO space_user VALUES "
					+ "(default, ?, ?, ?, ?, ?, ?)";
			String[] keys = {"id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			pstmt.setString(5, u.getEmail());
			pstmt.setInt(6, u.getRole().getId());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getInt(1);
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		u.setUserId(id);
		return id;
	}

	public Set<User> findUsers() {
		
		HashSet<User> users = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM space_user";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			users = new HashSet<>();
			
			while(rs.next()) {
				Role r = this.findRoleById(rs.getInt("role_type"));
				
				User u = new User();
				u.setUserId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
				u.setRole(r);
				
				Set<Transaction> transactions = new HashSet<>();
				// accounts = accountDAO.findAccountsByUser(u);
				u.setCart(transactions);
				
				users.add(u);
				
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return users;
	}
	
	
	public Set<User> findUsersByRole(Role r) {
		HashSet<User> users = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM space_user " +
					"WHERE role_type = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,r.getId());
			
			ResultSet rs = pstmt.executeQuery();
			
			users = new HashSet<>();
			
			while(rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
				
				u.setRole(r);
				
				Set<Transaction> transactions = new HashSet<>();
				// accounts = accountDAO.findAccountsByUser(u);
				u.setCart(transactions);
				
				users.add(u);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return users;
	}

	public User findUserByUsername(String username) {
		User u = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM space_user " +
					"WHERE username = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,username);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				u = new User();
				Role r = this.findRoleById(rs.getInt("role_type"));

				u.setUserId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
				u.setRole(r);
				
				Set<Transaction> transactions = new HashSet<>();
				// accounts = accountDAO.findAccountsByUser(u);
				u.setCart(transactions);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return u;
	}
	
	@Override
	public User findUserByEmail(String email) {
		User u = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM space_user " +
					"WHERE email = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				u = new User();
				Role r = this.findRoleById(rs.getInt("role_type"));

				u.setUserId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
				u.setRole(r);
				
				Set<Transaction> transactions = new HashSet<>();
				// accounts = accountDAO.findAccountsByUser(u);
				u.setCart(transactions);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return u;
	}

	public User findUserByUserId(int userId) {
		User u = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM space_user " +
					"WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,userId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				u = new User();
				Role r = this.findRoleById(rs.getInt("role_type"));

				u.setUserId(rs.getInt("user_id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("passwd"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
				u.setRole(r);
				
				Set<Transaction> transactions = new HashSet<>();
				// accounts = accountDAO.findAccountsByUser(u);
				u.setCart(transactions);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return u;
	}

	public void updateUser(User u) {
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "UPDATE space_user SET username = ?, passwd = ?, " +
					"first_name = ?, last_name = ?, email = ?, role_type = ? " +
					"WHERE user_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			pstmt.setString(5, u.getEmail());
			pstmt.setInt(6, u.getRole().getId());
			pstmt.setInt(7, u.getUserId());
			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(User u) {
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "DELETE FROM space_user " +
					"WHERE user_id = ?";
				
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, u.getUserId());
			
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}

	@Override
	public Set<Role> findRoles() {
		
		HashSet<Role> roles = null;
		
		try (Connection conn = cu.getConnection()) {
			conn.setAutoCommit(false);
			String sql = "SELECT * FROM user_role";
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			roles = new HashSet<>();
			
			while(rs.next()) {
				Role r = new Role();
				
				r.setId(rs.getInt("id"));
				r.setName(rs.getString("name"));
				
				roles.add(r);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				}
		
		return roles;
	}
	
	
	@Override
	public Role findRoleByName(String name) {
		Role role = null;
		
		try(Connection conn = cu.getConnection()) {
			String sql = "select * from user_role where name = ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return role;
	}
	
	@Override
	public Role findRoleById(int id) {
		Role role = null;
		
		try(Connection conn = cu.getConnection()) {
			String sql = "SELECT * from user_role where id = ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return role;
	}

	@Override
	public User findUserByTransaction(Transaction t) {
		// TODO Auto-generated method stub
		return null;
	}

}
