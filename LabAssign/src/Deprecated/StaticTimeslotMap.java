package Deprecated;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

@SuppressWarnings("serial")
public class StaticTimeslotMap extends HashMap<Integer, Timeslot>{


	public ArrayList<Timeslot> getFirsts(Student s){
		//Initialise return value.
		ArrayList<Timeslot> firsts = new ArrayList<Timeslot>();

		//Get integer representation of student's choices and save them locally.
		ArrayList<Integer> choices = s.getChoices();
		//Iterate student's choices.
		for(int i=0; i<choices.size(); i++){
			//Get first choices and save them to the return value.
			if(choices.get(i) == 1){
				firsts.add(this.get(i));
			}
		}
		return firsts;
	}


	public ArrayList<Timeslot> getSeconds(Student s){
		//Initialise return value.
		ArrayList<Timeslot> seconds = new ArrayList<Timeslot>();

		//Get integer representation of student's choices and save them locally.
		ArrayList<Integer> choices = s.getChoices();
		//Iterate student's choices.
		for(int i=0; i<choices.size(); i++){
			//Get first choices and save them to the return value.
			if(choices.get(i) == 1){
				seconds.add(this.get(i));
			}
		}
		return seconds;
	}


	public ArrayList<Timeslot> getThirds(Student s){
		//Initialise return value.
		ArrayList<Timeslot> thirds = new ArrayList<Timeslot>();

		//Get integer representation of student's choices and save them locally.
		ArrayList<Integer> choices = s.getChoices();
		//Iterate student's choices.
		for(int i=0; i<choices.size(); i++){
			//Get first choices and save them to the return value.
			if(choices.get(i) == 1){
				thirds.add(this.get(i));
			}
		}
		return thirds;
	}
	
	public ArrayList<Timeslot> getAllTimeslots(){
		ArrayList<Timeslot> allTimeslots=new ArrayList<Timeslot>();
		for(Integer i:keySet()){
			allTimeslots.add(get(i));
		}
		return allTimeslots;
	}


}