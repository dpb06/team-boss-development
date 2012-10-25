package algorithmDataStructures;

import java.util.ArrayList;

/**
 * Stores information about a single student, including: Each of their choices.
 * The number of labs they can attend. Their student number. Their name (as
 * entered with their lab selection, may differ from the one on their tutorial
 * selection). Their priority (used by BossSort). The lab and tutorial they are
 * assigned to.
 */
public class Student implements Comparable<Object>, Cloneable {

	// -----FIELDS-----\\
	private int studentNum;
	private String name;
	private Timeslot assignedLab;
	private Timeslot assignedTut;
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

	private ArrayList<Timeslot> combinedLabs=new ArrayList<Timeslot>();
	private ArrayList<Timeslot> combinedTuts=new ArrayList<Timeslot>();
	
	private int currentIndexLabs=0;
	private int currentIndexTuts=0;
	
	private boolean flaggedForLabs = false;
	private boolean flaggedForTuts = false;
	private boolean changedChoices = false;
	private String reasonForFlagging = "";

	// -----CONSTRUCTOR-----\\
	public Student(int studentNum, String name) {
		this.studentNum = studentNum;
		this.name = name;
	}

	// -----FUNCTIONALITIES-----\\
	@Override
	public String toString() {
		return studentNum+"   "+(name!=null?name:"")+"\n";
	}

	/**
	 * Prints out the student with all of its information that it holds first
	 * choices, second choice, third choices and cannot attends.
	 */
	public void printDebug() {
		// Print student name and number
		System.out.println(this.toString());
		// Initialize a local StringBuilder
		StringBuilder newString = new StringBuilder();
		// For each first choice
		newString.append("First Choices: \n");
		for (Timeslot t : firstChoicesLabs) {
			// Append ID, start time and end time
			newString.append("\t" + t.toString() + "\n");
		}
		// For each second choice
		newString.append("Second Choices: \n");
		for (Timeslot t : secondChoicesLabs) {
			// Append ID, start time and end time
			newString.append("\t" + t.toString() + "\n");
		}
		// For each third choice
		newString.append("Third Choices: \n");
		for (Timeslot t : thirdChoicesLabs) {
			// Append ID, start time and end time
			newString.append("\t" + t.toString() + "\n");
		}
		// For each cannot attend
		newString.append("Cannot Attends: \n");
		for (Timeslot t : cannotAttendLabs) {
			// Append ID, start time and end time
			newString.append("\t" + t.toString() + "\n");
		}
		// Print the stringbuilder's output
		System.out.println(newString.toString());

	}

	@Override
	public int compareTo(Object o) {
		// Ensure object is a Student.
		if (o instanceof Student) {
			// Cast object.
			Student otherStudent = (Student) o;
			// If this has greater priority, return 1.
			if (this.getPriority() > otherStudent.getPriority()) {
				return 1;
			}
			// If this has less priority, return -1.
			else if (this.getPriority() < otherStudent.getPriority()) {
				return -1;
			}
			// If priorities are even, return 0.
			else {
				return 0;
			}
		}
		return 0;
	}

	// -----ADD METHODS-----\\
	public boolean addFirstLab(Timeslot t) {
		if (firstChoicesLabs.contains(t)) {
			return false;
		}
		firstChoicesLabs.add(t);
		numCanAttendLabs++;
		return true;
	}

	public boolean addSecondLab(Timeslot t) {
		if (secondChoicesLabs.contains(t)) {
			return false;
		}
		secondChoicesLabs.add(t);
		numCanAttendLabs++;
		return true;
	}

	public boolean addThirdLab(Timeslot t) {
		if (thirdChoicesLabs.contains(t)) {
			return false;
		}
		thirdChoicesLabs.add(t);
		numCanAttendLabs++;
		return true;
	}

	public boolean addCannotAttendLab(Timeslot t) {
		if (cannotAttendLabs.contains(t))
			return false;
		cannotAttendLabs.add(t);
		return true;
	}

	public boolean addFirstTut(Timeslot t) {
		if (firstChoicesTuts.contains(t)) {
			return false;
		}
		firstChoicesTuts.add(t);
		numCanAttendTuts++;
		return true;
	}

