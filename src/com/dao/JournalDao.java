package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.entity.Journal;
import com.entity.JournalList;
import com.utils.DataSourceUtils;

public class JournalDao {
	public static boolean addJournal(Journal journal ) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "insert into journal(journal_id, save_path) value(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, journal.getJournal_id());
		stmt.setString(2, journal.getSavePath());
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
	
	public static boolean deleteJournal(Journal journal) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "delete from journal where journal_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, journal.getJournal_id());
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
	
	public static void getJournals() throws SQLException {
		
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from journal";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		ResultSet dataSet = null;
		try {
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				JournalList.journalList.add(new Journal(dataSet.getLong("journal_id"), dataSet.getString("save_path")));
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
