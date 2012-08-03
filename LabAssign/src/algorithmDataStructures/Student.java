package algorithmDataStructures;


import java.util.ArrayList;


public class Student {

	private	int UID;
	private	String firstName;
	private	String lastName;
		//last 3 digits of a student number
	private	int studentNum;
	//final assigned lab slot
	private	Timeslot assignedLab;
	//final assigned tutorial Slot
	private	Timeslot assignedTut;
	//students first lab choice from blackboard survey 
	private	ArrayList<Timeslot>  firstLabs = new ArrayList<Timeslot>();
	//students first Tut choice from blackboard survey 
	private	ArrayList<Timeslot>  firstTuts = new ArrayList<Timeslot>();
	//students second lab choice from blackboard survey 
	private	ArrayList<Timeslot>  secondLabs = new ArrayList<Timeslot>();
	//students second tut choice from blackboard survey 
	private	ArrayList<Timeslot>  secondTuts = new ArrayList<Timeslot>();
	//students third lab choice from blackboard survey 
	private	ArrayList<Timeslot>  thirdLabs = new ArrayList<Timeslot>();
	//students third tut choice from blackboard survey
	private	ArrayList<Timeslot> thirdTuts = new ArrayList<Timeslot>();
	//students  cannot attend lab choice from blackboard survey
	private	ArrayList<Timeslot> cannotAttendLabs = new ArrayList<Timeslot>();
	//students  cannot attend tut choice from blackboard survey
	private	ArrayList<Timeslot> cannotAttendTuts = new ArrayList<Timeslot>();
		
		public Student (int UID, String fname, String lname, int sNumb){
			this.UID=UID;
			this.firstName=fname;
			this.lastName=lname;
			this.studentNum=sNumb;
		}
	
		public void addFirstLab(Timeslot t){
			this.firstLabs.add(t);
		}

		public void addFirstTut(Timeslot t){
			this.firstTuts.add(t);
		}
		
		public void addSecondLab(Timeslot t){
			this.secondLabs.add(t);
		}
		
		public void addSecondTut(Timeslot t){
			this.secondTuts.add(t);
		}
		
		public void addThirdLab(Timeslot t){
			this.thirdLabs.add(t);
		}	
		
		public void addAssignLab(Timeslot t){
			this.thirdTuts.add(t);
		}
		
		public void addThirdTut(Timeslot t){
			this.thirdTuts.add(t);
		}
		
		public void addCannotAttendLab(Timeslot t){
			this.cannotAttendLabs.add(t);
		}
		
		public void addCannotAttendTut(Timeslot t){
			this.cannotAttendTuts.add(t);
		}
		
		public int getUID() {
			return UID;
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public int getStudentNum() {
			return studentNum;
		}

		public Timeslot getAssignedLab() {
			return assignedLab;
		}

		public Timeslot getAssignedTut() {
			return assignedTut;
		}

		public ArrayList<Timeslot> getFirstLabs() {
			return firstLabs;
		}

		public ArrayList<Timeslot> getFirstTuts() {
			return firstTuts;
		}

		public ArrayList<Timeslot> getSecondLabs() {
			return secondLabs;
		}

		public ArrayList<Timeslot> getSecondTuts() {
			return secondTuts;
		}

		public ArrayList<Timeslot> getThirdLabs() {
			return thirdLabs;
		}

		public ArrayList<Timeslot> getThirdTuts() {
			return thirdTuts;
		}

		public ArrayList<Timeslot> getCannotAttendLabs() {
			return cannotAttendLabs;
		}

		public ArrayList<Timeslot> getCannotAttendTuts() {
			return cannotAttendTuts;
		}
}
