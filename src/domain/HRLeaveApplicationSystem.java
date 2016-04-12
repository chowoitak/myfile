package domain;

import UI.*;
import domain.database.*;
import javax.swing.JFrame;


public class HRLeaveApplicationSystem extends JFrame {
	

	public HRLeaveApplicationSystem() {
		new LoginWindow();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		DemoData.createBatachRecords();
		new HRLeaveApplicationSystem();
		
	}
	
}