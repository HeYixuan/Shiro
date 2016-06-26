package org.springframe.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * 发送邮件工具类
 * 
 * @author 何壹轩
 */

public class EmailUtils {

	private static final Logger logger = Logger.getLogger(EmailUtils.class);

	public static void SendMail(String Host, final String From, final String Password, String To, String CONTENT) {

		try {
			
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", Host);
			// 必须有这项设置
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.debug", "true");

			// Session管理与stmp的链接信息
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(From, Password);
				}
			});

			// 创建邮件对象
			MimeMessage msg = new MimeMessage(session);
			// MimeMultipart mp = new MimeMultipart();
			
			logger.info("******************发送邮件开始***************");
			msg.setFrom(new InternetAddress(From));
			msg.addRecipients(Message.RecipientType.TO, To);
			msg.setSubject("测试--群发", "UTF-8");
			msg.setSentDate(new Date());
			// 设置正文 发送HTML格式
			msg.setContent(CONTENT, "text/html;charset=UTF-8");
			// 使用静态的方法发送信息
			// Transport.send(msg,new InternetAddress[]{new
			// InternetAddress("lianyumoshen@sina.com")});
			// 发送邮件
			Transport.send(msg);
		} catch (MessagingException e) {
			logger.error("*********邮件发送失败*********************", e);
			e.printStackTrace();
		}

	}

	public static void sys_out() {

		String host = "smtp.126.com";
		String From = "15517551511@126.com";
		String password = "HYX15517551511";
		String to = "1297044119@qq.com";
		String s = MD5_String.randomString(60);

		StringBuilder sb = new StringBuilder();
		sb.append("<p>你好!</p> ");
		sb.append("   感谢你注册部落平台。 <br> ");
		sb.append("   请点击以下链接激活帐号：<a href=\"#\">" + s + "</a>。 ");
		sb.append("<p>如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏激活您的账号。 （该链接在48小时内有效，48小时后需要重新注册）</p> ");

		SendMail(host, From, password, to, sb.toString());
	}

	public static void main(String[] args) {
		sys_out();
	}
}
