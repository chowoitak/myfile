package UI;

import domain.database.*;
import java.awt.*;
import javax.swing.*;


public class DBWindowStaff  extends JFrame{

	public DBWindowStaff() {
        super("Staff Database Window");
        this.setSize(1100, 1300);
        this.setLocation(950, 50);
        
        JPanel aPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Staff Database Window"));
        aPanel.add(topPanel, BorderLayout.NORTH);
 
        final JTextArea staffRecordsTextArea = new JTextArea(DBTableStaff.getAllStaffRecords());
        staffRecordsTextArea.setLineWrap(true);
        staffRecordsTextArea.setEditable(false);        
        
        JScrollPane scrollPane = new JScrollPane(staffRecordsTextArea);
        scrollPane.setBounds(10,60,780,500);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
               
        this.add(aPanel);
        this.add(scrollPane);
        this.setVisible(true);
    }
}
