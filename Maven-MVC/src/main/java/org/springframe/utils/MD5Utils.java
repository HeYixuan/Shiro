package org.springframe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
	/**
	 * MD5加密方法
	 * 
	 * @author HYX
	 * @datetime 2011-10-13
	 */

	/***
	 * MD5加密32位方法
	 */
	public static String EncryptionMD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * MD5
	 * @param str
	 * @return
	 */
	public static String crypt(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}

		StringBuffer hexString = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hexString.toString();
	}

	/**
	 * 加密后解密
	 */
	public static String DecryptMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}
	
	/**
	 * SHA-512
	 * 
	 * @param message
	 * @return
	 */
	public static String SHA512(String message) {
		return DigestUtils.sha512Hex(message);
	}

	/**
	 * 测试主函数
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println(
				"19d9e367e5d564169b4458ef3e440d77910a1b1d66426512d3c1feff41f06182f819f77aa2c6f97a54d5f3070eb6ceefb287b5a259e3c415db60df637313ab28");
		System.out.println("请输入要加密的字符：");
		String s = input.next();
		System.out.println(SHA512(s));
		System.out.println("长度:" + SHA512(s).length());
		/*
		 * System.out.println("原始：" + s); System.out.println("MD5加密后：" +
		 * DecryptMD5(s)); System.out.println("MD5加密后再加密：" + EncryptionMD5(s));
		 * System.out.println("MD5解密：" + DecryptMD5(DecryptMD5(s)));
		 */
	}
}
