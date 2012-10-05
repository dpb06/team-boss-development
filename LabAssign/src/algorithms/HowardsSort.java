package algorithms;

import java.util.ArrayList;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

/**
 * This is a Java implementation of howards original sorting algorithm he used using labview.
 * This is to have a benchmark to compare our newly implemented algorithm  boss sort.
 * @author phillijosh
 */
public class HowardsSort implements Algorithm{
	
	//-----FIELDS-----\\
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private ArrayList<Student> students;
	private ArrayList<Student> assignedStudents = new ArrayList<Student>();
	private ArrayList<Integer> indexOverfilledLabs = new ArrayList<Integer>();
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
		sort((ArrayList<Student>) students.clone());
		//TODO: Use new fitness function data structure
		//new FitnessFunctions(tutorials, students, labs);
		guiOutput();
		return output;
	}
	
	
	//-----FUNCTIONALITIES-----\\
	public void sort(ArrayList<Student> s){
		ArrayList<Student>students=s;
		Student currentStudent;
		ArrayList<Timeslot> firstChoiceLab;
		int numStudents=students.size();
		// random order of students put all students in there smallest size lab that is one of there first choices.
		for (int i=0;i<numStudents;i++){
			currentStudent=students.get((int)(Math.random()*students.size()));
			students.remove(currentStudent);
			firstChoiceLab=currentStudent.getFirstChoices();
			int lowestLabSize=0;
			for(int z=0;z<firstChoiceLab.size();z++){
				if(firstChoiceLab.get(z).getAssigned().size()<firstChoiceLab.get(lowestLabSize).getAssigned().size()){
					lowestLabSize=z;
				}
			}
			firstChoiceLab.get(lowestLabSize).addStudent(currentStudent);
			
		}

		//Iterate over the labs that are overfilled and if move students into another one of there first choices if they have first choices.

		//List of overfilled Labs
		ArrayList<Integer> indexOverfilledLabs=overFilledLabs();
		//ArrayList<Student> overfilledStudents=new ArrayList<Student>();
		for(int a= 0;a<indexOverfilledLabs.size();a++){

			int size=labs.get(indexOverfilledLabs.get(a)).getAssigned().size();
			//every student who is above the overfilled line
			for(int d=labs.get(indexOverfilledLabs.get(a)).getMaxStudents();d<size;d++){
				//if a student who is in the overfilled category for this lab then look to see if they have anymore first choices.
				currentStudent=labs.get(indexOverfilledLabs.get(a)).getAssigned().get(d);
				if(currentStudent.getFirstChoices().size()>1){
					for(Timeslot t:currentStudent.getFirstChoices()){
						if(t!=(labs.get(indexOverfilledLabs.get(a)))){
							if(!t.isOverfilled()){
								labs.get(indexOverfilledLabs.get(a)).removeStudent(currentStudent);
								labs.get(indexOverfilledLabs.get(a)).addStudent(currentStudent);
							}
						}
					}
				}
			}

		}
		//assignSecond Labs
		indexOverfilledLabs=overFilledLabs();
		for(int a= 0;a<indexOverfilledLabs.size();a++){

			int size=labs.get(indexOverfilledLabs.get(a)).getAssigned().size();
			//every student who is above the overfilled line
			for(int d=labs.get(indexOverfilledLabs.get(a)).getMaxStudents();d<size;d++){
				//if a student who is in the overfilled category for this lab then look to see if they have anymore first choices.
				currentStudent=labs.get(indexOverfilledLabs.get(a)).getAssigned().get(d);
				if(currentStudent.getSecondChoices().size()>1){
					for(Timeslot t:currentStudent.getSecondChoices()){
						if(t!=(labs.get(indexOverfilledLabs.get(a)))){
							if(!t.isOverfilled()){
								labs.get(indexOverfilledLabs.get(a)).removeStudent(currentStudent);
								labs.get(indexOverfilledLabs.get(a)).addStudent(currentStudent);
							}
						}
					}
				}
			}

		}
		//Assign Third Labs
	 indexOverfilledLabs=overFilledLabs();
		for(int a= 0;a<indexOverfilledLabs.size();a++){

			int size=labs.get(indexOverfilledLabs.get(a)).getAssigned().size();
			//every student who is above the overfilled line
			for(int d=labs.get(indexOverfilledLabs.get(a)).getMaxStudents();d<size;d++){
				//if a student who is in the overfilled category for this lab then look to see if they have anymore first choices.
				currentStudent=labs.get(indexOverfilledLabs.get(a)).getAssigned().get(d);
				if(currentStudent.getThirdChoices().size()>1){
					for(Timeslot t:currentStudent.getThirdChoices()){
						if(t!=(labs.get(indexOverfilledLabs.get(a)))){
							if(!t.isOverfilled()){
								labs.get(indexOverfilledLabs.get(a)).removeStudent(currentStudent);
								labs.get(indexOverfilledLabs.get(a)).addStudent(currentStudent);
							}
						}
					}
				}
			}

		}
		
		indexOverfilledLabs=overFilledLabs();
		for(Integer i: indexOverfilledLabs){
			for(int z=labs.get(i).getMaxStudents();i<labs.get(i).getAssigned().size();z++){
				flagged.add(labs.get(i).getAssigned().get(z));
			}
		}


	}

	public ArrayList<Integer> overFilledLabs(){
		indexOverfilledLabs.clear();
		for(int i=0;i<labs.size();i++){
			if(labs.get(i).isOverfilled()){
				System.out.println("over filled lab: "+labs.get(i).toString());
				indexOverfilledLabs.add(i);				
			}
		}
		return indexOverfilledLabs;
	}

	/**
	 * Creates a hashmap containing each lab/tutorial as keys and an arraylist of students assigned to
	 * that timeslot as values. This hashmap is saved in the variable 'output', and serves as the input
	 * to the GUI.
	 */
	private void guiOutput() {
		System.out.println();
		System.out.println("guiOutPut() in BossSort");
		//Iterate through Timeslots.
		for(Timeslot t:labs){
			//Add the timeslot and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the timeslot and its assigned students.
			System.out.println(t.getDay() + ": " + t.getStartTime() + "-" + t.getEndTime());
			for(Student s: t.getAssigned()){
				System.out.println("\t "+s.getStudentNum());
			}
		}
		//Printspam the flagged students.
		System.out.println();
		System.out.println("Not Assigned:");
		for(Student s: flagged){
			System.out.println("\t "+s.getStudentNum());
		}
	}
	
}
