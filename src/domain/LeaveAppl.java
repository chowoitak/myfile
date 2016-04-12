package domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import domain.database.DBTableLeaveAppl;

public class LeaveAppl {
	private static int nextLeaveApplID = 1;
	
	private String applID = null;
	private Staff applicant = null;
	private Staff nextEndorser = null;
	private Staff previousEndorser = null;
	private Staff applHandler = null;
	private List<Staff> allEndorserList = new ArrayList<Staff>();
	private Date startDate = null;
	private Date endDate = null;
	private String status = null;
	private Timestamp applyTime = null;
	private Timestamp approveTime = null;
	private boolean noticeMsgRead = false;
	
	public static final String APPL_STATUS_APPROVED = "Approved";
	public static final String APPL_STATUS_PENDING = "Pending Approval";
	public static final String APPL_STATUS_REJECTED = "Rejected";
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	private LeaveAppl(Staff applicant, Date startDate, Date endDate) {
		this.applicant = applicant;
		nextEndorser = applicant.getSupervisor();
		this.startDate = startDate;
		this.endDate = endDate;
		applyTime = new Timestamp(System.currentTimeMillis());
		status = APPL_STATUS_PENDING;
	}
	
	private static String genNewLeaveApplID() {
		String newLeaveApplID = Integer.toString(nextLeaveApplID);
		while (newLeaveApplID.length() < 3) {
			newLeaveApplID = "0" + newLeaveApplID;
		}
		newLeaveApplID = "APPL" + newLeaveApplID;
		nextLeaveApplID++;
		return newLeaveApplID;
	}
	
	// new a leave application and add the record to the leave appl DB
	public static LeaveAppl newLeaveAppl(Staff applicant, Date startDate, Date endDate){
		LeaveAppl newLeaveAppl = null;
		int totalDays = LeaveAppl.getNoOfDaysBetween(startDate, endDate);
		
		if ((verifyValidDates(startDate, endDate)) && (applicant.getNoOfAL()>=totalDays)){
			newLeaveAppl = new LeaveAppl(applicant, startDate, endDate);
			newLeaveAppl.setApplID();
			DBTableLeaveAppl.insertLeaveApplRecord(newLeaveAppl.getApplID(),newLeaveAppl);
		}
		return newLeaveAppl;
	}
	
	
	public String getApplID() {
		return applID;
	}
	private void setApplID() {
		this.applID = genNewLeaveApplID();
	}
	
	public Staff getApplicant() {
		return applicant;
	}
	private void setApplicant(Staff applicant) {
		this.applicant = applicant;
	}
	
	public Staff getNextEndorser() {
		return nextEndorser;
	}
	private void setNextEndorser(Staff nextEndorser) {
		this.nextEndorser = nextEndorser;
	}
	public Staff getPreviousEndorser() {
		return previousEndorser;
	}
	private void setPreviousEndorser(Staff previousEndorser) {
		this.previousEndorser = previousEndorser;
	}
	public List<Staff> getAllEndorserList() {
		return allEndorserList;
	}
	private void addToAllEndorserList(Staff endorser) {
		allEndorserList.add(endorser);
	}
	public void updateEndorsers (Staff previousEndorser, Staff nextEndorser){
		setPreviousEndorser(previousEndorser);
		setNextEndorser(nextEndorser);
		addToAllEndorserList(previousEndorser);
	}
	
	public Staff getApplHandler() {
		return applHandler;
	}
	public void setApplHandler(Staff applHandler) {
		this.applHandler = applHandler;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getNoOfDaysApply(){
		int noOfDaysApply = getNoOfDaysBetween(getStartDate(), getEndDate());
		if (noOfDaysApply < 0){
			noOfDaysApply = 0;
		}
		return noOfDaysApply;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Timestamp getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}
	
	public Timestamp getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	public boolean getNoticeMsgRead() {
		return noticeMsgRead;
	}
	public void setNoticeMsgRead(boolean noticeMsgRead) {
		this.noticeMsgRead = noticeMsgRead;
	}
	


	/**
	 * Get no of days between 2 days (inclusive)
	 */
	public static int getNoOfDaysBetween(Date fromDate, Date toDate) {
		
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(fromDate);
		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(toDate);
		Calendar cal = (Calendar) fromCalendar.clone();
		int daysBetween = 0;
		
		String formDateString = sdf.format(fromCalendar.getTime());
		String toDateString = sdf.format(toCalendar.getTime());
		
		if(formDateString.equals(toDateString)){
			daysBetween = 1;
		} else {          
			if (cal.before(toCalendar)){
				while (cal.before(toCalendar)) {
					cal.add(Calendar.DAY_OF_MONTH, 1);
					daysBetween++;
				}
				daysBetween++;
			} else {
				daysBetween = -1;
			}
		}
		return daysBetween;
	}
	public static String getNoOfDaysBetweenString (Date fromDate, Date toDate) {
		int noOfDays = getNoOfDaysBetween(fromDate, toDate);
		String totalDaysString = noOfDays + " days";
		if (noOfDays==1){
			totalDaysString = "1 day";
		}
		return totalDaysString;
	}
		
	/**
	 * Check if the dates are valid (i.e. startDate after current day, endDate after startDate)
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static boolean verifyValidDates (Date fromDate, Date toDate){
		Calendar cal = Calendar.getInstance();
		if ((getNoOfDaysBetween(cal.getTime(),fromDate)>0) && (getNoOfDaysBetween(fromDate, toDate)>0) ){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check if the applicant has enough noOfAL for the leave application
	 * @param staff
	 * @param leaveAppl
	 * @return
	 */
	public static boolean verifyEnoughAL (Staff staff, LeaveAppl leaveAppl){
		int noOfDaysApply = LeaveAppl.getNoOfDaysBetween(leaveAppl.getStartDate(), leaveAppl.getEndDate());
		if (staff.getNoOfAL() >= noOfDaysApply){
			return true;
		} else {
			return false;
		}
	}
	public static boolean verifyEnoughAL (Staff staff, int noOfDaysApply){
		if (staff.getNoOfAL() >= noOfDaysApply){
			return true;
		} else {
			return false;
		}
	}


}