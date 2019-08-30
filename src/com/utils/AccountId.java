package com.utils;

import java.util.UUID;

public class AccountId {
	public static String getAccountId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
