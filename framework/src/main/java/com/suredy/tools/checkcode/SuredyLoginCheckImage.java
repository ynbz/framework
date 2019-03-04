package com.suredy.tools.checkcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * 水滴登录验证码
 * 
 * @author VIVID.G
 * @since 2015-5-11
 * @version v0.1
 */
@Component
public class SuredyLoginCheckImage {

	// 字符集
	private final String CHAR_BUFFER = "ABCDEFGHJKLMNPQRSTUVWXY2345678";
	// 图片的宽度。
	private int WIDTH = 140;
	// 图片的高度。
	private int HEIGHT = 45;
	// 验证码字符个数
	private int CODE_COUNT = 5;
	// 验证码干扰线数
	private int LINE_COUNT = 50;

	public BufferedImage createImg(String code) {
		return this.createImg(code, this.WIDTH, this.HEIGHT);
	}

	public BufferedImage createImg(String code, int width, int height) {
		int x = 0, fontHeight = 0, codeY = 0;
		int red = 0, green = 0, blue = 0;

		x = width / (this.CODE_COUNT + 2);// 每个字符的宽度
		fontHeight = height - 2;// 字体的高度
		codeY = height - 4;

		// 图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 生成随机数
		Random random = new Random();
		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 创建字体
		ImgFontByte imgFont = new ImgFontByte();
		Font font = imgFont.getFont(fontHeight);
		g.setFont(font);

		for (int i = 0; i < this.LINE_COUNT; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width / 8);
			int ye = ys + random.nextInt(height / 8);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}

		// randomCode记录随机产生的验证码
		// 随机产生codeCount个字符的验证码。
		for (int i = 0; i < this.CODE_COUNT; i++) {
			// 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawString(code.charAt(i) + "", (i + 1) * x, codeY);
		}

		return buffImg;
	}

	public String write(OutputStream os) throws IOException {
		return this.write(os, this.WIDTH, this.HEIGHT);
	}

	public String write(OutputStream os, int width, int height) throws IOException {
		String code = RandomStringUtils.random(this.CODE_COUNT, this.CHAR_BUFFER);
		BufferedImage buf = this.createImg(code, width, height);

		ImageIO.write(buf, "png", os);
		os.close();

		return code;
	}

}
