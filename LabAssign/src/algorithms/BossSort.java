package algorithms;

public class BossSort {

	
	/**
	 *  Picking students - how do we order this? (Possibly parse straight into PriorityQueue?)
	 *  What happens if a student can only attend one lab? (Note they might not list this as a first)
	 *  If a lab is overfilled, who do we move first?
	 *  If a lab is overfilled, how do find students with more first choices? (PriorityQueue/Stack for Timeslot data structure)
	 *  What if a lab is underfilled? (How do define underfilled?)
	 *  What if cutting a lab is the best move, but it causes (a lot of) difficulty with one or two students?
	 *  
	 *  What ranges/weights do we need for our fitness functions?
	 *  
	 */
	
	

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
