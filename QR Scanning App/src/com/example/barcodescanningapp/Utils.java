package com.example.barcodescanningapp;
public class Utils {

	public static String byteToHex(byte b) {
		StringBuffer sb = new StringBuffer();
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		int v = b & 0xFF;
		sb.append(hexArray[v >>> 4]);
		sb.append(hexArray[v & 0x0F]);
		return sb.toString();
	}

	public static String getHexString(byte[] b, int len, String delimiter) {
		if (len == 0)
			return "";
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < len - 1; i++)
			result.append(byteToHex(b[i]) + delimiter);
		result.append(byteToHex(b[len - 1]));
		return result.toString();

	}

}
