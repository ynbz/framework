package com.suredy.security.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherService {

	private final static byte[] DEFAULT_SEED = "com.suredy".getBytes();

	private final static String ALGORITHM_AES = "AES";

	private static String Bytes2Hex(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	private static byte[] Hex2Bytes(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 对字符串加密
	 * 
	 * @param plainText
	 * @return encrytedText
	 * @throws Exception 
	 */
	public static String AESEncrypt(String plainText) throws Exception {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM_AES);
			kgen.init(128, new SecureRandom(DEFAULT_SEED));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM_AES);
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES);// 创建密码器
			byte[] byteContent = plainText.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent); // 加密
			return Bytes2Hex(result); 
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (NoSuchPaddingException e) {
			throw e;
		} catch (InvalidKeyException e) {
			throw e;
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (BadPaddingException e) {
			throw e;
		}
	}

	/**
	 * 对字符串解密
	 * 
	 * @param encrytedText
	 * @return decryptedText
	 * @throws Exception 
	 */
	public static String AESDecrypt(String encrytedText) throws Exception {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM_AES);
			kgen.init(128, new SecureRandom(DEFAULT_SEED));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM_AES);
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(Hex2Bytes(encrytedText)); //解密
			return new String(result); 
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (NoSuchPaddingException e) {
			throw e;
		} catch (InvalidKeyException e) {
			throw e;
		} catch (IllegalBlockSizeException e) {
			throw e;
		} catch (BadPaddingException e) {
			throw e;
		}
	}

	/** 
	 */
	public static void main(String[] args) throws Exception {
		String content = "这里是个飞天猪头的明文";
		// 加密
		System.out.println("明文：" + content);
		String encryptResult = AESEncrypt(content);
		System.out.println("加密后：" + encryptResult);
		// 解密
		String decryptResult = AESDecrypt(encryptResult);
		System.out.println("解密后：" + decryptResult);
	}

}
