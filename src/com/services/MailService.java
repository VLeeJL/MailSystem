package com.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.dao.MailDao;
import com.entity.MailContent;
import com.utils.DealDate;

public class MailService {
	
	public static boolean addMail(String sendAccount, ArrayList<String> recvAccountList, String subject, String context) throws SQLException {
		String sendAccountName = sendAccount.substring(0, sendAccount.indexOf("@") - 1);
		String sendAccountDomain = sendAccount.substring(sendAccount.indexOf("@") - 1, sendAccount.length() -1);
		MailContent mailContext = new MailContent(System.nanoTime(), sendAccount, subject, DealDate.dateToStr(new Date()), context);
		if (MailDao.addMailContext(mailContext) ) {//加入数据成功
			 return MailDao.addMailRelation(mailContext.getMail_id(), recvAccountList);
		}
		return false;
	}
	
	public static boolean deleteMail(Long mail_id) throws SQLException {
		if (MailDao.deleteMailContext(mail_id)) {
			return MailDao.deleteMailRelation(mail_id);
		}
		return false;
	}

	public static boolean deleteMail(String account_id) throws SQLException {
		if (MailDao.deleteMailContext(account_id)){
			return MailDao.deleteMailRelation(account_id);
		}
		return false;
	}
}
