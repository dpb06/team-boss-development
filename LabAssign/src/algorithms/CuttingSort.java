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
	}


	public void labSizeOverview(){
		//For each timeslot
		for (Timeslot t : labs){
			//Add to map, and initialize value
			onlyAttends.put(t, new Integer(0));
		}

		//for each student
		for (Student s : students){
			//if they have only one choice
			if (s.getNumCanAttend()==1){
				//increment the cannot attends for this lab
				if (s.getFirstChoiceLabs().size() == 1){
					onlyAttends.put(s.getFirstChoiceLabs().get(0),(Integer) (onlyAttends.get(s.getFirstChoiceLabs()) + 1));
				} else if (s.getSecondChoiceLabs().size() == 1){
					onlyAttends.put(s.getSecondChoiceLabs().get(0),(Integer) (onlyAttends.get(s.getSecondChoiceLabs()) + 1));
				} else if (s.getThirdChoiceLabs().size() == 1){
					onlyAttends.put(s.getThirdChoiceLabs().get(0),(Integer) (onlyAttends.get(s.getThirdChoiceLabs()) + 1));
				}

			}
		}
		for(Timeslot t:onlyAttends.keySet()){
			System.out.println(t+" has "+onlyAttends.get(t)+" unique attendees");
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
