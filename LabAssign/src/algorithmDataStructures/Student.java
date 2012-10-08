package algorithmDataStructures;


import java.util.ArrayList;

/**
 * Stores information about a single student, including:
 *   Each of their choices.
 *   The number of labs they can attend.
 *   Their student number.
 *   Their name (as entered with their lab selection, may differ from the one on their tutorial selection).
 *   Their priority (used by BossSort).
 *   The lab and tutorial they are assigned to. 
 */
public class Student implements Comparable<Object>{

	//-----FIELDS-----\\
	private	int studentNum;
	private String name;
	private	Timeslot assignedLab;
	private	Timeslot assignedTut;
	private int Priority = 0;
	private ArrayList<Timeslot> firstChoicesLabs = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> secondChoicesLabs = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> thirdChoicesLabs = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> cannotAttendLabs = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> firstChoicesTuts = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> secondChoicesTuts = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> thirdChoicesTuts = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> cannotAttendTuts = new ArrayList<Timeslot>();
	private int numCanAttendLabs = 0;
	private int numCanAttendTuts = 0;

	private boolean flagged=false;
	private boolean changedChoices=false;


	//-----CONSTRUCTOR-----\\
	public Student (int studentNum, String name){
		this.studentNum = studentNum;
		this.name = name;
	}



	//-----FUNCTIONALITIES-----\\
	@Override
	public String toString() {
		return "Student: " + studentNum + "\n";
	}

	/**
	 * Prints out the student with all of its information that it holds
	 * first choices, second choice, third choices and cannot attends.
	 */
	public void printDebug(){
		//Print student name and number
		System.out.println(this.toString());
		//Initialize a local StringBuilder
		StringBuilder newString = new StringBuilder();
		//For each first choice
		newString.append("First Choices: \n");
		for(Timeslot t:firstChoicesLabs){
			//Append ID, start time and end time
			newString.append("\t"+t.toString()+"\n");
		}
		//For each second choice
		newString.append("Second Choices: \n");
		for(Timeslot t:secondChoicesLabs){
			//Append ID, start time and end time
			newString.append("\t"+t.toString()+"\n");
		}
		//For each third choice
		newString.append("Third Choices: \n");
		for(Timeslot t:thirdChoicesLabs){
			//Append ID, start time and end time
			newString.append("\t"+t.toString()+"\n");
		}
		//For each cannot attend
		newString.append("Cannot Attends: \n");
		for(Timeslot t:cannotAttendLabs){
			//Append ID, start time and end time
			newString.append("\t"+t.toString()+"\n");
		}
		//Print the stringbuilder's output
		System.out.println(newString.toString());

	}

	@Override
	public int compareTo(Object o){
		//Ensure object is a Student.
		if(o instanceof Student){
			//Cast object.
			Student otherStudent = (Student) o;
			//If this has greater priority, return 1.
			if(this.getPriority() > otherStudent.getPriority()){
				return 1;
			}
			//If this has less priority, return -1.
			else if(this.getPriority() < otherStudent.getPriority()){
				return -1;
			}
			//If priorities are even, return 0.
			else{
				return 0;
			}
		}
		//If object is not a Student, throw exception.
		//TODO: put exception here.
		return 0;
	}

	/**
	 * clears students choices after they have been rearranged.
	 * @param i
	 */
	public void clearChoice(int i ){
		switch(i){
		case(0):
			this.firstChoicesLabs.clear();
			break;
		case(1):
			this.secondChoicesLabs.clear();
			break;
		case(2):
			this.thirdChoicesLabs.clear();
			break;
		}
	}

	//-----ADD METHODS-----\\
	public boolean addFirstLab(Timeslot t){
		if(firstChoicesLabs.contains(t)){
			return false;
		}
		firstChoicesLabs.add(t);
		numCanAttendLabs++;
		return true;
	}

	public boolean addSecondLab(Timeslot t){
		if(secondChoicesLabs.contains(t)){
			return false;
		}
		secondChoicesLabs.add(t);
		numCanAttendLabs++;
		return true;
	}

	public boolean addThirdLab(Timeslot t){
		if(thirdChoicesLabs.contains(t)){
			return false;
		}
		thirdChoicesLabs.add(t);
		numCanAttendLabs++;
		return true;
	}

	public boolean addCannotAttend(Timeslot t){
		if(cannotAttendLabs.contains(t))
			return false;
		cannotAttendLabs.add(t);
		return true;
	}
	
	public boolean addFirstTut(Timeslot t){
		if(firstChoicesTuts.contains(t)){
			return false;
		}
		firstChoicesTuts.add(t);
		numCanAttendTuts++;
		return true;
	}

	public boolean addSecondTut(Timeslot t){
		if(secondChoicesTuts.contains(t)){
			return false;
		}
		secondChoicesTuts.add(t);
		numCanAttendTuts++;
		return true;
	}

	public boolean addThirdTut(Timeslot t){
		if(thirdChoicesTuts.contains(t)){
			return false;
		}
		thirdChoicesTuts.add(t);
		numCanAttendTuts++;
		return true;
	}

	public boolean addCannotAttendTut(Timeslot t){
		if(cannotAttendTuts.contains(t))
			return false;
		cannotAttendTuts.add(t);
		return true;
	}


	//-----GET METHODS-----\\
	public int getPriority() {
		return Priority;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public ArrayList<Timeslot> getFirstChoices() {
		return firstChoicesLabs;
	}

	public ArrayList<Timeslot> getSecondChoices() {
		return secondChoicesLabs;
	}

	public ArrayList<Timeslot> getThirdChoices() {
		return thirdChoicesLabs;
	}

	public ArrayList<Timeslot> getCannotAttend() {
		return cannotAttendLabs;
	}

	public int getNumCanAttendLabs() {
		return numCanAttendLabs;
	}
	
	public int getNumCanAttendTuts() {
		return numCanAttendTuts;
	}

	public String getName() {
		return name;
	}

	public Timeslot getAssignedLab() {
		return assignedLab;
	}

	public Timeslot getAssignedTut() {
		return assignedTut;
	}

	public boolean getFlagged(){
		return flagged;
	}

	public boolean getChangedChoices(){
		return changedChoices;
	}


	//-----SET METHODS-----\\
	public void setPriority(int priority) {
		Priority = priority;
	}

	public void setAssignedLab(Timeslot t){
		this.assignedLab = t;
	}

	// for rearranging the order of students choices sets a students choice to an array of
	// another of their choices. Used by StudentChoiceOrder class.
	public void setFirst(ArrayList<Timeslot> choices) {
		if(this.firstChoicesLabs.isEmpty()){
			this.firstChoicesLabs=choices;
		}
	}
	public void setSecond(ArrayList<Timeslot> choices) {
		if(this.secondChoicesLabs.isEmpty()){
			this.secondChoicesLabs=choices;
		}
	}
	public void setThird(ArrayList<Timeslot> choices) {
		if(this.thirdChoicesLabs.isEmpty()){
			this.thirdChoicesLabs=choices;
		}
	}

	public void setFlagged(){
		this.flagged=true;
	}

	public void setChangedChoices(){
		this.changedChoices=true;
	}

	public void setAssignedTut(Timeslot t){
		this.assignedTut = t;
	}


}
