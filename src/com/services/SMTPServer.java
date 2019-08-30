package com.services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.entity.ServerConfig;
import com.utils.DealDate;


public class SMTPServer {
	private boolean smtpflag = false;
	
	private ServerSocket serverSocket;
	private static List<SMTPSession> socketList= Collections.synchronizedList(new LinkedList<SMTPSession>());
	private static SMTPThread smtpThread=null;  
	
	public void start() {
		this.smtpflag = true;
		open();
	}

	public void stop() {
		this.smtpflag = false;
		close();
	}
	
	public class SMTPThread implements Runnable {
		
		@Override
		public void run() {
			if (!smtpflag) {			
				System.out.println("SMTP serverû������");
				return;
			}
			try {
				serverSocket = new ServerSocket(1025);//ServerConfig.smtp_port
				System.out.println("SMTP��������ڵȴ�����������......");
				while (true) {				
						Socket s = serverSocket.accept();// �ȴ��ͻ��˵�¼���ӣ���ʱ���������״̬���ȴ��û���¼����
						InetAddress ia = s.getInetAddress();
						String ip = ia.getHostAddress();
						String ipName = ia.getHostName();
						System.out.println("�ͻ���ip:" + ip + "\t������:" + ipName
								+ "\t����ʱ�䣺" + DealDate.dateToStr(new Date()));
						// Ϊÿ���û�����һ���߳�
						SMTPSession session = new SMTPSession(s);
						session.run();
						socketList.add(session);
						//new Thread(session).start();// �����߳�
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void close() {
		try {
			if (serverSocket == null) {
				return;
			}
			serverSocket.close();
			socketList.clear();			
		} catch (IOException e) {
			e.printStackTrace();
	    }
		smtpThread = null;
	}
	
	public boolean open() {
		if(smtpThread!=null) return false;
	      (smtpThread=new SMTPThread()).run();
	      return true;
	}
}
