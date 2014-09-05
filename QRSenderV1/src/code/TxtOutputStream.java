package code;

import java.io.IOException;
import java.io.OutputStream;

import common.Util;

public class TxtOutputStream extends OutputStream {

	public int delay;
	byte seq = 0;
	byte[] buf;
	int curPos = 0;
	int size;

	public TxtOutputStream(int size, int delay) {
		buf = new byte[size];
		this.delay = delay;
		this.size = size;

	}

	@Override
	public void write(int b) throws IOException {
		buf[curPos] = (byte) b;
		curPos++;
		System.out.println("Writing a Byte: " + b);
		if (curPos >= size) {
			byte[] ba = new byte[size + 1];
			ba[0] = seq;

			for (int i = 0; i < buf.length; i++)
				ba[i + 1] = buf[i];

			seq++;
			// System.out.println(Util.getHexString(ba, ba.length, ","));

			System.out.println("---------------");
			for (int i = 0; i < 10; i++)
				System.out.format("Data[%d] = %d", i, (int) buf[i]);

			QRCode.create(Util.getHexString(ba, ba.length, ""));

			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			curPos = 0;
		}

	}

	@Override
	public void close() {
		byte[] ba = new byte[curPos + 1];

		seq = 127;
		ba[0] = seq;

		for (int i = 0; i < curPos; i++)
			ba[i + 1] = buf[i];

		QRCode.create(Util.getHexString(ba, ba.length, ""));
		seq = 0;
		curPos = 0;
	}

}
