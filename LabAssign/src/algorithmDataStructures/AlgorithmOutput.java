package algorithmDataStructures;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A data-storage class used to ensure algorithms all give the same output to the GUI. It includes:
 *   A map from each timeslot to their list of assigned students.
 *   A collection of fitness functions as a map from the function's name to the function's final integer value.
 *   A list of the students who were not assigned to any lab.
 *   NOTA BENE: Labs and tutorials are both saved in the same HashMap. This is because they can be differentiated
 *   by their types if necessary, and FitnessFunctions can still get all the Timeslots by calling .keySet()
 */
@SuppressWarnings("serial")
public class AlgorithmOutput extends HashMap<Timeslot,ArrayList<Student>>{

	//-----VARIABLES-----\\
	private HashMap<String,Double> fitness = new HashMap<String,Double>(); 
	private ArrayList<Student> flagged = new ArrayList<Student>();
	
	
	//-----ADD METHODS-----\\
	public void addFitness(String name, double value){
		fitness.put(name, value);
	}
	
	public void addFlagged(Student s){
		flagged.add(s);
	}
	
	public void addFlagged(ArrayList<Student> flagged){
		this.flagged.addAll(flagged);
	}
	
	
	//-----GET METHODS-----\\
	public ArrayList<Student> getFlagged(){
		return flagged;
	}
	
	public HashMap<String,Double> getFitness(){
		return fitness;
	}
	
	public double fitnessValue(){
		double fitnessValue=0;
		double count=0;
		for(String s: fitness.keySet()){
			fitnessValue+=fitness.get(s);
			count++;
		}
		return fitnessValue/count;
	}
}
