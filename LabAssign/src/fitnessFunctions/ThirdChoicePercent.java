package fitnessFunctions;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class ThirdChoicePercent {

	private double fitness;
	
	public ThirdChoicePercent(AlgorithmOutput output){
		//Initialise a count for total students and students in first choices.
		int total = 0;
		int thirds = 0;
		
		//Get a timeslot.
		for(Timeslot t: output.keySet()){
			//For each Student in that timeslot.
			for(Student s: output.get(t)){
				//Increment total.
				total++;
				//If student is in their first choice
				if(s.getThirdChoiceLabs().contains(s.getAssignedLab())){
					//Increment firsts
					thirds++;
				}
			}
		}
		
		//Make fitness equal to percentage of students in a first choice lab
		this.fitness = 100*((double) 1-((double)thirds/(double)total));
		//Add fitness to AlgorithmOutput
		System.out.println(fitness);
		output.addFitness("ThirdChoicePercent", (int) fitness);
	}
	
	public double fitness(){
		return fitness;
		
	}
	
}
