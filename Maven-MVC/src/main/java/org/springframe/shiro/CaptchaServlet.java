package org.springframe.shiro;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframe.utils.ImageUtils;
/**
 * 这个以后可以打包成自己的jar包了
 * @author HeYixuan
 *
 */
public class CaptchaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3449934280193405003L;
	public static final String KEY_CAPTCHA = "captcha";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setContentType("image/png");

		try {
			HttpSession session = req.getSession();
			// 利用图片工具生成图片
			// 第一个参数是生成的验证码，第二个参数是生成的图片
			Object[] objs = ImageUtils.createImage();
			// 将验证码存入Session
			session.setAttribute(KEY_CAPTCHA, objs[0]);
			System.err.println("验证码为:"+objs[0]);
			// 将图片输出给浏览器
			BufferedImage image = (BufferedImage) objs[1];
			response.setContentType("image/png");
			OutputStream os = response.getOutputStream();
			ImageIO.write(image, "png", os);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