	public boolean addSecondTut(Timeslot t) {
		if (secondChoicesTuts.contains(t)) {
			return false;
		}
		secondChoicesTuts.add(t);
		numCanAttendTuts++;
		return true;
	}

	public boolean addThirdTut(Timeslot t) {
		if (thirdChoicesTuts.contains(t)) {
			return false;
		}
		thirdChoicesTuts.add(t);
		numCanAttendTuts++;
		return true;
	}

	public boolean addCannotAttendTut(Timeslot t) {
		if (cannotAttendTuts.contains(t))
			return false;
		cannotAttendTuts.add(t);
		return true;
	}

	// -----REMOVE METHODS-----\\
	public boolean removeFirstChoiceTut(Timeslot t) {
		if (firstChoicesTuts.contains(t)) {
			firstChoicesTuts.remove(t);
			return true;
		}
		return false;
	}

	public boolean removeSecondChoiceTut(Timeslot t) {
		if (secondChoicesTuts.contains(t)) {
			secondChoicesTuts.remove(t);
			return true;
		}
		return false;
	}

	public boolean removeThirdChoiceTut(Timeslot t) {
		if (thirdChoicesTuts.contains(t)) {
			thirdChoicesTuts.remove(t);
			return true;
		}
		return false;
	}

	public void combineLabs(){
		combinedLabs.addAll(firstChoicesLabs);
		combinedLabs.addAll(secondChoicesLabs);
		combinedLabs.addAll(thirdChoicesLabs);
	}

	public void combineTuts(){
		combinedTuts.addAll(firstChoicesTuts);
		combinedTuts.addAll(secondChoicesTuts);
		combinedTuts.addAll(thirdChoicesTuts);
	}
	
	public void incrementIndexLabs(){
		currentIndexLabs++;
	}
	public void incrementIndexTuts(){
		currentIndexTuts++;
	}
	// -----GET METHODS-----\\
	public int getCurrentIndexLabs(){
		return currentIndexLabs;
	}
	
	public int getCurrentIndexTuts(){
		return currentIndexTuts;
	}


	public Timeslot getCurrentLab(){
		return combinedLabs.get(currentIndexLabs);
	}
	
	public Timeslot getCurrentTut(){
		return combinedTuts.get(currentIndexTuts);
	}
	public int getPriority() {
		return Priority;
	}

	public ArrayList<Timeslot> getCombinedTuts(){
		return combinedTuts;
	}

