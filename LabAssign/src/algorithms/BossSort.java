package algorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import fitnessFunctions.FirstChoicePercent;
import fitnessFunctions.LabFullness;
import fitnessFunctions.ThirdChoicePercent;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class BossSort implements Algorithm{


	//TODO: Implement hard/soft bossSort implementations that function on maxstudents and preferred max



	//-----FIELDS-----\\
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private PriorityQueue<Student> priority = new PriorityQueue<Student>();
	private ArrayList<Student> flagged= new ArrayList<Student>();
	private AlgorithmOutput output = new AlgorithmOutput();


	//-----CONSTRUCTOR-----\\
	public BossSort (ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
	}


	//-----INTERFACE METHODS-----\\
	public AlgorithmOutput start() {
		if(!labs.isEmpty()){
			//Prioritize students by their lab choices
			labPriorityCalculator();
			//Assign labs to students
			sortLabs();
		}
		System.out.println("Number of students: " + students.size());
		if(!tutorials.isEmpty()){
			if(!labs.isEmpty()){
				//Remove tutorial choices that clash with assigned labs
				TutorialChecker tc = new TutorialChecker(students);
				students = tc.getStudents();
			}		
			//Prioritize students by their tutorial choices
			tutPriorityCalculator();
			//Assign tutorials to students
			sortTuts();
		}
		//Create an AlgorithmOutput object
		generateAlgorithmOutput();
		System.out.println("\n\n");
		//Return output
		return output;
	}







	//-----FUNCTIONALITIES-----\\
	/**
	 * Prioritizes students according to the number and types of lab choices they didn't mark
	 * as 'cannot attend'. These students are then added to a global priorityQueue called 'priority'.
	 */
	private void labPriorityCalculator() {
		//Begin console output.
		System.out.println("labPriorityCalculator() in BossSort");
		//Initialize integer values to represent priority, and the factors that affect it. 
		int studentPriority;
		//For every student.
		for(Student s:students){
			//Initialise variables for assigning priority points for each choice.
			int thirdPoints = 0;
			int secondPoints = 0;
			int firstPoints = 0;

			//Largest priority weighting is the number of labs the student can attend.
			studentPriority = s.getNumCanAttendLabs()*1000;
			//Next is the number of third choices the student has selected.
			thirdPoints = s.getThirdChoiceLabs().size()*3;
			//Next is the number of second choices the student has selected.
			secondPoints = s.getSecondChoiceLabs().size()*2;
			//Next is the number of first choices the student has selected.
			firstPoints = s.getFirstChoiceLabs().size();

			//Combine priority values by multiplying the lab choices using the number of labs as a factor.
			studentPriority = studentPriority*(firstPoints+secondPoints+thirdPoints);
			//Add an element of randomization.
			studentPriority = studentPriority+((int) (Math.random()*1000));
			/*
			 * This means:
			 *   The students with fewest lab choices have a lower priority-value.
			 *   In cases of equal number of lab choices:
			 *     The students with fewest third choices have a lower priority-value.
			 *     The students with fewest second choices have a lower priority-value.
			 *     
			 * Thus, the algorithm primarily attempts to place students in their first choices,
			 * and does not prioritize placing students in a second choice if they can't be placed
			 * in a first choice. 
			 *   
			 * NB: A LOW PRIORITY-VALUE EQUATES TO A HIGH PRIORITY.
			 *    (as seen in the compareTo method of the Student class)
			 */
			//Set the priority in the Student object.
			s.setPriority(studentPriority);
			//Add the student to the priorityQueue.
			priority.add(s);
			//Printspam the priority of each student.
			System.out.println(s.getStudentNum() + " - " + s.getName() + ", Priority: " + s.getPriority());
		}

		//How many choices in total do they have? (More = higher priority)
		//How many first choices do they have? (More = lower priority)
		//How many second choices do they have? (More = lower priority)
		//How many third choices do they have? (More = lower priority)

		System.out.println("\n");

	}


	/**
	 * Places each student (in priority order) into a Timeslot, according to the fullness of each Timeslot
	 * and the student's choices. The Timeslot object is altered to reflect the student's placement.
	 * If the student cannot be assigned, they will be added to a list of flagged students.
	 */
	public void sortLabs(){
		//Begin console output.
		System.out.println("sortLabs() in BossSort");
		//For every student (in priority order)
		while(priority.size() > 0){
			Student s=priority.poll();
			//Printspam the priority of each student.
			System.out.println(s.getStudentNum() + " - " + s.getName() + ", Priority: " + s.getPriority());
			//Assert student is not assigned
			boolean assigned = false;

			//Create a list of first choices
			ArrayList<Timeslot> firsts = s.getFirstChoiceLabs();
			//Iterate the list
			while(firsts.size() > 0){
				//Randomly pick one of those choices and assign it to a variable
				Timeslot choice = firsts.get((int) (Math.random()*firsts.size()));
				//If the Timeslot is not full
				if(labs.contains(choice) && !choice.isOverfilled()){
					//Add student to the chosen lab
					choice.addStudent(s);
					//Set assigned lab in student object
					s.setAssignedLab(choice);
					assigned = true;
					//Printspam the lab this student is assigned to
					System.out.println("Assigned to first choice lab: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
					break;
				}
				//If Timeslot is full
				else {
					//Remove Timeslot from list of first choices
					firsts.remove(choice);
				}
			}
			if(!assigned){
				//Create a list of second choices
				ArrayList<Timeslot> seconds = s.getSecondChoiceLabs();
				//Iterate the list
				while(seconds.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = seconds.get((int) (Math.random()*seconds.size()));
					//If the Timeslot is not full
					if(labs.contains(choice) && !choice.isOverfilled()){
						//Add student to the chosen lab
						choice.addStudent(s);
						//Set assigned lab in student object
						s.setAssignedLab(choice);
						assigned = true;
						//Printspam the lab this student is assigned to
						System.out.println("Assigned to second choice lab: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
						break;
					}
					//If Timeslot is full
					else {
						//Remove Timeslot from list of second choices
						seconds.remove(choice);
					}
				}
			}


			if(!assigned){
				//Create a list of third choices
				ArrayList<Timeslot> thirds = s.getThirdChoiceLabs();
				//Iterate the list
				while(thirds.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = thirds.get((int) (Math.random()*thirds.size()));
					//If the Timeslot is not full
					if(labs.contains(choice) && !choice.isOverfilled()){
						//Add student to chosen lab
						choice.addStudent(s);
						//Set assigned lab in student object
						s.setAssignedLab(choice);
						assigned = true;
						//Printspam the lab this student is assigned to
						System.out.println("Assigned to third choice lab: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
						break;
					}
					//If Timeslot is full
					else {
						//Remove Timeslot from list of third choices
						thirds.remove(choice);
					}
				}
			}

			if(!assigned) {
				//If student cannot be assigned, add them to a list of flagged students and carry on without assigning them.
				if(!flagged.contains(s))
					flagged.add(s);
				s.setFlaggedForLabs(true);
				s.setReasonForFlagging("They cannot be put into any of their chosen labs");
				//Printspam that student is not assigned.
				System.out.println("Not Assigned");
			}
		}
		System.out.println("\n");
	}






	/**
	 * Prioritizes students according to the number and types of tutorial choices they didn't mark
	 * as 'cannot attend'. These students are then added to a priorityQueue called 'priority'.
	 */
	private void tutPriorityCalculator() {
		//Begin console output.
		System.out.println("tutPriorityCalculator() in BossSort");
		//Initialize integer values to represent priority, and factors that affect it. 
		int studentPriority;
		//Reinitialize priority, as it will still contain the same set of students from labPriorityCalculator
		priority = new PriorityQueue<Student>();
		//Check if there are no students in tutorials
		if(students != null){
			//Iterate list of students.
			for(Student s:students){
				//Initialise variables for assigning priority points for each choice.
				int thirdPoints = 0;
				int secondPoints = 0;
				int firstPoints = 0;

				//Largest priority weighting is the number of tuts the student can attend.
				studentPriority = s.getNumCanAttendTuts()*1000;
				//Next is the number of third choices the student has selected.
				thirdPoints = s.getThirdChoiceTuts().size()*3;
				//Next is the number of second choices the student has selected.
				secondPoints = s.getSecondChoiceTuts().size()*2;
				//Next is the number of first choices the student has selected.
				firstPoints = s.getFirstChoiceTuts().size();

				//Combine priority values by multiplying the tut choices using the number of tuts as a factor.
				studentPriority = studentPriority*(firstPoints+secondPoints+thirdPoints);
				//Add an element of randomization.
				studentPriority = studentPriority+((int) (Math.random()*1000));
				//Set the priority in the Student object.
				s.setPriority(studentPriority);
				//Add the student to the priorityQueue.
				priority.add(s);
				//Printspam the priority of each student.
				System.out.println(s.getStudentNum() + " - " + s.getName() + ", Priority: " + s.getPriority());
			}
		}
		System.out.println("\n");

	}



	/**
	 * Places each student (in priority order) into a Timeslot, according to the fullness of each Timeslot
	 * and the student's choices. The Timeslot object is altered to reflect the student's placement.
	 * If the student cannot be assigned, they will be added to a list of flagged students.
	 */
	private void sortTuts() {
		//Begin console output.
		System.out.println("sortTuts() in BossSort");
		//For every student (in priority order)
		while(priority.size()>0){
			Student s = priority.poll();
			//Printspam the priority of each student.
			System.out.println(s.getStudentNum() + " - " + s.getName() + ", Priority: " + s.getPriority());
			//While student is not assigned
			boolean assigned = false;

			//Create a list of first choices
			ArrayList<Timeslot> firsts = s.getFirstChoiceTuts();
			//Iterate the list
			while(firsts.size() > 0){
				//Randomly pick one of those choices and assign it to a variable
				Timeslot choice = firsts.get((int) (Math.random()*firsts.size()));
				//If the Timeslot is not full
				if(tutorials.contains(choice) && !choice.isOverfilled()){
					//Add student to the chosen lab
					choice.addStudent(s);
					//Set assigned lab in student object
					s.setAssignedTut(choice);
					assigned = true;
					//Printspam the tut this student is assigned to
					System.out.println("Assigned to first choice tut: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
					break;
				}
				//If Timeslot is full
				else {
					//Remove Timeslot from list of first choices
					firsts.remove(choice);
				}
			}
			if(!assigned){
				//Create a list of second choices
				ArrayList<Timeslot> seconds = s.getSecondChoiceTuts();
				//Iterate the list
				while(seconds.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = seconds.get((int) (Math.random()*seconds.size()));
					//If the Timeslot is not full
					if(tutorials.contains(choice) && !choice.isOverfilled()){
						//Add student to the chosen lab
						choice.addStudent(s);
						//Set assigned lab in student object
						s.setAssignedTut(choice);
						assigned = true;
						//Printspam the tut this student is assigned to
						System.out.println("Assigned to second choice tut: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
						break;
					}
					//If Timeslot is full
					else {
						//Remove Timeslot from list of second choices
						seconds.remove(choice);
					}
				}
			}
			if(!assigned){
				//Create a list of third choices
				ArrayList<Timeslot> thirds = s.getThirdChoiceTuts();
				//Iterate the list
				while(thirds.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = thirds.get((int) (Math.random()*thirds.size()));
					//If the Timeslot is not full
					if(tutorials.contains(choice) && !choice.isOverfilled()){
						//Add student to the chosen lab
						choice.addStudent(s);
						//Set assigned lab in student object
						s.setAssignedTut(choice);
						assigned = true;
						//Printspam the tut this student is assigned to
						System.out.println("Assigned to third choice tut: " + choice.getDay() + ", " + choice.getStartTime() + "-" + choice.getEndTime());
						break;
					}
					//If Timeslot is full
					else {
						//Remove Timeslot from list of third choiceslabs"
						thirds.remove(choice);
					}
				}
			}
			if(!assigned){
				//If student cannot be assigned, add them to a list of flagged students and carry on without assigning them.
				if(!flagged.contains(s))
				flagged.add(s);
				
				s.setFlaggedForTuts(true);
				s.setReasonForFlagging("They cannot be put into any of their chosen Tutorial");
				//Printspam that student is not assigned.
				System.out.println("Not Assigned");
			}
		}
		System.out.println("\n");
	}



	/**
	 * Creates a hashmap containing each lab/tutorial as keys and an arraylist of students assigned to
	 * that timeslot as values. This hashmap is saved in the variable 'output', and serves as the input
	 * to the GUI.
	 */
	private void generateAlgorithmOutput() {
		//Begin console output.
		System.out.println("generateAlgorithmOutput() in BossSort");
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
		new FirstChoicePercent(output);
		new ThirdChoicePercent(output);
		new LabFullness(output);
		//Printspam the flagged students.
		System.out.println("Flagged:");
		for(Student s: flagged){
			System.out.println(s.getStudentNum() + " - " + s.getName());
			output.addFlagged(s);
		}
	}
}
