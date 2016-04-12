package UI;

import javax.swing.*;

import domain.*;
import UI.action.*;
import domain.database.*;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaveApplWindow extends JFrame {
	
	
	public LeaveApplWindow(Staff loginStaff) {
		super("Leave Application System");
		this.setSize(900,1300);
		this.setLocation(50,50);

        JPanel aPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Leave Application Window --- " + loginStaff.getName() + " (StaffID: " + loginStaff.getStaffID() + ")"));
        topPanel.setBackground(Color.WHITE);
        aPanel.add(topPanel, BorderLayout.NORTH);
                
        JPanel centerPanel = new JPanel(new GridLayout(15, 3, 5, 10));
        
      //--------------------------- Apply Leave ---------------------------------------- (6 lines)         
        // line1
        centerPanel.add(new JPanel());
        centerPanel.add(new JLabel("Apply Leave", SwingConstants.CENTER));
        centerPanel.add(new JPanel());
        
        //line2
        centerPanel.add(new JLabel("From Date", SwingConstants.RIGHT));
        final JTextField fromDateTextField = new JTextField("Please enter start date (YYYY-MM-DD)", 20);
        centerPanel.add(fromDateTextField);
        centerPanel.add(new JPanel());
       
        //line3
        centerPanel.add(new JLabel("To Date", SwingConstants.RIGHT));
        final JTextField toDateTextField = new JTextField("Please enter end date (YYYY-MM-DD)", 20);
        centerPanel.add(toDateTextField);
        centerPanel.add(new JPanel());    	
        
        //line4
        //Show no. of AL leave, only decrease after application approved.
        centerPanel.add(new JLabel("No. Of AL Leave", SwingConstants.RIGHT));
        final JTextField noOfALTextField = new JTextField(loginStaff.getNoOfALString(), 20);
        noOfALTextField.setEditable(false);
        centerPanel.add(noOfALTextField);
        final JTextField remarksTextField = new JTextField("Only decrease after application approved .", 20);
        remarksTextField.setEditable(false);
        centerPanel.add(remarksTextField);
       
        //line5
		centerPanel.add(new JLabel("Total Days Applying", SwingConstants.RIGHT));
        final JTextField totalDaysTextField = new JTextField("", 20);
        totalDaysTextField.setEditable(false);
        centerPanel.add(totalDaysTextField);
        centerPanel.add(new JPanel());
        
        //line6
        centerPanel.add(new JPanel());
        JButton confirmButton = new JButton("Confirm");
           
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            	String fromDate = fromDateTextField.getText();
            	String toDate = toDateTextField.getText();

        		try {
        			Date sdfFromDate = sdf.parse(fromDate);
        			Date sdfToDate = sdf.parse(toDate);
                	
        			if (LeaveApplAction.leaveApplVerification(loginStaff, sdfFromDate, sdfToDate)){
        				totalDaysTextField.setText(LeaveAppl.getNoOfDaysBetweenString(sdfFromDate, sdfToDate));
        				JOptionPane.showMessageDialog(LeaveApplWindow.this, LeaveApplAction.getDisplayMessage(), "Application Done", JOptionPane.PLAIN_MESSAGE);
        			} else {
        				JOptionPane.showMessageDialog(LeaveApplWindow.this, LeaveApplAction.getErrorMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        			}
               	        			
        		} catch (ParseException pe) {
        			JOptionPane.showMessageDialog(LeaveApplWindow.this, "Invalid date format.\nPlease enter dates (YYYY-MM-DD)", "Error", JOptionPane.WARNING_MESSAGE);
        			pe.printStackTrace();
        		}
            }

        });
        centerPanel.add(confirmButton);
        centerPanel.add(new JPanel());
        
        
        //line7
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }
        
        
//--------------------- Current Application Status --------------------------------  (4 lines)
        //line1
        centerPanel.add(new JPanel());
        centerPanel.add(new JLabel("Pending Applications Status", SwingConstants.CENTER));
        centerPanel.add(new JPanel());
           
        //line2
        //Show pending application status
        centerPanel.add(new JPanel());
        JButton buttonPending = new JButton("Show Pending Applications");
        buttonPending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllPendingLeaveApplByApplicant(loginStaff));
            	new DBWindowLeaveAppl(displayText);
            }
        });
        centerPanel.add(buttonPending);
        centerPanel.add(new JPanel());
        
        //line3
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }

        

//--------------------- Check Leave Record -------------------------------- (3 lines)
        //line1
        centerPanel.add(new JPanel());
        centerPanel.add(new JLabel("Leave Applications History", SwingConstants.CENTER));
        centerPanel.add(new JPanel());
        
        //line2
        centerPanel.add(new JPanel());
        JButton buttonAllAL = new JButton("Show All Applications");
        buttonAllAL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllLeaveApplByApplicant(loginStaff));
            	new DBWindowLeaveAppl(displayText);
            }
        });
        centerPanel.add(buttonAllAL);
        centerPanel.add(new JPanel());

        //line3
        centerPanel.add(new JPanel());
        JButton buttonApproved = new JButton("Show Approved Applications");
        buttonApproved.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllApprovedLeaveApplByApplicant(loginStaff));
            	new DBWindowLeaveAppl(displayText);
            }
        });
        centerPanel.add(buttonApproved);
        centerPanel.add(new JPanel());
    
        //line4
        centerPanel.add(new JPanel());
        JButton buttonRejected = new JButton("Show Rejected Applications");
        buttonRejected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllRejectedLeaveApplByApplicant(loginStaff));
            	new DBWindowLeaveAppl(displayText);
            }
        });
        centerPanel.add(buttonRejected);
        centerPanel.add(new JPanel());
        
        
        //line5
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }       
                       
        
        aPanel.add(centerPanel, BorderLayout.CENTER);

        this.add(aPanel);
        this.setVisible(true);
		
		
	}
	
}
