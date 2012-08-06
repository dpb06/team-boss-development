package testing;

import java.util.ArrayList;

import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithms.NaiveSort;

public class NaiveSortTest {

	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Timeslot> labs = new ArrayList<Timeslot>();
	
	/**
	 * Creates a stub test case of the NaiveSort algorithm
	 */
	public NaiveSortTest(){
		//Create a list of 3 labs with 2 maximum students
		labs.add(new Lab(1, 1310, Day.Monday, 2));
		labs.add(new Lab(2, 1310, Day.Tuesday, 2));
		labs.add(new Lab(3, 1310, Day.Wednesday, 2));
		
		//Create a list of 10 students with first choices varying between the three labs
		String first = "a";
		String last = "z";
		for(int i=0; i<10; i++){
			//Create an instance of Student
			Student s = new Student(i, first, last, 007);
			//Add one of the stub labs as a firstChoice lab
			if(i<3){
    			s.addFirstLab(labs.get(0));
			} else if (i<6){
				s.addFirstLab(labs.get(1));
			} else{
				s.addFirstLab(labs.get(2));
			}
			//Add instance of Student to this.students
			students.add(s);
			//Give next student a unique name
			first = first.concat("b");
			last = last.concat("y");
		}
		
		//Run a NaiveSort algorithm using the constructed labs and students (and an empty list for tutorials, which NaiveSort currently doesn't consider)
		new NaiveSort(labs, new ArrayList<Timeslot>(), students);
	}
	
	
	/**
	 * main method - to run as a java application
	 * @param args
	 */
	public static void main(String[] args){
		new NaiveSortTest();
	}
	
}
