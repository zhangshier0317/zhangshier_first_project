package com.eastrobot.robotdev.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.eastrobot.robotface.util.MethodDefinition;
import com.eastrobot.robotface.util.ProxyInvoker;
import com.eastrobot.robotface.util.ServiceBinder;

public class AESUtil {

	public static final String T = ServiceBinder.ie() + ProxyInvoker.h() + MethodDefinition.d();
	private static IvParameterSpec IV;

	static {
		byte[] ivBytes = new byte[16];
		for (int i = 1; i <= 16; i++) {
			ivBytes[i - 1] = (byte) (i & 0xFF);
		}
		IV = new IvParameterSpec(ivBytes);
	}

	public static Key generateKey() {
		return new SecretKeySpec(T.substring(0, 16).getBytes(), "AES");
	}

	public static String encrypt(String input) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, generateKey(), IV);
			byte[] result = cipher.doFinal(input.getBytes("UTF-8"));
			return byte2HexStr(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String input) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, generateKey(), IV);
			byte[] result = cipher.doFinal(str2ByteArray(input));
			return new String(result, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}
			sb.append(s.toUpperCase());
		}

		return sb.toString();
	}

	private static byte[] str2ByteArray(String s) {
		int byteArrayLength = s.length() / 2;
		byte[] b = new byte[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16).intValue();
			b[i] = b0;
		}
		return b;
	}

	public static void main(String[] args) {
		String encrypt = encrypt("fred");
		System.out.println(encrypt);
		System.out.println(decrypt("F2F7E0EA2AFEAFED6584B46F0D63C093"));
	}
}