	public ArrayList<Timeslot> getCombinedLabs(){
		return combinedLabs;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public ArrayList<Timeslot> getFirstChoiceLabs() {
		return firstChoicesLabs;
	}

	public ArrayList<Timeslot> getSecondChoiceLabs() {
		return secondChoicesLabs;
	}

	public ArrayList<Timeslot> getThirdChoiceLabs() {
		return thirdChoicesLabs;
	}

	public ArrayList<Timeslot> getCannotAttendLabs() {
		return cannotAttendLabs;
	}

	public ArrayList<Timeslot> getFirstChoiceTuts() {
		return firstChoicesTuts;
	}

	public ArrayList<Timeslot> getSecondChoiceTuts() {
		return secondChoicesTuts;
	}

	public ArrayList<Timeslot> getThirdChoiceTuts() {
		return thirdChoicesTuts;
	}

	public ArrayList<Timeslot> getCannotAttendTuts() {
		return cannotAttendTuts;
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

	public boolean getFlaggedForTuts() {
		return flaggedForTuts;
	}
	public boolean getFlaggedForLabs() {
		return flaggedForLabs;
	}

	public boolean getChangedChoices() {
		return changedChoices;
	}

	public String getReasonForFlagging() {
		return reasonForFlagging;
	}

	// -----SET METHODS-----\\
	public void setPriority(int priority) {
		Priority = priority;
	}

	public void setAssignedLab(Timeslot t) {
		this.assignedLab = t;
	}

	public void setIndexLab(int i){
		currentIndexLabs=i;
	}
	public void setIndexTut(int i){
		currentIndexTuts=i;
	}
	// For rearranging the order of students choices. Sets a students choice to
	// an array of
	// another of their choices. Used by StudentChoiceOrder class.
	public void setFirstChoiceLabs(ArrayList<Timeslot> choices) {
		this.firstChoicesLabs = choices;
	}

	public void setSecondChoiceLabs(ArrayList<Timeslot> choices) {
		this.secondChoicesLabs = choices;
	}

	public void setThirdChoiceLabs(ArrayList<Timeslot> choices) {
		this.thirdChoicesLabs = choices;
	}

	// For removing tutorial choices without causing a concurrent modification
	// exception.
	// Sets a students choice to an array of choices. Used by
	// BossSort.modifyTuts().
	public void setFirstChoiceTuts(ArrayList<Timeslot> choices) {
		this.firstChoicesTuts = choices;
	}

	public void setSecondChoiceTuts(ArrayList<Timeslot> choices) {
		this.secondChoicesTuts = choices;
	}

	public void setThirdChoiceTuts(ArrayList<Timeslot> choices) {
		this.thirdChoicesTuts = choices;
	}
	
	public void setStudentNum(int studentNum){
		this.studentNum = studentNum;
	}

	public void setFlaggedForLabs(boolean flaggedForLabs) {
		this.flaggedForLabs = flaggedForLabs;
	}
	public void setFlaggedForTuts(boolean flaggedForTuts) {
		this.flaggedForTuts = flaggedForTuts;
	}

	public void setReasonForFlagging(String reasonForFlagging){
		this.reasonForFlagging = reasonForFlagging;
	}

	public void setChangedChoices(boolean b) {
		this.changedChoices = b;
	}

	public void setAssignedTut(Timeslot t) {
		this.assignedTut = t;
	}
	
	public void addThird(Timeslot t){
		if(t instanceof Lab){
			addThirdLab(t);
		}else if (t instanceof Tutorial){
			addThirdTut(t);
		}
	}
	
	public void removeTimeslot(Timeslot t){
		firstChoicesLabs.remove(t);
		secondChoicesLabs.remove(t);
		thirdChoicesLabs.remove(t);
		firstChoicesTuts.remove(t);
		secondChoicesTuts.remove(t);
		thirdChoicesTuts.remove(t);
	}

	/**
	 * This method merges the given students choices into this student
	 * 
	 * @param student The student to merge in
	 * @return
	 */
	public boolean merge(Student student) {
		if (studentNum != student.getStudentNum() || student == this) {
			return false;
		}
		firstChoicesLabs.addAll(student.getFirstChoiceLabs());
		secondChoicesLabs.addAll(student.getSecondChoiceLabs());
		thirdChoicesLabs.addAll(student.getThirdChoiceLabs());
		cannotAttendLabs.addAll(student.getCannotAttendLabs());
		firstChoicesTuts.addAll(student.getFirstChoiceTuts());
		secondChoicesTuts.addAll(student.getSecondChoiceTuts());
		thirdChoicesTuts.addAll(student.getThirdChoiceTuts());
		cannotAttendTuts.addAll(student.getCannotAttendLabs());
		return true;
	}
	
	public Student clone(){
		
		Student clone = new Student(Priority, name);
		
		clone.setStudentNum(studentNum);
		clone.setFirstChoiceLabs(firstChoicesLabs);
		clone.setFirstChoiceTuts(firstChoicesTuts);
		clone.setSecondChoiceLabs(secondChoicesLabs);
		clone.setSecondChoiceTuts(secondChoicesTuts);
		clone.setThirdChoiceLabs(thirdChoicesLabs);
		clone.setThirdChoiceTuts(thirdChoicesTuts);
		clone.setCanAttendLabs(numCanAttendLabs);
		clone.setCanAttendTuts(numCanAttendTuts);
		

		
		
		return clone;
		
	}

	private void setCanAttendTuts(int numCanAttendTuts) {
		this.numCanAttendTuts = numCanAttendTuts;
		
	}

	private void setCanAttendLabs(int numCanAttendLabs) {
		this.numCanAttendLabs = numCanAttendLabs;
		
	}

}
