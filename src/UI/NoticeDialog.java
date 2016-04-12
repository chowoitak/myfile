package UI;

import javax.swing.*;

import domain.LeaveAppl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NoticeDialog extends JFrame{
	
	public NoticeDialog(LeaveAppl leaveAppl, String string) {
        super("Application Result");
        this.setSize(500, 600);
        this.setLocation(250, 200);

        JPanel aPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Application Result"));
        aPanel.add(topPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 5, 10));
        
               
        //line 1-2
        centerPanel.add(new JPanel());
        final JTextArea usernameTextArea = new JTextArea(string);
        centerPanel.add(usernameTextArea);
        usernameTextArea.setLineWrap(true);
        usernameTextArea.setEditable(false); 
           
        //line 3-4
        centerPanel.add(new JPanel());
        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	leaveAppl.setNoticeMsgRead(true);
            	setVisible(false);
            }
        });
        centerPanel.add(button);
       
       
        aPanel.add(centerPanel, BorderLayout.CENTER);

        this.add(aPanel);
        this.setVisible(true);
    }
		



}
