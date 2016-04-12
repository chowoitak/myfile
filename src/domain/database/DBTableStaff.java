package domain.database;

import java.util.*;
import domain.Staff;

public class DBTableStaff  {
	
	private static HashMap<String, Staff> allStaff = new HashMap<String, Staff>();

	public DBTableStaff() {
	}
	
	/**
	 * Get all staff 
	 * @return
	 */
	public static HashMap<String, Staff> getAllStaff(){
		return allStaff;
	}
	
	/**
	 * Get staff by staffID
	 * @param staffID
	 * @return
	 */
	public static Staff getStaffByID(String staffID) {
		Staff searchingStaff = null;
		if (checkExistenceOfStaff(staffID)){
			searchingStaff = allStaff.get(staffID);
		}
		return searchingStaff;
	}
	
	
	
	/**
	 * Check existence of a staff by staffID
	 * @param staffID
	 * @return
	 */
	public static boolean checkExistenceOfStaff (String staffID){
		if (allStaff.get(staffID)!=null){
			return true;
		} else {
			return false;
		}
	}
			
	/**
	 * Check whether a staff has subordinates or not
	 * @param staffID
	 * @return
	 */
	public static boolean checkExistenceOfSubOrdinates (String staffID){
		boolean hasSubOr = false;
		if (checkExistenceOfStaff(staffID)){
			if (getSubOrdinatesArrayList(staffID).size()>0){
				hasSubOr = true;
			}
		}
		return hasSubOr;
	}
		
	/**
	 * Check whether a staff is a subordinate of a staff
	 * @param staffID
	 * @return
	 */
	public static boolean checkIsSubOrdinate (String suborID, String supID){
		boolean isSubor = false;
		if (checkExistenceOfStaff(suborID) && checkExistenceOfStaff(supID)){
			Staff subor = DBTableStaff.getStaffByID(suborID);
			if ((subor.getSupervisor()!=null) && (supID.equals(subor.getSupervisor().getStaffID()))){
				isSubor = true;
			}
		}
		return isSubor;
	}
	
	/**
	 * Check if a staff can be deleted or not
	 * @param staffIDToBeChecked
	 * @return
	 */
	private static boolean checkCanBeDeleted (String staffIDToBeChecked){
		boolean canBeDeleted = false;
		if (checkExistenceOfStaff(staffIDToBeChecked)){
			if(!staffIDToBeChecked.equals("DIR") && !staffIDToBeChecked.equals("HR")){
				if (getSubOrdinatesArrayList(staffIDToBeChecked).size() == 0){
					canBeDeleted = true;
				}
			}
		}
		return canBeDeleted;
	}
	
	
		
	/**
	 * Insert new staff record to the database
	 * @param nextStaffRecordKey
	 * @param nextStaff
	 */
	public static void insertStaffRecord (String nextStaffRecordKey, Staff nextStaff){
			allStaff.put(nextStaffRecordKey, nextStaff);
	}
	
	/**
	 * Remove a staff from the database
	 * (or re-assign the supervisor of the subordinates)
	 * @param staffIDToBeDeleted
	 * @return
	 */
	public static boolean deleteStaffRecord(String staffIDToBeDeleted) {
		boolean hasBeenDeleted = false;
//		if (checkExistanceOfStaff(staffIDToBeDeleted)){
			if (checkCanBeDeleted(staffIDToBeDeleted)){
				allStaff.remove(staffIDToBeDeleted);
				hasBeenDeleted = true;
			}
//		}
		return hasBeenDeleted;
	}
	

	
	
	/**
	 * Get a list of the subordinates of the given staff
	 * @param staffID
	 * @return
	 */
	public static List<Staff> getSubOrdinatesArrayList(String staffID) {
		List<Staff> subOrdinates = new ArrayList<Staff>();
		if (checkExistenceOfStaff(staffID)){
			for (Iterator<Staff> its = allStaff.values().iterator(); its.hasNext();) {
				Staff tmpStaff = (Staff) its.next();
				if (!tmpStaff.getStaffID().equals(staffID)) {
					Staff tmpSup = tmpStaff.getSupervisor();
					if ((tmpSup != null) && (tmpSup.getStaffID().equals(staffID))) {
						subOrdinates.add(tmpStaff);
					}
				}
			}
		}
		return subOrdinates;
	}	

