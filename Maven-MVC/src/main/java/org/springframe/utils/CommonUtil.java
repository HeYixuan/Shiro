package org.springframe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class CommonUtil {
	
	/**
	 * 取得当前日期
	 * @param separator 分隔符 -  /  _ 等
	 * @return
	 */
	public static String  getDate(String separator){
		  StringBuffer date=new StringBuffer();
		  Calendar cal=Calendar.getInstance();//使用日历类
		  int year=cal.get(Calendar.YEAR);//得到年
		  int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		  int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		  //int hour=cal.get(Calendar.HOUR);//得到小时
		  //int minute=cal.get(Calendar.MINUTE);//得到分钟
		  //int second=cal.get(Calendar.SECOND);//得到秒
		  //System.out.println("结果："+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
		  date.append(year).append(separator).append(month).append(separator).append(day);
		return date.toString();
	}
	
	
    /**
     * 产生唯一文件名，包括缩略图文件名以T字母开头
     * @return 文件名及缩略图文件名
     */
    public static String[] getNewFileName(){	
    	String fileNames[] =new String[3];
        Random random = new Random();
		int add = random.nextInt(10000000); // 产生随机数10000000以内
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String  ti= sdf.format(new Date());
		String ret = add +ti;
		fileNames[0]=ret;
		fileNames[1]="M"+ret;
		fileNames[2]="T"+ret;
		return fileNames;
    }
	/**
	 * 验证文件是否允许上传
	 * 首先检查文件后缀是否有效，通过之后则检查文件编码是否正确
	 *
	 * @param file 文件
	 * @param types 允许上传的文件类型
	 * @param fileExt 文件后缀
	 * @return  map.get(0)= true or false ;map.get(1)= 文件大小
	 */
	public static Map<Integer,Object> validateFile(File file,String types[] ,String fileExt){
		//默认判断结果为false
		boolean result = false;
		double size = 0;
		for(String type:types){
			//判断后缀名是否允许上传
			if(type.equals(fileExt)){
				//判断文件编码是否正确
				result = validateTypeHead(file);
				//如果正确则判断文件大小是否超过限制
				if(result){
					FileInputStream  fileIS=null;
					try{
						//取得文件流
						fileIS = new FileInputStream(file);
						//取得文件大小
						size = fileIS.available();
						size = Math.round(size/1024*100)*.01;
						//当文件小于2000kb时允许上传 否则不允许
						if(size > 0 && size<2000){
							result = true;
						}else{
							size = 0;
							result = false;
						}
					}catch(Exception e){
						result =false;
					}finally{
						if(fileIS !=null){
							try{
								fileIS.close();
							}catch(Exception e){}
						}
					}
				}
				break;
			}
		}
		Map<Integer,Object> resultMap = new HashMap<Integer,Object>();
		resultMap.put(0, result);
		resultMap.put(1, size);
		return resultMap;
	}
	
	/**
	 * 检查文件头(检查文件编码)
	 * @param file
	 * @return
	 */
	public static boolean validateTypeHead(File file) {   
        if (file == null) {   
            return false;   
        }   
        try {   
            byte[] imgContent = FileUtils.readFileToByteArray(file);   
            int len = imgContent.length;   
            byte n1 = imgContent[len - 2];   
            byte n2 = imgContent[len - 1];   
            byte b0 = imgContent[0];   
            byte b1 = imgContent[1];   
            byte b2 = imgContent[2];   
            byte b3 = imgContent[3];   
            byte b4 = imgContent[4];   
            byte b5 = imgContent[5];   
            byte b6 = imgContent[6];   
            byte b7 = imgContent[7];   
            byte b8 = imgContent[8];   
            byte b9 = imgContent[9];   
               
            //GIF(G I F 8 7 a)   
            if (b0 == (byte)'G' && b1 == (byte)'I' && b2 == (byte)'F' && b3 == (byte)'8' && b4 == (byte)'7' && b5 == (byte)'a') {   
                return true;   
            //GIF(G I F 8 9 a)   
            } else if (b0 == (byte)'G' && b1 == (byte)'I' && b2 == (byte)'F' && b3 == (byte)'8' && b4 == (byte)'9' && b5 == (byte)'a') {   
                return true;   
            //PNG(89 P N G 0D 0A 1A)   
            }else if (b0 == -119 && b1 == (byte)'P' && b2 == (byte)'N' && b3 == (byte)'G' && b4 == 13 && b5 == 10 && b6 == 26) {   
                return true;   
            //JPG JPEG(FF D8 --- FF D9)   
            } else if (b0 == -1 && b1 == -40 && n1 == -1 && n2 == -39){   
                return true;   
            } else if (b6 == (byte)'J' && b7 == (byte)'F' && b8 == (byte)'I' && b9 == (byte)'F'){   
                return true;   
            } else if (b6 == (byte)'E' && b7 == (byte)'x' && b8 == (byte)'i' && b9 == (byte)'f'){   
                return true;   
            //BMP(B M)   
            } else if (b0 == (byte)'B' && b1 == (byte)'M') {   
                return true;   
            }else {   
                return false;   
            }   
        } catch (ArrayIndexOutOfBoundsException e) {   
            return false;   
        } catch (IOException e) {   
            return false;   
        }   
    }  
	
	/**
	 * 取得文件名及文件后缀名 [0]为文件名  [1]为文件后缀
	 * @param fileName
	 * @return
	 */
	public static String[] splitFileName(String fileName){
		String fileAndExt[] ={fileName.substring(0,fileName.lastIndexOf(".")),
				fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase()};
		return fileAndExt;
	}
	
	public static String getWebRoot() {
		String classPath = CommonUtil.class.getResource("/").getPath().substring(1);
		return classPath.substring(0,classPath.indexOf("/WEB-INF/classes/"));
	}
	public static String getClassPath() {
		return CommonUtil.class.getResource("/").getPath().substring(1);
	}
	
}
