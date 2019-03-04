package com.suredy.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SuredyFileTool {

	/**
	 * 计算大文件 md5获取getMD5(); SHA256获取getSha1() CRC32获取 getCRC32()
	 */
	protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	public static MessageDigest messagedigest = null;

	/**
	 * 对一个文件获取md5值
	 * 
	 * @return md5串
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMd5Code(File file) throws IOException, NoSuchAlgorithmException {
		messagedigest = MessageDigest.getInstance("MD5");
		long size = 8 * 1024 * 1024;
		if (file.length() <= size) {
			size = file.length();
		}
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, size);
		messagedigest.update(byteBuffer);
		ch.close();
		in.close();
		return bufferToHex(messagedigest.digest());

	}

	/***
	 * 计算SHA码
	 * 
	 * @return String SHA256校验码
	 * @throws NoSuchAlgorithmException
	 */
	public static String getShaCode(File file) throws  IOException, NoSuchAlgorithmException {
		messagedigest = MessageDigest.getInstance("SHA-256");
		long size = 8 * 1024 * 1024;
		if (file.length() <= size) {
			size = file.length();
		}
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, size);
		messagedigest.update(byteBuffer);
		ch.close();
		in.close();
		return bufferToHex(messagedigest.digest());
	}



	public static String toMd5(String s) {
		messagedigest.update(s.getBytes());
		return bufferToHex(messagedigest.digest());

	}

	/**
	 * @Description 计算二进制数据
	 * @return String
	 */
	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	
}
