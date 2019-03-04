package com.suredy.tools.checkcode;

import java.awt.Font;

/**
 * TTF字体文件
 * 
 * @author VIVID.G
 * @since 2015-5-12
 * @version v0.1
 */
public class ImgFontByte {

	public Font getFont(int fontHeight) {
		try {
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, ImgFontByte.class.getResourceAsStream("font.ttf"));
			return baseFont.deriveFont(Font.PLAIN, fontHeight);
		} catch (Exception e) {
			return new Font("Arial", Font.PLAIN, fontHeight);
		}
	}

}
