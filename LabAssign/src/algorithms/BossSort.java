package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class BossSort {


	/**
	 *  Picking students - how do we order this? (Possibly parse straight into PriorityQueue?)
	 *  		-Priority 1 students who only have 1 choice
	 *  		-Priority 2 Students who only have 2-3 choices
	 *  		-Priority 3 students who have more than 3 choices 
	 *  
	 *  What happens if a student can only attend one lab? (Note they might not list this as a first)
	 *  If a lab is overfilled, who do we move first?
	 *  If a lab is overfilled, how do find students with more first choices? (PriorityQueue/Stack for Timeslot data structure)
	 *  What if a lab is underfilled? (How do define underfilled?)
	 *  What if cutting a lab is the best move, but it causes (a lot of) difficulty with one or two students?
	 *  
	 *  What ranges/weights do we need for our fitness functions?
	 *  
	 */
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private PriorityQueue<Student> priority;
	private ArrayList<Timeslot> notFull = new ArrayList<Timeslot>();
	private ArrayList<Student> flagged= new ArrayList<Student>();
	private HashMap<Timeslot,ArrayList<Student>> output = new HashMap<Timeslot, ArrayList<Student>>();

	public BossSort (ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
		priority=new PriorityQueue<Student>();

		priorityCalculator();
		System.out.println();
		sort();
		System.out.println();
		new FitnessFunctions(tutorials, students, labs);
		
		guiOutPut();
	}
	private void guiOutPut() {
		// TODO Auto-generated method stub
		System.out.println();
		for(Timeslot t:labs){
			output.put(t,  t.getAssigned());
    		System.out.println(t.getDay());
			for(Student s: t.getAssigned()){
				System.out.println("\t "+s.getFirstName());
			}
		}
		System.out.println();
		System.out.println("Flagged students");
		for(Student s: flagged){
			System.out.println("\t "+s.getFirstName());
		}
	}


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
				ArrayList<Timeslot> choices = checkLabs(s.getFirstLabs());
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
				synchronized (s) {
				ArrayList<Timeslot> choiceSecond = checkLabs(s.getSecondLabs());
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
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//Create a list of third choices
				//Check if those choices are in the list of labs that aren't full
				synchronized (s) {
									ArrayList<Timeslot>choiceThird = checkLabs(s.getThirdLabs());
			
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
				}
				//If assigned, break outer loop.
				if(assigned){
					break;
				}

				//If student cannot be assigned, add them to a list of flagged students and carry on without assigning them.
				flagged.add(s);
				System.out.println("Flagged: " + s.getFirstName() + "\n");
				break;

			}
		}
	}

		private ArrayList<Timeslot> checkLabs(ArrayList<Timeslot> choices) {
			//For each choice given
			for (Timeslot t : choices){
				//If that choice is full
				if (!notFull.contains(t)){
					//Remove that choice from the list
					//choices.remove(t);
				}
			}
			//Return the altered list
			return choices;
		}

	//HashMap for each Student, linking each Timeslot to choice number
	//Priority of Student in Student
	//PriorityQueue of Students to be assigned in BossSort (Low points = high priority)



	//Before finding priority, if a Student has no first choices, bump up all their choices.
	//Flag every Student that has their choices bumped.

	private void priorityCalculator() {
		int studentPri;
		int first;
		int second;
		int third;
		for(Student s:students){
			studentPri=s.getnumOfChoiceLab()*1000;
			first=s.getFirstLabs().size();
			second=s.getSecondLabs().size()*2;
			third=s.getThirdLabs().size()*3;
			studentPri=studentPri*(first+second+third);
			studentPri = studentPri+ ((int) (Math.random()*1000));
			s.setPriority(studentPri);
			priority.add(s);
			System.out.println(s.getFirstName()+" Priority: "+s.getPriority());
		}

		//How many choices in total do they have? (More = higher priority)
		//How many first choices do they have? (More = lower priority)
		//How many second choices do they have? (More = lower priority)
		//How many third choices do they have? (More = lower priority)

	}






//	 Possible priority clashes:
//		 All lab attendees cannot attend any other labs. (Not gonna happen)
//		 Clashing attendees have no other choices.
//		 Clashing attendees' other choices have no openings.
//
//
//
//
//
//
//		 Possible alarms to raise:
//		 Many students prefer a particular time slot (total points assigned to that lab is very low.)
//		 Many students cannot make a particular time slot
//		 A time slot has very few students assigned to it after algorithm is completed
//		 Time slots are very unevenly allocated
//
//
//	 */




}
