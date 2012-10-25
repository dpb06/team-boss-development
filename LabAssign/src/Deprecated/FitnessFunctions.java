package Deprecated;

import java.sql.Time;
import java.util.ArrayList;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class FitnessFunctions  { 
 private final boolean DEBUG = false; 

	
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private ArrayList<Float> total;
	private static StaticTimeslotMap hash;
	
	private float labEveness;
	private float firstChoice;
	private float secondChoice;
	private float thirdChoice;
	
	public FitnessFunctions(ArrayList<Timeslot> tut, ArrayList<Student>student,ArrayList<Timeslot> labs){
		this.students=student;
		this.labs=labs;
		this.tutorials=tut;
		
		LabEveness();
		PercentageFirstChoice();
	}
	/**
	 * Calculates how full a respective lab is and displays it to the command line 
	 * will need to store these values for later extensions.
	 */
	private void LabEveness() {
		//number of students in a lab.
		int size;
		for(Timeslot t: labs){
			size= t.getAssigned().size();
			if(DEBUG){ System.out.println(t.getStartTime()+" - "+t.getEndTime()+" "+t.getDay()); } 
			labEveness=(float)size/(float)t.getMaxStudents();
			float labPercent=100*labEveness;
			System.out.printf("Lab fullness : %.2f percent \n",labPercent);	
		}
		if(DEBUG){ System.out.println(); } 
	}
	/**
	 * Calculates the percentage of student who are in there first choice lab.
	 */
	private void PercentageFirstChoice(){
		int totalStudents=0;
		int numFirstChoice=0;
		int numSecondChoice=0;
		int numThirdChoice=0;
		total=new ArrayList<Float>();
		for(Timeslot t: labs){
			for(Student s: t.getAssigned()){
				if(hash.getFirsts(s).contains(t)){
					numFirstChoice++;
				}
				else if(hash.getSeconds(s).contains(t)){
					numSecondChoice++;
				}
				else if(hash.getThirds(s).contains(t)){
					numThirdChoice++;
				}
				totalStudents++;
			}
			firstChoice=(float)numFirstChoice/(float)totalStudents;
			secondChoice=(float)numSecondChoice/(float)totalStudents;
			thirdChoice=(float)numThirdChoice/(float)totalStudents;
			total.add(firstChoice);
			
			if(DEBUG){ System.out.println(t.getStartTime()+" - "+t.getEndTime()+" "+t.getDay()); } 
			System.out.printf("Percentage of students in there first choice: %.2f \n",firstChoice);
			System.out.printf("Percentage of students in there second choice: %.2f \n",secondChoice);
			System.out.printf("Percentage of students in there third choice: %.2f \n",thirdChoice);
		}
		float sum=0;
		for(Float f:total){
			sum=sum+f;
		}
		float overall =100*(float)sum/(float)total.size();
		System.out.printf("Total Percentage of happy students : %.2f\n",overall);
	}
}
