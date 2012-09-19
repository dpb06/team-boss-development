package testing;

import java.util.ArrayList;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class BossSortJUnitSuite {

	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	
	public BossSortJUnitSuite(){
		JUnitTestingData j = new JUnitTestingData();
		students = j.getStudents();
		labs = j.getLabs();
		tutorials = j.getTutorials();
	}
	
}
