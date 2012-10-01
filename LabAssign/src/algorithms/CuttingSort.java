package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import testing.JUnitTestingData;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class CuttingSort implements Algorithm {
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	//private HashMap<Timeslot,TimeSlotTotals> totals=new HashMap<Timeslot, TimeSlotTotals>();
	private HashMap<Timeslot, Integer> onlyAttends = new HashMap<Timeslot, Integer>();


	public CuttingSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students=students;
		this.labs=labs;
		labSizeOverview();
//		printTotals();
	}

//	private void printTotals() {
//		//For each timeslot, print the titles
//		for(Timeslot t:totals.keySet()){
//			System.out.println(t.toString());
//			System.out.println(totals.get(t).toString());
//		}
//
//	}

	public void labSizeOverview(){
		//For each timeslot
		for (Timeslot t : labs){
			//Add to map, and initialize value
			onlyAttends.put(t, new Integer(0));
		}
		//for each student
		for (Student s : students){
			//if they have only one choice
			if (s.getChoiceCount()==1){
				//increment the cannot attends for this lab
				if (s.getFirstChoices().size() == 1){
					onlyAttends.put(s.getFirstChoices().get(0),(Integer) (onlyAttends.get(s.getFirstChoices()) + 1));
				} else if (s.getSecondChoices().size() == 1){
					onlyAttends.put(s.getSecondChoices().get(0),(Integer) (onlyAttends.get(s.getSecondChoices()) + 1));
				} else if (s.getThirdChoices().size() == 1){
					onlyAttends.put(s.getThirdChoices().get(0),(Integer) (onlyAttends.get(s.getThirdChoices()) + 1));
				}
			}
		}
		for(Timeslot t:onlyAttends.keySet()){
			System.out.println(t+" has "+onlyAttends.get(t)+" unique attendees");
			if (onlyAttends.get(t) == 0){
				labs.remove(t);
			}
		}
	}
	
	public static void main(String[] args){
		JUnitTestingData j= new JUnitTestingData();
		CuttingSort cs = new CuttingSort(j.getLabs(), j.getTutorials(), j.getStudents());
		cs.useTestData();
	}
	
	public void useTestData(){
		labSizeOverview();
	}

	@Override
	public AlgorithmOutput start() {
		// TODO Auto-generated method stub

		return null;
	}




}
