package com.example.barcodescanningapp;

import java.io.UnsupportedEncodingException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barcodescanningapp.PackageData.RTYPE;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends ActionBarActivity implements
		View.OnClickListener {

	PackageData packageData;
	private static EditText ipAddress, portNumber;
	// public static String ipAddr, portNum;
	private Button scanBtn;

	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		scanBtn = (Button) findViewById(R.id.scan_button);
		ipAddress = (EditText) findViewById(R.id.ip_address);
		portNumber = (EditText) findViewById(R.id.port_number);
		scanBtn.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * private boolean isEmpty(EditText etText) {
	 * System.out.println(etText.getText().toString().trim().length()); return
	 * etText.getText().toString().trim().length() == 0; }
	 * 
	 * public void assignVals(EditText textOne, EditText textTwo) { if
	 * (isEmpty(textOne) == true || isEmpty(textTwo) == true) { ipAddr = null;
	 * portNum = null; } else { ipAddr = textOne.getText().toString(); portNum =
	 * textTwo.getText().toString(); } }
	 */
	@Override
	public void onClick(View v) {
		System.out.println("LEN: " + getIP().trim().length());
		System.out.println("LEN: " + getPort().trim().length());
		if (getIP().trim().length() == 0 || getPort().trim().length() == 0) {

			Toast t = Toast.makeText(getApplicationContext(),
					"Input IP adrress and port", Toast.LENGTH_SHORT);
			t.show();
			return;
		}

		packageData = new PackageData();
		startScan();

	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		// Result of activity
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);

		if (null == scanningResult.getContents())
			return;

		byte[] myData = null;
		try {
			myData = scanningResult.getContents().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RTYPE ret = packageData.update(myData);
		if (RTYPE.CONTINUE == ret)
			startScan();
		else {
			String tString;
			if (RTYPE.ERROR == ret)
				tString = "Data was lost, please try again!";
			else
				tString = "You have completed the transmission successfully!";

			Toast toast = Toast.makeText(getApplicationContext(), tString,
					Toast.LENGTH_LONG);
			toast.show();
		}
	}

	public void startScan() {
		IntentIntegrator scanIntegrator = new IntentIntegrator(
				MainActivity.this);
		scanIntegrator.initiateScan();
	}

	public static String getIP() {
		return ipAddress.getText().toString();
	}

	public static String getPort() {
		return portNumber.getText().toString();
	}

}
