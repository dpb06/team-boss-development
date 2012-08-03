package algorithms;

import java.util.ArrayList;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class naiveSort {

	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	
	public naiveSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
		sort();
	}
	
	/**
	 * 
	 */
	public void sort(){
		for (Student s : students){
			//For every student
			//Put into first first lab choice
			  //Put first first choice into a variable
			//Remove setter for Student.assignedLab/Student.assignedTut (Change to preassigned to remove ambiguity?)
			  //Find choice in this.labs
			  //Add student to this.labs.choice.assigned
			
			
			Timeslot first = s.getFirstLabs().get(0);
			s.setAssignedLabs(first);
			int index = labs.indexOf(first);
			Timeslot assign = labs.get(index);
			
			
		}
	}
	
	
	
	
	
	
	
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
	public ArrayList<Timeslot> getLabs() {
		return labs;
	}
	public void setLabs(ArrayList<Timeslot> labs) {
		this.labs = labs;
	}
	public ArrayList<Timeslot> getTutorials() {
		return tutorials;
	}
	public void setTutorials(ArrayList<Timeslot> tutorials) {
		this.tutorials = tutorials;
	}
	
}
