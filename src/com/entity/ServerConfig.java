package com.entity;

public class ServerConfig {
	public static int smtp_port;
	public static int pop3_port;
	public static String domain;
	public static int journal_size;
	public static void updateServerConfig(int smtpPort, int pop3Port, String domainName, int journalSize) {
		smtp_port = smtpPort;
		pop3_port = pop3Port;
		domain = domainName;
		journal_size = journalSize;
	}
	
	public static boolean smtpServer = true;
	public static boolean pop3Server = true;
	
}
