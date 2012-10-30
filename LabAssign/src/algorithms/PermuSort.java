package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import fitnessFunctions.FirstChoicePercent;
import fitnessFunctions.LabFullness;
import fitnessFunctions.ThirdChoicePercent;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.PermuLeafNode;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;


/** Permusort is a permutation algorithm which naively works through 
 * every possible combination of choices for the students and saves only the
 * combination which yields the highest happiness value.
 * 
 * As this is a naive algorithm with many trillion combinations (easily more)
 * this could easily run for a very long time and not finish. 
 * As such it is set to look for the best combination possible within a set time frame, 
 * and store only that one, finishing when the time period has past.
 * If given a long period of time (read "infinite") this would find the best combination.
 * 
 *  However as it stands this algorithm has an issue with the time it finishes, finishing 
 *  much too early so no useful results are found.  
 *  
 *  
 *  This would need to be resolved before this algorithm is used.
 *
 */

public class PermuSort implements Algorithm { 
 private final boolean DEBUG = false; 


	//-----FIELDS-----\\
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private int numStudents;
	private ArrayList<Student> flagged = new ArrayList<Student>();
	private AlgorithmOutput output = new AlgorithmOutput();
	int counting=0;
	private PermuLeafNode start;
	private PermuLeafNode current;

	private static double count = 0;
	private double bestFitness = 0;
	private boolean kill = false;
	private HashMap<Timeslot, Integer> numAssignedToTimeslot = new HashMap<Timeslot, Integer>(); //Used by fitness functions only


