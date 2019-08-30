package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.entity.ServerConfig;
import com.utils.DataSourceUtils;

public class ServerConfigDao {
	public static boolean updateServerConfigSmtpPort() throws SQLException {
		boolean isSuccess = false;
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "update server_config set journal_size = ? where onlyId = 0";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, ServerConfig.smtp_port);
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
	
	public static boolean updateServerConfigPop3Port() throws SQLException {
		boolean isSuccess = false;
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "update server_config set pop3_port = ? where onlyId = 0";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, ServerConfig.pop3_port);
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
	
	public static boolean updateServerConfigDomain() throws SQLException {
		boolean isSuccess = false;
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "update server_config set domain = ? where onlyId = 0";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setString(1, ServerConfig.domain);
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
	
	public static boolean updateServerConfigJournalSize() throws SQLException {
		boolean isSuccess = false;
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "update server_config set journal_size = ? where onlyId = 0";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setInt(1, ServerConfig.journal_size);
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
	
	public static void getServerConfig() throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from server_config";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		ResultSet dataSet = null;
		try {
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				ServerConfig.updateServerConfig(dataSet.getInt("smtp_port"), dataSet.getInt("pop3_port"), 
						dataSet.getString("domain"), dataSet.getInt("journal_size"));
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
