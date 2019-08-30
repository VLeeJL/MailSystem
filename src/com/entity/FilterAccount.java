package com.entity;

public class FilterAccount {
	private long afilter_id;
	private String account_id;
	
	public FilterAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FilterAccount(long afilter_id, String account_id) {
		super();
		this.afilter_id = afilter_id;
		this.account_id = account_id;
	}
	
	public void updateFilterAccount(long afilter_id, String account_id) {
		this.afilter_id = afilter_id;
		this.account_id = account_id;
	}

	public synchronized long getAfilter_id() {
		return afilter_id;
	}

	public synchronized void setAfilter_id(long afilter_id) {
		this.afilter_id = afilter_id;
	}

	public synchronized String getAccount_id() {
		return account_id;
	}

	public synchronized void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
}
