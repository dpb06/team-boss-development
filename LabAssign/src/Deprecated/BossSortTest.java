package Deprecated;

import java.util.ArrayList;

import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithms.BossSort;















//TODO: Make JUnit test suite instead






















public class BossSortTest {

	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Timeslot> labs = new ArrayList<Timeslot>();
	
	public BossSortTest(){
	//Create list of Student objects
	//Create list of Lab objects
	//Create new BossSort
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
    			if(Math.random() > 0.3){
    				s.addSecondLab(labs.get(2));
    			}
    			if(Math.random() > 0.6){
    				s.addThirdLab(labs.get(1));
    			}
			} else if (i<6){
				s.addFirstLab(labs.get(1));
				if(Math.random() > 0.6){
					s.addThirdLab(labs.get(0));
				}
				if(Math.random() > 0.9){
					s.addSecondLab(labs.get(2));
				}
			} else{
				s.addFirstLab(labs.get(2));
				if(Math.random() > 0.6){
					s.addThirdLab(labs.get(0));
				}
				else if(Math.random() > 0.1){
					s.addSecondLab(labs.get(1));
				}
			}
			students.add(s);
			first = first.concat("b");
			last = last.concat("y");
		}
		new BossSort(labs, new ArrayList<Timeslot>(), students);
	}
	
	public static void main(String[] args){
		new BossSortTest();
	}
	
}
