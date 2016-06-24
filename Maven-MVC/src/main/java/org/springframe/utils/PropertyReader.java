package org.springframe.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 *  Class Name: PropertyReader.java
 *  Function: 读取资源文件
 *  Modifications:   
 *  @author CR  DateTime 2012-9-13 08:53:44    
 *  @version 1.0
 */
public class PropertyReader {

	public static Properties  loadProperty(String fileName){
		InputStream inputStream2 =  PropertyReader.class.getResourceAsStream("/"+fileName);  
		Properties p = new Properties();  
        try {  
            p.load(inputStream2);  
            inputStream2.close();  
        } catch (IOException e1) {
        	System.out.println("load propertyFile ->" +fileName+" error");
            e1.printStackTrace();  
        }
        return p;
	}
	
	public static String  loadPropertyParam(String fileName,String paramName){
		InputStream inputStream2 =  PropertyReader.class.getResourceAsStream("/"+fileName);  
		Properties p = new Properties();  
        try {  
            p.load(inputStream2);  
            inputStream2.close();  
        } catch (IOException e1) {
        	System.out.println("load propertyFile ->" +fileName+" error");
            e1.printStackTrace();  
        }
        return (String)p.get(paramName);
	}
	
}
