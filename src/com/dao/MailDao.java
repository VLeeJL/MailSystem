package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.entity.MailContent;
import com.utils.DataSourceUtils;
import com.utils.DealDate;

public class MailDao {
	public static boolean addMailContext(MailContent mailContext) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		boolean isSuccess = false;
		String sql_updateMailContext = "insert into mail_context(mail_id, send_account_id, subject, sendTime, context) value(?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql_updateMailContext);
		stmt.setLong(1, mailContext.getMail_id());
		stmt.setString(2, mailContext.getSendAccount());
		stmt.setString(3, mailContext.getSubject());
		stmt.setTimestamp(4, DealDate.strToSqlDate(mailContext.getSendTime()));
		stmt.setString(5, mailContext.getContext());
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
	
	public static boolean deleteMailContext(Long mail_id) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		boolean isSuccess = false;
		String sql_update = "delete from mail_context where mail_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, mail_id);
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
	
	public static boolean deleteMailContext(String account_id) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		boolean isSuccess = false;
		String sql_update = "delete from mail_context where send_account_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
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
	
	public static MailContent getMailContext(Long mail_id) throws SQLException {
		MailContent mailContext = new MailContent();
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from mail_context where mail_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, mail_id);
		ResultSet dataSet = null;
		try {
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				mailContext.updateMailContext(mail_id, dataSet.getString("send_account_id"), dataSet.getString("subject"), 
						DealDate.dateToStr(dataSet.getTimestamp("sendTime")), dataSet.getString("context"));
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
		return mailContext;
	}
	
	private static ArrayList<Long> getRecvMailList(String account_id) throws SQLException {//根据账号查找得到收件列表的mail_id
		account_id += "@localhost.com";
		account_id = account_id.toUpperCase();
		System.out.println(account_id);
		ArrayList<Long> recvMailList = new ArrayList<Long>();
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from mail_relation where recv_account_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setString(1, account_id);
		ResultSet dataSet = null;
		try {
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				recvMailList.add(dataSet.getLong("mail_id"));				
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
		return recvMailList;
	}
	
	public static ArrayList<MailContent> getMailContext(String account_id) throws SQLException {//根据账号查找
		ArrayList<Long> recvMailIdList = getRecvMailList(account_id);//获得收件人信箱的mail_id
		ArrayList<MailContent> recvMailList = new ArrayList<MailContent>();
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from mail_context where mail_id = ?";
		//System.out.println("This is a test");
		PreparedStatement stmt = conn.prepareStatement(sql_update);		
		ResultSet dataSet = null;
		try {			
			for (Long mail_id : recvMailIdList) {//循环对所有mail_id进行查找
				stmt.setLong(1, mail_id);
				dataSet = stmt.executeQuery();
				while (dataSet.next()) {
					recvMailList.add(new MailContent(dataSet.getLong("mail_id"), dataSet.getString("send_account_id"), dataSet.getString("subject"), 
							DealDate.dateToStr(dataSet.getTimestamp("sendTime")), dataSet.getString("context")));
				}
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
		return recvMailList;
	}
	
	public static boolean addMailRelation(long mail_id, ArrayList<String> recvAccountList) throws SQLException {//插入邮件的收件人
		Connection conn = DataSourceUtils.getConnection();
		boolean isSuccess = false;
		String sql_updateMailRelation = "insert into mail_relation(mail_id, recv_account_id) value(?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql_updateMailRelation);
		for (String recvMail : recvAccountList) {
			stmt.setLong(1, mail_id);
			stmt.setString(2, recvMail);
			stmt.addBatch();
		}
		try {
			isSuccess = stmt.executeBatch() != null ? true : false;
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
	
	public static boolean deleteMailRelation(String account_id) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		boolean isSuccess = false;
		String sql_update = "delete from mail_relation where recv_account_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
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
	
	public static boolean deleteMailRelation(Long mail_id) throws SQLException {
		Connection conn = DataSourceUtils.getConnection();
		boolean isSuccess = false;
		String sql_update = "delete from mail_relation where mail_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, mail_id);
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
	
	public static ArrayList<String> getMailRelation(String account_id) throws SQLException{
		ArrayList<String> recvAccount = new ArrayList<String>();
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from mail_relation where send_account_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setString(1, account_id);
		ResultSet dataSet = null;
		try {
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				recvAccount.add(dataSet.getString("recv_account_id"));
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
		return recvAccount;
	}
	
	public static ArrayList<String> getMailRelation(long mail_id) throws SQLException{
		ArrayList<String> recvAccount = new ArrayList<String>();
		Connection conn = DataSourceUtils.getConnection();
		String sql_update = "select * from mail_relation where mail_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql_update);
		stmt.setLong(1, mail_id);
		ResultSet dataSet = null;
		try {
			dataSet = stmt.executeQuery();
			while (dataSet.next()) {
				recvAccount.add(dataSet.getString("recv_account_id"));
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
		return recvAccount;
	}
}
