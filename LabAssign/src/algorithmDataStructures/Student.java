package algorithmDataStructures;


import java.util.ArrayList;
import java.util.Comparator;


public class Student implements Comparator{

	private	int UID;
	private	String firstName;
	private	String lastName;
	private boolean oneChoiceLab=true;
	private boolean oneChoiceTut=true;
	private int numOfChoicesLab=0;
	private int Priority=0;

	public int getPriority() {
		return Priority;
	}



	public void setPriority(int priority) {
		Priority = priority;
	}



	public int getNumOfChoicesLab() {
		return numOfChoicesLab;
	}



	public boolean isOneChoiceLab() {
		return oneChoiceLab;
	}



	public boolean isOneChoiceTut() {
		return oneChoiceTut;
	}

	private int numOfChoicesTut=0;
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



	//Make UID automatically generated
	public Student (int UID, String fname, String lname, int sNumb){
		this.UID=UID;
		this.firstName=fname;
		this.lastName=lname;
		this.studentNum=sNumb;
	}



	public void printStudent() {
		//Print UID firstname lastname
		System.out.printf("%d %s %s", getUID(), getFirstName(), getLastName());
	}

	/**
	 * if a student has more than 1 choice for labs to attend set the onechoice boolean to false
	 */
	public void oneChoiceStudentLab(){
		if (getnumOfChoiceLab() >1){
			setOneChoiceStudentLab(false);				
		}
	}
	/**
	 * if a student has more than 1 tut set the one choice boolean to false
	 */
	public void oneChoiceStudentTut(){
		if (getnumOfChoiceTut() >1){
			setOneChoiceStudentTut(false);				
		}
	}

	private void setOneChoiceStudentLab(boolean b) {
		this.oneChoiceLab=b;
	}

	private void setOneChoiceStudentTut(boolean b) {
		this.oneChoiceTut=b;
	}

	public void addFirstLab(Timeslot t){
		this.firstLabs.add(t);
		numOfChoicesLab++;
		oneChoiceStudentLab();
	}

	public void addSecondLab(Timeslot t){
		this.secondLabs.add(t);
		numOfChoicesLab++;
		oneChoiceStudentLab();
	}

	public void addThirdLab(Timeslot t){
		this.thirdLabs.add(t);
		numOfChoicesLab++;
		oneChoiceStudentLab();
	}

	public void addFirstTut(Timeslot t){
		this.firstTuts.add(t);
		numOfChoicesTut++;
		oneChoiceStudentTut();
	}

	public void addSecondTut(Timeslot t){
		this.secondTuts.add(t);
		numOfChoicesTut++;
		oneChoiceStudentTut();
	}

	public void addThirdTut(Timeslot t){
		this.thirdTuts.add(t);
		numOfChoicesTut++;
		oneChoiceStudentTut();
	}

	public void addAssignedLab(Timeslot t){
		this.assignedLab = t;
	}

	public void addAssignedTut(Timeslot t){
		this.assignedTut = t;
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

	public int  getnumOfChoiceLab(){
		return this.numOfChoicesLab;
	}

	public int  getnumOfChoiceTut(){
		return this.numOfChoicesTut;
	}



	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		Student s1;
		Student s2;
		if(o1 instanceof Student && o2 instanceof Student ){
			s1=(Student)o1;
			s2=(Student)o2;

			if(s1.getPriority()>s2.getPriority()){
				return 1;
			}
			if(s1.getPriority()<s2.getPriority()){
				return -1;
			}
			return 0;
		}
		return -1;
	}



}
