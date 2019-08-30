package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.entity.Account;
import com.entity.AccountList;
import com.utils.DataSourceUtils;

public class AccountDao {
	public static void saveAccount(Account account) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_saveAccount = "insert into account(account_id, account_name, password, authority, disabled) "
				+ "values(?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql_saveAccount);
		stmt.setString(1, account.getAccount_id());
		stmt.setString(2, account.getAccount_name());
		stmt.setString(3, account.getPassword());
		stmt.setInt(4, account.getAuthority());
		stmt.setInt(5, account.getDisabled());		
		try {
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}		
	}
	
	public static boolean deleteAccount(String account_id) throws SQLException {
		boolean isSuccess = false;
		Connection conn = DataSourceUtils.getConnection();
		String sql_deleteAccount = "delete fron account where account_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_deleteAccount);
		stmt.setString(1, account_id);
		try {
			isSuccess = stmt.executeUpdate() != 0 ? true : false; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}	
		return isSuccess;
	}
	
	public static boolean updateAccountPassword(String account_name, String password) throws SQLException {
		boolean isSuccess = false;
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "update account set password = ? where account_name = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setString(1, password);
		stmt.setString(2, account_name);
		try {
			isSuccess = stmt.executeUpdate() != 0 ? true : false; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}	
		return isSuccess;
	}
	
	public static boolean updateAccountAuthority(Account account) throws SQLException {
		boolean isSuccess = false;
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "update account set authority = ? where account_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setInt(1, account.getAuthority());
		stmt.setString(2, account.getAccount_id());
		try {
			isSuccess = stmt.executeUpdate() != 0 ? true : false; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}	
		return isSuccess;
	}
	
	public static boolean updateAccountDisabled(Account account) throws SQLException {
		boolean isSuccess = false;
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "update account set disabled = ? where account_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setInt(1, account.getDisabled());
		stmt.setString(2, account.getAccount_id());
		try {
			isSuccess = stmt.executeUpdate() != 0 ? true : false; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}	
		return isSuccess;
	}
	
	//注册用户时需要先查询用户名
	//其他情况都使用account_id进行查找用户
	public static Account queryAccountName(String account_name) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_queryAccount = "select account_id, account_name, password, authority, disabled from account where account_name = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_queryAccount);
		stmt.setString(1, account_name);
		ResultSet dataSet = null;
		Account account = new Account();
		boolean isUpdate = false;
		try {
			dataSet = stmt.executeQuery();//这里只能查到一个用户
			while (dataSet.next()) {
				isUpdate = account.updateAccount(dataSet.getString("account_id"), dataSet.getString("account_name"), dataSet.getString("password"),
						dataSet.getInt("authority"), dataSet.getInt("disabled"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return isUpdate ? account : null;
	}
	
	public static Account queryAccountId(String account_id) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_queryAccount = "select account_id, account_name, password, authority, disabled from account where account_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_queryAccount);
		stmt.setString(1, account_id);
		ResultSet dataSet = null;
		Account account = new Account();
		boolean isUpdate = false;
		try {
			dataSet = stmt.executeQuery();//这里只能查到一个用户
			while (dataSet.next()) {
				isUpdate = account.updateAccount(dataSet.getString("account_id"), dataSet.getString("account_name"), dataSet.getString("password"),
						dataSet.getInt("authority"), dataSet.getInt("disabled"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return isUpdate ? account : null;
	}
	
	public static boolean loginAccount(String account_name, String password) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_queryAccount = "select * from account where account_name=?";
		PreparedStatement stmt = conn.prepareStatement(sql_queryAccount);
		stmt.setString(1, account_name);
		ResultSet dataSet = null;
		String queryPassword = null;
		try {
			dataSet = stmt.executeQuery();//这里只能查到一个用户
			while (dataSet.next()) {
				queryPassword = dataSet.getString("password");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		if (queryPassword == null) {
			return false;
		}
		return queryPassword.equals(password);
	}
	
	public static void getAccountList() throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_queryAccount = "select account_id, account_name, password, authority, disabled from account";
		PreparedStatement stmt = conn.prepareStatement(sql_queryAccount);
		ResultSet dataSet = null;
		try {			
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				Account account = new Account();
				account.updateAccount(dataSet.getString("account_id"), dataSet.getString("account_name"), dataSet.getString("password"),
						dataSet.getInt("authority"), dataSet.getInt("disabled"));
				AccountList.accountList.add(account);
				//System.out.println(account.getAccount_id() + "           ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}
