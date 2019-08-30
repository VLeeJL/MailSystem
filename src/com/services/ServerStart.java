package com.services;

public class ServerStart {
	
	private static SMTPServer smtpServer = new SMTPServer();
	private static POP3Server pop3Server = new POP3Server();
	
	public static void SMTPStart() {		
		smtpServer.start();
	}
	
	public static void SMTPClose() {		
		smtpServer.stop();
	}
	
	public static void POP3Start() {
		pop3Server.start();
	}
	
	public static void POP3Close() {
		pop3Server.start();
	}
	
	
}
