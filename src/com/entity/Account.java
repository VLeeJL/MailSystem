package com.entity;

import java.util.ArrayList;

public class Account {
	private String account_id;
	String account_name;
	String password;
	//权限
	//0：root；1：管理员：2：普通用户
	int authority;
	//账户是否可使用
	//0，不可使用；1可使用
	int disabled;
	private ArrayList<Long> recvMailList = new ArrayList<Long>();
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Account(String account_id, String account_name, String password, int authority, int disabled) {
		this.account_id = account_id;
		this.account_name = account_name;
		this.password = password;
		this.authority = authority;		
		this.disabled = disabled;
	}
	public boolean updateAccount(String account_id, String account_name, String password, int authority, int disabled) {
		this.account_id = account_id;
		this.account_name = account_name;
		this.password = password;
		this.authority = authority;		
		this.disabled = disabled;
		return true;
	}
	public synchronized String getAccount_id() {
		return account_id;
	}
	public synchronized void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public synchronized String getAccount_name() {
		return account_name;
	}
	public synchronized void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public synchronized String getPassword() {
		return password;
	}
	public synchronized void setPassword(String password) {
		this.password = password;
	}
	public synchronized int getAuthority() {
		return authority;
	}
	public synchronized void setAuthority(int authority) {
		this.authority = authority;
	}
	public synchronized int getDisabled() {
		return disabled;
	}
	public synchronized void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	public synchronized void addMail(Long mail_id) {
		recvMailList.add(mail_id);
	}
	public synchronized void deleteMail(Long mail_id) {
		recvMailList.remove(mail_id);
	}
}
