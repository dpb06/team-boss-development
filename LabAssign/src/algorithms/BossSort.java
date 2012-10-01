package algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class BossSort {

	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private PriorityQueue<Student> priority = new PriorityQueue<Student>();
	private ArrayList<Timeslot> notFull = new ArrayList<Timeslot>();
	private ArrayList<Student> flagged= new ArrayList<Student>();
	private HashMap<Timeslot,ArrayList<Student>> output = new HashMap<Timeslot, ArrayList<Student>>();


	public BossSort (ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;

		priorityCalculator();
		System.out.println();
		System.out.println("priorityCalculator() in BossSort");
		sort();
		System.out.println();
		System.out.println("FitnessFunctions(tuts,stus,labs) in BossSort");
		new FitnessFunctions(tutorials, students, labs);

		guiOutput();
	}

	/**
	 * Prioritizes students according to the number and types of lab/tutorial choices they didn't mark
	 * as 'cannot attend'. These students are then added to a priorityQueue called 'priority'.
	 */
	private void priorityCalculator() {
		//Initialize integer values to represent priority, and factors that affect it. 
		int studentPriority;
		int first;
		int second;
		int third;
		//Iterate list of students.
		for(Student s:students){
			//Largest priority weighting is the number of labs the student can attend.
			studentPriority = s.getnumOfChoiceLab()*1000;
			//Next is the number of third choices the student has selected.
			third = s.getThirdLabs().size()*3;
			//Then the number of second choices the student has selected.
			second = s.getSecondLabs().size()*2;
			//Finally the number of first choices the student has selected.
			first = s.getFirstLabs().size();
			//Combine priority values by multiplying the lab choices using the number of labs as a factor.
			studentPriority = studentPriority*(first+second+third);
			//Add an element of randomization.
			studentPriority = studentPriority+ ((int) (Math.random()*1000));
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
			System.out.println(s.getFirstName()+" Priority: "+s.getPriority());
		}

		//How many choices in total do they have? (More = higher priority)
		//How many first choices do they have? (More = lower priority)
		//How many second choices do they have? (More = lower priority)
		//How many third choices do they have? (More = lower priority)

	}

	/**
	 * Places each student (in priority order) into a Timeslot, according to the fullness of each Timeslot
	 * and the student's choices. The Timeslot object is altered to reflect the student's placement.
	 * If the student cannot be assigned, they will be added to a list of flagged students.
	 */
	// TODO: Bump third choices up to second if first choices are full???
	public void sort(){

		//Create a list of labs that aren't full
		for (Timeslot t : labs){
			notFull.add(t);
		}
		//For every student (in priority order)
		while(priority.size() > 0){
			Student s=priority.poll();
			System.out.println("Student priority: "+s.getPriority());
			//WHILE assigned = false
			boolean assigned = false;
			while(!assigned ){
				//Create a list of first choices
				//Check if those choices are in the list of labs that aren't full
				ArrayList<Timeslot> choices = s.getFirstLabs();
				//If the list is now empty
				while(choices.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = choices.get((int) (Math.random()*choices.size()));
					//Try to add student to the chosen lab
					if(choice.addStudent(s)){
						System.out.println("First");
						System.out.println(choice.getDay() + "\n");
						//If successful, assigned = true
						assigned = true;
						break;
					}
					//If unsuccessful
					else {
						//Remove lab from list of labs that aren't full
						notFull.remove(choice);
						choices.remove(choice);

					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//Create a list of second choices
				//Check if those choices are in the list of labs that aren't full
				ArrayList<Timeslot> choiceSecond = s.getSecondLabs();
				//If the list is now empty
				while(choiceSecond.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = choiceSecond.get((int) (Math.random()*choiceSecond.size()));
					//Try to add student to the chosen lab
					if(choice.addStudent(s)){
						System.out.println("Second");
						System.out.println(choice.getDay() + "\n");
						//If successful, assigned = true
						assigned = true;
						break;
					}
					//If unsuccessful
					else {
						//Remove lab from list of labs that aren't full
						notFull.remove(choice);
						choiceSecond.remove(choice);
					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//Create a list of third choices
				//Check if those choices are in the list of labs that aren't full

				ArrayList<Timeslot>choiceThird = s.getThirdLabs();

				//If the list is now empty
				while(choiceThird.size() > 0){
					//Randomly pick one of those choices and assign it to a variable
					Timeslot choice = choiceThird.get((int) (Math.random()*choiceThird.size()));
					//Try to add student to the chosen lab
					if(choice.addStudent(s)){
						System.out.println("Third");
						System.out.println(choice.getDay() + "\n");
						//If successful, assigned = true
						assigned = true;
						break;
					}
					//If unsuccessful
					else {
						//Remove lab from list of labs that aren't full
						notFull.remove(choice);
						choiceThird.remove(choice);
					}
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//If student cannot be assigned, add them to a list of flagged students and carry on without assigning them.
				flagged.add(s);
				System.out.println("Not Assigned: " + s.getFirstName() + "\n");
				break;

			}
		}
	}

	/**
	 * Creates a hashmap containing each lab/tutorial as keys and an arraylist of students assigned to
	 * that timeslot as values. This hashmap is saved in the variable 'output', and serves as the input
	 * to the GUI.
	 */
	private void guiOutput() {
		try{
			// Create file 
			FileWriter fstream = new FileWriter("Output/printout.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("guiOutPut() in BossSort");
			out.newLine();
			//Iterate through Timeslots.
			for(Timeslot t:labs){
				//Add the timeslot and its assigned students to the output hashmap.
				output.put(t,  t.getAssigned());
				//Printspam the timeslot and its assigned students.
				out.write(t.getDay() + ": " + t.getStartTime() + "-" + t.getEndTime());
				out.newLine();
				for(Student s: t.getAssigned()){
					out.write("\t "+s.getFirstName());
					out.newLine();
				}
			}
			//Printspam the flagged students.
			out.newLine();
			out.write("Not Assigned:");
			out.newLine();
			for(Student s: flagged){
				out.write("\t "+s.getFirstName());
				out.newLine();
			}
			//Close the output stream
			out.close();

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	//Before finding priority, if a Student has no first choices, bump up all their choices.
	//Flag every Student that has their choices bumped.

	public HashMap<Timeslot, ArrayList<Student>> getOutput() {
		return output;
	}

}