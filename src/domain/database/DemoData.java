package domain.database;

import domain.*;
import UI.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DemoData {
	
	public static void createBatachRecords() throws Exception{
		
		// Creating batch of staff
		Staff director = Director.newDirector("The Only Director");
		Staff hrstaff = HRStaff.newHRStaff("The Only HRStaff");													//StaffID	Supervisor
		
		HRStaff.getExistingHR();
		Staff sManagerA = HRStaff.createNewStaff("Senior Manager A", "Senior Manager", "DIR");					//STF001	director
		Staff sManagerB = HRStaff.createNewStaff("Senior Manager B", "Senior Manager", "DIR");					//STF002	director
		Staff jManagerA1 = HRStaff.createNewStaff("Junior Manager A1", "Junior Manager", "STF001");				//STF003	sManagerA
		Staff jManagerA2 = HRStaff.createNewStaff("Junior Manager A2", "Junior Manager", "STF001");				//STF004	sManagerA
		Staff jManagerB1 = HRStaff.createNewStaff("Junior Manager B1", "Junior Manager", "STF002");				//STF005	sManagerB
		Staff sAssistantA1a = HRStaff.createNewStaff("Senior Assistant A1a", "Senior Assistant", "STF003");		//STF006	jManagerA1
		Staff sAssistantA1b = HRStaff.createNewStaff("Senior Assistant A1b", "Senior Assistant", "STF003");		//STF007	jManagerA1
		Staff sAssistantA1c = HRStaff.createNewStaff("Senior Assistant A1c", "Senior Assistant", "STF003");		//STF008	jManagerA1
		Staff sAssistantA2a = HRStaff.createNewStaff("Senior Assistant A2a", "Senior Assistant", "STF004");		//STF009	jManagerA2
		Staff sAssistantB1a = HRStaff.createNewStaff("Senior Assistant B1a", "Senior Assistant", "STF005");		//STF010	jManagerB1
		Staff jAssistantA1a1 = HRStaff.createNewStaff("Junior Assistant A1a1", "Junior Assistant", "STF006");	//STF011	sAssistantA1a
		Staff jAssistantA1a2 = HRStaff.createNewStaff("Junior Assistant A1a1", "Junior Assistant", "STF006");	//STF012	sAssistantA1a

		
		// Creating batch of leave
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fromDateStg1 = "2016-05-01";
		String toDateStg1 = "2016-05-05";
		String fromDateStg2 = "2016-05-07";
		String toDateStg2 = "2016-05-07";
		String fromDateStg3 = "2016-05-20";
		String toDateStg3 = "2016-05-26";
		String fromDateStg4 = "2016-06-08";
		String toDateStg4 = "2016-06-10";
				
		Date fromDate1 = sdf.parse(fromDateStg1);
		Date toDate1 = sdf.parse(toDateStg1);
		Date fromDate2 = sdf.parse(fromDateStg2);
		Date toDate2 = sdf.parse(toDateStg2);
		Date fromDate3 = sdf.parse(fromDateStg3);
		Date toDate3 = sdf.parse(toDateStg3);
		Date fromDate4 = sdf.parse(fromDateStg4);
		Date toDate4 = sdf.parse(toDateStg4);
		LeaveAppl la1 = sManagerA.applyNewLeave(fromDate1, toDate1);
		LeaveAppl la2 = sManagerA.applyNewLeave(fromDate3, toDate3);
		LeaveAppl la3 = sManagerB.applyNewLeave(fromDate1, fromDate1);
		LeaveAppl la4 = jManagerA1.applyNewLeave(fromDate2, toDate2);
		LeaveAppl la5 = jManagerA2.applyNewLeave(fromDate1, fromDate1);
		LeaveAppl la6 = jManagerB1.applyNewLeave(fromDate4, fromDate4);
		LeaveAppl la7 = sAssistantA1a.applyNewLeave(fromDate2, toDate2);
		LeaveAppl la8 = sAssistantA1b.applyNewLeave(fromDate3, toDate3);
		LeaveAppl la9 = sAssistantA2a.applyNewLeave(fromDate2, toDate2);
		LeaveAppl la10 = sAssistantB1a.applyNewLeave(fromDate1, fromDate1);
		LeaveAppl la11 = jAssistantA1a1.applyNewLeave(fromDate4, fromDate4);
		LeaveAppl la12 = jAssistantA1a2.applyNewLeave(fromDate3, fromDate3);

//		generate the windows for testing
//		new DBWindowLeaveAppl("testing");		
//		new DBWindowStaff();
//		new EndorsedHistoryWindow(sManagerA);
//		new LeaveApplHRWindow(hrstaff);
//		new LeaveApplWindow(sManagerA);
//		new LeaveMgtWindow(sManagerA);
//		new LoginWindow();
//		new NoticeDialog(la1,"testing");
//		new StaffAccMgtWindow(hrstaff);
	}
	

}
