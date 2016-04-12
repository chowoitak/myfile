package UI;

import domain.*;
import UI.action.StaffAccMgtAction;
import domain.database.DBTableStaff;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StaffAccMgtWindow extends JFrame {

	public StaffAccMgtWindow(Staff loginStaff){
		super("Staff Account Management System");
		this.setSize(900,1000);
		this.setLocation(50,50);
		
		JPanel aPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Staff Account Management System ---" + loginStaff.getName() + " (StaffID: " + loginStaff.getStaffID() + ")"));
        topPanel.setBackground(Color.WHITE);
        aPanel.add(topPanel, BorderLayout.NORTH);
        
        
        JPanel centerPanel = new JPanel(new GridLayout(17, 3, 5, 10));
        
        // line1
        centerPanel.add(new JLabel("Create Staff", SwingConstants.LEFT));
        centerPanel.add(new JPanel());
        centerPanel.add(new JPanel());
        
        //line2
        //show the staff no in text box
        centerPanel.add(new JLabel("Staff Name", SwingConstants.RIGHT));
        final JTextField staffNameTextField = new JTextField("", 20);
        centerPanel.add(staffNameTextField);
        centerPanel.add(new JPanel());
        
        //line3
        centerPanel.add(new JLabel("Title", SwingConstants.RIGHT));
        String[] options = {"", "Senior Manager", "Junior Manager", "Senior Assistant", "Junior Assistant"};
        JComboBox comboBox = new JComboBox(options);
        centerPanel.add(comboBox);
        centerPanel.add(new JPanel());
        
        //line4
        centerPanel.add(new JLabel("Supervisor (Staff No.)", SwingConstants.RIGHT));
        final JTextField supervisorTextField = new JTextField("", 20);
        centerPanel.add(supervisorTextField);
        centerPanel.add(new JPanel());

        //line5
        centerPanel.add(new JPanel());
        final JTextField assignSupTextField = new JTextField("Search supervisor by entering StaffID. ", 20);
        JButton buttonSearch1 = new JButton("Search Supervisor");
        buttonSearch1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	String enteredStaffID = supervisorTextField.getText();

            	if (DBTableStaff.checkExistenceOfStaff(enteredStaffID)){
            		Staff searchingStaff = DBTableStaff.getStaffByID(enteredStaffID);	
            		
            		if (enteredStaffID.equals("HR")){
            			assignSupTextField.setText("The \"" + enteredStaffID + "\" staff can not be a supervisor.");  
        			} else {            		
	            		if (HRStaff.verifyValidSupervisor(enteredStaffID, null)){
	            			assignSupTextField.setText(searchingStaff.getName() + "(StaffID: " + enteredStaffID + ")");
	            		} else {
	            			assignSupTextField.setText("Invalid supervisor. Please search for another staff.");  
	            		}
        			}
            	} else {
            		assignSupTextField.setText("Invalid StaffID. Please enter a valid StaffID.");
            	}	         
            }
        });
        centerPanel.add(buttonSearch1);
        centerPanel.add(new JPanel());
        
        //line6
        centerPanel.add(new JLabel("Supervisor to be assigned: ", SwingConstants.RIGHT));
        assignSupTextField.setEditable(false);
        centerPanel.add(assignSupTextField);
        centerPanel.add(new JPanel());
        
        //line7
        centerPanel.add(new JPanel());
        JButton createButton = new JButton("Confirm Create");
           
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
         	            	
            	String staffName = staffNameTextField.getText();
                String title = (String)comboBox.getSelectedItem();
                String supervisorID = supervisorTextField.getText();            
                            
                if (StaffAccMgtAction.verifyValidNewStaff(staffName, title, supervisorID)){
    				JOptionPane.showMessageDialog(StaffAccMgtWindow.this, StaffAccMgtAction.getDisplayMessage(), "Staff Creation Success", JOptionPane.PLAIN_MESSAGE);
    			} else {
    				JOptionPane.showMessageDialog(StaffAccMgtWindow.this, StaffAccMgtAction.getErrorMessage(), "Error", JOptionPane.WARNING_MESSAGE);
    			}
            }  
        });
        centerPanel.add(createButton);
        centerPanel.add(new JPanel());
        
        //line8 & 9
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }
        
        //line10
        centerPanel.add(new JLabel("Remove Staff", SwingConstants.LEFT));
        centerPanel.add(new JPanel());
        centerPanel.add(new JPanel());
        
        //line11
        //show the staff name, title, supervisor in text box
        centerPanel.add(new JLabel("Search Staff (Staff No.)", SwingConstants.RIGHT));
        final JTextField staffIDTextField = new JTextField("", 20);
        centerPanel.add(staffIDTextField);
        centerPanel.add(new JPanel());
        
        //line12
        centerPanel.add(new JPanel());
        final JTextField deleteStaffTextField = new JTextField("Search staff by entering StaffID. ", 20);
        JButton buttonSearch2 = new JButton("Search Staff");
        buttonSearch2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	String enteredStaffID = staffIDTextField.getText();

            	if (DBTableStaff.checkExistenceOfStaff(enteredStaffID)){
            		Staff searchingStaff = DBTableStaff.getStaffByID(enteredStaffID);	
            		
            		if (enteredStaffID.equals("DIR") ||enteredStaffID.equals("HR")){
        				deleteStaffTextField.setText("The \"" + enteredStaffID + "\" staff can not be deleted.");  
        			} else {            		
	            		if (!DBTableStaff.checkExistenceOfSubOrdinates(enteredStaffID)){
	            				deleteStaffTextField.setText(searchingStaff.getName() + "(StaffID: " + enteredStaffID + ")");
	            		} else {
	            			deleteStaffTextField.setText("Can not be deleted. The enter staff has subordinates.");  
	            		}
        			}
            	} else {
            		deleteStaffTextField.setText("Invalid StaffID. Please enter a valid StaffID.");
            	}	         
            }
        });
        centerPanel.add(buttonSearch2);
        centerPanel.add(new JPanel());
       
        //line13
        centerPanel.add(new JLabel("Staff to be deleted: ", SwingConstants.RIGHT));
        deleteStaffTextField.setEditable(false);
        centerPanel.add(deleteStaffTextField);
        centerPanel.add(new JPanel());

        //line14
        centerPanel.add(new JPanel());
        JButton deleteButton = new JButton("Confirm Delete");
           
        deleteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	String staffIDToBeDeleted = staffIDTextField.getText();
            	
            	if (StaffAccMgtAction.verifyValidStaffToBeDeleted(staffIDToBeDeleted)){
     				JOptionPane.showMessageDialog(StaffAccMgtWindow.this, StaffAccMgtAction.getDisplayMessage(), "Staff Deletion Success", JOptionPane.PLAIN_MESSAGE);
     			} else {
     				JOptionPane.showMessageDialog(StaffAccMgtWindow.this, StaffAccMgtAction.getErrorMessage(), "Error", JOptionPane.WARNING_MESSAGE);
     			}
            	
            }
        });
        centerPanel.add(deleteButton);
        centerPanel.add(new JPanel());
    
        //line15
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }
        
        //line16
        centerPanel.add(new JLabel("Check Staff Database", SwingConstants.LEFT));
        centerPanel.add(new JPanel());
        centerPanel.add(new JPanel());
        
        //line17
        centerPanel.add(new JPanel());
        JButton staffDBButton = new JButton("Staff Database");
           
        staffDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new DBWindowStaff();
            }
        });
        centerPanel.add(staffDBButton);
        centerPanel.add(new JPanel());
        
        //line18
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }
        
        aPanel.add(centerPanel, BorderLayout.CENTER);

        this.add(aPanel);
        this.setVisible(true);
		
	}
}
