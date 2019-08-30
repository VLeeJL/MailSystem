package com.entity;

public class FilterIPAddress {
	private long ifilter_id;
	private String ip_address;
	
	public FilterIPAddress() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FilterIPAddress(long ifilter_id, String ip_address) {
		super();
		this.ifilter_id = ifilter_id;
		this.ip_address = ip_address;
	}
	public void updateFilterIPAddress(long ifilter_id, String ip_address) {
		this.ifilter_id = ifilter_id;
		this.ip_address = ip_address;
	}
	public synchronized long getIfilter_id() {
		return ifilter_id;
	}
	public synchronized void setIfilter_id(long ifilter_id) {
		this.ifilter_id = ifilter_id;
	}
	public synchronized String getIp_address() {
		return ip_address;
	}
	public synchronized void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	
}
