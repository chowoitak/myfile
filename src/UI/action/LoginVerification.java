package UI.action;

import domain.*;
import domain.database.*;

public class LoginVerification {

	public static boolean loginVerification(String username, String password){
		boolean loginSuccess = false;
		if (verifyUser(username)){
			Staff loginStaff = DBTableStaff.getStaffByID(username);
		
			if(loginStaff.getPassword().equals(password)){
				loginSuccess = true;
			}
		}
		return loginSuccess;
	}
	
	private static boolean verifyUser(String username){
		if(DBTableStaff.checkExistenceOfStaff(username)){
			return true;
		} else {
			return false;
		}
	}
	
}
