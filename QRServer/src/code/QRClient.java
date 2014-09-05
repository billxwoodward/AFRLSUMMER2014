package code;

import java.awt.Desktop;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import panels.ClientPanel;

import common.IconDB;
import common.Util;

public class QRClient implements Runnable {
	public static final ArrayList<QRClient> list = new ArrayList<QRClient>();

	public String fileName = "<???>";
	public double fileSize;
	public double curByte = 0;
	private Socket s;
	File f;

	public QRClient(Socket s) {
		this.s = s;
		list.add(this);
		ClientPanel.update();
	}

	public ImageIcon getFileIcon() {
		return IconDB.getIcon(Util.getExtention(fileName));
	}

	public ImageIcon getStateIcon() {
		if (s.isClosed() || s.isInputShutdown())
			return (curByte == fileSize) ? IconDB.getIcon("gray") : IconDB
					.getIcon("red");
		return IconDB.getIcon("green");
	}

	public String getAddress() {
		return s.getRemoteSocketAddress().toString();
	}

	@Override
	public void run() {

		try {
			DataInputStream dis = new DataInputStream(new HexInputStream(
					s.getInputStream()));

			fileSize = dis.readLong(); // File Size
			fileName = dis.readUTF(); // File Name

			if (fileSize > 5000000) {
				System.out.println("Bad File Size");
				throw new IOException("Invalid File Size");
			}
			System.out.println("Filesize is: " + fileSize);
			f = File.createTempFile("QR_CLIENT", fileName);
			FileOutputStream fos = new FileOutputStream(f);

			byte buf[] = new byte[500];
			int count = 0;
			while ((count = dis.read(buf)) > 0) {
				fos.write(buf, 0, count);
				curByte += count;
				System.out.println("curByte is: " + curByte);
				ClientPanel.update();
			}
			fos.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERRRRRRRRRRRRRORRR");
			fileName = "Error";
		}

		ClientPanel.update();
		System.out.println("Done");
	}

	public void tryOpen() {
		try {
			Desktop.getDesktop().open(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
