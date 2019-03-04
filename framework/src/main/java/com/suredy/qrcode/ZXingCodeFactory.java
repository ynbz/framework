package com.suredy.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 条码类（一维码，二维码）
 * 
 * @author VIVID.G
 * @since 2016-5-24
 * @version v0.1
 */
public abstract class ZXingCodeFactory {

	protected BarcodeFormat format = null;

	public BufferedImage toBufferedImage(BitMatrix matrix, Integer frontColor, Integer backgroundColor) {
		if (matrix == null)
			throw new IllegalArgumentException("BitMatrix is required.");

		if (frontColor == null)
			frontColor = MatrixToImageConfig.BLACK;

		if (backgroundColor == null)
			backgroundColor = MatrixToImageConfig.WHITE;

		MatrixToImageConfig config = new MatrixToImageConfig(frontColor, backgroundColor);

		return MatrixToImageWriter.toBufferedImage(matrix, config);
	}

	public BufferedImage toBufferedImage(BitMatrix matrix) {
		return this.toBufferedImage(matrix, null, null);
	}

	public void writeToFile(BitMatrix matrix, ImgFileFormat format, String filePath, Integer frontColor, Integer backgroundColor) throws IOException {
		if (matrix == null)
			throw new IllegalArgumentException("BitMatrix is required.");

		if (format == null)
			throw new IllegalArgumentException("ImgFileFormat is required.");

		if (StringUtils.isBlank(filePath))
			throw new IllegalArgumentException("A file path is required.");

		if (frontColor == null)
			frontColor = MatrixToImageConfig.BLACK;

		if (backgroundColor == null)
			backgroundColor = MatrixToImageConfig.WHITE;

		MatrixToImageConfig config = new MatrixToImageConfig(frontColor, backgroundColor);

		File f = new File(filePath);
		if (!f.isFile())
			f.createNewFile();

		MatrixToImageWriter.writeToPath(matrix, format.name(), f.toPath(), config);
	}

	public void writeToFile(BitMatrix matrix, ImgFileFormat format, String filePath) throws IOException {
		this.writeToFile(matrix, format, filePath, null, null);
	}

	public void writeToStream(BitMatrix matrix, ImgFileFormat format, OutputStream stream, Integer frontColor, Integer backgroundColor) throws IOException {
		if (matrix == null)
			throw new IllegalArgumentException("BitMatrix is required.");

		if (format == null)
			throw new IllegalArgumentException("ImgFileFormat is required.");

		if (stream == null)
			throw new IllegalArgumentException("OutputStream is required.");

		if (frontColor == null)
			frontColor = MatrixToImageConfig.BLACK;

		if (backgroundColor == null)
			backgroundColor = MatrixToImageConfig.WHITE;

		MatrixToImageConfig config = new MatrixToImageConfig(frontColor, backgroundColor);

		MatrixToImageWriter.writeToStream(matrix, format.name(), stream, config);
	}

	public void writeToStream(BitMatrix matrix, ImgFileFormat format, OutputStream stream) throws IOException {
		this.writeToStream(matrix, format, stream, null, null);
	}

	public BitMatrix getBitMatrix(String utf8Content, Integer frameLength, Map<EncodeHintType, ?> hints) throws WriterException {
		if (StringUtils.isBlank(utf8Content))
			throw new IllegalArgumentException("A UTF-8 encoded content string is required.");

		if (this.format == null)
			throw new IllegalArgumentException("A BarcodeFormat is required.");

		if (frameLength == null || frameLength <= 0)
			frameLength = 185;

		hints = hints == null || hints.isEmpty() ? null : hints;

		Writer writer = new MultiFormatWriter();

		BitMatrix matrix = writer.encode(utf8Content, format, frameLength, frameLength, hints);

		return matrix;
	}

}
