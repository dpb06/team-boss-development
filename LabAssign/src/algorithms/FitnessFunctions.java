package algorithms;

import java.util.ArrayList;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class FitnessFunctions {
	
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	
	private ArrayList<Float> total;
	
	private float firstChoice;
	private float secondChoice;
	private float thirdChoice;
	
	/**
	 * Initializes a FitnessFunctions class, prints how full each lab is (as percentage), prints percentage of students in each choice for each lab,
	 * prints overall percentage of students in first choice labs.
	 * @param tut
	 * @param student
	 * @param labs
	 */
	public FitnessFunctions(ArrayList<Timeslot> tut, ArrayList<Student>student,ArrayList<Timeslot> labs){
		this.students=student;
		this.labs=labs;
		this.tutorials=tut;
		LabFullness();
		System.out.printf("\n");
		PercentageFirstChoice();
		System.out.printf("\n");
	}
	
	/**
	 * Calculates how full a respective lab is and prints it to the console.
	 * Will need to store these values for later extensions.
	 */
	private void LabFullness() {
		//Initialize a variable to contain each lab's maximum amount of students
		int size;
		for(Timeslot t: labs){
			//Set size of lab
			size = t.getAssigned().size();
			//Print lab time/day details
			System.out.println(t.getTime()+" "+t.getDay());
			//Calculate fullness of lab as percentage
			float fullnessPercent = 100*size/t.getMaxStudents();
			//Print result
			System.out.printf("Lab fullness: %.0f%%\n", fullnessPercent);		
		}
	}
	
	/**
	 * Calculates the percentages of students in first/second/third choices and prints it to the console.
	 */
	private void PercentageFirstChoice(){
		//Initialize count variables
		int totalStudents=0;
		int numFirstChoice=0;
		int numSecondChoice=0;
		int numThirdChoice=0;
		
		total=new ArrayList<Float>();
		//For every lab
		for(Timeslot t: labs){
			//Get each student assigned to that lab
			for(Student s: t.getAssigned()){
				//Count what choice that lab was to this student
				if(s.getFirstLabs().contains(t)){
					numFirstChoice++;
				}
				else if(s.getSecondLabs().contains(t)){
					numSecondChoice++;
				}
				else if(s.getThirdLabs().contains(t)){
					numThirdChoice++;
				}
				//Increment total student count
				totalStudents++;
			}

			//Print lab time/day details
			System.out.println(t.getTime()+" "+t.getDay());
			//Calculate percentage of first choices in this lab
			firstChoice=100*numFirstChoice/totalStudents;
			//Print result
			System.out.printf("Students in first choice: %.0f%%\n",firstChoice);
			//Calculate percentage of second choices in this lab
			secondChoice=100*numSecondChoice/totalStudents;
			//Print result
			System.out.printf("Students in first choice: %.0f%%\n",secondChoice);
			//Calculate percentage of third choices in this lab
			thirdChoice=100*numThirdChoice/totalStudents;
			//Print result
			System.out.printf("Students in first choice: %.0f%%\n",thirdChoice);
			
			//Add first choice percentage to list. (Used for calculating percentage of first choices across all labs)
			total.add(firstChoice);
		}
		
		//Calculate sum of first choice percentages
		float sum = 0;
		for(float f: total){
			sum = sum + f;
		}
		//Average first choice percentage
		float overall = sum/total.size();
		//Print result
		System.out.printf("\nOverall first choices: %.0f%%",overall);
	}
}