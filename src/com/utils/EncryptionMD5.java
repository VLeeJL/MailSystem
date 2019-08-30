package com.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class EncryptionMD5 {
	public static String encryption(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest instance = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		String dealPassword = base64en.encode(instance.digest(password.getBytes("utf-8")));
		return dealPassword;
	}
	
	public boolean checkPassword(String newpasswd,String oldpasswd) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        if(encryption(newpasswd).equals(oldpasswd))
            return true;
        else
            return false;
    }
}
