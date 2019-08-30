package com.services;

import java.sql.SQLException;

import com.dao.JournalDao;
import com.entity.Journal;
import com.entity.JournalList;

public class JournalService {
	public static boolean addJournal(String buildPath) throws SQLException {
		Journal journal = new Journal(System.nanoTime(), buildPath);
		return JournalDao.addJournal(journal);
	}
	
	public static boolean deleteJournal(Journal journal) throws SQLException {
		JournalList.journalList.remove(journal);
		return JournalDao.deleteJournal(journal);
	}
	
	public static void getJournals() throws SQLException {
		JournalList.journalList.clear();
		JournalDao.getJournals();
	}
}
