package UI;

import domain.*;
import UI.action.*;
import domain.database.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveMgtWindow extends JFrame {

	private static String displayString = "";
	
	public LeaveMgtWindow(Staff loginStaff) {
		super("Leave Application System");
		
        int noOfPendingApprovalsToBeHandle = DBTableLeaveAppl.getNoOfPendingApprovalsByEndorser(loginStaff);
        
        int rows =4 + noOfPendingApprovalsToBeHandle;
        int high = rows*150;
        
        
		this.setSize(2000,high);
		this.setLocation(50,50);

		JPanel aPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Leave Management Window --- " + loginStaff.getName() + " (StaffID: " + loginStaff.getStaffID() + ")"));
        topPanel.setBackground(Color.WHITE);
        aPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(rows, 4, 10, 20));

  
    
//--------------------- Pending Approval Applications --------------------------------  (3 +k-1 lines = k+2 lines)
        
        //line1
        //centerPanel.add(new JPanel());
        centerPanel.add(new JPanel());
        centerPanel.add(new JLabel("Pending Approval Applications", SwingConstants.CENTER));
        String displayContent;
        if (noOfPendingApprovalsToBeHandle==0){
            displayContent = "There are currently no pending approvals.";
        } else {
        	if (noOfPendingApprovalsToBeHandle==1){
        		displayContent = "There is 1 pending approval.";
        	} else {
        		displayContent = "There are " + noOfPendingApprovalsToBeHandle + " pending approvals.";
        	}
        }
        centerPanel.add(new JLabel(displayContent, SwingConstants.CENTER));
        centerPanel.add(new JPanel());
        
      
        //line2 to line (noOfCases-1)
        List<LeaveAppl> listOfPendingApprovalsToBeHandle = DBTableLeaveAppl.getAllPendingLeaveApplByEndorser(loginStaff);
        List<JTextArea> listOfPendingApprovalTitleTextArea = new ArrayList<JTextArea>();
        List<JTextArea> listOfPendingApprovalRecordTextArea = new ArrayList<JTextArea>();
        List<JButton> endorseButton = new ArrayList<JButton>();
        List<JButton> declineButton = new ArrayList<JButton>();
        for (int i = 0; i < noOfPendingApprovalsToBeHandle; i++) {
        	listOfPendingApprovalRecordTextArea.add(new JTextArea(""));
        	listOfPendingApprovalTitleTextArea.add(new JTextArea(""));
        	endorseButton.add(new JButton("Endorse"));
        	declineButton.add(new JButton("Decline"));
        }
        
        for (int i=0; i<noOfPendingApprovalsToBeHandle; i++){
        	listOfPendingApprovalsToBeHandle = DBTableLeaveAppl.getAllPendingLeaveApplByEndorser(loginStaff);
        	LeaveAppl tmpPendingApproval = listOfPendingApprovalsToBeHandle.get(i);
        	JTextArea tmpPendingApprovalRecordTextArea;
        	JTextArea tmpPendingApprovalTitleTextArea;
        	JButton tmpEndorseButton = endorseButton.get(i);
        	JButton tmpDeclineButton = declineButton.get(i);
        	
        	int j = i+1;
       	
        	String tmpPendingApprovalTitle = "Pending Approval_" + j + "\n\nEndorsed by:\n" + DBTableLeaveAppl.getLeaveApplAllEndorsers(tmpPendingApproval);
        	
        	listOfPendingApprovalTitleTextArea.set(i, new JTextArea(tmpPendingApprovalTitle));
        	tmpPendingApprovalTitleTextArea = listOfPendingApprovalTitleTextArea.get(i);
        	tmpPendingApprovalTitleTextArea.setLineWrap(true);
        	tmpPendingApprovalTitleTextArea.setEditable(false);
            centerPanel.add(tmpPendingApprovalTitleTextArea);
        	
        	String tmpPendingApprovalContent =DBTableLeaveAppl.getLeaveApplRecord(tmpPendingApproval);
           	listOfPendingApprovalRecordTextArea.set(i, new JTextArea(tmpPendingApprovalContent));
        	tmpPendingApprovalRecordTextArea = listOfPendingApprovalRecordTextArea.get(i);
        	tmpPendingApprovalRecordTextArea.setLineWrap(true);
        	tmpPendingApprovalRecordTextArea.setEditable(false);
            centerPanel.add(tmpPendingApprovalRecordTextArea);
            
            
            
            tmpEndorseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	boolean decline = false;
                	
                	boolean handle = loginStaff.handleApplication(tmpPendingApproval, decline);

                	if (handle && tmpPendingApproval.getStatus().equals(LeaveAppl.APPL_STATUS_REJECTED)){
                		JOptionPane.showMessageDialog(LeaveMgtWindow.this, displayString, "Application Approved", JOptionPane.WARNING_MESSAGE);
                	} else {
                		JOptionPane.showMessageDialog(LeaveMgtWindow.this, displayString, "Application Approved", JOptionPane.PLAIN_MESSAGE);
                	}
                	tmpEndorseButton.setEnabled(false);
                   	tmpDeclineButton.setEnabled(false);           	
                }
            });
            centerPanel.add(tmpEndorseButton);
            
            
            tmpDeclineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	boolean decline = true;
                	loginStaff.handleApplication(tmpPendingApproval, decline);
                	
                	JOptionPane.showMessageDialog(LeaveMgtWindow.this,displayString, "Application Approved", JOptionPane.PLAIN_MESSAGE);
                	
                	tmpEndorseButton.setEnabled(false);
                   	tmpDeclineButton.setEnabled(false);
                }
            });
            centerPanel.add(tmpDeclineButton);
        }
        
        //line k+1
        for (int i = 0; i < 4; i++) {
            centerPanel.add(new JPanel());
        }
        
        centerPanel.add(new JPanel());
        JButton buttonCheck = new JButton("Check Endorse History");
        buttonCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	new EndorsedHistoryWindow(loginStaff);
            	
            }
        });
        centerPanel.add(buttonCheck);
        centerPanel.add(new JPanel());
        centerPanel.add(new JPanel());
         
        aPanel.add(centerPanel, BorderLayout.CENTER);

        this.add(aPanel);
        this.setVisible(true);
		
	}
	
	public static void setDisplayString(String displayString){
		LeaveMgtWindow.displayString = displayString;
	}
	
}
