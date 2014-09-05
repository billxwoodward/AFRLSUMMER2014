package code;

import java.io.File;
import java.io.IOException;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCode {

	public static void create(String strData) {

		try {
			System.out.println("strData Len: " + strData.length());
			System.out.print("String: " + strData);
			BitMatrix matrix = null;
			com.google.zxing.Writer w = new QRCodeWriter();

			int width = QRSenderMain.ip.getWidth();
			int height = QRSenderMain.ip.getHeight();
			System.out.println("Width: " + width + " Height: " + height);
			matrix = w.encode(new String(strData.getBytes(), "UTF-8"),
					com.google.zxing.BarcodeFormat.QR_CODE, width, height);

			MatrixToImageWriter.writeToFile(matrix, "jpg", new File(
					"tmpImg.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QRSenderMain.ip.updateImage("tmpImg.jpg");
	}
}
