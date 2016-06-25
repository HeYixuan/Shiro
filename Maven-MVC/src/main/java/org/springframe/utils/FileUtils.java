package org.springframe.utils;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 
 *  类            名:      FileUtils
 *  修 改 记 录:     // 修改历史记录，包括修改日期、修改者及修改内容
 *  版 权 所 有:     版权所有(C)2010-2014
 *  上传文件工具类
 *  @version      V1.0
 *  @date              2016年6月9日
 *  @author        何壹轩
 *
 */
public class FileUtils {
	
	private static final Logger logger = Logger.getLogger(FileUtils.class);
	
	
	/**
	 * 单个文件上传
	 * @param request
	 * @param file
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String FileUpload(HttpServletRequest request,MultipartFile file,String path) throws Exception {
		String fileName = null;
		//判断文件是否为空
		if (!file.isEmpty()) {
			//获取文件名
			String originalFilename = file.getOriginalFilename();
			/** 根据真实路径创建目录  **/
			File logoSaveFile = new File(path);
	        if (!logoSaveFile.exists()){
	        	logoSaveFile.mkdirs();
	        }
	        if (file.getSize() > 102400*10) {
	        	logger.error("-----文件对象太大,上传失败!-----");
			}else{
				fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
				File newFile = new File(path,fileName);
				file.transferTo(newFile);
				logger.info("文件根路径："+path+fileName);
				return fileName;
			}
		}else{
			logger.error("-----文件对象为空,上传失败!-----");
		}
		return fileName;
	}

	
	/**
	 * 多文件上传
	 * @param request
	 * @param files
	 * @param path
	 * @return 新文件名
	 * @throws Exception
	 */
	public static String FileUpload(HttpServletRequest request, MultipartFile[] files, String path) throws Exception {
		String fileName = null;
		String originalFilename = null;    //文件原名
		File newFile = null;
		for(MultipartFile file : files){  
            if(file.isEmpty()){  
                System.out.println("文件未上传");  
            }else{
            	//获取文件名
    			originalFilename = file.getOriginalFilename();
    			File logoSaveFile = new File(path);
    	        if (!logoSaveFile.exists()){
    	        	logoSaveFile.mkdirs();
    	        }
    	        
    	        if (file.getSize() > 102400*10) {
    	        	logger.error("-----文件对象太大,上传失败!-----");
    			}else{
    				fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
    				newFile = new File(path,fileName);
    				file.transferTo(newFile);
    				logger.info("文件根路径："+path+fileName);
    				return fileName;
    			}
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
                //FileUtils.copyInputStreamToFile();  
            }
        }
		return null;
	}
	
	
	
	public static String FileUpload(HttpServletRequest request,String path) throws Exception{
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> it = multiRequest.getFileNames();  
            while(it.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int currentTime = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(it.next());  
                if(file != null){
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();
                    //文件路径不存在就创建
                    File logoSaveFile = new File(path);
        	        if (!logoSaveFile.exists()){
        	        	logoSaveFile.mkdirs();
        	        }
        	        //重命名上传后的文件名  
                    String fileName = UUID.randomUUID() + myFileName.substring(myFileName.lastIndexOf("."));
                    //定义上传路径  
                    File localFile = new File(path,fileName);  
                    file.transferTo(localFile);  
                    return fileName;  
                }  
                //记录上传该文件后的时间  
                int lastTime = (int) System.currentTimeMillis();  
                System.out.println(lastTime - currentTime);  
            }  
              
        }
		return null;
	}

}
