package domain;

import domain.database.*;
import java.sql.Timestamp;
import java.util.Date;

public class Director extends Staff{

	//Only ONE director can be created.
	private static Director existingDir;
	
	protected Director(String name) {
		super(name);
		super.staffID = "DIR";
		super.title = "Director";
		super.supervisor = null;
		super.noOfAL = 0;
		String password = "pw" + super.staffID.toLowerCase();
		super.password = password;
	}
		
	/**
	 * Create a new director (Only when there is no existing director)
	 * @param name
	 * @return
	 */
	public static Director newDirector(String name){
		Director tmpDir = null;
		if(existingDir==null){
			tmpDir = new Director(name);
			DBTableStaff.insertStaffRecord("DIR", tmpDir);
		} else {
			tmpDir = existingDir;
		}
		return tmpDir;
	}

	public static Director getExistingDir(){
		return existingDir;
	}
	
	
	
	@Override
	protected void setStaffID(){
		super.staffID = "DIR";
	}
	
	@Override
	public void setTitle(String title){
		super.setTitle("Director");
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

		if ((leaveAppl.getNextEndorser().getStaffID().equals(this.getStaffID())) && (this.getSupervisor()==null)){
			if (leaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_PENDING)){		
				if (LeaveAppl.verifyEnoughAL(leaveAppl.getApplicant(), leaveAppl)){
					
					leaveAppl.setStatus(LeaveAppl.APPL_STATUS_APPROVED);
					leaveAppl.setApproveTime(new Timestamp(System.currentTimeMillis()));
					leaveAppl.setApplHandler(this);
					leaveAppl.getApplicant().setNoOfAL(leaveAppl.getApplicant().getNoOfAL()-leaveAppl.getNoOfDaysApply()); 
					
					this.updateEndorsers(leaveAppl, this, null);
				}
			}
		}
	}


}
