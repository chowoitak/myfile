package UI;

import domain.*;
import domain.database.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LeaveApplHRWindow extends JFrame {

	public LeaveApplHRWindow(Staff loginStaff){
		super("Leave Application Management System");
		this.setSize(900,300);
		this.setLocation(800,50);
		
		JPanel aPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Leave Application Management System ---" + loginStaff.getName() + " (StaffID: " + loginStaff.getStaffID() + ")"));
        topPanel.setBackground(Color.WHITE);
        aPanel.add(topPanel, BorderLayout.NORTH);
                
        JPanel centerPanel = new JPanel(new GridLayout(3, 3, 5, 10));
            
        //line1
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }
        
        //line2
        centerPanel.add(new JPanel());
        JButton leaveApplDBButton = new JButton("Leave Appl Database");
           
        leaveApplDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllLeaveApplList());
            	new DBWindowLeaveAppl(displayText);
            }
        });
        centerPanel.add(leaveApplDBButton);
        centerPanel.add(new JPanel());
        
        //line3
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }
        
        aPanel.add(centerPanel, BorderLayout.CENTER);

        this.add(aPanel);
        this.setVisible(true);
		
	}
}
