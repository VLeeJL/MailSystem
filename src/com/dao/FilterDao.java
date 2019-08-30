package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.entity.FilterAccount;
import com.entity.FilterIPAddress;
import com.entity.FilterList;
import com.utils.DataSourceUtils;

public class FilterDao {
	public static boolean addFilterAddress(FilterIPAddress filterIPAddress) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "insert into ip_filter(ifilter_id, ip_address) value(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, filterIPAddress.getIfilter_id());
		stmt.setString(2, filterIPAddress.getIp_address());
		boolean isSuccess = false;
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
	
	public static boolean deleteFilterAddress(FilterIPAddress filterIPAddress) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "delete from ip_filter where ifilter_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, filterIPAddress.getIfilter_id());
		boolean isSuccess = false;
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
	
	public static void getFilterAddress() throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from ip_filter";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		ResultSet dataSet = null;
		try {
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				FilterList.filterIPAddresList.add(new FilterIPAddress(dataSet.getInt("ifilter_id"), dataSet.getString("ip_address")));
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
	
	public static boolean addFilterAccount(FilterAccount filterAccount) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "insert into account_filter(afilter_id, account_id) value(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, filterAccount.getAfilter_id());
		stmt.setString(2, filterAccount.getAccount_id());
		boolean isSuccess = false;
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
	
	public static boolean deleteFilterAccount(FilterAccount filterAccount) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "delete from account_filter where afilter_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, filterAccount.getAfilter_id());
		boolean isSuccess = false;
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
	
	public static void getFilterAccount() throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from account_filter";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		ResultSet dataSet = null;
		try {
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				FilterList.filterAccountList.add(new FilterAccount(dataSet.getInt("afilter_id"), dataSet.getString("account_id")));
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
