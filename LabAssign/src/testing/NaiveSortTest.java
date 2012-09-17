package testing;

import java.util.ArrayList;

import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithms.NaiveSort;


















//TODO: Make JUnit test suite instead




















public class NaiveSortTest {

	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Timeslot> labs = new ArrayList<Timeslot>();
	
	
	public NaiveSortTest(){
	//Create list of Student objects
	//Create list of Lab objects
	//Create new NaiveSort
		int[] threshOne = {0,0,3,3};
		int[] threshTwo = {0,0,2,2};
		labs.add(new Lab(1, 1310,1510, Day.Monday, threshOne));
		labs.add(new Lab(2, 1310,1510, Day.Tuesday, threshOne));
		labs.add(new Lab(3, 1310, 1510,Day.Wednesday, threshTwo));
		String first = "a";
		String last = "z";
		for(int i=0; i<10; i++){
			Student s = new Student(i, first, last, 007);
			if(i<3){
    			s.addFirstLab(labs.get(0));
			} else if (i<6){
				s.addFirstLab(labs.get(1));
			} else{
				s.addFirstLab(labs.get(2));
			}
			students.add(s);
			first = first.concat("b");
			last = last.concat("y");
		}
		new NaiveSort(labs, new ArrayList<Timeslot>(), students);
	}
	
	public static void main(String[] args){
		new NaiveSortTest();
	}
	
}
