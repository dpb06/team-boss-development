package algorithmDataStructures;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;

import Deprecated.StaticTimeslotMap;


public class Student implements Comparable{

	private	int studentNum;
	private int Priority=0;
	
	private String name;

	//final assigned lab slot
	private	Timeslot assignedLab;
	//final assigned tutorial Slot
	private	Timeslot assignedTut;
	//list of integers representing choices. (Timeslot correlates with index) 0 = cannot attend
	private ArrayList<Timeslot> firstChoices;
	private ArrayList<Timeslot> secondChoices;
	private ArrayList<Timeslot> thirdChoices;
	private ArrayList<Timeslot> cannotAttend;
	
	private int choiceCount=0;
	
	public Student (int studentNum){
		this.studentNum=studentNum;
	}

	
	/**
	 * adds a timeslot to the students list of first choices.
	 * @param t
	 * @return
	 */
	public boolean addFirstLab(Timeslot t){
		if(firstChoices==null){
			firstChoices=new ArrayList<Timeslot>();
		}
		if(firstChoices.contains(t)){
			return false;
		}
		firstChoices.add(t);
		choiceCount++;
		return true;
	}
	
/**
 * adds a timeslot to the list of students second choices.
 * @param t
 * @return
 */
	public boolean addSecondLab(Timeslot t){
		if(secondChoices==null){
			secondChoices=new ArrayList<Timeslot>();
		}
		if(secondChoices.contains(t)){
			return false;
		}
		secondChoices.add(t);
		choiceCount++;
		return true;
	}
/**
 * adds a timeslot to the list of students third choices.
 * @param t
 * @return
 */
	public boolean addThirdLab(Timeslot t){
		if(thirdChoices==null){
			thirdChoices=new ArrayList<Timeslot>();
		}
		if(thirdChoices.contains(t)){
			return false;
		}
		thirdChoices.add(t);
		choiceCount++;
		return true;
	}
/**
 * adds a timeslot to the list of students cannot attend.
 * @param t
 * @return
 */
	public boolean addCannotAttend(Timeslot t){
		if(cannotAttend==null){
			cannotAttend=new ArrayList<Timeslot>();
		}
		if(cannotAttend.contains(t))
			return false;
		cannotAttend.add(t);
		return true;
	}

	public int getPriority() {
		return Priority;
	}
	
	public void setPriority(int priority) {
		Priority = priority;
	}
	
	public void setAssignedLab(Timeslot t){
		this.assignedLab = t;
	}

	public void setAssignedTut(Timeslot t){
		this.assignedTut = t;
	}

	public int getStudentNum() {
		return studentNum;
	}
	
	public Timeslot getAssignedLab() {
		return assignedLab;
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
	
	public Timeslot getAssignedTut() {
		return assignedTut;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getChoiceCount() {
		return choiceCount;
	}


	public String toString() {
		return "Student: " + studentNum + "\n";
	}
	/**
	 * prints out the student with all of its information that it holds
	 * first choices,second choice,third choices and cannot attends
	 */
	public void printDebug(){
		StringBuilder firstLabTest=new StringBuilder();	
		for(Timeslot t:firstChoices){
			firstLabTest.append("\t"+t.toString()+"\n");
		}
		firstLabTest.append("second choice: \n");
		for(Timeslot t:secondChoices){
			firstLabTest.append("\t"+t.toString()+"\n");
		}
		firstLabTest.append("thirdc choice: \n");
		for(Timeslot t:thirdChoices){
			firstLabTest.append("\t"+t.toString()+"\n");
		}
		firstLabTest.append("Cannot Attend: \n");
		for(Timeslot t:cannotAttend){
			firstLabTest.append("\t"+t.toString()+"\n");
		}
		System.out.println(toString());
		System.out.println("First Choices: ");
		System.out.println(firstLabTest.toString());
	
	}
	
	@Override
	public int compareTo(Object o) {
		//Ensure object is a Student.
		if( o instanceof Student){
			//Cast object.
			Student s1= (Student)o;
			//If this has greater priority, return 1.
			if(this.getPriority()>s1.getPriority()){
				return 1;
			}
			//If this has less priority, return -1.
			else if(this.getPriority()<s1.getPriority()){
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


	public int getTotalLabChoices() {
		return firstChoices.size() + secondChoices.size() + thirdChoices.size();
	}


}
