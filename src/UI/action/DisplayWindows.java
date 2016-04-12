package UI.action;

import UI.*;
import domain.*;
import domain.database.*;

public class DisplayWindows {

	public static void popWindows(Staff loginStaff){

		String staffID = loginStaff.getStaffID();
		
		switch (staffID) {
        case "HR":	new StaffAccMgtWindow(loginStaff);
        			new LeaveApplHRWindow(loginStaff);
                 	break;
        
        case "DIR":	new LeaveMgtWindow(loginStaff);
                 	break;
       
        default:   	// The LeaveMgtWindow will pop up only when the staff has subordinates
        			if (DBTableStaff.checkExistenceOfSubOrdinates(staffID)){
        				new LeaveMgtWindow(loginStaff);
        			}
        			new LeaveApplWindow(loginStaff);
        			SendResultNotice.sendNotice(loginStaff);
        			break;
		}
		
	}
}
