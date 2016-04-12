package domain.database;

import domain.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class DBTableLeaveAppl {
	
	private static HashMap<String, LeaveAppl> allLeaveAppl = new HashMap<String, LeaveAppl>();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public DBTableLeaveAppl() {
	}
	
	
	/**
	 * Insert new leave application record to the database
	 * @param nextLeaveApplRecordKey
	 * @param nextLeaveAppl
	 */
	public static void insertLeaveApplRecord (String nextLeaveApplRecordKey, LeaveAppl nextLeaveAppl){
		allLeaveAppl.put(nextLeaveApplRecordKey, nextLeaveAppl);
	}
	
	/**
	 * Get all leave applications
	 * @return
	 */
	public static HashMap<String, LeaveAppl> getAllLeaveAppl(){
		return allLeaveAppl;
	}
	
	/**
	 * Get leave application by leaveApplID
	 * @param leaveApplID
	 * @return
	 */
	public static LeaveAppl getLeaveApplByID(String leaveApplID) {
		LeaveAppl searchingLeaveAppl = null; 
		if (checkExistenceOfLeaveAppl(leaveApplID)){
			searchingLeaveAppl = allLeaveAppl.get(leaveApplID);
		}
		return searchingLeaveAppl;
	}
	
	/**
	 * Check existence of a leave application
	 * @param leaveApplID
	 * @return
	 */
	public static boolean checkExistenceOfLeaveAppl(String leaveApplID){
		if (allLeaveAppl.get(leaveApplID)!=null){
			return true;
		} else {
			return false;
		}
	}
	
	
	
	// Search leave applications
	 /**
	 * Get a list of all leave applications
	 */
	public static List<LeaveAppl> getAllLeaveApplList() {
		return getLeaveAppsList(null, null, null);
	}

	/**
	 * Get a list of all APPROVED applications
	 */
	public static List<LeaveAppl> getAllApprovedLeaveAppl() {
		return getLeaveAppsList(null, null, LeaveAppl.APPL_STATUS_APPROVED);
	}

	/**
	 * Get a list of all PENDING applications
	 */
	public static List<LeaveAppl> getAllPendingLeaveAppl() {
		return getLeaveAppsList(null, null, LeaveAppl.APPL_STATUS_PENDING);
	}

	/**
	 * Get a list of all REJECTED applications
	 */
	public static List<LeaveAppl> getAllRejectedLeaveAppl() {
		return getLeaveAppsList(null, null, LeaveAppl.APPL_STATUS_REJECTED);
	}
	
	
	
	// Search leave applications by individual applicant
	/**
	 * Get a list of all leave applications by individual applicant
	 * @param applicant
	 * @return
	 */
	public static List<LeaveAppl> getAllLeaveApplByApplicant(Staff applicant) {
		return getLeaveAppsList(applicant, null, null);
	}

	/**
	 * Get a list of all APPROVED applications by individual applicant
	 * @param applicant
	 * @return
	 */
	public static List<LeaveAppl> getAllApprovedLeaveApplByApplicant(Staff applicant) {
		return getLeaveAppsList(applicant, null, LeaveAppl.APPL_STATUS_APPROVED);
	}

	/**
	 * Get a list of all PENDING applications by individual applicant
	 * @param applicant
	 * @return
	 */
	public static List<LeaveAppl> getAllPendingLeaveApplByApplicant(Staff applicant) {
		return getLeaveAppsList(applicant, null, LeaveAppl.APPL_STATUS_PENDING);
	}	

	/**
	 * Get a list of all REJECTED applications by individual applicant
	 * @param applicant
	 * @return
	 */
	public static List<LeaveAppl> getAllRejectedLeaveApplByApplicant(Staff applicant) {
		return getLeaveAppsList(applicant, null, LeaveAppl.APPL_STATUS_REJECTED);
	}
	
	/**
	 * Get a list of all Handled applications with notice not yet sent to the individual applicant
	 * @param applicant
	 * @return
	 */
	public static List<LeaveAppl> getHandledLeaveApplToBeNotice(Staff applicant) {
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			if ((tmpLeaveAppl.getApplicant().equals(applicant))&& (tmpLeaveAppl.getApplHandler()!=null) && (tmpLeaveAppl.getNoticeMsgRead()==false)){
				leaveApplList.add(tmpLeaveAppl);
			}
		}
		return leaveApplList;
	}
	
	
	

	// Search leave applications by individual endorser		
	/**
	 * Get a list of all leave applications which endorsed by individual endorser (if staff exists in allEndorserList && status==approved))
	 * @param endorser
	 * @return
	 */
	public static List<LeaveAppl> getAllLeaveApplEndorsedByEndorser(Staff endorser){
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			if (checkIsEndorserInEndorserList(endorser, tmpLeaveAppl)){
				leaveApplList.add(tmpLeaveAppl);
			}
		}
		return leaveApplList;
	}
	public static List<LeaveAppl> getAllLeaveApplEndorsedByEndorser(Staff endorser, Staff subor){
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			if (checkIsEndorserInEndorserList(endorser, tmpLeaveAppl)){
				if (subor.equals(tmpLeaveAppl.getApplicant())){
					leaveApplList.add(tmpLeaveAppl);
				}
			}
		}
		return leaveApplList;
	}

	/**
	 * Get a list of APPROVED leave applications which endorsed by individual endorser (if staff exists in allEndorserList && status==approved))
	 * @param endorser
	 * @return
	 */
	public static List<LeaveAppl> getAllApprovedLeaveApplEndorsedByEndorser(Staff endorser) {
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			
			if (checkIsEndorserInEndorserList(endorser, tmpLeaveAppl)){
				if (tmpLeaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_APPROVED)){
							leaveApplList.add(tmpLeaveAppl);
				}
			}
		}
		return leaveApplList;
	}
	public static List<LeaveAppl> getAllApprovedLeaveApplEndorsedByEndorser(Staff endorser, Staff subor) {
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			
			if (checkIsEndorserInEndorserList(endorser, tmpLeaveAppl)){
				if (tmpLeaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_APPROVED)){
					if (subor.equals(tmpLeaveAppl.getApplicant())){
							leaveApplList.add(tmpLeaveAppl);
					}
				}
			}
		}
		return leaveApplList;
	}
	
	/**
	 * Get a list of PENDING leave applications which endorsed by individual endorser (if staff exists in allEndorserList && status==pending))
	 * @param endorser
	 * @return
	 */
	public static List<LeaveAppl> getAllPendingLeaveApplEndorsedByEndorser(Staff endorser) {
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			
			if (checkIsEndorserInEndorserList(endorser, tmpLeaveAppl)){
				if (tmpLeaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_PENDING)){
							leaveApplList.add(tmpLeaveAppl);
				}
			}
		}
		return leaveApplList;
	}
	public static List<LeaveAppl> getAllPendingLeaveApplEndorsedByEndorser(Staff endorser, Staff subor) {
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			
			if (checkIsEndorserInEndorserList(endorser, tmpLeaveAppl)){
				if (tmpLeaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_PENDING)){
					if (subor.equals(tmpLeaveAppl.getApplicant())){
							leaveApplList.add(tmpLeaveAppl);
					}
				}
			}
		}
		return leaveApplList;
	}
	
	/**
	 * Get a list of REJECTED leave applications which endorsed by individual endorser (if staff exists in allEndorserList && status==rejected))
	 * @param endorser
	 * @return
	 */
	public static List<LeaveAppl> getAllRejectedLeaveApplEndorsedByEndorser(Staff endorser) {
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			
			if (checkIsEndorserInEndorserList(endorser, tmpLeaveAppl)){
				if (tmpLeaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_REJECTED)){
					leaveApplList.add(tmpLeaveAppl);
				}
			}
		}
		return leaveApplList;
	}
	public static List<LeaveAppl> getAllRejectedLeaveApplEndorsedByEndorser(Staff endorser, Staff subor) {
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
			
			if (checkIsEndorserInEndorserList(endorser, tmpLeaveAppl)){
				if (tmpLeaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_REJECTED)){
					if (subor.equals(tmpLeaveAppl.getApplicant())){
						leaveApplList.add(tmpLeaveAppl);
					}
				}
			}
		}
		return leaveApplList;
	}
	

	/**
	 * Get a list of PENDING leave applications waiting for handled by individual endorser
	 * @param endorser
	 * @return
	 */
	public static List<LeaveAppl> getAllPendingLeaveApplByEndorser(Staff endorser) {
		return getLeaveAppsList(null, endorser, LeaveAppl.APPL_STATUS_PENDING);
	}	

	/**
	 * Get a list of REJECTED leave applications by individual endorser
	 * @param endorser
	 * @return
	 */
	public static List<LeaveAppl> getAllRejectedLeaveApplRejectedByEndorser(Staff endorser) {
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
				
			if (endorser.equals(tmpLeaveAppl.getApplHandler())){
				if (tmpLeaveAppl.getStatus().equals(LeaveAppl.APPL_STATUS_REJECTED)){
							leaveApplList.add(tmpLeaveAppl);
				}
			}
		}
		return leaveApplList;
	}
	
	
	
	/**
	 * Get number of the pendingApprovals has to be handle by the endorser
	 * @param endorser
	 * @return
	 */
	public static int getNoOfPendingApprovalsByEndorser(Staff endorser) {
		List<LeaveAppl> tmpPendingApprovals = getAllPendingLeaveApplByEndorser(endorser);
		int count = tmpPendingApprovals.size();
		return count;
	}
	
	/**
	 * Check if the staff is an endorser of a leave application
	 * @param endorser
	 * @param leaveAppl
	 * @return
	 */
	public static boolean checkIsEndorserOfAppl(Staff endorser, LeaveAppl leaveAppl){

		boolean isEndorser = false;	
		if (endorser.equals(leaveAppl.getNextEndorser())){
			isEndorser = true;
		} else {		
			if (checkIsEndorserInEndorserList(endorser, leaveAppl)){			
				isEndorser = true;
			}
		}
		return isEndorser;
	}	
	
	private static boolean checkIsEndorserInEndorserList(Staff endorser, LeaveAppl leaveAppl){

		boolean isEndorserInList = false;	
		List<Staff> endorserList = leaveAppl.getAllEndorserList();
			if (endorserList.size()!=0){			
				for (int i=0; i<endorserList.size(); i++) {
					if (endorserList.get(i)!=null){
						Staff tmpEndorser = endorserList.get(i);
						if (tmpEndorser.equals(endorser)){
							isEndorserInList = true;
						}
					}
				}
			}
		return isEndorserInList;
	}
	
	
	// Get leave application records
	/**
	 * Get general info of a leave application
	 * @param leaveAppl
	 * @return
	 */
	public static String getLeaveApplRecord (LeaveAppl leaveAppl){
			
		String leaveApplRecordString = "";
		int totalDays = LeaveAppl.getNoOfDaysBetween(leaveAppl.getStartDate(), leaveAppl.getEndDate());
		String displayUnit = " days";
		String displayApproveTime = "N.A.";
		if (totalDays==1){
			displayUnit = " day";
		}			
		if (leaveAppl.getApproveTime()!=null){
			displayApproveTime = sdf2.format(leaveAppl.getApproveTime());
		}
		leaveApplRecordString = leaveAppl.getApplID() + "\n                  "
							+ "Status: "        	+ leaveAppl.getStatus() + "\n                  "
							+ "Applicant: "			+ leaveAppl.getApplicant().getName()
							+ " (StaffID: "			+ leaveAppl.getApplicant().getStaffID() + ")" + ";     "
							+ "No. of AL Left: "	+ leaveAppl.getApplicant().getNoOfAL() + displayUnit + "\n                  "
							+ "Applying Period: " + "From  " + sdf.format(leaveAppl.getStartDate())
																	+ "  To  " + sdf.format(leaveAppl.getEndDate())
																	+ "  (" + totalDays + displayUnit + ")  " + "\n                  "
							+ "Apply Time: "		+ sdf2.format(leaveAppl.getApplyTime()) + ";     "
							+ "Approve Time: "		+ displayApproveTime;	
		return leaveApplRecordString;
	}
	
	/**
	 * Get all the endorsers of a leave application
	 * @param leaveAppl
	 * @return
	 */
	public static String getLeaveApplAllEndorsers(LeaveAppl leaveAppl){

		List<Staff> allEndorserList = leaveAppl.getAllEndorserList();
		String allEndorserString = "";
		
		if (allEndorserList.size()!=0){			
			for (int i=0; i<allEndorserList.size(); i++) {
				if (allEndorserList.get(i)!=null){
					Staff tmpEndorser = allEndorserList.get(i);
					allEndorserString = allEndorserString + tmpEndorser.getName() + " (StaffID: " + tmpEndorser.getStaffID() + ")";
					if(i!=allEndorserList.size()-1){
						allEndorserString = allEndorserString + ", ";
					}
				}
			}
		}
		return allEndorserString;
	}	
		
	/**
	 * Get endorsers details of a leave application (Previous endorser/Next endorser/Application Handler)
	 * @param leaveAppl
	 * @return
	 */
	public static String getLeaveApplRecordEndorserDetails (LeaveAppl leaveAppl){
		
		String endorserDetailsString = "";
		String displayPreEndorser = "";
		String displayApplHandler = "";
		String displayNextEndorser = "";
		if (leaveAppl.getPreviousEndorser()==null){
			displayPreEndorser = "New application. (No previous endorser)";
		} else {
			displayPreEndorser = leaveAppl.getPreviousEndorser().getName() + " (StaffID: " + leaveAppl.getPreviousEndorser().getStaffID() + ")";
		}
		if (leaveAppl.getApplHandler()==null){
			displayApplHandler = "N.A.";
		} else {
			displayApplHandler = leaveAppl.getApplHandler().getName() + " (StaffID: " + leaveAppl.getApplHandler().getStaffID() + ")";
		}
		if (leaveAppl.getNextEndorser()==null){
			displayNextEndorser = "Application has been handled. (No next endorser)";
		} else {
			displayNextEndorser = leaveAppl.getNextEndorser().getName() + " (StaffID: " + leaveAppl.getNextEndorser().getStaffID() + ")";
		}
		
		endorserDetailsString = 
							//leaveAppl.getApplID() + " "
							"\n                  " 
							+ "Previous Endorser: "   + displayPreEndorser
							+";\n                  " 
							+ "Next Endorser: "		+ displayNextEndorser
							+";\n                  " 
							+ "Application Handler: "	+ displayApplHandler
							+ ";\n                  "
							+ "All Endorsers List: " + getLeaveApplAllEndorsers(leaveAppl)
							+ ";\n\n";
				return endorserDetailsString;
	}
	
	/**
	 * Get details of all the leave application records
	 * @param leaveApplList
	 * @return
	 */
	public static String getLeaveApplRecordDetails(List<LeaveAppl> leaveApplList){
		
		String allLeaveApplRecords = "\nLeave Application Records:\n\n";
				
		if (leaveApplList.size()==0){
			allLeaveApplRecords = allLeaveApplRecords + "No applications found.";
		} else {
			for (int i=0; i<leaveApplList.size(); i++) {
				LeaveAppl tmpLeaveAppl = leaveApplList.get(i);
					allLeaveApplRecords = allLeaveApplRecords + getLeaveApplRecord(tmpLeaveAppl) + "\n" + getLeaveApplRecordEndorserDetails(tmpLeaveAppl) + "\n\n";
				}
		}
		return allLeaveApplRecords;
	}

	
	

	/**
	 * Get leave applications by individual staff or endorser or status
	 * @param applicant
	 * @param status
	 * @return
	 */
	public static List<LeaveAppl> getLeaveAppsList(Staff applicant, Staff endorser, String status) {
		
		List<LeaveAppl> leaveApplList = new ArrayList<LeaveAppl>();
		
		for (Iterator<LeaveAppl> its = allLeaveAppl.values().iterator(); its.hasNext();) {
			LeaveAppl tmpLeaveAppl = (LeaveAppl) its.next();
	
			//all applicant & all endorser & all status
			if ((applicant == null) && (endorser == null) && (status == null)){
				leaveApplList.add(tmpLeaveAppl);
			}
			//all applicant & all endorser & specific status
			if ((applicant == null) && (endorser == null) && (status != null)){
				if (status.equals(tmpLeaveAppl.getStatus())){
					leaveApplList.add(tmpLeaveAppl);
				}
			}
			//all applicant & specific endorser & all status
			if ((applicant == null) && (endorser != null) && (status == null)){
				if (tmpLeaveAppl.getNextEndorser()!=null){
					if (endorser.equals(tmpLeaveAppl.getNextEndorser())){
						leaveApplList.add(tmpLeaveAppl);
					}
				}
			}
			//all applicant & specific endorser & specific status
			if ((applicant == null) && (endorser != null) && (status != null)){
				if (tmpLeaveAppl.getNextEndorser()!=null){
					if ((endorser.equals(tmpLeaveAppl.getNextEndorser())) && (status.equals(tmpLeaveAppl.getStatus()))){
						leaveApplList.add(tmpLeaveAppl);
					}
				}
			}
			
			//specific applicant & all endorser & all status
			if ((applicant != null) && (endorser == null) && (status == null)){
				if (applicant.equals(tmpLeaveAppl.getApplicant())){
					leaveApplList.add(tmpLeaveAppl);
				}
			}
			//specific applicant & all endorser & specific status
			if ((applicant != null) && (endorser == null) && (status != null)){
				if ((applicant.equals(tmpLeaveAppl.getApplicant())) && (status.equals(tmpLeaveAppl.getStatus()))){
					leaveApplList.add(tmpLeaveAppl);
				}
			}
			//specific applicant & specific endorser & all status
			if ((applicant != null) && (endorser != null) && (status == null)){
				if (tmpLeaveAppl.getNextEndorser()!=null){
					if ((tmpLeaveAppl.getApplicant().equals(applicant)) && (tmpLeaveAppl.getNextEndorser().equals(endorser))){
						leaveApplList.add(tmpLeaveAppl);
					}
				}
			}
			//specific applicant & specific endorser & specific status
			if ((applicant != null) && (endorser != null) && (status != null)){
				if (tmpLeaveAppl.getNextEndorser()!=null){
					if ((tmpLeaveAppl.getApplicant().equals(applicant)) && (tmpLeaveAppl.getNextEndorser().equals(endorser)) && (tmpLeaveAppl.getStatus().equals(status))){
						leaveApplList.add(tmpLeaveAppl);
					}
				}
			}
		}
		return leaveApplList;
	}
	

}
