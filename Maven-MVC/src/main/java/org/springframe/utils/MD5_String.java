package org.springframe.utils;

import java.util.Random;

public class MD5_String {
	/**
	   * 产生随机字符串
	   * */
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	public static final String randomString(int length) {
	         if (length < 1) {
	             return null;
	         }
	         if (randGen == null) {
	                randGen = new Random();
	                numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz").toCharArray();
	                 }
	         char [] randBuffer = new char[length];
	         for (int i=0; i<randBuffer.length; i++) {
	             randBuffer[i] = numbersAndLetters[randGen.nextInt(60)];
	         }
	         return new String(randBuffer);
	}
	
	public static void main(String[] args) {
		String s=randomString(60);
		System.out.println(s);
		
	}
}
