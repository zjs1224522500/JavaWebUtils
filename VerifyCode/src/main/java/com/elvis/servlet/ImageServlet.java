package com.elvis.servlet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Version:v1.0 (description:  ) Date:2017/10/29 0029  Time:14:49
 */
public class ImageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//初始化图片类型
		BufferedImage bufferedImage = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
		//进行制图
		Graphics graphics = bufferedImage.getGraphics();
		//RGB设置颜色并填充对应图像坐标像素点
		Color color = new Color(200, 150, 255);
		graphics.setColor(color);
		graphics.fillRect(0, 0, 68, 22);
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random random = new Random();
		int length = chars.length, index;

		//循环随机生成验证码
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			index = random.nextInt(length);
			//设置验证码的不同颜色并记录验证码
			graphics.setColor(
					new Color(random.nextInt(88), random.nextInt(188), random.nextInt(255)));
			graphics.drawString(chars[index] + "", (i * 15) + 3, 18);
			stringBuffer.append(chars[index]);
		}
		//将验证码的记录值存储在session中
		req.getSession().setAttribute("picCode", stringBuffer.toString());
		//将生成的图片写入Response的输出流中
		ImageIO.write(bufferedImage, "JPG", resp.getOutputStream());
	}
}