	/**
	 * Get general info of the subordinates of a given staff
	 * @param staffID
	 * @return
	 */
	public static String getSubOrdinatesString (String staffID) {
		
		String tmpSubOrdinateList = "";
		if (checkExistenceOfStaff(staffID)){
			List<Staff> tmpSubOrdinates =  getSubOrdinatesArrayList(staffID);
			if (tmpSubOrdinates.size()==0){
				tmpSubOrdinateList = "no subordinates";
			} else {
				for (int i=0; i<tmpSubOrdinates.size(); i++) {
					Staff tmpSubOrdinate = tmpSubOrdinates.get(i);
					tmpSubOrdinateList = tmpSubOrdinateList + tmpSubOrdinate.getName() + " (StaffID: " + tmpSubOrdinate.getStaffID() + ")";
					if(i!=tmpSubOrdinates.size()-1){
						tmpSubOrdinateList = tmpSubOrdinateList + ", ";
					}
				}
			}
		}
		return tmpSubOrdinateList;
	}
	
	
	/**
	 * Get String Array of the subordinates of a given staff
	 * @param staffID
	 * @return
	 */
/*	public static String[] getSubOrdinatesStringArray (String staffID) {
		
		String[] tmpSubOrdinateArray = {};
		if (checkExistenceOfStaff(staffID)){
			List<Staff> tmpSubOrdinates =  getSubOrdinatesArrayList(staffID);
			if (tmpSubOrdinates.size()==0){
				tmpSubOrdinateArray[0] = "No subordinates";	
			} else {
				for (int i=0; i<tmpSubOrdinates.size(); i++) {
					Staff tmpSubor = tmpSubOrdinates.get(i);
					tmpSubOrdinateArray[i] = tmpSubor.getName() + "(" + tmpSubor.getStaffID() + ")";
					
				}
			}
		}
		return tmpSubOrdinateArray;
	}
*/	
	/**
	 * Get general info of a staff 
	 * @param staffID
	 * @return
	 */
	public static String getStaffRecordByID (String staffID){
		
		String staffRecord = "";
			
		if (checkExistenceOfStaff(staffID)){
			Staff staff = getStaffByID(staffID);
			String subOrdinateList = getSubOrdinatesString(staffID);
			String displaySupervisor = "";
							
			if (staff.getSupervisor() == null) {
				displaySupervisor = "No supervisor";
			} else {
				displaySupervisor = staff.getSupervisor().getName() + " (StaffID: " + staff.getSupervisor().getStaffID() + ")";
			}

						staffRecord = staffRecord + staffID + "\n                "
						+ "Name: " + staff.getName() + ";    "
						+ "Title: " + staff.getTitle() + ";    "
						+ "Supervisor: " + displaySupervisor + ";\n                "
						+ "NoOfAL: " + staff.getNoOfALString() + ";    "
						+ "Password: " + staff.getPassword() + ";    "
						+ "Subordinates: " + subOrdinateList +"\n\n";
		}
		return staffRecord;
	}
	
	public static String getStaffRecordMsgByID (String staffID){
		
		String staffRecord = "";
			
		if (checkExistenceOfStaff(staffID)){
			Staff staff = getStaffByID(staffID);
			String subOrdinateList = getSubOrdinatesString(staffID);
			String displaySupervisor = "";
							
			if (staff.getSupervisor() == null) {
				displaySupervisor = "No supervisor";
			} else {
				displaySupervisor = staff.getSupervisor().getName() + " (StaffID: " + staff.getSupervisor().getStaffID() + ")";
			}

						staffRecord = staffRecord + staffID + "\n                "
						+ "Name: " + staff.getName() + ";\n                "
						+ "Title: " + staff.getTitle() + ";\n                "
						+ "Supervisor: " + displaySupervisor + ";\n                "
						+ "NoOfAL: " + staff.getNoOfALString() + ";\n                "
						+ "Password: " + staff.getPassword() + ";\n                "
						+ "Subordinates: " + subOrdinateList + ".\n\n";
		}
		return staffRecord;
	}
	

	
	
	/**
	 * Get general info of all the staff 
	 * @return
	 */
	public static String getAllStaffRecords(){
		
		String allStaffRecords = "\nAll Staff Records:\n\n";
		
		for (Iterator<String> its = allStaff.keySet().iterator(); its.hasNext();) {
			String tmpStaffID = (String) its.next();
			allStaffRecords = allStaffRecords + getStaffRecordByID(tmpStaffID);
		}
		return allStaffRecords; 
	}

}
