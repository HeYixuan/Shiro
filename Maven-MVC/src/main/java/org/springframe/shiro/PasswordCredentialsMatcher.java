package org.springframe.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframe.utils.MD5Utils;

/** 
 * 自定义 密码验证类 
 * @author HeYixuan
 * 
 */ 
public class PasswordCredentialsMatcher extends SimpleCredentialsMatcher {
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		/*UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Object tokenCredentials = encrypt(String.valueOf(token.getPassword()));  
        Object accountCredentials = getCredentials(info);*/
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false 
		
		return super.doCredentialsMatch(authcToken, info);
	}
	
	/**
	 * 密码加密  
	 * @param data
	 * @return
	 */
    protected String Encry(String data) {  
        String salts = MD5Utils.encrypt(data);//这里可以选择自己的密码验证方式 比如 md5或者sha256等  
        return salts;  
    }
}
