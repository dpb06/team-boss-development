package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.TimeSlotTotals;
import algorithmDataStructures.Timeslot;

public class CuttingSort implements Algorithm {
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private HashMap<Timeslot,TimeSlotTotals> totals=new HashMap<Timeslot, TimeSlotTotals>();


	public CuttingSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students=students;
		this.labs=labs;
		labSizeOverview();
		printTotals();
	}

	private void printTotals() {
		for(Timeslot t:totals.keySet()){
			System.out.println(t.toString());
			System.out.println(totals.get(t).toString());
		}

	}

	public void labSizeOverview(){
		for(Timeslot t:labs){
			totals.put(t,new TimeSlotTotals());
		}
		for(Student s: students){
			if(s.getFirstChoices()!=null){
				for(Timeslot t:s.getFirstChoices()){
					totals.get(t).increment(1);
				}
			}
			if(s.getSecondChoices()!=null){
				for(Timeslot t:s.getSecondChoices()){
					totals.get(t).increment(2);
				}
			}
			if(s.getThirdChoices()!=null){
				for(Timeslot t:s.getThirdChoices()){
					totals.get(t).increment(3);
				}
			}
			if(s.getCannotAttend()!=null){
				for(Timeslot t:s.getCannotAttend()){
					totals.get(t).increment(0);
				}
			}
			if(s.getNumCanAttend()==1){
				if(s.getFirstChoices().size()==1){
					totals.get(s.getFirstChoices().get(0)).increment(4);
				}
				else if(s.getSecondChoices().size()==1){
					totals.get(s.getSecondChoices().get(0)).increment(4);
				}
				else if(s.getThirdChoices().size()==1){
					totals.get(s.getThirdChoices().get(0)).increment(4);
				}
			}
		}
	}



























	@Override
	public AlgorithmOutput start() {
		// TODO Auto-generated method stub

		return null;
	}




}
