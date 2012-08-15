package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
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
	
	public BossSort (ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
		priority=new PriorityQueue<Student>(this.students.size(), new StudentComparator());
	
		priorityCalculator();
		new FitnessFunctions(tutorials, students, labs);

		checkQueue();
	}
	private void checkQueue() {
		while(priority.size() > 0){
		Student s = priority.poll();
		System.out.printf("%s, %d\n", s.getFirstName(), s.getPriority());
		}
	}
	public void sort(){
		//For every student
		for (Student s : priority){
			//Put first first lab choice into a variable
			Timeslot first = s.getFirstLabs().get(0);
			//Find index of choice in this.labs
			int index = labs.indexOf(first);
			//Find this.labs.choice.assigned
			Timeslot assign = labs.get(index);
			//Add student to this.labs.choice.assigned
			assign.addStudent(s);
		}
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
		priority.offer(s);
		System.out.println(s.getFirstName()+" Priority: "+s.getPriority());
	}
	
	//How many choices in total do they have? (More = higher priority)
	//How many first choices do they have? (More = lower priority)
    	//How many second choices do they have? (More = lower priority)
	        //How many third choices do they have? (More = lower priority)
	
	}
	
	
	//Randomly pick first choices
	  //Make a list for each lab containing students assigned to that lab who have more first choices 
	//Balanced out overflowed labs
	  //Randomly pick first choices
	
	
	

	
	
	
	/*Default algorithm (suggested by Vex)

	Point system:
	3rd choice = 1
	2nd choice = 3
	1st choice = 6
	cannot attend = 10

	Max point limit = |labs|*10 - 16 to ensure everyone lists at least two labs as possible.

	(The reason for the arbitrary points system is to allow prioritisation that works for the benefit of those who do not try to game the system. Numbers may need review.)

	People who can only attend one or partial labs must provide an explanation in advance so special consideration can be put in for them. This is handled by the lecturer.


	Conflict Resolution Priority:
	1) Pre-assigned by faculty (Students who require special considerations before algorithm is run)
	2) Not assigned

	Same assign state:
	 1) Cannot attend any other labs
	 2) Can attend other labs
	Same attendance:
	 1) Used fewest points
	 2) Used most points
	Same points:
	 1) Has another 1st choice
	 2) Has another 2nd choice
	 3) Has another 3rd choice
	Same choice:
	 1) Choice has more openings
	 2) Choice has less openings
	Same openings:
	 1) Choice has more overall points in it
	 2) Choice has less overall points in it
	Same points:
	 1) Random
	 
	 
	 Possible priority clashes:
		 All lab attendees cannot attend any other labs. (Not gonna happen)
		 Clashing attendees have no other choices.
		 Clashing attendees' other choices have no openings.






		 Possible alarms to raise:
		 Many students prefer a particular time slot (total points assigned to that lab is very low.)
		 Many students cannot make a particular time slot
		 A time slot has very few students assigned to it after algorithm is completed
		 Time slots are very unevenly allocated


	*/
	
	
	
	
}
