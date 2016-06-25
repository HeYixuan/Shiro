package org.springframe.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
/**
 * DES加密字符串
 *
 * @author CR
 * Created Apr 21, 2011
 */
public class Encrypt {

 static Key key;

	public static void getKey(String strKey) {
	  try {
		   KeyGenerator _generator = KeyGenerator.getInstance("DES");
		   _generator.init(new SecureRandom(strKey.getBytes()));
		   key = _generator.generateKey();
		   _generator = null;
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	}

	public static String getEncString(String strMing) {
	  
	  byte[] byteMi = null;
	  byte[] byteMing = null;
	  String strMi = "";
	  try {
		   return byte2hex(getEncCode(strMing.getBytes()));
		   // byteMing = strMing.getBytes("UTF8");
		   // byteMi = this.getEncCode(byteMing);
		   // strMi = new String( byteMi,"UTF8");
	  } catch (Exception e) {
		  e.printStackTrace();
	  } finally {
		   byteMing = null;
		   byteMi = null;
	  }
	  return strMi;
	}

	public static String getDesString(String strMi) {
	  byte[] byteMing = null;
	  byte[] byteMi = null;
	  String strMing = "";
	  try {
		  return new String(getDesCode(hex2byte(strMi.getBytes())));
		  // byteMing = this.getDesCode(byteMi);
		  // strMing = new String(byteMing,"UTF8");
	  } catch (Exception e) {
		  e.printStackTrace();
	  } finally {
		  byteMing = null;
		  byteMi = null;
	  }
	  return strMing;
	}

	private static byte[] getEncCode(byte[] byteS) {
	  byte[] byteFina = null;
	  Cipher cipher;
	  try {
		  cipher = Cipher.getInstance("DES");
		  cipher.init(Cipher.ENCRYPT_MODE, key);
		  byteFina = cipher.doFinal(byteS);
	  } catch (Exception e) {
		  e.printStackTrace();
	  } finally {
		  cipher = null;
	  }
	  return byteFina;
	}

	private static byte[] getDesCode(byte[] byteD) {
	  Cipher cipher;
	  byte[] byteFina = null;
	  try {
		  cipher = Cipher.getInstance("DES");
		  cipher.init(Cipher.DECRYPT_MODE, key);
		  byteFina = cipher.doFinal(byteD);
	  } catch (Exception e) {
		  e.printStackTrace();
	  } finally {
		  cipher = null;
	  }
	  return byteFina;
	}

	public static String byte2hex(byte[] b) { // 一个字节的数，
	  // 转成16进制字符串
	  String hs = "";
	  String stmp = "";
	  for (int n = 0; n < b.length; n++) {
	   // 整数转成十六进制表示
	   stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	   if (stmp.length() == 1)
	    hs = hs + "0" + stmp;
	   else
	    hs = hs + stmp;
	  }
	  return hs.toUpperCase(); // 转成大写
	}

	public static byte[] hex2byte(byte[] b) {
	  if ((b.length % 2) != 0)
		  throw new IllegalArgumentException("长度不是偶数");
		  byte[] b2 = new byte[b.length / 2];
	  for (int n = 0; n < b.length; n += 2) {
		   String item = new String(b, n, 2);
		   // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
		   b2[n / 2] = (byte) Integer.parseInt(item, 16);
	  }
	  return b2;
	}

 public static void main(String[] args) {
	  Encrypt des = new Encrypt();// 实例化一个对像
	  des.getKey("eshiti@!~().com****");// 生成密匙
	
	 String strEnc = des.getEncString("infinitychen@hotmai.com@sdfsfsd好的");// 加密字符串,返回String的密文
	 System.out.println(strEnc);
	
	  String strDes = des.getDesString("7c7c42bf3b4cd72190d4cde5d20f068773576f195844cce14aa522db2a4d6674");// 把String 类型的密文解密
	  System.out.println(strDes);
 }

}
