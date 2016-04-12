package UI;

import domain.database.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import domain.Staff;
import java.awt.*;
import java.awt.event.*;


public class EndorsedHistoryWindow extends JFrame{
	
	private Staff searchingSubor = null;
	
	public EndorsedHistoryWindow(Staff loginStaff) {
			super("Endorsed History Search Window");
		    this.setSize(1700,800);
			this.setLocation(200,200);
			
			JPanel aPanel = new JPanel(new BorderLayout());
	        JPanel topPanel = new JPanel();
	        topPanel.add(new JLabel("Endorsed History Search Window --- " + loginStaff.getName() + " (StaffID: " + loginStaff.getStaffID() + ")"));
	        topPanel.setBackground(Color.WHITE);
	        aPanel.add(topPanel, BorderLayout.NORTH);

	        JPanel centerPanel = new JPanel(new GridLayout(8, 4, 10, 20));

                
	        
//--------------------- Handled Applications History--------------------------------  (3 lines)
	        

	        //line1
	        centerPanel.add(new JPanel());
	        centerPanel.add(new JLabel("Appl Endorsed History (With Current Status)", SwingConstants.CENTER));
	        centerPanel.add(new JPanel());
	        centerPanel.add(new JPanel());

	        
	        //line2a
	        JButton buttonEndorsed1 = new JButton("Show ALL Endorsed Applications");
	        buttonEndorsed1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
		            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllLeaveApplEndorsedByEndorser(loginStaff));
		            	new DBWindowLeaveAppl(displayText);
	            }
	        });
	        centerPanel.add(buttonEndorsed1);
	        
	        //line2b
	        JButton buttonEndorsed2 = new JButton("Show Endorsed Applications (APPROVED)");
	        buttonEndorsed2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            		String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllApprovedLeaveApplEndorsedByEndorser(loginStaff));
	            		new DBWindowLeaveAppl(displayText);
	            }
	        });
	        centerPanel.add(buttonEndorsed2);

	        //line2c
	        JButton buttonEndorsed3 = new JButton("Show Endorsed Applications (PENDING)");
	        buttonEndorsed3.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllPendingLeaveApplEndorsedByEndorser(loginStaff));
	            	new DBWindowLeaveAppl(displayText);
	            }
	        });
	        centerPanel.add(buttonEndorsed3);
	        if (loginStaff.getStaffID().equals("DIR")){
	        	buttonEndorsed3.setEnabled(false);
	        }
	        
	        //line2d
	        JButton buttonEndorsed4 = new JButton("Show Endorsed Applications (REJECTED)");
	        buttonEndorsed4.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllRejectedLeaveApplEndorsedByEndorser(loginStaff));
	            	new DBWindowLeaveAppl(displayText);
	            }
	        });
	        centerPanel.add(buttonEndorsed4);
	        
	        
	        //line 3
	        for (int i = 0; i < 4; i++) {
	            centerPanel.add(new JPanel());
	        }
	        
	                
	        
//--------------------- Handled Applications History (Search by Subordinate)--------------------------------  (5 lines)        
	        
	        //line1
	        centerPanel.add(new JPanel());
	        centerPanel.add(new JLabel("Appl Endorsed History (With Current Status) \n Search By Subordinate", SwingConstants.CENTER));
	        centerPanel.add(new JPanel());
	        centerPanel.add(new JPanel());
	        

	        //line2
	        centerPanel.add(new JLabel("Enter StaffID of Subordinate", SwingConstants.RIGHT));
	        final JTextField searchSuborTextField = new JTextField("Scroll list to select all / individual subordinate(s)", 20);
	        centerPanel.add(searchSuborTextField);
	        
	        final JTextField subOrTextField = new JTextField("Subordinate's Name", 20);
	        final JTextField subOrTitleTextField = new JTextField("Subordinate's Title", 20);
	        
	        JButton buttonSubor = new JButton("Select Surordinate");
	        buttonSubor.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	String enteredSuborID = searchSuborTextField.getText();
	            	
	            	if (DBTableStaff.checkExistenceOfStaff(enteredSuborID)){
	            		searchingSubor = DBTableStaff.getStaffByID(enteredSuborID);	
	            		
	            		if (DBTableStaff.checkIsSubOrdinate(enteredSuborID, loginStaff.getStaffID())){
	            			subOrTextField.setText(searchingSubor.getName() + "(StaffID: " + searchingSubor.getStaffID() + ")");
	            			subOrTitleTextField.setText(searchingSubor.getTitle());
	            		} else {
	            			subOrTextField.setText("Is not one of your subordinates.\n Please enter again.");
	            			subOrTitleTextField.setText("Is not one of your subordinates.\n Please enter again.");
	            		}
	            	} else {
	            		subOrTextField.setText("Invalid StaffID. Please enter a valid StaffID.");
	            		subOrTitleTextField.setText("Invalid StaffID. Please enter a valid StaffID.");
	            	}	         
	            }
	        });
	        centerPanel.add(buttonSubor);
	        centerPanel.add(new JPanel());
	        
	        
	        //line3 
	        centerPanel.add(new JLabel("Subordinate Selected", SwingConstants.RIGHT));
	        subOrTextField.setEditable(false);
	        centerPanel.add(subOrTextField);
	        subOrTitleTextField.setEditable(false);
	        centerPanel.add(subOrTitleTextField);
	        centerPanel.add(new JPanel());
	         
	        
	        //line4a
	        JButton buttonEndorsed5 = new JButton("Show ALL Endorsed Applications");
	        buttonEndorsed5.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
		            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllLeaveApplEndorsedByEndorser(loginStaff, searchingSubor));
		            	new DBWindowLeaveAppl(displayText);
	            }
	        });
	        centerPanel.add(buttonEndorsed5);
	        
	        //line4b
	        JButton buttonEndorsed6 = new JButton("Show Endorsed Applications (APPROVED)");
	        buttonEndorsed6.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            		String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllApprovedLeaveApplEndorsedByEndorser(loginStaff, searchingSubor));
	            		new DBWindowLeaveAppl(displayText);
	            }
	        });
	        centerPanel.add(buttonEndorsed6);

	        //line4c
	        JButton buttonEndorsed7 = new JButton("Show Endorsed Applications (PENDING)");
	        buttonEndorsed7.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllPendingLeaveApplEndorsedByEndorser(loginStaff, searchingSubor));
	            	new DBWindowLeaveAppl(displayText);
	            }
	        });
	        centerPanel.add(buttonEndorsed7);
	        if (loginStaff.getStaffID().equals("DIR")){
	        	buttonEndorsed7.setEnabled(false);
	        }
	        
	        //line4d
	        JButton buttonEndorsed8 = new JButton("Show Endorsed Applications (REJECTED)");
	        buttonEndorsed8.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String displayText = DBTableLeaveAppl.getLeaveApplRecordDetails(DBTableLeaveAppl.getAllRejectedLeaveApplEndorsedByEndorser(loginStaff, searchingSubor));
	            	new DBWindowLeaveAppl(displayText);
	            }
	        });
	        centerPanel.add(buttonEndorsed8);
	        
	        
	        //line 5
	        for (int i = 0; i < 4; i++) {
	            centerPanel.add(new JPanel());
	        }
	        
	                
	        aPanel.add(centerPanel, BorderLayout.CENTER);

	        this.add(aPanel);
	        this.setVisible(true);
			
			
		}
		
	}


