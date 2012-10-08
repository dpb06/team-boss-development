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

public class CuttingSort implements Algorithm {
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
		labSizeOverview();
	}

	public void labSizeOverview() {
		// For each timeslot
		for (Timeslot t : labs) {
			// Add to map, and initialize value
			onlyAttends.put(t, new Integer(0));
		}

		// for each student
		for (Student s : students) {
			// if they have only one choice
			if (s.getNumCanAttend() == 1) {
				// Find which timeslot was chosen
				Timeslot choice = null;
				if (s.getFirstChoices().size() == 1) {
					choice = s.getFirstChoices().get(0);
				} else if (s.getSecondChoices().size() == 1) {
					choice = s.getSecondChoices().get(0);
				} else if (s.getThirdChoices().size() == 1) {
					choice = s.getThirdChoices().get(0);
				}
				//Increment the onlyAttends value for this timeslot
				onlyAttends.put(choice, onlyAttends.get(choice) + 1);
			}
		}
		List<Timeslot> potentialRemovals = new ArrayList<Timeslot>();
		for (Timeslot t : onlyAttends.keySet()) {
			//System.out.println(t + " has " + onlyAttends.get(t)	+ " unique attendees");
			if(onlyAttends.get(t).equals(0)){
				potentialRemovals.add(t);
			}
		}
		//Run bossSort on the data, and converts the timeslots to a list
		List<Timeslot> temp = new ArrayList<Timeslot>(new BossSort(labs, tutorials, students).start().keySet());
		Collections.sort(temp, new TimeslotSizeComparator());
		for(Timeslot t: temp){
			System.out.println(t.getAssigned().size());
		}
	}

	public static void main(String[] args) {
		JUnitTestingData j = new JUnitTestingData();
		CuttingSort cs = new CuttingSort(j.getLabs(), j.getTutorials(),
				j.getStudents());
		cs.useTestData();
	}

	public void useTestData() {
		labSizeOverview();
	}

	@Override
	public AlgorithmOutput start() {
		// TODO Auto-generated method stub

		return null;
	}
	
	private class TimeslotSizeComparator implements Comparator<Timeslot>{

		@Override
		/**
		 * Compare the two timeslots according the what percentage full they are
		 */
		public int compare(Timeslot o1, Timeslot o2) {
			return o1.getAssigned().size()/o1.getMaxStudents() - o2.getAssigned().size()/o2.getMaxStudents();
		}
		
	}

}
