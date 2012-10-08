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
	private ArrayList<Timeslot> firstChoices = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> secondChoices = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> thirdChoices = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> cannotAttend = new ArrayList<Timeslot>();
	private int numCanAttend = 0;
	
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
		for(Timeslot t:firstChoices){
			//Append ID, start time and end time
			newString.append("\t"+t.toString()+"\n");
		}
		//For each second choice
		newString.append("Second Choices: \n");
		for(Timeslot t:secondChoices){
			//Append ID, start time and end time
			newString.append("\t"+t.toString()+"\n");
		}
		//For each third choice
		newString.append("Third Choices: \n");
		for(Timeslot t:thirdChoices){
			//Append ID, start time and end time
			newString.append("\t"+t.toString()+"\n");
		}
		//For each cannot attend
		newString.append("Cannot Attends: \n");
		for(Timeslot t:cannotAttend){
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



	//-----ADD METHODS-----\\
	public boolean addFirstLab(Timeslot t){
		if(firstChoices.contains(t)){
			return false;
		}
		firstChoices.add(t);
		numCanAttend++;
		return true;
	}

	public boolean addSecondLab(Timeslot t){
		if(secondChoices.contains(t)){
			return false;
		}
		secondChoices.add(t);
		numCanAttend++;
		return true;
	}

	public boolean addThirdLab(Timeslot t){
		if(thirdChoices.contains(t)){
			return false;
		}
		thirdChoices.add(t);
		numCanAttend++;
		return true;
	}

	public boolean addCannotAttend(Timeslot t){
		if(cannotAttend.contains(t))
			return false;
		cannotAttend.add(t);
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
		return firstChoices;
	}

	public ArrayList<Timeslot> getSecondChoices() {
		return secondChoices;
	}

	public ArrayList<Timeslot> getThirdChoices() {
		return thirdChoices;
	}

	public ArrayList<Timeslot> getCannotAttend() {
		return cannotAttend;
	}

	public int getNumCanAttend() {
		return numCanAttend;
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



	//-----SET METHODS-----\\
	public void setPriority(int priority) {
		Priority = priority;
	}

	public void setAssignedLab(Timeslot t){
		this.assignedLab = t;
	}
	

	/**
	 * for rearrangeing the order of students choices
	 * sets a students choice to an array of another of their choices.
	 * @param choices
	 */
	public void setFirst(ArrayList<Timeslot> choices) {
		if(this.firstChoices.isEmpty()){
		this.firstChoices=choices;
		}
	}
	public void setSecond(ArrayList<Timeslot> choices) {
		if(this.secondChoices.isEmpty()){
			this.secondChoices=choices;
		}
	}
	public void setThird(ArrayList<Timeslot> choices) {
		if(this.thirdChoices.isEmpty()){
		this.thirdChoices=choices;
		}
	}
	
	/**
	 * clears students choices after they have been rearranged.
	 * @param i
	 */
	public void clearChoice(int i ){
		switch(i){
		case(0):{
			this.firstChoices.clear();
			break;
		}
		case(1):{
			this.secondChoices.clear();
			break;
		}
		case(2):{
			this.thirdChoices.clear();
			break;
		}
		}
	}
	
	public void setFlagged(){
		this.flagged=true;
	}
	
	public void setChangedChoices(){
		this.changedChoices=true;
	}

	public boolean getFlagged(){
		return flagged;
	}
	
	public boolean getChangedChoices(){
		return changedChoices;
	}
=======
>>>>>>> branch 'master' of https://github.com/dpb06/team-boss-development.git
	public void setAssignedTut(Timeslot t){
		this.assignedTut = t;
	}


}
