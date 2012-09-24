package Deprecated;

import java.util.ArrayList;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class NaiveSort {

	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	
	public NaiveSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
		sort();
		commandLineOutput();
		new FitnessFunctions(tutorials, students, labs);
	}
	
	/**
	 * 
	 */
	public void sort(){
		//For every student
		for (Student s : students){
			//Put first first lab choice into a variable
			Timeslot first = hash.getFirsts(s).get(0);
			//Find index of choice in this.labs
			int index = labs.indexOf(first);
			//Find this.labs.choice.assigned
			Timeslot assign = labs.get(index);
			//Add student to this.labs.choice.assigned
			assign.addStudent(s);
		}
		
	}
	
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
