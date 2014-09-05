package common;

public class Util {

	public static String byteToHex(byte b)
	{
		StringBuffer sb = new StringBuffer();
		final char[] hexArray = "0123456789ABCDEF".toCharArray();
		int v = b & 0xFF;
		sb.append(hexArray[v >>> 4]);
		sb.append(hexArray[v & 0x0F]);
		return sb.toString();				
	}

	public static String getHexString(byte[] b, int len,String delimiter) {
		if (len == 0)
			return "";
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < len-1; i++)
			result.append(byteToHex(b[i]) + delimiter);
		result.append(byteToHex(b[len-1]));
		return result.toString();
	}
	
	public static String getString(byte[] value)
	{
		StringBuffer sb = new StringBuffer();
		for (byte b : value)
		{
			if (0 == b)
				break;
			sb.append((char)b);
		}
		return sb.toString();
	}
	
	public static byte[] hexStringToByteArray(String s) {
    	// TODO: some validation here
    	int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
	
	public static String getShortPath(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/")+1);
	}

	public static String getHexString(byte[] ba) {
		return getHexString(ba, ba.length,",");
		
	}
}
