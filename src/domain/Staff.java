package domain;

import domain.database.*;
import UI.LeaveMgtWindow;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Staff {
	protected static int nextStaffID = 1;	

	protected String staffID = null;
	protected String name = null;
	protected String title = null;
	protected Staff supervisor = null;
	protected int noOfAL = 0;
	protected static final int defaultNoOfAL =7;
	protected String password = null;
	
	
	protected Staff(String staffID, String name, String title, String supervisorID) {
		this.staffID = staffID;
		this.name = name;
		this.title = title;
		supervisor = DBTableStaff.getStaffByID(supervisorID);
		noOfAL = defaultNoOfAL;
		String password = "pw" + staffID.toLowerCase();
		this.password = password;
	}
	protected Staff(String name) {
		this.name = name;
	}

	
	public String getStaffID() {
		return staffID;
	}
	// new staffID will generate automatically when a new staff is created
	protected void setStaffID(){
		staffID = genNewStaffID();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(verifyValidName(name)){
			this.name = name;
		}
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title){
		if (verifyValidTitle(title)){
			this.title = title;
		}
	}
	
	public Staff getSupervisor() {
		return supervisor;
	}
	public void setSupervisor (String supervisorID) {
		if (verifyValidSupervisor(supervisorID,this.getStaffID())){	
			this.supervisor = DBTableStaff.getStaffByID(supervisorID);
		}
	}
	
	public int getNoOfAL() {
		return noOfAL;
	}
	public String getNoOfALString (){
		String getNoOfALString = noOfAL + " days";
		if (noOfAL==1){
			getNoOfALString = "1 day";
		}
		return getNoOfALString;
	}
	protected void setNoOfAL(int noOfAL) {
		if (verifyValidNoOfALEnter(noOfAL)){
			this.noOfAL = noOfAL;
		}
	}

	public String getPassword() {
		return password;
	}
	protected void setPassword (String password) {
		if (verifyValidPasswordSet(password)){
			this.password = password;
		}
	}

	
	
	/**
	 * Create new leave application
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public LeaveAppl applyNewLeave (Date startDate, Date endDate){
		LeaveAppl newLeaveAppl = LeaveAppl.newLeaveAppl(this, startDate, endDate);
		return newLeaveAppl;
	}
	
	/**
	 * Endorse leave application. The approver must be the applicant's supervisor
	 * @param leaveAppl
	 * @return
	 */
	 public void endorseLeave(LeaveAppl leaveAppl, boolean decline){

		if ((this.getSupervisor()!=null)){
			this.getSupervisor().updateEndorsers(leaveAppl,this,this.getSupervisor());
		}
	}
	
	/**
	 * Reject leave application. The approver must be the applicant's supervisor
	 * @param leaveAppl
	 */
	public boolean declineLeave(LeaveAppl leaveAppl){
		boolean declined = false;
		if (leaveAppl.getNextEndorser().getStaffID().equals(this.getStaffID())){
			if (leaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_PENDING)){
				leaveAppl.setStatus(LeaveAppl.APPL_STATUS_REJECTED);
				
				this.updateEndorsers(leaveAppl,this,null);
				
				leaveAppl.setApproveTime(new Timestamp(System.currentTimeMillis()));
				leaveAppl.setApplHandler(this);
				declined = true;
			}
		}
		return declined;
	}

	/**
	 * Update the endorsers' info of an application
	 * @param leaveAppl
	 * @param previousEndorser
	 * @param nextEndorser
	 */
	protected void updateEndorsers(LeaveAppl leaveAppl, Staff previousEndorser, Staff nextEndorser){
		leaveAppl.updateEndorsers(previousEndorser,nextEndorser);
	}

	/**
	 * Handling the leave application (return true if decline and return false if endorse)
	 * @param tmpPendingApproval
	 * @param decline
	 * @return
	 */
	public boolean handleApplication(LeaveAppl tmpPendingApproval, boolean decline){
		
		boolean handle = true;
		String displayString = null;
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		String tmpPendingApprovalDetails = DBTableLeaveAppl.getLeaveApplRecord(tmpPendingApproval);
		Staff currentEndorser = tmpPendingApproval.getNextEndorser();
		
		if (currentEndorser.getSupervisor()==null){
			currentEndorser = (Director) tmpPendingApproval.getNextEndorser();
		}
		
		if (decline){
	     	//handle to application
			currentEndorser.declineLeave(tmpPendingApproval);
			displayString = "The application has been declined and notice will be sent to the applicant.\n\n" + "Rejected time ---\n" + sdf2.format(tmpPendingApproval.getApproveTime()) + "\n\n\n"
						+ "Declined appliction details ---\n" + tmpPendingApprovalDetails;
			handle = true;
			//applStatus = LeaveAppl.APPL_STATUS_REJECTED;
	     } else {
	    	 
	    	 if (tmpPendingApproval.getStatus().equals(LeaveAppl.APPL_STATUS_PENDING)){
					int noOfDaysApply = LeaveAppl.getNoOfDaysBetween(tmpPendingApproval.getStartDate(), tmpPendingApproval.getEndDate());
					// check if applicant noOfAL is still >= applying days
					if (LeaveAppl.verifyEnoughAL(tmpPendingApproval.getApplicant(), noOfDaysApply)){

						//pass the application to loginStaff's supervisor
				     		currentEndorser.endorseLeave(tmpPendingApproval, decline);
				     		
				     		if (currentEndorser.getSupervisor()!=null){
				     			displayString = "The application has been endorsed and pass to your supervisor:\n\n" + "The next endorser ---\n" + tmpPendingApproval.getNextEndorser().getName() 
										+ "(StaffID: "+ tmpPendingApproval.getNextEndorser().getStaffID() + ")\n\n\n" + "Endorsed appliction details ---\n" + tmpPendingApprovalDetails ;
				     			handle = false;
				     			//applStatus = LeaveAppl.APPL_STATUS_PENDING;
				     		} else {								
								displayString = "The application has been approved.\n\n" + "Approve time ---\n" + sdf2.format(tmpPendingApproval.getApproveTime()) + "\n\n\n"
				       							+ "Approved appliction details ---\n" + tmpPendingApprovalDetails ;
								handle = true;
								//applStatus = LeaveAppl.APPL_STATUS_APPROVED;
				     		}				
					
					} else {
						// no enough AL
						currentEndorser.declineLeave(tmpPendingApproval);
						displayString = "The application cannot be endorsed.\n\n" + "Pending appliction details ---\n" + tmpPendingApprovalDetails ;
						handle = true;
						//applStatus = LeaveAppl.APPL_STATUS_REJECTED;
					}
	    	 }
	    }
		setDisplayDialog(displayString);
		return handle;
	}
	

	private void setDisplayDialog(String displayString){
		LeaveMgtWindow.setDisplayString(displayString);
	}
	
	/**
	 * Generate new staff ID
	 */
	protected static String genNewStaffID() {
		String newStaffID = Integer.toString(nextStaffID);
		while (newStaffID.length() < 3) {
			newStaffID = "0" + newStaffID;
		}
		newStaffID = "STF" + newStaffID;
		nextStaffID++;
		return newStaffID;
	}
	
	/**
	 * Verify if the name to be set is valid.
	 * @param nameToBeSet
	 * @return
	 */
	public static boolean verifyValidName (String nameToBeSet){
		if (nameToBeSet!=null && nameToBeSet.length()>=3){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verify if the title to be set is valid.
	 * @param titleToBeSet
	 * @return
	 */
	protected static boolean verifyValidTitle (String titleToBeSet){
		if ((titleToBeSet!=null) && 
			(titleToBeSet.equals("Senior Manager") || titleToBeSet.equals("Junior Manager") 
			|| titleToBeSet.equals("Senior Assistant") || titleToBeSet.equals("Junior Assistant"))){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verify if the supervisor to be set is valid.
	 * @param supervisorID
	 * @param staffID
	 * @return
	 */
	public static boolean verifyValidSupervisor (String supervisorID, String staffID){
		boolean exists = false;			
		boolean notHimself = false;		
		boolean notSubordinate = false;	
		
		// Return true if supervisorToBeSet exists.
		if (DBTableStaff.getStaffByID(supervisorID)==null){
			exists = false;
		} else {
			exists = true;
		}
		// Return true if supervisorToBeSet is not the staff himself.
		if (supervisorID.equals(staffID)){
			notHimself = false;
		} else {
			notHimself = true;
		}
		// Return true if supervisorToBeSet is not one of the staff's subordinates.
		List<Staff> subOrdinates =  DBTableStaff.getSubOrdinatesArrayList(staffID);
		notSubordinate = true;		// Assuming supervisorToBeSet is not one of the staff's subordinates.
		for (int i=0; i<subOrdinates.size(); i++){
			Staff tmpSubOrdinate = subOrdinates.get(i);
			if (tmpSubOrdinate.getStaffID().equals(supervisorID)){
				notSubordinate = false;
			}
		}
		
		if ((exists==true && notHimself==true && notSubordinate==true) && (supervisorID.equals("HR"))==false){
			return true;
		} else {
			return false;
		}	
	}

	/**
	 * Verify if the noOfAL to be set is valid.
	 * @param titleToBeSet
	 * @return
	 */
	protected static boolean verifyValidNoOfALEnter (int noOfALToBeSet){
		if (noOfALToBeSet>=0 && noOfALToBeSet<=(defaultNoOfAL*2)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verify if the password to be set is valid.
	 * @param passwordToBeSet
	 * @return
	 */
	protected static boolean verifyValidPasswordSet (String passwordToBeSet){
		if (passwordToBeSet!=null && passwordToBeSet.length()>3){
			return true;
		} else {
			return false;
		}
	}
	
}

