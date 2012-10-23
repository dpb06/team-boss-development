package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

/**
 * This is a Java implementation of howards original sorting algorithm he used using labview.
 * This is to have a benchmark to compare our newly implemented algorithm  boss sort.
 * @author phillijosh
 */

public class HowardsSort implements Algorithm {

	//-----FIELDS-----\\
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private ArrayList<Student> students;
	private PriorityQueue<Student> priority = new PriorityQueue<Student>();
	private ArrayList<Student> flagged = new ArrayList<Student>();
	private AlgorithmOutput output = new AlgorithmOutput();

	//-----CONSTRUCTOR-----\\
	public HowardsSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
		Collections.sort(students,new Comparator<Student>(){
			@Override
			public int compare(Student arg0, Student arg1) {
				Student s1=arg0;
				Student s2=arg1;
				if(s1.getNumCanAttendLabs()<s2.getNumCanAttendLabs()){
					return 1;
				}
				else if(s1.getNumCanAttendLabs()>s2.getNumCanAttendLabs()){
					return -1;
				}
				return 0;
			}

		});
	}


	//-----INTERFACE METHODS-----\\
	@Override
	public AlgorithmOutput start() {
		//Give students a random order
		priorityCalculator();
		//Sort students into labs
		for(Student s: students){
			s.combineLabs();
			if(s.getCombinedLabs().isEmpty()){
				s.setFlagged();
				flagged.add(s);
			}
		}
		for(Timeslot t:labs)
			t.sortAssigned();
		sortLabs();
		//	tutorialChecker tc = new tutorialChecker(students);
		//		students = tc.getStudents();
		//Prioritize students by their tutorial choices
		//Give students random order again
		//	priorityCalculator();
		//Sort students into tutorials
		//	sortTuts();
		//Generate AlgorithmOutput
		generateAlgorithmOutput();
		//Return output
		return output;
	}


	private void sortTuts() {
		// TODO Basically just copypasta and find+replace Labs with Tuts; ONLY ONCE SORTLABS() WORKS PERFECTLY.
	}


	@SuppressWarnings("unchecked")
	private void priorityCalculator() {

		Collections.shuffle(students);

		//		//Begin console output.
		//		System.out.println("priorityCalculator() in HowardsSort");
		//		//Create a local clone of students so original data remains intact
		//		//TODO: FIX THE NULL POINTER EXCEPTION BEING THROWN HERE
		//		priority = new PriorityQueue<Student>(students);
		//		//Give each a random priority
		//		for(Student s: priority){
		//			s.setPriority((int) (Math.random()*students.size()*3));
		//			//Printspam the priority of each student.
		//			System.out.println(s.getStudentNum() + " - " + s.getName() + ", Priority: " + s.getPriority());
		//		}
		//		System.out.println("\n");
	}


	//-----FUNCTIONALITIES-----\\
	public void sortLabs(){
		ArrayList<Timeslot>overfilledLabs;
	//	System.out.println("WAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAT" +labs.get(0).toString());
		for( Student s : students){
			if(s.getCombinedLabs().contains(labs.get(0)))
				System.out.println("Contains as first choice: "+labs.get(0).toString());
		}
		for(Student s: students){
			if(!s.getFlagged()){
				s.setAssignedLab(s.getCombinedLabs().get(0));
				s.getCombinedLabs().get(0).addStudent(s);
			}
		}
		Student currentStudent;
		overfilledLabs=overFilledLabs();
		while(!overfilledLabs.isEmpty()){
			for(Timeslot t: overfilledLabs){
				for(int i=t.getPreferredMax();i<t.getAssigned().size();i++){
					currentStudent=t.getAssigned().get(i);
					currentStudent.getCurrentTimeslot().removeStudent(currentStudent);
					if((currentStudent.getCurrentIndex()+1)<currentStudent.getNumCanAttendLabs()){						
						currentStudent.incrementIndex();
						if(currentStudent.getCurrentTimeslot().getAssigned().size()<currentStudent.getCurrentTimeslot().getPreferredMax()){
							currentStudent.setAssignedLab(currentStudent.getCombinedLabs().get(currentStudent.getCurrentIndex()));
							currentStudent.getCurrentTimeslot().addStudent(currentStudent);				
						}
					}
					else{
						currentStudent.setFlagged();
						flagged.add(currentStudent);
					}
				}
			}
			overfilledLabs=overFilledLabs();
		}

		//		//Begin console output.
		//		System.out.println("sortLabs() in HowardsSort");
		//		//Initialize a reference to the current student
		//		Student currentStudent;
		//		//Initialize a list of first choices
		//		ArrayList<Timeslot> firstChoiceLab;
		//		//Pick a student at random
		//		while(!priority.isEmpty()){
		//			currentStudent = priority.poll();
		//			//Printspam the details of each student.
		//			System.out.println(currentStudent.getStudentNum() + " - " + currentStudent.getName());
		//			//Set the list of first choices
		//			firstChoiceLab = currentStudent.getFirstChoiceLabs();
		//			//Set an index for the lowest lab
		//			int lowestLabIndex = 0;
		//			//Put the student into the first choice lab that has the least students assigned to it
		//			//For each lab choice
		//			for(int i = 0; i < firstChoiceLab.size(); i++){
		//				//If the lab choice has fewer assigned students than the smallest lab choice so far
		//				if(firstChoiceLab.get(i).getAssigned().size() < firstChoiceLab.get(lowestLabIndex).getAssigned().size()){
		//					//Set the smallest lab choice index to this lab's index
		//					lowestLabIndex = i;
		//				}
		//			}
		//			//Put the student into the first choice lab with the least students assigned to it
		//			//TODO: FIX THE INDEX OUT OF BOUNDS BEING THROWN HERE
		//			Timeslot choice = firstChoiceLab.get(lowestLabIndex);
		//			choice.addStudent(currentStudent);
		//			currentStudent.setAssignedLab(choice);
		//			//Printspam the lab this student is assigned to
		//			System.out.println("Assigned to first choice lab: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
		//		}
		//
		//		/*Iterate over the labs that are overfilled and move students into another one of there first choices if they have first choices.*/
		//
		//		//Create a list of overfilled Labs
		//		ArrayList<Timeslot> overfilledLabsList = overFilledLabs();
		//		//For each overfilled lab
		//		for(Timeslot t : overfilledLabsList){
		//			//Get the size of the lab
		//			int size = t.getAssigned().size();
		//			//For every student who is above the overfilled line
		//			for(int j = t.getMaxStudents(); j < size; j++){
		//				//Check if they have anymore first choices.
		//				currentStudent = t.getAssigned().get(j);
		//				//Printspam the details of each student.
		//				System.out.println(currentStudent.getStudentNum() + " - " + currentStudent.getName());
		//				if(currentStudent.getFirstChoiceLabs().size() > 1){
		//					//For each lab choice
		//					for(Timeslot choice : currentStudent.getFirstChoiceLabs()){
		//						//If it is not the current lab
		//						if(choice != t){
		//							//And is not overfilled already
		//							if(!choice.isOverfilled()){
		//								//Move the student into that lab
		//								t.removeStudent(currentStudent);
		//								choice.addStudent(currentStudent);
		//								currentStudent.setAssignedLab(choice);
		//								//Printspam the lab this student is assigned to
		//								System.out.println("Moved to first choice lab: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
		//							}
		//						}
		//					}
		//				}
		//			}
		//		}
		//		
		//		/*Iterate over the labs that are overfilled and move students into a second choice.*/
		//
		//		//Create a list of overfilled labs
		//		overfilledLabsList = overFilledLabs();
		//		//For each overfilled lab
		//		for(Timeslot t : overfilledLabsList){
		//			//Get the size of the lab
		//			int size = t.getAssigned().size();
		//			//For every student who is above the overfilled line
		//			for(int j = t.getMaxStudents(); j < size; j++){
		//				//Check if they have anymore second choices.
		//				currentStudent=t.getAssigned().get(j);
		//				//Printspam the details of each student.
		//				System.out.println(currentStudent.getStudentNum() + " - " + currentStudent.getName());
		//				if(currentStudent.getSecondChoiceLabs().size()>1){
		//					//For each lab choice
		//					for(Timeslot choice : currentStudent.getSecondChoiceLabs()){
		//						//If it is not the current lab
		//						if(choice != t){
		//							//And is not overfilled already
		//							if(!choice.isOverfilled()){
		//								//Move the student into that lab
		//								t.removeStudent(currentStudent);
		//								choice.addStudent(currentStudent);
		//								currentStudent.setAssignedLab(choice);
		//								//Printspam the lab this student is assigned to
		//								System.out.println("Moved to second choice lab: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
		//							}
		//						}
		//					}
		//				}
		//			}
		//		}
		//
		//		/*Iterate over the labs that are overfilled and move students into a third choice.*/
		//
		//		//Create a list of overfilled labs
		//		overfilledLabsList = overFilledLabs();
		//		//For each overfilled lab
		//		for(Timeslot t : overfilledLabsList){
		//			//Get the size of the lab
		//			int size = t.getAssigned().size();
		//			//For every student who is above the overfilled line
		//			for(int j = t.getMaxStudents(); j < size; j++){
		//				//Check if they have anymore third choices.
		//				currentStudent=t.getAssigned().get(j);
		//				//Printspam the details of each student.
		//				System.out.println(currentStudent.getStudentNum() + " - " + currentStudent.getName());
		//				if(currentStudent.getThirdChoiceLabs().size()>1){
		//					//For each lab choice
		//					for(Timeslot choice:currentStudent.getThirdChoiceLabs()){
		//						//If it is not the current lab
		//						if(choice != t){
		//							//And is not overfilled already
		//							if(!choice.isOverfilled()){
		//								//Move the student into that lab
		//								t.removeStudent(currentStudent);
		//								choice.addStudent(currentStudent);
		//								currentStudent.setAssignedLab(choice);
		//								//Printspam the lab this student is assigned to
		//								System.out.println("Moved to third choice lab: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
		//							}
		//						}
		//					}
		//				}
		//			}
		//		}
		//
		//		/*Flag students in overfilled labs*/
		//
		//		//Create a list of overfilled labs
		//		overfilledLabsList = overFilledLabs();
		//		//For each lab
		//		for(Timeslot t : overfilledLabsList){
		//			//Flag every student who is still over the maximum lab size
		//			for(int z = t.getMaxStudents(); z < t.getAssigned().size(); z++){
		//				currentStudent = t.getAssigned().get(z);
		//				//Printspam the details of each student.
		//				System.out.println(currentStudent.getStudentNum() + " - " + currentStudent.getName());
		//				flagged.add(t.getAssigned().get(z));
		//				//TODO: Should this also be removing the student??????????????????????????
		//				//Printspam that student is not assigned.
		//				System.out.println("Not Assigned");
		//			}
		//		}
		//		System.out.println("\n");
	}





	public ArrayList<Timeslot> overFilledLabs(){
		ArrayList<Timeslot> overfilledLabsList = new ArrayList<Timeslot>();
		for(Timeslot t : labs){
			if(t.isOverfilled()){
				overfilledLabsList.add(t);
			}
		}
		return overfilledLabsList;
	}


	/**
	 * Creates a hashmap containing each lab/tutorial as keys and an arraylist of students assigned to
	 * that timeslot as values. This hashmap is saved in the variable 'output', and serves as the input
	 * to the GUI.
	 */
	private void generateAlgorithmOutput() {
		//Begin console output.
		System.out.println("generateAlgorithmOutput() in HowardsSort");
		//Iterate through Labs.
		System.out.println("Labs:");
		for(Timeslot t:labs){
			//Add the lab and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the lab and its assigned students.
			System.out.println(t.getDay() + ", " + t.getStartTime() + "-" + t.getEndTime());
			for(Student s: t.getAssigned()){
				System.out.println(s.getStudentNum() + " - " + s.getName());
			}
		}
		System.out.println();

		//Iterate through Tutorials.
		System.out.println("Tuts:");
		for(Timeslot t:tutorials){
			//Add the tutorial and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the tutorial and its assigned students.
			System.out.println(t.getDay() + ", " + t.getStartTime() + "-" + t.getEndTime());
			for(Student s: t.getAssigned()){
				System.out.println(s.getStudentNum() + " - " + s.getName());
			}
		}
		System.out.println();

		//Printspam the flagged students.
		System.out.println("Flagged:");
		for(Student s: flagged){
			System.out.println(s.getStudentNum() + " - " + s.getName());
			output.addFlagged(s);
		}
	}


}
