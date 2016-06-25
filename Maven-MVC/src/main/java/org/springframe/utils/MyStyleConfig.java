package org.springframe.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MyStyleConfig {
	private MyStyleConfig(){}
	private static Properties props = new Properties(); 
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MyStyle.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key){
		String value = "";
		try {
			value = new String(props.getProperty(key).getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(null != value) {
			value = value.trim();
		}
		return value;
	}

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    } 
}
