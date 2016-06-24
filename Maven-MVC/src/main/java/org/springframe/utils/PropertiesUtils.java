package org.springframe.utils;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtils {
	
	private static Logger logger=Logger.getLogger(PropertiesUtils.class);
	/**  
	 * 采用静态方法  
	 */   
    private static Properties props = new Properties();   
	
	/** 
	 * Spring 提供的 PropertiesLoaderUtils 允许您直接通过基于类路径的文件地址加载属性资源 
	 * 最大的好处就是：实时加载配置文件，修改后立即生效，不必重启 
	 */
	public static void readProperties(String propertiesName){  
		props = new Properties();   
		try {  
            props=PropertiesLoaderUtils.loadAllProperties(propertiesName);  
            for(Object key:props.keySet()){
                System.out.print(key+":"); 
                System.out.println(key+":"+props.get(key));  
            }
            System.out.println();
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
	}
	
	/**
	 * 读取配置文件
	 * @param propertiesName 文件名
	 * @param keyName 键
	 * @return
	 */
	public static String readProperties(String propertiesName,String keyName){  
		props = new Properties();   
		try {  
            props=PropertiesLoaderUtils.loadAllProperties(propertiesName);
            final String value = props.getProperty(keyName).trim();
            logger.info(keyName+"的值为: "+value);
            return value;
        } catch (IOException e) {  
            System.out.println(e.getMessage());
            logger.error("--------读取配置文件失败--------", e);
        }
		return null;  
	}
	 
	public static void main(String[] args) {
		//PropertiesUtils p = new PropertiesUtils();
		/*HttpServletRequest request = null;
		String rootDir="src/main/resources/config.properties";
		p.updateProperties(rootDir, "A", "B");*/
		//p.springUtil();
		PropertiesUtils.readProperties("config.properties", "local");
	}
}
