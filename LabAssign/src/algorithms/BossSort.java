package algorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

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
		priorityCalculator();

		System.out.println();
		System.out.println("priorityCalculator() in BossSort");

		sortLabs();
		modifyTuts();
		tutPriorityCalculator();
		sortTuts();

		System.out.println();
		System.out.println("FitnessFunctions(tuts,stus,labs) in BossSort");

		guiOutput();
		return output;
	}







	//-----FUNCTIONALITIES-----\\
	/**
	 * Prioritizes students according to the number and types of lab/tutorial choices they didn't mark
	 * as 'cannot attend'. These students are then added to a priorityQueue called 'priority'.
	 */
	private void priorityCalculator() {
		//Initialize integer values to represent priority, and factors that affect it. 
		int studentPriority;
		//Iterate list of students.
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
			System.out.println(s.getStudentNum()+" Priority: "+s.getPriority());
		}

		//How many choices in total do they have? (More = higher priority)
		//How many first choices do they have? (More = lower priority)
		//How many second choices do they have? (More = lower priority)
		//How many third choices do they have? (More = lower priority)

	}

	private void tutPriorityCalculator() {
		//Initialize integer values to represent priority, and factors that affect it. 
		int studentPriority;
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
			System.out.println(s.getStudentNum()+" Priority: "+s.getPriority());
		}
	}


	private void modifyTuts() {
		//For each student
		for (Student s : students){
			//Find assigned lab timeslot
			Timeslot assignedLab = s.getAssignedLab();
			//For each of the student's tutorial first choices
			for (Timeslot tut : s.getFirstChoiceTuts()){
				//If assigned lab time intersects with a time of a tutorial
				if(tut.compareTo(assignedLab) != 0){
					//Remove that tutorial from student's first choices
					s.removeFirstChoiceTut(tut);
				}
			}
			//For each of the student's tutorial second choices
			for (Timeslot tut : s.getSecondChoiceTuts()){
				//If assigned lab time intersects with a time of a tutorial
				if(tut.compareTo(assignedLab) != 0){
					//Remove that tutorial from student's second choices
					s.removeSecondChoiceTut(tut);
				}
			}
			//For each of the student's tutorial third choices
			for (Timeslot tut : s.getThirdChoiceTuts()){
				//If assigned lab time intersects with a time of a tutorial
				if(tut.compareTo(assignedLab) != 0){
					//Remove that tutorial from student's third choices
					s.removeThirdChoiceTut(tut);
				}
			}
		}
	}

	/**
	 * Places each student (in priority order) into a Timeslot, according to the fullness of each Timeslot
	 * and the student's choices. The Timeslot object is altered to reflect the student's placement.
	 * If the student cannot be assigned, they will be added to a list of flagged students.
	 */
	public void sortLabs(){

		//For every student (in priority order)
		while(priority.size() > 0){
			Student s=priority.poll();
			System.out.println("Student priority: "+s.getPriority());
			//WHILE assigned = false
			boolean assigned = false;
			while(!assigned ){
				//Create a list of first choices
				//Check if those choices are in the list of labs that aren't full
				ArrayList<Timeslot> firsts = s.getFirstChoiceLabs();
				//If the list is now empty
				while(firsts.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = firsts.get((int) (Math.random()*firsts.size()));
					//Try to add student to the chosen lab
					if(!choice.isOverfilled()){
						choice.addStudent(s);
						System.out.println("Third");
						System.out.println(choice.getDay() + "\n");
						assigned = true;
						break;
					}
					//If unsuccessful
					else {
						firsts.remove(choice);
					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//Create a list of second choices
				ArrayList<Timeslot> seconds = s.getSecondChoiceLabs();
				//Check if those choices are in the list of labs that aren't full
				//If the list is now empty
				while(seconds.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = seconds.get((int) (Math.random()*seconds.size()));
					//Try to add student to the chosen lab
					if(!choice.isOverfilled()){
						choice.addStudent(s);
						System.out.println("Third");
						System.out.println(choice.getDay() + "\n");
						assigned = true;
						break;
					}
					//If unsuccessful
					else {
						seconds.remove(choice);
					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//Create a list of third choices
				//Check if those choices are in the list of labs that aren't full
				//Create a list of second choices
				ArrayList<Timeslot> thirds = s.getThirdChoiceLabs();
				//Check if those choices are in the list of labs that aren't full

				//If the list is now empty
				while(thirds.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = thirds.get((int) (Math.random()*thirds.size()));
					//Try to add student to the chosen lab
					if(!choice.isOverfilled()){
						choice.addStudent(s);
						System.out.println("Third");
						System.out.println(choice.getDay() + "\n");
						assigned = true;
						break;
					}
					//If unsuccessful
					else {
						thirds.remove(choice);
					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//If student cannot be assigned, add them to a list of flagged students and carry on without assigning them.
				flagged.add(s);
				System.out.println("Not Assigned: " + s.getStudentNum() + "\n");
				break;

			}
		}
	}


	private void sortTuts() {
		//For every student (in priority order)
		while(priority.size()>0){
			Student s = priority.poll();
			//While student is not assigned
			boolean assigned = false;
			while(!assigned){
				//Create a list of first choices
				ArrayList<Timeslot> firsts = s.getFirstChoiceTuts();
				//Iterate the list
				while(firsts.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = firsts.get((int) (Math.random()*firsts.size()));
					//If the Timeslot is not full
					if(!choice.isOverfilled()){
						//Add student to the chosen lab
						choice.addStudent(s);
						assigned = true;
					}
					//If Timeslot is full
					else {
						//Remove Timeslot from list of first choices
						firsts.remove(choice);
					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//Create a list of second choices
				ArrayList<Timeslot> seconds = s.getSecondChoiceTuts();
				//Iterate the list
				while(seconds.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = seconds.get((int) (Math.random()*seconds.size()));
					//If the Timeslot is not full
					if(!choice.isOverfilled()){
						//Add student to the chosen lab
						choice.addStudent(s);
						assigned = true;
					}
					//If Timeslot is full
					else {
						//Remove Timeslot from list of second choices
						seconds.remove(choice);
					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}
				
				//Create a list of third choices
				ArrayList<Timeslot> thirds = s.getThirdChoiceTuts();
				//Iterate the list
				while(thirds.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = thirds.get((int) (Math.random()*thirds.size()));
					//If the Timeslot is not full
					if(!choice.isOverfilled()){
						//Add student to the chosen lab
						choice.addStudent(s);
						assigned = true;
					}
					//If Timeslot is full
					else {
						//Remove Timeslot from list of third choices
						thirds.remove(choice);
					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}
				//If student cannot be assigned, add them to a list of flagged students and carry on without assigning them.
				else{
					flagged.add(s);
					break;
				}
			}
		}
	}

			/**
			 * Creates a hashmap containing each lab/tutorial as keys and an arraylist of students assigned to
			 * that timeslot as values. This hashmap is saved in the variable 'output', and serves as the input
			 * to the GUI.
			 */
			private void guiOutput() {
				System.out.println();
				System.out.println("guiOutPut() in BossSort");
				//Iterate through Timeslots.
				for(Timeslot t:labs){
					//Add the timeslot and its assigned students to the output hashmap.
					output.put(t,  t.getAssigned());
					//Printspam the timeslot and its assigned students.
					System.out.println(t.getDay() + ": " + t.getStartTime() + "-" + t.getEndTime());
					for(Student s: t.getAssigned()){
						System.out.println("\t "+s.getStudentNum());
					}
				}
				//Printspam the flagged students.
				System.out.println();
				System.out.println("Not Assigned:");
				for(Student s: flagged){
					System.out.println("\t "+s.getStudentNum());
					output.addFlagged(s);
				}
			}

			//Before finding priority, if a Student has no first choices, bump up all their choices.
			//Flag every Student that has their choices bumped.



		}