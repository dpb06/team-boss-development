package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import fitnessFunctions.FirstChoicePercent;
import fitnessFunctions.LabFullness;
import fitnessFunctions.ThirdChoicePercent;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

/**
 * This is a Java implementation of howards original sorting algorithm he used using labview.
 * This is to have a benchmark to compare our newly implemented algorithm  boss sort.
 * @author phillijosh
 */

public class HowardsSort implements Algorithm {

	//-----FIELDS-----\\
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private ArrayList<Student> students;
	private ArrayList<Student> flagged = new ArrayList<Student>();
	private AlgorithmOutput output = new AlgorithmOutput();

	//-----CONSTRUCTOR-----\\
	public HowardsSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;

	}


	//-----INTERFACE METHODS-----\\
	@Override
	public AlgorithmOutput start() {
		//Give students a random order
		if(!labs.isEmpty()){
			Collections.shuffle(students);
			//Sort students into labs
			for(Student s: students){
				s.combineLabs();
				if(s.getCombinedLabs().isEmpty()){
					s.setFlaggedForLabs(true);
					s.setReasonForFlagging("Student has no first, second, or third lab choices.");
					flagged.add(s);
				}
			}
			for(Timeslot t:labs)
				t.sortAssigned();

			sortLabs();

		}
		if(!tutorials.isEmpty()){
			for(Student s: students){
				s.combineTuts();
							if(s.getCombinedTuts().isEmpty()){
							s.setFlaggedForTuts(true);
							s.setReasonForFlagging("Student has no first, second, or third lab choices.");
								if(!flagged.contains(s))
									flagged.add(s);
							}
			}
			if(!labs.isEmpty()){
				TutorialChecker tc = new TutorialChecker(students);
				students = tc.getStudents();
			}
			sortTuts();
		}
		generateAlgorithmOutput();
		//Return output
		return output;
	}


	private void sortTuts() {
		ArrayList<Timeslot>overfilledTuts;

		for(Student s: students){
			if(!s.getFlaggedForTuts()){
				s.setAssignedLab(s.getCombinedTuts().get(0));
				s.getCombinedTuts().get(0).addStudent(s);
			}
		}
		Student currentStudent;
		overfilledTuts=overFilledTuts();

		while(!overfilledTuts.isEmpty()){
			for(Timeslot t: overfilledTuts){
				for(int i=t.getPreferredMax();i<t.getAssigned().size();i++){
					currentStudent=t.getAssigned().get(i);
					if(currentStudent.getCurrentIndexLabs()+1<currentStudent.getCombinedLabs().size()){
						currentStudent.getCurrentTut().removeStudent(currentStudent);
						currentStudent.incrementIndexTuts();
						currentStudent.getCurrentTut().addStudent(currentStudent);
						currentStudent.setAssignedTut(currentStudent.getCurrentTut());
					}
					else{
						if(currentStudent.getAssignedTut()!=null){
							currentStudent.getAssignedTut().removeStudent(currentStudent);
							currentStudent.setAssignedTut(null);
						}
						currentStudent.setFlaggedForTuts(true);
						if(!flagged.contains(currentStudent)){
							flagged.add(currentStudent);
						}
					}
				}
				overfilledTuts=overFilledTuts();
			}
		}

	}



	@SuppressWarnings("unchecked")
	private void randomize() {
		Collections.shuffle(students);
	}


	//-----FUNCTIONALITIES-----\\
	public void sortLabs(){
		ArrayList<Timeslot>overfilledLabs;

		for(Student s: students){
			if(!s.getFlaggedForLabs()){
				s.setAssignedLab(s.getCombinedLabs().get(0));
				s.getCombinedLabs().get(0).addStudent(s);
			}
		}
		Student currentStudent;
		overfilledLabs=overFilledLabs();

		while(!overfilledLabs.isEmpty()){
			for(Timeslot t: overfilledLabs){
				for(int i=t.getPreferredMax();i<t.getAssigned().size();i++){
					currentStudent=t.getAssigned().get(i);
					if(currentStudent.getCurrentIndexLabs()+1<currentStudent.getCombinedLabs().size()){
						currentStudent.getCurrentLab().removeStudent(currentStudent);
						currentStudent.incrementIndexLabs();
						currentStudent.getCurrentLab().addStudent(currentStudent);
						currentStudent.setAssignedLab(currentStudent.getCurrentLab());
					}
					else{
						if(currentStudent.getAssignedLab()!=null){
							currentStudent.getAssignedLab().removeStudent(currentStudent);
							currentStudent.setAssignedLab(null);
						}
						currentStudent.setFlaggedForLabs(true);
						if(!flagged.contains(currentStudent)){
							flagged.add(currentStudent);
						}
					}
				}
				overfilledLabs=overFilledLabs();
			}
		}

	}
/**
 * Checks what Tutorials are over filled if so add them to ArrayList and return it.
 * @return
 */
	public ArrayList<Timeslot> overFilledTuts(){
		ArrayList<Timeslot> overfilledTutsList = new ArrayList<Timeslot>();
		for(Timeslot t : tutorials){
			if(t.getAssigned().size()>t.getPreferredMax()){
				overfilledTutsList.add(t);
			}
		}
		return overfilledTutsList;
	}

	public ArrayList<Timeslot> overFilledLabs(){
		ArrayList<Timeslot> overfilledLabsList = new ArrayList<Timeslot>();
		for(Timeslot t : labs){
			if(t.getAssigned().size()>t.getPreferredMax()){				
				overfilledLabsList.add(t);
			}
		}
		return overfilledLabsList;
	}


	/**
	 * Creates a hashmap containing each lab/tutorial as keys and an arraylist of students assigned to
	 * that timeslot as values. This hashmap is saved in the variable 'output', and serves as the input
	 * to the GUI.
	 */
	private void generateAlgorithmOutput() {
		//Begin console output.
		System.out.println("generateAlgorithmOutput() in HowardsSort");
		//Iterate through Labs.
		System.out.println("Labs:");
		for(Timeslot t:labs){
			//Add the lab and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the lab and its assigned students.
			System.out.println(t.getDay() + ", " + t.getStartTime() + "-" + t.getEndTime());
			for(Student s: t.getAssigned()){
				System.out.println(s.getStudentNum() + " - " + s.getName());
			}
		}
		System.out.println();

		//Iterate through Tutorials.
		System.out.println("Tuts:");
		for(Timeslot t:tutorials){
			//Add the tutorial and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the tutorial and its assigned students.
			System.out.println(t.getDay() + ", " + t.getStartTime() + "-" + t.getEndTime());
			for(Student s: t.getAssigned()){
				System.out.println(s.getStudentNum() + " - " + s.getName());
			}
		}
		System.out.println();
		new FirstChoicePercent(output);
		new ThirdChoicePercent(output);
		new LabFullness(output);
		output.addFitness("Not Flagged Students", 100*((double)1-((double)flagged.size()/(double)students.size())));
		//Printspam the flagged students.
		System.out.println("Flagged:");
		for(Student s: flagged){
			System.out.println(s.getStudentNum() + " - " + s.getName());
			output.addFlagged(s);
		}
		System.out.println("First choice Percentage: "+ output.getFitness().get("FirstChoicePercent"));
		System.out.println("Third choice Percentage: "+ output.getFitness().get("ThirdChoicePercent"));
		System.out.println("Labfullness Percentage: "+ output.getFitness().get("LabFullness"));
		System.out.println("Not Flagged Percentage: "+ output.getFitness().get("Not Flagged Students"));
	}


}
