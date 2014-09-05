package code;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.ImageIcon;


public class ClientComm {
	public static String address = "127.0.0.1";
	public static Integer port = 1234;
	public static Socket s;
	private static boolean isConnected = false;
	
	public static void connect()
	{
		InetSocketAddress ia = new InetSocketAddress(address,port);
		try {
			s = new Socket();
			s.connect(ia);
			isConnected = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close()
	{
		try {
			s.close();
			isConnected = false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isRunning()
	{
		return isConnected;
	}
	
	public static ImageIcon getIcon() {
		return isRunning() ?
			new ImageIcon(System.class.getResource("/icons/comm/circle_green.png"), "running") :
			new ImageIcon(System.class.getResource("/icons/comm/circle_gray.png"), "not running");
	}

	
	
	
}
