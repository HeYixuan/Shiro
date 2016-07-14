/**
 * 
 */
package org.springframe.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframe.constant.GlobalConstant;
import org.springframe.utils.ImageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 340819
 *
 */
@Controller
//@RequestMapping("/auth")
public class LoginController {

	private static final Logger logger = Logger.getLogger(LoginController.class);

	//private static String resultPageURL = InternalResourceViewResolver.FORWARD_URL_PREFIX + "login.html";

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String login() {
		Subject currentUser = SecurityUtils.getSubject();
		if( currentUser.isAuthenticated() || currentUser.isRemembered() ){
			return "redirect:/test";
		} 
		return "Login";
	}
	
	
	@RequestMapping(value="/doLogin",method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String username, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		return "Login";
	}

	@RequestMapping("/test")
	public String test(){
		return "/test/test";
	}

	/**
	 * 生成验证码方法
	 * 
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/kaptcha")
	public void kaptcha(HttpServletResponse response, HttpSession session) throws IOException {
		// 禁止图像缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/png");
		// 利用图片工具生成图片
		// 第一个参数是生成的验证码，第二个参数是生成的图片
		Object[] objs = ImageUtils.createImage();
		// 先移除session 再将验证码存入Session
		session.removeAttribute(GlobalConstant.KEY_CAPTCHA);
		session.setAttribute(GlobalConstant.KEY_CAPTCHA, objs[0]);
		System.err.println("验证码为:"+objs[0]);
		// 将图片输出给浏览器
		BufferedImage image = (BufferedImage) objs[1];
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ImageIO.write(image, "png", os);

	}
	
	
/*	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String login(@RequestParam String username, String password, HttpServletRequest request) {
		// 获取HttpSession中的验证码
		String imageCode = (String) request.getSession().getAttribute(GlobalConstant.KEY_CAPTCHA);
		// 获取用户请求表单中输入的验证码
		String submitCode = WebUtils.getCleanParam(request, GlobalConstant.KEY_CAPTCHA);
		System.out.println("用户[" + username + "]登录时输入的验证码为[" + submitCode + "],HttpSession中的验证码为[" + imageCode + "]");
		if (StringUtils.isEmpty(submitCode) || !StringUtils.equalsIgnoreCase(imageCode, submitCode)) {
			request.setAttribute("message_login", "验证码不正确");
			return resultPageURL;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		logger.info("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			// 验证是否登录成功
			if (currentUser.isAuthenticated()) {
				logger.info("对用户[" + username + "]进行登录验证..验证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
				resultPageURL = "/";
				return resultPageURL;
			} else {
				token.clear();
				return resultPageURL;
			}
		} catch (UnknownAccountException uae) {
			logger.error("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
		} catch (ExpiredCredentialsException ece) {  
	        logger.error("对用户[" + username + "]进行登录验证..验证未通过,帐号已过期");
	    } catch (IncorrectCredentialsException ice) {
			logger.error("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
		} catch (UnauthorizedException ue) {  
			logger.error("对用户[" + username + "]进行登录验证..验证未通过,没有相应的授权");
	    } catch (LockedAccountException lae) {
			logger.error("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
		} catch (DisabledAccountException dae) {  
	        logger.error("对用户[" + username + "]进行登录验证..验证未通过,帐号已被禁用");
	    } catch (ExcessiveAttemptsException eae) {
			logger.error("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			System.out.println("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			logger.error("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下", ae);
			ae.printStackTrace();
			request.setAttribute("message_login", "用户名或密码不正确");
		}
		return resultPageURL;
	}*/
	
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		logger.info("-------------注销---------------");
		//清除session中的验证码
		HttpSession session = (HttpSession) request.getSession();
		session.removeAttribute(GlobalConstant.KEY_CAPTCHA);
		SecurityUtils.getSubject().logout();
		return "redirect:login.html";
	}
}
