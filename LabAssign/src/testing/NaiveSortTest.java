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
		
		new NaiveSort(labs, new ArrayList<Timeslot>(), students);

	}
	
	
}
