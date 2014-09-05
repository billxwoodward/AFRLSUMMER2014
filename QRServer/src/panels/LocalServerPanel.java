package panels;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import code.ServerThread;

@SuppressWarnings("serial")
public class LocalServerPanel extends JPanel {
	public static JTable table = new JTable(new MyTableModel());
	public static JScrollPane scrollPane = new JScrollPane(table);
	public JCheckBox serverBox = new JCheckBox("Enable Server   Port:");
	
	public LocalServerPanel()
	{
		setBorder(BorderFactory.createTitledBorder("Local Server:"));
		setLayout(new BorderLayout());
		
		add(scrollPane,BorderLayout.CENTER);
		
		table.setPreferredScrollableViewportSize(new Dimension(200, 17));
        table.setFillsViewportHeight(true);
        
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMinWidth(80);
        table.getColumnModel().getColumn(3).setMaxWidth(20);
        table.getColumnModel().getColumn(3).setMinWidth(20);
	}
	
	
	public static class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Status",
        		                        "Address",
                                        "Port",
                                        "!",
                                        };
        
        public int getColumnCount() {
            return columnNames.length;
        }
 
        public int getRowCount() {
            return 1;
        }
 
        public String getColumnName(int col) {
            return columnNames[col];
        }
 
        public Object getValueAt(int row, int col) {
        	
        	switch (col)
        	{
        	case 0: return ServerThread.getIcon();
        	case 1: return ServerThread.address;
        	case 2: return ServerThread.port;
        	case 3: return ServerThread.isRunning();
        	default: return null; /* should never happen */
        	}
        }
 
        
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c) {
			return (c == 0) ? Icon.class : getValueAt(0, c).getClass();
        }
 
        public boolean isCellEditable(int row, int col) {
        	switch (col)
        	{
        	case 1: return !ServerThread.isRunning();
        	case 3: return true;
        	}
        	
        	return false;
        }
 
        public void setValueAt(Object value, int row, int col) {
	        if (1 == col)
	        	ServerThread.port = (Integer) value;
	        else if (3 == col) {
	        	if (ServerThread.isRunning())
	        		ServerThread.closeServer();
	        	else
	        		ServerThread.startServer();
	        }    
        }
        
        public static void serverStateUpdated()
        {
        	AbstractTableModel atm = (AbstractTableModel)table.getModel();
        	atm.fireTableCellUpdated(0, 0);	
        	atm.fireTableCellUpdated(0, 3);
        }
        
        
    }
	
}
