package algorithmDataStructures;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;


public class Student implements Comparable{

	private	int studentNum;
	private int Priority=0;
	private static StaticTimeslotMap hash;


	//final assigned lab slot
	private	Timeslot assignedLab;
	//final assigned tutorial Slot
	private	Timeslot assignedTut;
	//list of integers representing choices. (Timeslot correlates with index) 0 = cannot attend
	private ArrayList<Integer> choices;


	public Student (int studentNum, int[] choices){
		this.studentNum=studentNum;
		ArrayList<Integer> c = new ArrayList<Integer>();
		for(int i:choices){
			c.add(i);
		}
		this.choices = c;
	}
	
	
	
	public int getPriority() {
		return Priority;
	}



	public void setPriority(int priority) {
		Priority = priority;
	}

	
	public String toString() {
		return "Student: " + studentNum + "\n" + choices.toString();
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
	
	public ArrayList<Integer> getChoices() {
		return choices;
	}

	public Timeslot getAssignedLab() {
		return assignedLab;
	}

	public Timeslot getAssignedTut() {
		return assignedTut;
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



public int getnumOfChoiceLab() {
	int sum = 0;
	for (int i:choices){
		if(i!=0){
			sum++;
		}
	}
	return sum;
}

}
