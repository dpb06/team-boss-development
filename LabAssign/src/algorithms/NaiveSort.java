package algorithms;

import java.util.ArrayList;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class NaiveSort {

	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	
	/**
	 * Initializes a NaiveSort algorithm, sorts the students into their first choices, prints algorithm output, constructs a FitnessFunctions class to analyze fitness.
	 * @param labs - A list of lab timeslots
	 * @param tutorials - A list of tutorial timeslots
	 * @param students - A list of Student objects, including identifying data and information about student choices
	 */
	public NaiveSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
		sort();
		commandLineOutput();
		new FitnessFunctions(tutorials, students, labs);
	}
	
	/**
	 * Puts all students into the lab that appears first in their list of first choices. (changes are stored in the labs arraylist)
	 */
	public void sort(){
		//For every student
		for (Student s : students){
			//Put first first lab choice into a variable
			Timeslot first = s.getFirstLabs().get(0);
			//Find index of choice in this.labs
			int index = labs.indexOf(first);
			//Find this.labs.choice.assigned
			Timeslot assign = labs.get(index);
			//Add student to this.labs.choice.assigned
			assign.addStudent(s);
		}
	}
	
	/**
	 * Iterates the list of labs and prints details to console so we can track output.
	 */
	public void commandLineOutput(){
		//For every lab slot
		for (Timeslot t : labs){
		//Call labPrint method
			t.timeslotPrint();
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
