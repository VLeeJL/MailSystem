package com.services;

import java.sql.SQLException;

import com.dao.FilterDao;
import com.entity.FilterAccount;
import com.entity.FilterIPAddress;
import com.entity.FilterList;

public class FilterService {
	
	public static boolean addFilterAddress(String ip_address) throws SQLException {
		FilterIPAddress filterIPAddress = new FilterIPAddress(System.nanoTime(), ip_address);
		FilterList.filterIPAddresList.add(filterIPAddress);
		return FilterDao.addFilterAddress(filterIPAddress);
	}
	
	public static boolean deleteFilterAddress(FilterIPAddress filterIPAddress) throws SQLException {
		FilterList.filterIPAddresList.remove(filterIPAddress);
		return FilterDao.deleteFilterAddress(filterIPAddress);
	}
	
	public static void getFilterAddress() throws SQLException{//从数据库重新读取数据
		FilterList.filterIPAddresList.clear();
		FilterDao.getFilterAddress();
	}
	
	public static boolean addFilterAccounts(String account_name) throws SQLException {
		FilterAccount filterAccount = new FilterAccount(System.nanoTime(), account_name);
		FilterList.filterAccountList.add(filterAccount);
		return FilterDao.addFilterAccount(filterAccount);
	}
	
	public static boolean deleteFilterAccount(FilterAccount filterAccount) throws SQLException {
		FilterList.filterAccountList.remove(filterAccount);
		return FilterDao.deleteFilterAccount(filterAccount);
	}
	
	public static void getFilterAccount() throws SQLException{
		FilterList.filterAccountList.clear();
		FilterDao.getFilterAccount();
	}
}
