package com.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;


public class ParseJsonObject {
	public static JSONObject parseJsonObject(HttpServletRequest request) {
		JSONObject json = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
			String line = null;  
	        StringBuilder sb = new StringBuilder();  
	        while ((line = br.readLine()) != null) {  
	            sb.append(line);  
	        }  
	        System.out.println(sb);
	        json = JSONObject.fromObject(sb.toString());  
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
