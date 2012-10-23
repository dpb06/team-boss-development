package fitnessFunctions;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class FirstChoicePercent implements FitnessFunction{

	private double fitness;
	
	public FirstChoicePercent(AlgorithmOutput output){
		//Initialise a count for total students and students in first choices.
		int total = 0;
		int firsts = 0;
		
		//Get a timeslot.
		for(Timeslot t: output.keySet()){
			//For each Student in that timeslot.
			for(Student s: output.get(t)){
				//Increment total.
				total++;
				//If student is in their first choice
				if(s.getFirstChoiceLabs().contains(s.getAssignedLab())){
					//Increment firsts
					firsts++;
				}
			}
		}
		
		//Make fitness equal to percentage of students in a first choice lab
		this.fitness = firsts/total;
		//Add fitness to AlgorithmOutput
		output.addFitness("FirstChoicePercent", (int) fitness);
	}
	
	public double fitness(){
		return fitness;
		
	}
	
	
}
