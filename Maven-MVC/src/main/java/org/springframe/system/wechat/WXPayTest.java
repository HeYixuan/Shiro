package org.springframe.system.wechat;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class WXPayTest {
	public static void main(String[] args) {
		System.out.println(">>>模拟微信支付<<<");  
		//微信api提供的参数  
        String appid = "wx8b76660d825e1b42";
        String mch_id = "10000100";  
        String device_info = "1000";  
        String body = "test";  
        String nonce_str = "ibuaiVcKdpRxkhJA";  
        
        //WXPay.initSDKConfiguration(key, appID, mchID, sdbMchID, certLocalPath, certPassword);
        Map<String,Object> parameters = new TreeMap<String,Object>(); 
        parameters.put("appid", appid);  
        parameters.put("mch_id", mch_id);  
        parameters.put("device_info", device_info);  
        parameters.put("body", body);  
        parameters.put("nonce_str", nonce_str);  
          
        /*String weixinApiSign = "9A0A8659F005D6984697E2CA0A9CF3B7";  
        System.out.println("微信的签名是：" + weixinApiSign);  */
        String mySign = Signature.getSign(parameters);
	}

}
