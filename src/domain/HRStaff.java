package domain;

import domain.database.*;
import java.util.Date;

public class HRStaff extends Staff{

	//Only ONE hrstaff can be created.
	private static HRStaff existingHR;
	
	protected HRStaff(String name) {
		super(name);
		super.staffID = "HR";
		super.title = "HRStaff";
		super.supervisor = null;
		super.noOfAL = 0;
		String password = "pw" + super.staffID.toLowerCase();
		super.password = password;
	}
		

	/**
	 * Create a new hrstaff (Only when there is no existing hrstaff)
	 * @param name
	 * @return
	 */
	public static HRStaff newHRStaff(String name){
		HRStaff tmpHR;
		if(existingHR==null){
			tmpHR = new HRStaff(name);
			DBTableStaff.insertStaffRecord("HR", tmpHR);
		} else {
			tmpHR = existingHR;
		}
		return tmpHR;
	}

	public static HRStaff getExistingHR(){
		return existingHR;
	}

	
	
	@Override
	protected void setStaffID(){
		super.staffID = "HR";
	}
	
	@Override
	public void setTitle(String title){
		super.setTitle("HRStaff");
	}
	
	@Override
	public void setSupervisor(String supervisorID) {
		super.setSupervisor(null);
	}
	
	@Override
	public void setNoOfAL(int noOfAL) {
		super.setNoOfAL(0);
	}
	
	
	
	@Override
	public LeaveAppl applyNewLeave (Date startDate, Date endDate){
		return null;
	}
	
	@Override
	public void endorseLeave(LeaveAppl leaveAppl, boolean decline){
//		return false;
	}
	
	@Override
	public boolean declineLeave(LeaveAppl leaveAppl){
		return false;
	}

		

	/**
	 * Create new staff and insert record to staff DB
	 * Verify the parameter before create
	 * @param name
	 * @param title
	 * @param supervisor
	 * @return
	 */
	public static Staff createNewStaff(String name, String title, String supervisorID){
		
		String newStaffID = null;		// StaffID not yet generate before enter data verifications
		boolean validName = verifyValidName(name);
		boolean validTitle = verifyValidTitle(title);
		boolean validSupervisor = verifyValidSupervisor(supervisorID,newStaffID);		
		
		if (validName==true && validTitle==true && validSupervisor==true){
				newStaffID = genNewStaffID();
				Staff newStaff = new Staff(newStaffID, name, title, supervisorID);
				// Insert new staff record to the StaffDatabaseTable
				DBTableStaff.insertStaffRecord(newStaff.getStaffID(), newStaff);
				return newStaff;	
        } else {
        	return null;	
         }
	}
	
	/**
	 * Delete staff and remove record from staff DB
	 * @param staffIDToBeDeleted
	 * @return
	 */
	public static boolean deleteStaff(String staffIDToBeDeleted){
        boolean deleted = DBTableStaff.deleteStaffRecord(staffIDToBeDeleted);
        return deleted;        
	}

	
}

