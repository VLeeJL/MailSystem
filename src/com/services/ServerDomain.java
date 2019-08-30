package com.services;

import com.entity.ServerConfig;

public class ServerDomain {
	
	public static boolean checkLocalServerDomain(String domain) {
		return domain.equals(ServerConfig.domain); 
	}
}
