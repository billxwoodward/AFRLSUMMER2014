package panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import code.QRClient;

 @SuppressWarnings("serial")
public class ClientPanel extends JPanel {
    public static JTable table = new JTable(new MyTableModel());
    public static JScrollPane scrollPane = new JScrollPane(table);
    
    public static ArrayList<QRClient> selectedTargets = new ArrayList<QRClient>();
    
    public ClientPanel() {
    	super(new GridLayout(1,0));
    	setBorder(BorderFactory.createTitledBorder("Clients:"));
        
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);
        
        // Set up column sizes.
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMinWidth(40);

        table.getColumnModel().getColumn(4).setCellRenderer(new ProgressRenderer());
        // add selection listener to parent
        add(scrollPane);
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table.addMouseListener(new MouseAdapter()
        {
    	   public void mouseClicked(MouseEvent e) {		
    		   if (SwingUtilities.isLeftMouseButton(e))
    		   {
    			   if (0 == table.getSelectedRows().length)
    				   return;   
    			   if (e.getClickCount() != 2)
    				   return;
						
    			   QRClient.list.get(table.getSelectedRow()).tryOpen();
				}
			}
        });
        
    }
    
	static class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Status",
        								"Address",
        								"Type",
                                        "Incomming File",
                                        "Progress",
                                        };
        
        public int getColumnCount() {
            return columnNames.length;
        }
 
        public int getRowCount() {
            return QRClient.list.size();
        }
 
        public String getColumnName(int col) {
            return columnNames[col];
        }
 
        public Object getValueAt(int row, int col) {
        	QRClient qrClient = QRClient.list.get(row);
        	switch (col)
        	{
        	case 0: return qrClient.getStateIcon();
        	case 1: return qrClient.getAddress();
        	case 2: return qrClient.getFileIcon();
        	case 3: return qrClient.fileName;
        	case 4: return (int)(qrClient.curByte / qrClient.fileSize * 100);
        	default: return null; /* should never happen */
        	}
        }
 
        
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c) {
			if (c == 0 || c == 2)
				return Icon.class;
            return getValueAt(0, c).getClass();
        }
 
        public boolean isCellEditable(int row, int col) {
        	return false;
        }
 
        public void setValueAt(Object value, int row, int col) {
            fireTableCellUpdated(row, col);
        }
    }
	
	class ProgressRenderer extends DefaultTableCellRenderer
    {
    	private final JProgressBar b = new JProgressBar(0,100);
    	
    	public ProgressRenderer()
    	{
    		super();
    		setOpaque(true);
    		b.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    	}
    	
    	@Override
    	public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    	{
    		Integer i = (Integer)value;
    		String text = "Completed";
    		if (i < 0) {
    			text = "Error";
    		} else if (i < 100) {
    			b.setValue(i);
    			return b;
    		}
    		
    		super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
    		return this;
    	}
    	
    }

	public static void update()
	{
		table.invalidate();
		table.repaint();
	}
	
}