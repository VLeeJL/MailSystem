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
				System.out.println("SMTP server没有启动");
				return;
			}
			try {
				serverSocket = new ServerSocket(1025);//ServerConfig.smtp_port
				System.out.println("SMTP服务端正在等待并监听连接......");
				while (true) {				
						Socket s = serverSocket.accept();// 等待客户端登录连接，此时程序成阻塞状态，等待用户登录进来
						InetAddress ia = s.getInetAddress();
						String ip = ia.getHostAddress();
						String ipName = ia.getHostName();
						System.out.println("客户端ip:" + ip + "\t主机名:" + ipName
								+ "\t连接时间：" + DealDate.dateToStr(new Date()));
						// 为每个用户分配一个线程
						SMTPSession session = new SMTPSession(s);
						session.run();
						socketList.add(session);
						//new Thread(session).start();// 启动线程
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
