package code;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import common.Util;

public class HexInputStream extends FilterInputStream {

	protected HexInputStream(InputStream in) {
		super(in);
	}

	public int read() throws IOException {

		byte[] hex = new byte[2];
		int[] iHex = new int[2];
		iHex[0] = in.read();
		iHex[1] = in.read();

		if (iHex[0] < 0 || iHex[1] < 0) {
			System.out.println("EOF");
			return -1;
		}
		hex[0] = (byte) iHex[0];
		hex[1] = (byte) iHex[1];

		String hexVal = Util.getString(hex);
		return Integer.parseInt(hexVal, 16);
	}

	public int read(byte[] ba) throws IOException {
		for (int i = 0; i < ba.length; i++) {
			int ret = read();
			if (ret < 0) {
				System.out.println("partial read byte[] returned: " + i);
				return i;
			}
			ba[i] = (byte) ret;
		}

		return ba.length;
	}

	public int read(byte[] ba, int offset, int len) throws IOException {
		int bytesRead = 0;
		for (bytesRead = 0; bytesRead < len; bytesRead++) {
			int ret = read();
			if (ret < 0) {
				System.out.println("partial read byte[],o,l returned: "
						+ bytesRead);
				return bytesRead;
			}
			ba[offset + bytesRead] = (byte) ret;
		}
		return bytesRead;

	}

}
