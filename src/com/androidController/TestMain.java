package com.androidController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

public class TestMain {
	private static final String ACCOUNT_REGEX = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[0-9A-Za-z]{8,16}$";
    private static final String PWD_REGEX = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[0-9A-Za-z]{8,16}$";
    
    private static boolean isAccountValid(String account) {
        //TODO: Replace this with your own logic
        Pattern p = Pattern.compile(ACCOUNT_REGEX);
        Matcher m = p.matcher(account);
        return m.matches();
    }

    private static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        Pattern p = Pattern.compile(PWD_REGEX);
        Matcher m = p.matcher(password);
        return m.matches();
    }

	public static void main(String[] args) {
		String str = "tee123456";
		System.out.println(isAccountValid(str));
		System.out.println(isPasswordValid(str));
		
		JSONObject object = new JSONObject();
		object.put("user", "account");
		object.put("pwd", "password");
		System.out.println("user" + object.getString("user"));
		System.out.println("user" + object.getString("pwd"));
	}

}
