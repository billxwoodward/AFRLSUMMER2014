package panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	public static BufferedImage image;

	public ImagePanel() {
		this.setPreferredSize(new Dimension(300, 300));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public void updateImage(String path) {
		System.out.println("Updated image:" + path);
		File fi = new File(path);
		try {
			image = ImageIO.read(fi);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.repaint();
	}
}
