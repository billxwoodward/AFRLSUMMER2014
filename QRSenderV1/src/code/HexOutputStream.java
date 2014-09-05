package code;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import common.Util;

public class HexOutputStream extends FilterOutputStream {

	public HexOutputStream(OutputStream out) {
		super(out);
	}

	public void write(int b) throws IOException {
		String hexStr = Util.byteToHex((byte) b);

		byte b0 = hexStr.getBytes()[0];
		byte b1 = hexStr.getBytes()[1];
		out.write(b0);
		out.write(b1);
	}

}
