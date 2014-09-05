package code;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.ImageIcon;

import common.IconDB;

import panels.LocalServerPanel;

public class ServerThread implements Runnable {
	public static ServerSocket  ss;
	public static int port = 1234;
	public static String address = "";
	public static String loopback = "(Loopback)";
	public static boolean foundReal = false;
	
	static
	{
		Enumeration<NetworkInterface> e = null;
		try {
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			address = "Could not obtain addresses";
		}
		
		while(null != e && e.hasMoreElements())
		{
			NetworkInterface n = e.nextElement();
			Enumeration<InetAddress> ee = n.getInetAddresses();
			while (ee.hasMoreElements())
			{
				InetAddress i = ee.nextElement();
				if (!i.isLoopbackAddress())
				{
					foundReal = true;
					address += "|" + i.getHostAddress();
				}
				else
					loopback += "|" + i.getHostAddress();
			}
		}
		
		if (!foundReal)
			address = loopback;
	}
	
	@Override
	public void run() {
	
			try {
				ss = new ServerSocket(port);
			} catch (IOException e) {
				System.out.println("Cant Start Socket Server on port " + port);
			}
			
			
			LocalServerPanel.MyTableModel.serverStateUpdated();
			while (true)
			{
				Socket clientSocket;
				try {
					clientSocket = ss.accept();
				} catch (IOException e) {
					System.out.println("Error Accepting client");
					continue;
				}
				
				Thread t = new Thread(new QRClient(clientSocket));
				t.start();
			}
	}
	
	public static void startServer() {
		Thread t = new Thread(new ServerThread());
		t.start();
	}

	public static void closeServer() {
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isRunning() {
		if (null == ss)
			return false;
		return ss.isBound() && !ss.isClosed();
	}

	public static ImageIcon getIcon() {
		return isRunning() ? IconDB.getIcon("green") : IconDB.getIcon("gray"); 
	}

}
