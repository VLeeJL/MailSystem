package com.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MailContent {
	private long mail_id;
	private String sendAccount;
	private Set<String> receiverAccount = new HashSet<String>();
	private String subject;
	private String sendTime;
	private String context;
	
	public MailContent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MailContent(long mail_id, String sendAccount, String subject, String sendTime, String context) {
		super();
		this.mail_id = mail_id;
		this.sendAccount = sendAccount;
		this.subject = subject;
		this.sendTime = sendTime;
		this.context = context;
	}


	public synchronized void updateMailContext(long mail_id, String sendAccount, String subject, String sendTime, String context) {
		this.mail_id = mail_id;
		this.sendAccount = sendAccount;
		this.subject = subject;
		this.sendTime = sendTime;
		this.context = context;
	}
	
	public synchronized long getMail_id() {
		return mail_id;
	}
	
	public synchronized void setMail_id(long mail_id) {
		this.mail_id = mail_id;
	}
	
	public synchronized String getSendAccount() {
		return sendAccount;
	}
	
	public synchronized void setSendAccount(String sendAccount) {
		this.sendAccount = sendAccount;
	}
	
	public synchronized String getSubject() {
		return subject;
	}
	
	public synchronized void setSubject(String subject) {
		this.subject = subject;
	}
	
	public synchronized String getSendTime() {
		return sendTime;
	}
	
	public synchronized void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	public synchronized String getContext() {
		return context;
	}
	
	public synchronized void setContext(String context) {
		this.context = context;
	}	
	
	public ArrayList<String> getRecvAccount(){//返回一个数组
		return new ArrayList<String>(receiverAccount);
	}	
	
	public void addAccount(String account_id) {
		receiverAccount.add(account_id);
	}
}
