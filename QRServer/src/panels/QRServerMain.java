package panels;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class QRServerMain {
        
	 public static void createAndShowGUI() {
	        JFrame frame = new JFrame("QR Server");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        //Create and set up the content pane.
	        MainJPanel mainPanel = new MainJPanel();
	        mainPanel.setOpaque(true); //content panes must be opaque
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
		public static class MainJPanel extends JPanel
	    {
	    	public MainJPanel()
	    	{
	    		setLayout(new BorderLayout());
	    		add(new LocalServerPanel(),BorderLayout.PAGE_START);
	    		add(new ClientPanel(),BorderLayout.CENTER);
	    	}
	    	
	    }
	    
}
