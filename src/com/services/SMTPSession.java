package com.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class SMTPSession implements Runnable {
	private Socket s;
	private BufferedReader br = null;
	private PrintWriter ps = null;
	public SMTPSession(Socket s) {
		this.s = s;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
			ps = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), "utf-8"), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getAccount(String str) {
		
		return str.substring(str.indexOf("<") + 1,	str.indexOf(">"));
	}
	
	public void run() {
		ps.println("220 Welcome to SMTP server��ӭ");
		String comment = null;//���͹�����ָ��
		String argument = null;//ָ���Ĳ���
		String account_name = null;
		String sendAccount = null;
		String rcptAccount = null;
		try {
			while ((comment = br.readLine()) != null) {
				comment = new String(comment.getBytes(),"UTF-8");				
				System.out.println("�յ���ָ������:" + comment);
				if (comment.length() > 0) {//��ò���
					String arrays[] = comment.split(" ");
					comment = arrays[0];					
					System.out.println("comment��   " + comment);
					if (arrays.length > 1) {
						argument = arrays[1];
						System.out.println("argument��   " + argument);
					}					
				}
				if (comment.equalsIgnoreCase("EHLO")) {
					doEhlo();
				} 
				else if (comment.equalsIgnoreCase("HELO")) {
					doHelo();
				} 
				else if (comment.equalsIgnoreCase("AUTH")) {
					account_name = doAuth(argument);//�����û�����ʾ��¼�ɹ�
				}
				else if ((account_name != null)&&comment.equalsIgnoreCase("MAIL")) {//��÷�����					
					if ((argument != null)&&(argument.contains("<")&&argument.contains(">"))&&(doMail(argument))) {
						sendAccount = getAccount(argument);						
					} 
					else {
						ps.println("550 ��Ч���û�");
					}
				} 
				else if ((sendAccount != null)&&comment.equalsIgnoreCase("RCPT")) {
					if ((argument != null)&&(argument.contains("<")&&argument.contains(">"))&&(doRcpt(argument))) {
						rcptAccount = getAccount(argument);	
					} 
					else {
						ps.println("550 ��Ч���û�");
					}
				} 
				else if ((rcptAccount != null)&&comment.equalsIgnoreCase("DATA")) {
					if (!doData(rcptAccount)) {//����false���������quit����
						doQuit();
						return;
					}
				} 
				else if (comment.equalsIgnoreCase("QUIT")) {
					doQuit();
					return;
				} 
				else {
					ps.println("500 Error�����ָ������");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (ps != null)
					ps.close();
				if (s != null)
					s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void doEhlo() {
		String str = "250-PIPELINING\r\n" + "250-AUTH LOGIN PLAIN\r\n"
				+ "250-AUTH=LOGIN PLAIN\r\n" + "250 coremail";
		ps.println(str);
	}
	
	private void doHelo() {
		InetAddress ia = s.getInetAddress();
		String ipName = ia.getHostName();
		ps.println("250 Hello "+ipName);
	}
	
	private String doAuth(String argument) {
		try {
			if (argument.equalsIgnoreCase("login")) {
				ps.println("334 �������½�û���");
				//String name = new String(Base64.decode(br.readLine()));
				//System.out.println(name);
				String user_name = br.readLine();				
				if (AccountService.queryAccountName(user_name)) {
					ps.println("334 �������½����");
					//String password = new String(Base64.decode(br.readLine()));	
					String password = br.readLine();
					if (AccountService.loginAccount(user_name, password)) {
						ps.println("235 OK��½�ɹ�");
						return user_name;
					} else {
						ps.println("500 �����������");
					}
				} else {
					ps.println("500 �����ڸ��û���");
				}
			} else {
				ps.println("500 ��Ч��ָ��");
			}
		} catch (IOException | NoSuchAlgorithmException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean doMail(String argument) {
		argument = argument.toUpperCase();
		if (argument.startsWith("FROM:")) {
			ps.println("250 OK");
			return true;
		} else {
			ps.println("550 ��Ч���û�");
			return false;
		}
	}
	
	private boolean doRcpt(String argument) {
		argument = argument.toUpperCase();
		if (argument.startsWith("TO:")) {
			ps.println("250 OK");
			return true;
		} else {
			ps.println("550 ��Ч���û�");
			return false;
		}
	}
	
	private boolean doData(String rcptName) {
		ps.println("354 End data with <CR><LF>.<CR><LF>");
		String messageDate = null;
		String mimeVersion = null;
		String contentType = null;
		String contentEncoding = null;
		String messageId = null;
		boolean flagContent = false;
		
		String sendAccount = null;
		ArrayList<String> recvAccountList = new ArrayList<String>();
		String subject = null;	
		String content = null;
		
		try {
			String readMessage = null;
			while ((readMessage = br.readLine()) != null) {
				readMessage = flagContent ? readMessage : readMessage.toUpperCase();//��������ʱ�������ַ�ת��
				System.out.println(readMessage);
				if (readMessage.startsWith("DATE")) {
					messageDate = readMessage.substring(readMessage.indexOf(":") + 1);
				}
				else if (readMessage.startsWith("MIME-VERSION")) {
					mimeVersion = readMessage.substring(readMessage.indexOf(":") + 1);
				}
				else if (readMessage.startsWith("CONTENT-TYPE")) {
					contentType = readMessage.substring(readMessage.indexOf(":") + 1);
				}
				else if (readMessage.startsWith("CONTENT-TRANSFER-ENCODING")) {
					contentEncoding = readMessage.substring(readMessage.indexOf(":") + 1);
				}
				else if (readMessage.startsWith("MESSAGE-ID")) {
					messageId = readMessage.substring(readMessage.indexOf(":") + 1);
				}
				else if (readMessage.startsWith("FROM")) {
					sendAccount = getAccount(readMessage);
				}
				else if (readMessage.startsWith("TO")) {
					if (sendAccount != null) {
						recvAccountList.add(new String(getAccount(readMessage)));
					}
					else {
						ps.println("your send account is null");
					}
				}
				else if (readMessage.startsWith("SUBJECT")) {
					if (recvAccountList.size() != 0) {
						subject = readMessage.substring(readMessage.indexOf(":") + 1);
					}
					else {
						ps.println("your send-account or recv-account is null");
					}
				}
				else if ((subject != null)&&(readMessage.equals(""))) {
					System.out.println("�õ�����");
					flagContent = true;
				}
				else if (flagContent&&(".".equals(readMessage))) {//�������жϵ�˳���ܵߵ�					
					flagContent = false;
					MailService.addMail(sendAccount, recvAccountList, subject, content);
					ps.println("250 Ok: queued as");
					break;
				}
				else if (flagContent) {
					System.out.println("������������");
					content = readMessage;
				}
				else if (readMessage.startsWith("QUIT")) {
					doQuit();
					return false;
				}
				else {
					ps.println("502 Error: command not implemented");					
				}
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}	
		return true;
	}
	
	private void doQuit() {
		ps.println("221 Bye");
	}	
}

class TransferSend implements Runnable {

	private String from = null;
	private String to = null;
	private String subject = null;
	private String content = null;

	public TransferSend(String from, String to, String subject, String content) {
		this.from = from; // ��mail from ������
		this.to = to; // ��list�����л�ȡ
		this.subject = subject;// ��text�л�ȡ
		this.content = content;// ��text�л�ȡ
	}

	// javamail����ʵ��ת��
	public void run() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "49.123.81.103"); // ָ��SMTP������
		props.put("mail.smtp.auth", "false"); // ָ���Ƿ���ҪSMTP��֤
		try {

			Session mailSession = Session.getInstance(props, null);
			mailSession.setDebug(true); // �Ƿ��ڿ���̨��ʾdebug��Ϣ

			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from)); // ������

			message.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
			message.setSubject(subject); // �ʼ�����
			message.setText(content); // �ʼ�����
			message.setSentDate(new Date());// ���÷���ʱ��
			message.saveChanges();

			Transport.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