	//-----CONSTRUCTOR-----\\
	public PermuSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
		this.numStudents = students.size();
		//Map each timeslot to the amount of students assigned to it.
		for(Timeslot t:labs){
			numAssignedToTimeslot.put(t, 0);
		}
		//Order students by the number of labs they can attend.
		Collections.sort(students,new Comparator<Student>(){
			@Override
			public int compare(Student arg0, Student arg1) {
				Student s1=arg0;
				Student s2=arg1;
				if(s1.getNumCanAttendLabs() > s2.getNumCanAttendLabs()){
					return 1;
				}
				else if(s1.getNumCanAttendLabs() < s2.getNumCanAttendLabs()){
					return -1;
				}
				return 0;
			}

		});
	}


	//-----INTERFACE METHODS-----\\
	public AlgorithmOutput start() {
		//Find permutation with best fitness.
		sortLabs();
		//Generate that permutation's output.
		generateAlgorithmOutput();
		//Return output.
		return output;
	}


	//-----FUNCTIONALITIES-----\\
	/**
	 * Initializes the tree, then proceeds to create a killthread and call the timed method of this algorithm.
	 */
	public void sortLabs(){
		//Begin console output.
		if(DEBUG){ System.out.println("sortLabs() in PermuteSort"); } 
		//For each student in order of how few labs they can attend. 
		for(Student s:students){
			//If the student hasn't already been flagged
			if(!s.getFlaggedForLabs()){
				Collections.sort(students,new Comparator<Student>(){
					@Override
					public int compare(Student arg0, Student arg1) {
						Student s1=arg0;
						Student s2=arg1;
						if(s1.getNumCanAttendLabs()<s2.getNumCanAttendLabs()){
							return 1;
						}
						else if(s1.getNumCanAttendLabs()>s2.getNumCanAttendLabs()){
							return -1;
						}
						return 0;
					}

				});
			}
			//If the root node is null
			if(start==null){
				//Initialize a new tree, with this student as its root node value.
				start = new PermuLeafNode(s,null);
				current = start;
			}
			//Otherwise
			else{
				//Create a new node for this student with the current node as a parent.
				PermuLeafNode newNode = new PermuLeafNode(s,current);
				//Make the current node point to the new node.
				current.setNext(newNode);
				//Set the current node to the new node.
				current = newNode;
			}
		}
		//Begin timing the algorithm.
		long startTime = System.currentTimeMillis();
		if(DEBUG){ System.out.println("Starting PermuSort at system time: " + startTime); } 
		//Start a thread that will end the algorithm after a set period.
		new Killthread().start();
		//Start the algorithm that iterates through permutations, passing it the root node.
		iterateLabPermutations(start);
		//Printspam results.
		long endTime = System.currentTimeMillis();
		if(DEBUG){ System.out.println("Ending PermuSort at system time: " + endTime); } 
		long time = endTime-startTime;
		if(DEBUG){ System.out.println("Run time: \n"+time+" Millisecs"); } 
		time = time/1000;
		if(DEBUG){ System.out.println(time+" secs"); } 
		time = time/60;
		if(DEBUG){ System.out.println(time+" mins\n"); } 
	}

	/**
	 * Iterates through possible solutions, checking the fitness of each.
	 * @param currentNode - the root of the tree structure
	 */
	public void iterateLabPermutations(PermuLeafNode currentNode){
		//For every lab the student of the current node can possibly attend

		for(Timeslot t: currentNode.getAttendableLabs()){
			//If the lab size is under its preferred maximum
			if(numAssignedToTimeslot.get(t).intValue() < t.getPreferredMax()){
				//Assign the student of this node to that lab
				currentNode.setCurrentlyAssignedLab(t);
				//Increment the number assigned to that timeslot
				int numAssignedStudents = numAssignedToTimeslot.get(currentNode.getCurrentlyAssignedLab()).intValue();
				numAssignedToTimeslot.put(currentNode.getCurrentlyAssignedLab(), ++numAssignedStudents);
				//If this node has a child
				if(currentNode.getNext()!=null){
					//Check if algorithm killswitch has been flipped by the killthread
					if(!kill){
						//Recurse on child
						//if(DEBUG){ System.out.println(c.getStudent()+" : "+c.t.toString()); } 

						
						iterateLabPermutations(currentNode.getNext());
					}
					else{
						if(DEBUG){ System.out.println("process killed"); } 
					}
				}
				//If this node doesn't have a child
				else{
					//Mark that a solution has been found.
					count+=1;
					//Check fitness of this solution
					if(DEBUG){ System.out.println("found Solution"); } 
					checkFitness(currentNode);
				}
			}
		}
	}


	/**
	 * 
	 * @param currentNode - the bottom leaf of the tree structure
	 */
	private void checkFitness(PermuLeafNode currentNode) {
		//Initialize values for fitness measures and assigned students count for each lab 
		int countFirst=0;
		int countThird=0;
		double proposedFitness=0;

		//Work from the bottom of the tree back up to the root
		while(currentNode.getParent()!=null){
			//If the student of this node is assigned to a first choice
			if(currentNode.getStudent().getFirstChoiceLabs().contains(currentNode.getCurrentlyAssignedLab())){
				//Increment the first choice count
				countFirst++;
			}
			//If the student of this node is assigned to a third choice
			else if(currentNode.getStudent().getThirdChoiceLabs().contains(currentNode.getCurrentlyAssignedLab())){
				//Increment the third choice count
				countThird++;
			}
			//Check next node up
			currentNode = currentNode.getParent();
		}
		//If the student of the root node is assigned to a first choice
		if(currentNode.getStudent().getFirstChoiceLabs().contains(currentNode.getCurrentlyAssignedLab())){
			//Increment the first choice count
			countFirst++;
		}
		//If the student of the root node is assigned to a third choice
		else if(currentNode.getStudent().getThirdChoiceLabs().contains(currentNode.getCurrentlyAssignedLab())){
			//Increment the third choice count
			countThird++;
		}
		//Find the percentages of students in first and third choices
		double percentFirst = (double)countFirst/(double)numStudents;
		double percentThird = (double)countThird/(double)numStudents;

		int labCount = 0;
		double averageLabFullness = 1;
		//For every LAB
		for(Timeslot t:numAssignedToTimeslot.keySet()){
			int assigned = numAssignedToTimeslot.get(t);
			//If lab is empty
			if(assigned == 0){
				//Increment lab fullness by 1
				averageLabFullness++;
			}
			//If lab isn't empty
			else{
				//Increase fullness by the percentage of the preferred maximum
				averageLabFullness += (assigned/t.getPreferredMax());	
			}
			labCount++;
		}
		//Find the average fullness
		averageLabFullness /= labCount;
		//Set the fitness value of this iteration
		proposedFitness=(averageLabFullness+percentFirst+(1-percentThird))/3;
		//If the value of this iteration is greater than the best current value
		if(proposedFitness > bestFitness){
			//if(DEBUG){ System.out.println("found a better Solution"); } 
			//if(DEBUG){ System.out.println(percentFirst+" percent first choice"); } 
			//if(DEBUG){ System.out.println(percentThird+" percent third choice "+(1-percentThird)+" fitness value"); } 
			//if(DEBUG){ System.out.println("average value: "+averageLabFullness); } 
			//Set the best fitness to this
			bestFitness = proposedFitness;
			//if(DEBUG){ System.out.println("Highest fitness function value so far: "+bestFitness); } 

			//Iterate from root to bottom leaf
			while(currentNode.getNext()!=null){
				//If the student of the current node isn't already assigned to the Timeslot the node wishes to assign it to
				if(!currentNode.getCurrentlyAssignedLab().getAssigned().contains(currentNode.getStudent())){
					//Assign it
					currentNode.getCurrentlyAssignedLab().addStudent(currentNode.getStudent());
					//For each lab
					for(Timeslot z:labs){
						//If it isn't the same lab
						if(z!=currentNode.getCurrentlyAssignedLab()){
							//If that lab has the same student assigned 
							if(z.getAssigned().contains(currentNode.getStudent())){
								//Remove the student from that lab
								z.removeStudent(currentNode.getStudent());
							}
						}
					}
				}
				//Move onto next node
				currentNode = currentNode.getNext();
			}
			//If the last node's student isn't already assigned to the Timeslot the node wishes to assign it to 
			if(!currentNode.getCurrentlyAssignedLab().getAssigned().contains(currentNode.getStudent())){
				//Assign it
				currentNode.getCurrentlyAssignedLab().addStudent(currentNode.getStudent());
				//For each lab
				for(Timeslot z:labs){
					//If it isn't the same lab
					if(z!=currentNode.getCurrentlyAssignedLab()){
						//If that lab has the student assigned
						if(!(z==currentNode.getCurrentlyAssignedLab()) && z.getAssigned().contains(currentNode.getStudent())){
							//Remove the student from that lab
							z.removeStudent(currentNode.getStudent());
						}
					}
				}
			}
		}
		//if(DEBUG){ System.out.println("Proposed fitness: " + proposedFitness); } 
	}

	/**
	 * Creates a hashmap containing each lab/tutorial as keys and an arraylist of students assigned to
	 * that timeslot as values. This hashmap is saved in the variable 'output', and serves as the input
	 * to the GUI.
	 */
	private void generateAlgorithmOutput() {
		//Begin console output.
		if(DEBUG){ System.out.println("generateAlgorithmOutput() in PermuSort"); } 
		//Iterate through Labs.
		if(DEBUG){ System.out.println("Labs:"); } 
		for(Timeslot t:labs){
			//Add the lab and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the lab and its assigned students.
			if(DEBUG){ System.out.println(t.getDay() + ", " + t.getStartTime() + "-" + t.getEndTime()); } 
			for(Student s: t.getAssigned()){
				if(DEBUG){ System.out.println(s.getStudentNum() + " - " + s.getName()); } 
			}
		}
		if(DEBUG){ System.out.println(); } 

		//Iterate through Tutorials.
		if(DEBUG){ System.out.println("Tuts:"); } 
		for(Timeslot t:tutorials){
			//Add the tutorial and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the tutorial and its assigned students.
			if(DEBUG){ System.out.println(t.getDay() + ", " + t.getStartTime() + "-" + t.getEndTime()); } 
			for(Student s: t.getAssigned()){
				if(DEBUG){ System.out.println(s.getStudentNum() + " - " + s.getName()); } 
			}
		}
		if(DEBUG){ System.out.println(); } 
		new FirstChoicePercent(output);
		new ThirdChoicePercent(output);
		new LabFullness(output);
		//Printspam the flagged students.
		if(DEBUG){ System.out.println("Flagged:"); } 
		for(Student s: flagged){
			if(DEBUG){ System.out.println(s.getStudentNum() + " - " + s.getName()); } 
			output.addFlagged(s);
		}
		if(DEBUG){ System.out.println("End fitnees: " + bestFitness); } 
		if(DEBUG){ System.out.println(); } 
		if(DEBUG){ System.out.println("Solutions found: " + count); } 
	}


	/**
	 * A thread class that runs for a set amount of time, then changes the killswitch boolean to true to interrupt PermuSort
	 */
	private class Killthread extends Thread{
		private final int timeToRun = 120000; //2 min 
		public void run(){
			try {
				sleep(timeToRun);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			kill=true;
		}
	}


}
