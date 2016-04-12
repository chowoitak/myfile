How to use the program

Run the project.jar

After the program is executed, you should see a login window. You login with the correct user name and password. A welcome window will popup after login. And other window(s) will popup depends on the type of account you have signed in with.

Demo Accounts:
[Account pattern is LoginID is always capital letters, and the password is "pw"+LoginID with all small letters]
[New staffID (starting with "STF"+a 3-digital number) will be generated automatically when creating a new staff. Staff with staffID "STF001 to STF012", "DIR" and "HR" have already been created when the program is run.]
[There are also some pending applications ready when the program is run.]


Staff that already exist when running the program:
     LoginID                 Password
----------------	----------------
      HR		     pwhr		(The HR Staff)
      DIR		     pwdir		(The Director)
      STF001		     pwstf001		(1st Staff)
      STF002		     pwstf002		(2nd Staff)
      	:			:
	:			:		(and so on)
	:			:
      STF012		     pwstf012		(12th Staff)



1) When a HR staff signed in
	(a) A new window containing functions that allow the user to 
		(i)   create a new user with a supervisor assignment
			Each staff has only one supervisor and a staff can supervise multiple staff;
			Only the Director is allowed to have no supervisor;
			User can search for a valid supervisor before the supervisor assignment;
			The loginID and password of the new created staff are as follows:

		 	    LoginID                 Password
			----------------	----------------
			     STF013		    pwstf013		(13th Staff, the 1st new created staff)
			     STF014		    pwstf014		(14th Staff, the 2nd new created staff)
		    	  	:			:
				:			:		(and so on)

		(ii)  delete an existing user and
			User can search for a valid staff before the deletion.
		(iii) view the staff database
			User can view the details of staff by click the "Staff Database" button;
			A new window showing all the staff with their details, such as staff name, supervisor,
			no. of AL leave, list of subordinates etc.
	(b) A second window allowing the user to view all the leave applications records is also popped upon login.

2) When the Director signed in
	(a) A new window is shown to let the user to view the current leave applications and choose to endorse/decline them.
		(i)   The application will be approved if the director chooses endorse or will be rejected if he chooses decline;
		(ii)  The applicant will see a notice about the successful/unsuccessful approval of his leave;
		(iii) User can also view all the previous endorsed application histories by clicking "Check Endorse History" button;
			The new window have functions that allow the user to show all the endorsed applications 
			by approving categories as well as view by different staff ID individually.

3) When a non-HR/Director staff signed in
	(a) A new window is shown to allow the user to
		(i)  apply leaves;
			User can apply for a leave from a date X to a date Y, and the date must enter in the format "dd-MM-yyyy";
		(ii) check his own application history;
			User can chooses to check all the applications or just the pending approval/approved/rejected applications.
	
	If the login staff has subordinate(s)
	(b) Another window is also shown to let the user to endorse/decline leave applications for his subordinate(s).
		(i)   The application will be passed to the login staff's supervisor if the login staff chooses endorse;
		(ii)  The application will be rejected if he chooses decline;
		(iii) And the applicant will see a notice about the unsuccessful approval of his leave 
		      once the login staff chooses decline;
		(iv)  Just like the Director, the login staff can view all the previous endorsed application histories 
		      by clicking "Check Endorse History" button.



How to read the source code

a) The package domain has the main java file, java files for Staff, Director, HRStaff and LeaveAppl classes. The Director and HRStaff both extend the Staff class. Chain-of-responsibility design pattern was used when the staff is handling the application.
b) The java files within the domain.database contain the initial user accounts data and a HashMap storing all the staffs objects. Another HashMap storing all the leave applications data;
c) The java files within the package UI contain all the code for the setup (e.g. dimension/positions) of each pop up window for different logins;
d) The java files within the packege UI.action are actions and verifications on each pop up window.


