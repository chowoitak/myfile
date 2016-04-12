package UI;

import domain.*;
import UI.action.*;
import domain.database.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginWindow extends JFrame{
	
	private String username = null;
    private String password = null;
    private Staff loginSuccessStaff = null;
    private boolean loginSuccess = false;
	
	public LoginWindow() {
        super("Staff Login");
        this.setSize(750, 500);
        this.setLocation(50, 50);

        JPanel aPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(9, 3, 5, 10));
        
        //line 1
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }
        
        //line 2
        centerPanel.add(new JPanel());
        centerPanel.add(new JLabel("Staff Login", SwingConstants.CENTER));
        centerPanel.add(new JPanel());
        
        //line 3
        for (int i = 0; i < 3; i++) {
            centerPanel.add(new JPanel());
        }
        
        //line 4
        centerPanel.add(new JLabel("Username", SwingConstants.RIGHT));
        final JTextField usernameTextField = new JTextField("", 20);
        centerPanel.add(usernameTextField);
        centerPanel.add(new JPanel());
        
        //line 5
        centerPanel.add(new JLabel("Password", SwingConstants.RIGHT));
        final JTextField passwordPasswordField = new JPasswordField("", 20);
        centerPanel.add(passwordPasswordField);
        centerPanel.add(new JPanel());
             
        //line 6
        centerPanel.add(new JPanel());
        JButton button = new JButton("Login");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             
            	username = usernameTextField.getText();
                password = passwordPasswordField.getText();
                            
            	
                if (username.equals("")){
                	JOptionPane.showMessageDialog (LoginWindow.this, "Please enter username!", "Error", JOptionPane.WARNING_MESSAGE);
                } else {
                	if (password.equals("")){
                		JOptionPane.showMessageDialog (LoginWindow.this, "Please enter password!", "Error", JOptionPane.WARNING_MESSAGE);
                	} else {
                		loginSuccess = LoginVerification.loginVerification(username, password);
                		if (loginSuccess){
                			 loginSuccessStaff = DBTableStaff.getStaffByID(username);
 	
                			JOptionPane.showMessageDialog (LoginWindow.this, "Hello, \"" + loginSuccessStaff.getName() + " (StaffID: " + loginSuccessStaff.getStaffID() + ") !", "Welcome", JOptionPane.PLAIN_MESSAGE);
                			DisplayWindows.popWindows(loginSuccessStaff);
                			                				
                		} else {
                			JOptionPane.showMessageDialog(LoginWindow.this, "Invalid Input!\nPlease enter again.", "Error", JOptionPane.WARNING_MESSAGE);
                		}
                	}
                }
            }
        });
        
        centerPanel.add(button);
        centerPanel.add(new JPanel());
        
        //line 7 - 9
        for (int i = 0; i < 9; i++) {
            centerPanel.add(new JPanel());
        }
        aPanel.add(centerPanel, BorderLayout.CENTER);

        this.add(aPanel);
        this.setVisible(true);
    }


}
