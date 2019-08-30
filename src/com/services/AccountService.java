package com.services;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.dao.AccountDao;
import com.entity.Account;
import com.entity.AccountList;
import com.utils.AccountId;
import com.utils.EncryptionMD5;

public class AccountService {		
	public static Account registerAccount(String account_name, String password, int authority) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Account account = new Account();
		account.setAccount_id(AccountId.getAccountId());
		account.setAccount_name(account_name);
		account.setPassword(EncryptionMD5.encryption(password));
		account.setAuthority(authority);
		account.setDisabled(1);
		return account;
	}
	
	public static void deleteAccount(String account_id) throws SQLException {
		if (queryAccountId(account_id)) {
			AccountDao.deleteAccount(account_id);
		}
	}
	
	public static boolean updateAccountPassword(String account_name, String password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return AccountDao.updateAccountPassword(account_name, EncryptionMD5.encryption(password));
	}
	//权限
	//0：root；1：管理员：2：普通用户
	public static boolean updateAccountAuthority(Account account, int authority) throws SQLException {
		account.setAuthority(authority);
		return AccountDao.updateAccountAuthority(account);
	}
	//账户是否可使用
	//0，不可使用；1可使用
	public static boolean updateAccountDisabled(Account account, int disabled) throws SQLException {
		account.setDisabled(disabled);
		return AccountDao.updateAccountDisabled(account);
	}
	
	public static boolean queryAccountName(String account_name) throws SQLException {
		return (AccountDao.queryAccountName(account_name) != null) ? true : false;
	}
	
	public static boolean queryAccountId(String account_id) throws SQLException {
		return (AccountDao.queryAccountId(account_id) != null) ? true : false;
	}
	
	public static boolean loginAccount(String account_name, String password) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return AccountDao.loginAccount(account_name, EncryptionMD5.encryption(password));
	}
	
	public static Account getAccount(String account_name) throws SQLException {
		return AccountDao.queryAccountName(account_name);
	}
	
	public static void getAccountList() throws SQLException {
		AccountList.accountList.clear();
		AccountDao.getAccountList();
	}
}
