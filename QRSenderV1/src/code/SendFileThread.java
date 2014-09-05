package code;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import common.Util;

public class SendFileThread implements Runnable {
	File f;

	public SendFileThread(File f) {
		this.f = f;
	}

	@Override
	public void run() {
		int CHUNKSIZE = 500;
		DataInputStream dis;
		try {
			dis = new DataInputStream(new FileInputStream(f));

			ByteArrayOutputStream baos = new ByteArrayOutputStream(CHUNKSIZE);
			DataOutputStream dos = new DataOutputStream(baos);
			dos.writeLong(f.length());
			dos.writeUTF(f.getName());

			// OutputStream os = ClientComm.s.getOutputStream();
			TxtOutputStream tos = new TxtOutputStream(1000, 5000);

			dos.flush();

			byte[] ba = baos.toByteArray();

			System.out.println("HEX: " + Util.getHexString(ba));

			System.out.println("Calling Write To");

			for (int i = 0; i < ba.length; i++) {
				System.out.println("Out: " + (byte) ba[i]);
				tos.write(ba[i]);
			}

			// baos.writeTo(hos);
			System.out.println("Done Calling Write To");

			byte[] buf = new byte[500];
			while (dis.available() > 0) {
				dis.read(buf);
				tos.write(buf);
			}
			tos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
