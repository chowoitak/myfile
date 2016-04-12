package UI.action;

import UI.*;
import domain.*;
import domain.database.*;
import java.util.List;


public class SendResultNotice {
		
	public static void sendNotice(Staff applicant){
		if (DBTableLeaveAppl.getHandledLeaveApplToBeNotice(applicant)!=null){
			
			List<LeaveAppl> leaveApplToBeNotice = DBTableLeaveAppl.getHandledLeaveApplToBeNotice(applicant);
			
			if (leaveApplToBeNotice.size()!=0){
				for (int i=0; i<leaveApplToBeNotice.size(); i++) {
					if (leaveApplToBeNotice.get(i)!=null){
						LeaveAppl tmpLeaveAppl = leaveApplToBeNotice.get(i);
							String displayText = DBTableLeaveAppl.getLeaveApplRecord(tmpLeaveAppl) + DBTableLeaveAppl.getLeaveApplRecordEndorserDetails(tmpLeaveAppl);
							new NoticeDialog(tmpLeaveAppl, displayText);
							
						}
					}
				}
			}
	}
}

