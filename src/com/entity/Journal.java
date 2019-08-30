package com.entity;

public class Journal {
	private Long journal_id;
	private String savePath;
	
	public Journal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Journal(Long journal_id, String savePath) {
		super();
		this.journal_id = journal_id;
		this.savePath = savePath;
	}

	public synchronized Long getJournal_id() {
		return journal_id;
	}

	public synchronized void setJournal_id(Long journal_id) {
		this.journal_id = journal_id;
	}

	public synchronized String getSavePath() {
		return savePath;
	}

	public synchronized void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
}
