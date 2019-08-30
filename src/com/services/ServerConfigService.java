package com.services;

import java.sql.SQLException;

import com.dao.ServerConfigDao;
import com.entity.ServerConfig;

public class ServerConfigService {
	public static boolean updateServerConfigSmtpPort(int smtp_port) throws SQLException {
		ServerConfig.smtp_port = smtp_port;
		return ServerConfigDao.updateServerConfigSmtpPort();
	}
	
	public static boolean updateServerConfigPop3Port(int pop3_port) throws SQLException {
		ServerConfig.pop3_port = pop3_port;
		return ServerConfigDao.updateServerConfigPop3Port();
	}
	
	public static boolean updateServerConfigDomain(String domain) throws SQLException {
		ServerConfig.domain = domain;
		return ServerConfigDao.updateServerConfigDomain();
	}
	
	public static boolean updateServerConfigJournalSize(int journal_size) throws SQLException {
		ServerConfig.journal_size = journal_size;
		return ServerConfigDao.updateServerConfigJournalSize();
	}
	
	public static void getServerConfig() throws SQLException {//从数据库获取参数设置
		ServerConfigDao.getServerConfig();
	}
}
