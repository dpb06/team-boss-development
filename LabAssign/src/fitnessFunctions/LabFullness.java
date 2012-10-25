package fitnessFunctions;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Timeslot;

public class LabFullness implements FitnessFunction{

	private double fitness;

	public LabFullness(AlgorithmOutput output){
		//Initialise the average percentage of fullness for all labs.
		double average = 0;
		int count=0;
		//For each lab.
		for(Timeslot t: output.keySet()){
			count++;
			//Check how many students are assigned to it.
			int size = output.get(t).size();
			//Check maximum capacity.
			int max = t.getPreferredMax();
			//Calculate percentage.
			double labPercent;
			if(size!=0){
			labPercent = 100*(float)size/(float)max;
			//Print out each lab's fullness.
			}
			else{
				labPercent=100;
			}
			System.out.printf("Lab fullness: %.0f percent\n", labPercent);
			//Keep running total of average fullness.
			average += labPercent;
		}
		average /= count;
		//Make fitness equal to percentage of fullness of all labs.
		this.fitness = average;
		//Add fitness to AlgorithmOutput
		System.out.println(fitness);
		output.addFitness("LabFullness",fitness);
	}

	public double fitness(){
		return fitness;
	}
}