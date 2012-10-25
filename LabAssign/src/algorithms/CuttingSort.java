package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import testing.JUnitTestingData;
import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class CuttingSort implements Algorithm  { 
 private final boolean DEBUG = false; 

	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	// private HashMap<Timeslot,TimeSlotTotals> totals=new HashMap<Timeslot,
	// TimeSlotTotals>();
	private HashMap<Timeslot, Integer> onlyAttends = new HashMap<Timeslot, Integer>();

	public CuttingSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials,
			ArrayList<Student> students) {
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
	}

	public AlgorithmOutput start() {
		// For each timeslot
		for (Timeslot t : labs) {
			// Add to map, and initialize value
			onlyAttends.put(t, new Integer(0));
		}
		// for each student
		for (Student s : students) {
			// if they have only one choice
			if (s.getNumCanAttendLabs() == 1) {
				// Find which timeslot was chosen
				Timeslot choice = null;
				if (s.getFirstChoiceLabs().size() == 1) {
					choice = s.getFirstChoiceLabs().get(0);
				} else if (s.getSecondChoiceLabs().size() == 1) {
					choice = s.getSecondChoiceLabs().get(0);
				} else if (s.getThirdChoiceLabs().size() == 1) {
					choice = s.getThirdChoiceLabs().get(0);
				}
				//Increment the onlyAttends value for this timeslot
				onlyAttends.put(choice, onlyAttends.get(choice) + 1);
			}
		}
		List<Timeslot> potentialRemovals = new ArrayList<Timeslot>();
		for (Timeslot t : onlyAttends.keySet()) {
			//if(DEBUG){ System.out.println(t + " has " + onlyAttends.get(t)	+ " unique attendees"); } 
			if(onlyAttends.get(t).equals(0)){
				potentialRemovals.add(t);
			}
		}
		//Run bossSort on the data, and converts the timeslots to a list
		List<Timeslot> temp = new ArrayList<Timeslot>(new BossSort(new ArrayList<Timeslot>(labs), new ArrayList<Timeslot>(tutorials), new ArrayList<Student>(students)).start().keySet());
		//Sort the output of BossSort according to timeslot fullness (emptiest timeslots first)
		Collections.sort(temp, new TimeslotSizeComparator());
//					for (ArrayList<Student> s: new BossSort(labs, tutorials, students).start().values()){
//						for(Student t : s)
//						System.out.print(t);
//					}
//					System.exit(0);
		//For each timeslot
		for(Timeslot t: temp){
			//if(DEBUG){ System.out.println(t.getAssigned().size()+" maxSize="+t.getMaxStudents()); } 
			//If the timeslot has no persons who can only attend that slot
			if(potentialRemovals.contains(t) && allLabsBelowPreferred(temp)){
				//Remove the slot from labs
				labs.remove(t);
				//And then re-run bossSort on the outcome
				temp = new ArrayList<Timeslot>(new BossSort(new ArrayList<Timeslot>(labs), new ArrayList<Timeslot>(tutorials), new ArrayList<Student>(students)).start().keySet());
				//Re-sort the list
				Collections.sort(temp, new TimeslotSizeComparator());
			}
		}
		//Return the outcome of a BossSort on the reduced lab list		
		return new BossSort(new ArrayList<Timeslot>(labs), new ArrayList<Timeslot>(tutorials), new ArrayList<Student>(students)).start();
	}

	private boolean allLabsBelowPreferred(List<Timeslot> timeslots) {
		//For each timeslot
		for (Timeslot t: timeslots){
			//If the lab is currently above preferred max
			if (t.getAssigned().size()>t.getPreferredMax()){
				return false;
			}
		}
		//Else if they are all under preferredMax, return true
		return true;
	}

	public static void main(String[] args) {
		JUnitTestingData j = new JUnitTestingData();
		CuttingSort cs = new CuttingSort(j.getLabs(), j.getTutorials(),
				j.getStudents());
		cs.start();
	}

	/**
	 * Compare timeslots according the what percentage full they are.
	 * When used in a sort, this will cause the timeslots to be ordered emptiest to fullest
	 */	
	private class TimeslotSizeComparator implements Comparator<Timeslot>{

		@Override
		public int compare(Timeslot o1, Timeslot o2) {
			return o2.getAssigned().size()*100 /o2.getMaxStudents()- o1.getAssigned().size()*100 /o1.getMaxStudents();
		}
		
	}

}
