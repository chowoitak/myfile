package UI.action;

import domain.*;
import domain.database.*;
import java.util.Date;


public class LeaveApplAction {

	private static String errorMessage = null;
	private static String displayMessage = null;
	private static int totalDays = 0;
	
	public static boolean leaveApplVerification(Staff loginStaff, Date fromDate, Date toDate){
	
		boolean leaveApplVerify = false;
    	
    	if (LeaveAppl.verifyValidDates(fromDate, toDate)){
    		totalDays = LeaveAppl.getNoOfDaysBetween(fromDate, toDate);
    		if (LeaveAppl.verifyEnoughAL(loginStaff, totalDays)){
    			
    			leaveApplVerify = true;
    			LeaveAppl newLeaveAppl = loginStaff.applyNewLeave(fromDate, toDate);
    			String newLeaveApplString = DBTableLeaveAppl.getLeaveApplRecord(newLeaveAppl);
    			displayMessage = "Leave application done.\nYour application is being processed.\n\n" + newLeaveApplString;

    		} else {
        		errorMessage = "Not enough AL.\nPlease try again.";
    		}
    	} else {
    		errorMessage = "Invalid dates.\nPlease try again.";
    	}
    	return leaveApplVerify;
	}
	
	public static String getErrorMessage(){
		return errorMessage;
	}
	
	public static String getDisplayMessage(){
		return displayMessage;
	}
	
	public static int getApplyNoOfDays(){
		return totalDays;
	}
}
