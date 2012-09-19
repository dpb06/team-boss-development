package testing;

import java.util.ArrayList;

import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class JUnitTestingData {

	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Timeslot> labs = new ArrayList<Timeslot>();
	private ArrayList<Timeslot> tutorials = new ArrayList<Timeslot>();
	
	public JUnitTestingData() {
		
		//Create small but sensible threshold values.
		int[] threshOne = {0,5,10,10};
		int[] threshTwo = {0,6,10,12};
		int[] threshThree = {2,4,10,15};
		
		//Create five labs, with maximums of 10,10,10,12,15.
		Lab labOne = new Lab(1, 1310, 1410, Day.Monday, threshOne);
		Lab labTwo = new Lab(2, 1310, 1410, Day.Tuesday, threshOne);
		Lab labThree = new Lab(3, 900, 1000, Day.Wednesday, threshTwo);
		Lab labFour = new Lab(4, 1310, 1410, Day.Thursday, threshOne);
		Lab labFive = new Lab(5, 1510, 1620, Day.Thursday, threshThree);

		//Add the five labs to labs.
		labs.add(labOne);
		labs.add(labTwo);
		labs.add(labThree);
		labs.add(labFour);
		labs.add(labFive);
		
		//Create a selection of possible choices.
		int[] choicesOne = {0,2,1,3,0};
		int[] choicesTwo = {0,0,2,0,3};
		int[] choicesThree = {1,2,3,2,1};
		int[] choicesFour = {0,2,0,0,1};
		int[] choicesFive = {0,1,0,0,0};
		int[] choicesSix = {3,2,1,0,0};
		int[] choicesSeven = {0,0,1,2,3};
		int[] choicesEight = {0,0,3,0,3};
		int[] choicesNine = {0,0,0,0,0};
		int[] choicesTen = {0,1,0,0,3};
		int[] choicesEleven = {0,2,1,1,0};
		int[] choicesTwelve = {0,0,2,0,3};
		int[] choicesThirteen = {0,1,1,1,2};
		int[] choicesFourteen = {3,2,0,1,0};
		int[] choicesFifteen = {3,0,0,2,0};
		
		//Add choices to an ArrayList to make it easier to distribute them.
		ArrayList<int[]> choices = new ArrayList<int[]>();
		choices.add(choicesOne);
		choices.add(choicesTwo);
		choices.add(choicesThree);
		choices.add(choicesFour);
		choices.add(choicesFive);
		choices.add(choicesSix);
		choices.add(choicesSeven);
		choices.add(choicesEight);
		choices.add(choicesNine);
		choices.add(choicesTen);
		choices.add(choicesEleven);
		choices.add(choicesTwelve);
		choices.add(choicesThirteen);
		choices.add(choicesFourteen);
		choices.add(choicesFifteen);
		
		//Create fifty students, with choices for the five labs.
		for(int i = 300213937; i < 300213987; i++){
			//Pick a choice for the student.
			int index = i%14;
			//Create a student.
			Student s = new Student(i, choices.get(index));
			//Add student to students.
			students.add(s);
		}
		
		//TODO: Add test data to represent tutorials
	}
	
	
	
	public ArrayList<Student> getStudents(){
		return students;
	}
	public ArrayList<Timeslot> getLabs(){
		return labs;
	}
	public ArrayList<Timeslot> getTutorials(){
		return tutorials;
	}
	
	
}
