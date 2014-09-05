package code;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import panels.ClientConnectPanel;
import panels.ImagePanel;

public class QRSenderMain {

	static ImagePanel ip = new ImagePanel();

	public static void createAndShowGUI() {
		JFrame frame = new JFrame("QR Sender");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		MainJPanel mainPanel = new MainJPanel();
		mainPanel.setOpaque(true); // content panes must be opaque
		frame.setContentPane(mainPanel);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

	@SuppressWarnings("serial")
	public static class MainJPanel extends JPanel {
		JList fileSent = new JList();
		JButton sendFileButton = new JButton("Send File");

		public MainJPanel() {
			setLayout(new BorderLayout());
			add(new ClientConnectPanel(), BorderLayout.PAGE_START);
			add(ip, BorderLayout.CENTER);
			add(sendFileButton, BorderLayout.PAGE_END);
			fileSent.setPreferredSize(new Dimension(150, 150));

			sendFileButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					final JFileChooser fc = new JFileChooser();
					int retVal = fc
							.showOpenDialog(QRSenderMain.MainJPanel.this);

					if (JFileChooser.APPROVE_OPTION == retVal) {
						try {
							sendFile(fc.getSelectedFile());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

				private void sendFile(File f) throws IOException {
					Thread t = new Thread(new SendFileThread(f));
					t.start();
				}

			});

		}

	}

}
