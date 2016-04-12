package UI;

import java.awt.*;
import javax.swing.*;


public class DBWindowLeaveAppl  extends JFrame{

	public DBWindowLeaveAppl(String displayText) {
        super("Leave Application Database Window");
        this.setSize(800, 1300);
        this.setLocation(950, 50);
        
        JPanel aPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Leave Application Database Window"));
        aPanel.add(topPanel, BorderLayout.NORTH);
        
        final JTextArea leaveApplfRecordsTextArea = new JTextArea(displayText);
        leaveApplfRecordsTextArea.setLineWrap(true);
        leaveApplfRecordsTextArea.setEditable(false);        
        
        JScrollPane scrollPane = new JScrollPane(leaveApplfRecordsTextArea);
        scrollPane.setBounds(10,60,780,500);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
               
        this.add(aPanel);
        this.add(scrollPane);
        this.setVisible(true);
    }
}