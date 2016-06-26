package org.springframe.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * ͼ����֤�� ��֤���࣬��Ҫ��ɼ��ֲ�ͬ���͵���֤�� ��һ�֣�����֤�룬4λ������� �ڶ��֣�Ӣ���ַ�����ֵ���֤��
 * �����֣�����·��Ʊϵͳһ�����֤�룬½+��=10
 * 
 * @author ��Ҽ��
 * 
 */
public class VerificationCodeUtil {

	public ByteArrayInputStream getImage() {
		return image;
	}

	public void setImage(ByteArrayInputStream image) {
		this.image = image;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	private String str; // ��֤��
	private ByteArrayInputStream image; // ͼ��
	private static final int WIDTH = 80; // ͼƬ���
	private static final int HEIGHT = 20; // ͼƬ�߶�

	public static void main(String[] args) {
		VerificationCodeUtil Vcu = VerificationCodeUtil.Instance();
		System.out.println(Vcu.getVerificationCodeUtilValues());
	}

	/**
	 * ���ܣ���ȡ��֤����ַ�ֵ
	 */
	public String getVerificationCodeUtilValues() {
		return this.getStr();
	}

	/**
	 * ���ܣ���ȡһ����֤���ʵ��
	 * 
	 * @return
	 */
	public static VerificationCodeUtil Instance() {
		return new VerificationCodeUtil();
	}

	private VerificationCodeUtil() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		int randomNum = new Random().nextInt(3);
		if (randomNum == 0) {
			InitNumCode(image);
		} else if (randomNum == 1) {
			InitLetterAndNumCode(image);
		} else {
			InitChinesePlshNumCode(image);
		}
	}

	/**
	 * ����1������������֤�������
	 * 
	 * @param image
	 */
	public void InitNumCode(BufferedImage image) {
		Random random = new Random(); // ���������
		Graphics g = InitImage(image, random);

		String sRand = "";

		for (int i = 0; i < 4; i++) {
			String ran = String.valueOf(random.nextInt(10));
			sRand += ran;

			// ����֤����ʾ��ͼ����
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));

			// ���ú����������ɫ��ͬ����������Ϊ����̫�ӽ�,����ֻ��ֱ�����
			g.drawString(ran, 13 * i + 6, 16);
		}
		this.setStr(sRand); // ��ֵ��֤��
		// ͼ����Ч �ͷ���Դ
		g.dispose();
		this.setImage(drawImage(image));
	}

	/**
	 * ����2��������ĸ��������֤�������
	 * 
	 * @param image
	 */
	public void InitLetterAndNumCode(BufferedImage image) {
		Random random = new Random(); // ���������
		Graphics g = InitImage(image, random);

		String[] Letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String tempRand = "";
			if (random.nextBoolean()) {
				tempRand = String.valueOf(random.nextInt(10));
			} else {
				tempRand = Letter[random.nextInt(25)];
				if (random.nextBoolean()) {
					// ������ĸ��Сд
					tempRand = tempRand.toLowerCase();
				}
			}
			sRand += tempRand;
			g.setColor(new Color(20 + random.nextInt(10), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			
			g.drawString(tempRand, 13 * i + 6, 16);
		}

		this.setStr(sRand);// ��ֵ��֤��
		// ͼ����Ч���ͷ���Դ
		g.dispose();
		this.setImage(drawImage(image));
	}

	/**
	 * ����3�����������㷨��� ��:½+��=10
	 * 
	 * @param image
	 */
	public void InitChinesePlshNumCode(BufferedImage image) {
		Random random = new Random();  // 生成随机类  
		Graphics g = InitImage(image, random);
		String[] cn = { "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾" };

		int x = random.nextInt(10) + 1;
		int y = random.nextInt(30);
		this.setStr(String.valueOf(y));
		g.setFont(new Font("楷体", Font.PLAIN, 16)); // 设定字体 
		// ����֤����ʾ��ͼ����
		g.setColor(new Color(20 + random.nextInt(10), 20 + random.nextInt(110),
				20 + random.nextInt(110)));
		// ���ú����������ɫ��ͬ����������Ϊ����̫�ӽ�,����ֻ��ֱ�����
		g.drawString(cn[x - 1], 1 * 1 + 6, 16);
		g.drawString("+", 22, 16);
		g.drawString("？", 35, 16);
		g.drawString("=", 48, 16);

		g.drawString(String.valueOf(x + y), 61, 16);
		// 图象生效  
		g.dispose();
		this.setImage(drawImage(image));
	}

	// ��ʼ��ͼƬ
	public Graphics InitImage(BufferedImage image, Random random) {

		Graphics g = image.getGraphics();  // 获取图形上下文  
		g.setColor(getRandomColor(200, 250)); //设定背景色
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setFont(new Font("Times New Roman", Font.PLAIN, 18)); //设定字体
		g.setColor(getRandomColor(160, 200));

		// 随机产生165条干扰线，使图象中的认证码不易被其它程序探测到
		for (int i = 0; i < 165; i++) {
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			int x1 = random.nextInt(12);
			int y1 = random.nextInt(12);
			g.drawLine(x, x + x1, y, y + y1);
		}

		return g;
	}

	public ByteArrayInputStream drawImage(BufferedImage image) {
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			ImageOutputStream imageOut = ImageIO
					.createImageOutputStream(outputStream);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close(); // �ر���
			inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		} catch (Exception e) {
			System.out.println("验证码图片产生出现错误：" + e.toString());
		}
		return inputStream;
	}

	/**
	 * 功能：给定范围获得随机颜色 
	 */
	private Color getRandomColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}

		if (bc > 255) {
			bc = 255;
		}

		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);

		return new Color(r, g, b);
	}
}
