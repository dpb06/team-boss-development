package testing;

import java.util.ArrayList;

import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithmDataStructures.Tutorial;
import algorithms.CuttingSort;
import algorithms.PermuteSort;
import algorithms.StudentChoiceOrder;

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
		Lab labOne = new Lab(1, 900, 1000, Day.Monday);
		Lab labTwo = new Lab(2, 1000, 1100, Day.Tuesday);
		Lab labThree = new Lab(3, 1100, 1200, Day.Wednesday);
		Lab labFour = new Lab(4, 1310, 1410, Day.Thursday);
		Lab labFive = new Lab(5, 1510, 1620, Day.Thursday);
		labOne.setThresholds(threshOne);
		labTwo.setThresholds(threshOne);
		labThree.setThresholds(threshTwo);
		labFour.setThresholds(threshTwo);
		labFive.setThresholds(threshThree);

		//Add the five labs to labs.
		labs.add(labOne);
		labs.add(labTwo);
		labs.add(labThree);
		labs.add(labFour);
		labs.add(labFive);
		
		
		//Create five tuts, with maximums of 10,10,10,12,15.
		Tutorial tutOne = new Tutorial(1, 900, 1000, Day.Tuesday);
		Tutorial tutTwo = new Tutorial(2, 1130, 1230, Day.Wednesday);
		Tutorial tutThree = new Tutorial(3, 900, 1000, Day.Thursday);
		Tutorial tutFour = new Tutorial(4, 1310, 1410, Day.Thursday);
		Tutorial tutFive = new Tutorial(5, 1310, 1410, Day.Friday);
		tutOne.setThresholds(threshOne);
		tutTwo.setThresholds(threshOne);
		tutThree.setThresholds(threshTwo);
		tutFour.setThresholds(threshTwo);
		tutFive.setThresholds(threshThree);

		//Add the five tuts to tutorials.
		tutorials.add(tutOne);
		tutorials.add(tutTwo);
		tutorials.add(tutThree);
		tutorials.add(tutFour);
		tutorials.add(tutFive);
		
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
			//Pick a set of lab and tutorial choices for the student.
			int index = i%14;
			int[] studentLabChoices = choices.get(index);
			int[] studentTutChoices = {};
			if(index == 14){
				studentTutChoices = choicesOne;
			}
			else {
				studentTutChoices = choices.get(index + 1);
			}
			//Create a student.
			Student s = new Student(i, "name");			
			//TODO: UNUGLIFY THIS. (maybe???)
			//For each integer in the student's lab choices
			for(int ind=0; ind < studentLabChoices.length; ind++){
				//If the choice is 0
				if(studentLabChoices[ind] == 0){
					//Map the index to a Timeslot
					switch(ind){
					//Set that Timeslot to a cannotattend
					case 0:
						s.addCannotAttendLab(labOne);
						break;
					case 1:
						s.addCannotAttendLab(labTwo);
						break;
					case 2:
						s.addCannotAttendLab(labThree);
						break;
					case 3:
						s.addCannotAttendLab(labFour);
						break;
					case 4:
						s.addCannotAttendLab(labFive);
						break;
					}					
				}
				//If the choice is 1
				else if(studentLabChoices[ind] == 1){
					//Map the index to a Timeslot
					switch(ind){
					//Set that Timeslot to a first lab
					case 0:
						s.addFirstLab(labOne);
						break;
					case 1:
						s.addFirstLab(labTwo);
						break;
					case 2:
						s.addFirstLab(labThree);
						break;
					case 3:
						s.addFirstLab(labFour);
						break;
					case 4:
						s.addFirstLab(labFive);
						break;
					}
				}
				//If the choice is 2
				else if(studentLabChoices[ind] == 2){
					//Map the index to a Timeslot
					switch(ind){
					//Set that Timeslot to a second lab
					case 0:
						s.addSecondLab(labOne);
						break;
					case 1:
						s.addSecondLab(labTwo);
						break;
					case 2:
						s.addSecondLab(labThree);
						break;
					case 3:
						s.addSecondLab(labFour);
						break;
					case 4:
						s.addSecondLab(labFive);
						break;
					}
				}
				//If the choice is 3
				else if(studentLabChoices[ind] == 3){
					//Map the index to a Timeslot
					switch(ind){
					//Set that Timeslot to a third lab
					case 0:
						s.addThirdLab(labOne);
						break;
					case 1:
						s.addThirdLab(labTwo);
						break;
					case 2:
						s.addThirdLab(labThree);
						break;
					case 3:
						s.addThirdLab(labFour);
						break;
					case 4:
						s.addThirdLab(labFive);
						break;
					}
				}
			}
			
			
			//For each integer in the student's Tut choices
			for(int ind=0; ind < studentTutChoices.length; ind++){
				//If the choice is 0
				if(studentTutChoices[ind] == 0){
					//Map the index to a Timeslot
					switch(ind){
					//Set that Timeslot to a cannotattend
					case 0:
						s.addCannotAttendTut(tutOne);
						break;
					case 1:
						s.addCannotAttendTut(tutTwo);
						break;
					case 2:
						s.addCannotAttendTut(tutThree);
						break;
					case 3:
						s.addCannotAttendTut(tutFour);
						break;
					case 4:
						s.addCannotAttendTut(tutFive);
						break;
					}					
				}
				//If the choice is 1
				else if(studentTutChoices[ind] == 1){
					//Map the index to a Timeslot
					switch(ind){
					//Set that Timeslot to a first Tut
					case 0:
						s.addFirstTut(tutOne);
						break;
					case 1:
						s.addFirstTut(tutTwo);
						break;
					case 2:
						s.addFirstTut(tutThree);
						break;
					case 3:
						s.addFirstTut(tutFour);
						break;
					case 4:
						s.addFirstTut(tutFive);
						break;
					}
				}
				//If the choice is 2
				else if(studentTutChoices[ind] == 2){
					//Map the index to a Timeslot
					switch(ind){
					//Set that Timeslot to a second Tut
					case 0:
						s.addSecondTut(tutOne);
						break;
					case 1:
						s.addSecondTut(tutTwo);
						break;
					case 2:
						s.addSecondTut(tutThree);
						break;
					case 3:
						s.addSecondTut(tutFour);
						break;
					case 4:
						s.addSecondTut(tutFive);
						break;
					}
				}
				//If the choice is 3
				else if(studentTutChoices[ind] == 3){
					//Map the index to a Timeslot
					switch(ind){
					//Set that Timeslot to a third Tut
					case 0:
						s.addThirdTut(tutOne);
						break;
					case 1:
						s.addThirdTut(tutTwo);
						break;
					case 2:
						s.addThirdTut(tutThree);
						break;
					case 3:
						s.addThirdTut(tutFour);
						break;
					case 4:
						s.addThirdTut(tutFive);
						break;
					}
				}
			}
			
			
			//Add student to students.
			students.add(s);
		}
		

		
		
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
	public static void main(String []args){
		JUnitTestingData j=new JUnitTestingData();
		StudentChoiceOrder sco = new StudentChoiceOrder(j.getStudents());
		new PermuteSort(j.getLabs(), j.getTutorials(), sco.getStudents()).start();
	}
	
}
