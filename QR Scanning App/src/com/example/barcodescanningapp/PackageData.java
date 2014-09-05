package com.example.barcodescanningapp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PackageData {
	private static final int SENTINEL_VALUE = 127;
	private int lastSeq = -1;
	private ArrayList<byte[]> bufList = new ArrayList<byte[]>();

	// AsyncCallD task = new AsyncCallD();

	public static enum RTYPE {
		DONE, CONTINUE, ERROR
	}

	public RTYPE update(byte[] currentCode) {

		byte[] hex = new byte[2];
		hex[0] = currentCode[0];
		hex[1] = currentCode[1];
		String hexVal = Util.getString(hex);
		int curSeq = Integer.parseInt(hexVal, 16);

		System.out.println("Current seq is:" + curSeq);

		if (curSeq == lastSeq)
			return RTYPE.CONTINUE; // keep going - skipped
		else if (curSeq == lastSeq + 1) {
			System.out.println("Added Seq # " + curSeq + " to buffer");
			bufList.add(currentCode);
			lastSeq = curSeq;
			return RTYPE.CONTINUE; // keep going good!
		} else if (SENTINEL_VALUE == curSeq) {
			bufList.add(currentCode);
			System.out.println("Completed Transmission, Sending Data!!!!");
			Thread t = new Thread(new SendThread(bufList));
			t.start();
			// task.execute();
			return RTYPE.DONE;
		}
		System.out.println("Sequence Error");
		return RTYPE.ERROR;
	}

	public static class SendThread implements Runnable {

		ArrayList<byte[]> bufList;

		public SendThread(ArrayList<byte[]> bufList) {
			this.bufList = bufList;
		}

		@Override
		public void run() {
			try {

				Socket s = new Socket(MainActivity.getIP(),
						Integer.parseInt(MainActivity.getPort()));

				OutputStream os = s.getOutputStream();

				for (int i = 0; i < bufList.size(); i++)
					os.write(bufList.get(i), 2, bufList.get(i).length - 2);

				os.close();
				s.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/*
	 * private class AsyncCallD extends AsyncTask<Void, Void, Void> {
	 * 
	 * @SuppressWarnings("resource")
	 * 
	 * @Override protected Void doInBackground(Void... args) {
	 * 
	 * try {
	 * 
	 * System.out.println("ip address is:" + MainActivity.ipAddr +
	 * " portNum is: " + MainActivity.portNum); Socket s = new
	 * Socket(MainActivity.ipAddr, Integer.parseInt(MainActivity.portNum));
	 * 
	 * OutputStream os = s.getOutputStream();
	 * 
	 * System.out.println("Num Buffers: " + bufList.size());
	 * 
	 * for (int i = 0; i < bufList.size(); i++) { System.out.println("BufSize: "
	 * + (bufList.get(i).length - 2)); os.write(bufList.get(i), 2,
	 * bufList.get(i).length - 2); }
	 * 
	 * os.close(); } catch (UnknownHostException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return null; }
	 * 
	 * @Override protected void onPostExecute(Void result) {
	 * 
	 * }
	 * 
	 * }
	 */
}
