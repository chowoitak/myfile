package UI.action;

import domain.HRStaff;
import domain.Staff;
import domain.database.DBTableStaff;

public class StaffAccMgtAction {

	private static String errorMessage = null;
	private static String displayMessage = null;
	
	public static boolean verifyValidNewStaff(String staffName, String title, String supervisorID){
		
	    boolean validNewStaff = false;
		String tmpStaffID = null;		// New StaffID not yet generate before enter data verifications
        Staff newStaff = null;
        
        if (staffName.equals("") || title.equals("") || supervisorID.equals("")){
        	errorMessage =  "Please fill in all the required fields.";
        } else {
        	if (staffName.equals("DIR") || staffName.equals("HR")){
        		errorMessage = "The \"" + staffName + "\" staff cannot be created.";
        	} else {
            	HRStaff.getExistingHR();
				if (!HRStaff.verifyValidName(staffName)){
					errorMessage = "Invalid name.\nPlease enter name with at least 3 characters.";
            	} else {
            		if (supervisorID.equals("HR")){
            			errorMessage = "The \"HR\" Staff cannot be assigned as a supervisor.";
            		} else {
            			if (!HRStaff.verifyValidSupervisor(supervisorID, tmpStaffID)){
            				errorMessage = "Invalid supervisor.";
            			} else {
            				
							HRStaff.getExistingHR();
							newStaff = HRStaff.createNewStaff(staffName, title, supervisorID);
            				
							if (newStaff!=null){
            					displayMessage = "New staff has been created.\n\n" + DBTableStaff.getStaffRecordMsgByID(newStaff.getStaffID());
            					validNewStaff = true;
							} else {
            					errorMessage = "New staff cannot be created.\nPlease try again.";
            				}
            			}
            		}
            	}
        	}
        }
        return validNewStaff;
    }
	
	
	public static boolean verifyValidStaffToBeDeleted(String staffIDToBeDeleted){
		
	//String staffIDToBeDeleted = staffIDTextField.getText();
	
		boolean validStaffToBeDeleted = false;
			
		if (DBTableStaff.checkExistenceOfStaff(staffIDToBeDeleted)){
			Staff staffToBeDeleted = DBTableStaff.getStaffByID(staffIDToBeDeleted);
			String staffDetails = DBTableStaff.getStaffRecordMsgByID(staffToBeDeleted.getStaffID());
			
			HRStaff.getExistingHR();
			boolean canBeDeleted = HRStaff.deleteStaff(staffIDToBeDeleted);
			
			if (canBeDeleted){
				displayMessage = "The following staff has been removed.\n\n" + staffDetails;
				validStaffToBeDeleted = true;
			} else {
				if (staffToBeDeleted.getStaffID().equals("DIR") || staffToBeDeleted.getStaffID().equals("HR")){
					errorMessage = "The \"" + staffToBeDeleted.getStaffID() + "\" staff cannot be removed.";
				} else {
					errorMessage = "The following staff cannot be removed.\n\n" + staffDetails;
				}
			}
		} else {
			errorMessage = "The input staffID (ID:" + staffIDToBeDeleted + ") is not valid.\n Please enter a valid staffID.";
		}
		return validStaffToBeDeleted;
	}

	
	public static String getErrorMessage(){
		return errorMessage;
	}
	
	public static String getDisplayMessage(){
		return displayMessage;
	}
		
	
}
