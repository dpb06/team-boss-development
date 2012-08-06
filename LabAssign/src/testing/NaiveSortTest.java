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
	
	
	public NaiveSortTest(){
	//Create list of Student objects
	//Create list of Lab objects
	//Create new NaiveSort
		labs.add(new Lab(1, 1310, Day.Monday, 2));
		labs.add(new Lab(2, 1310, Day.Tuesday, 2));
		labs.add(new Lab(3, 1310, Day.Wednesay, 2));
		String first = "a";
		String last = "z";
		for(int i=0; i<10; i++){
			students.add(new Student(i, first, last, 007));
			first.concat(last);
			last.concat(first);
		}
		new NaiveSort(labs, new ArrayList<Timeslot>(), students);

	}
	
	
}
