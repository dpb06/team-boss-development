package algorithms;

import java.util.ArrayList;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

/**
 * Removes student's tutorial choices that clash with the lab they are assigned to. 
 */
public class tutorialChecker {

	private ArrayList<Student> students;
	
	public tutorialChecker(ArrayList<Student> students){
		//Begin console output.
		System.out.println("modifyTuts() in BossSort");
		//For each student
		for (Student s : students){
			//Ensure only assigned students are modified
			if(s.getAssignedLab() == null){continue;}
			//Initialize boolean to track changes 
			boolean removals = false;
			//Printspam the name of each student.
			System.out.println(s.getStudentNum() + " - " + s.getName() + ":");
			//Find assigned lab timeslot
			Timeslot assignedLab = s.getAssignedLab();
			//Create a copy of student's tutorial first choices
			ArrayList<Timeslot> firsts = new ArrayList<Timeslot>(s.getFirstChoiceTuts());
			//For each of the student's tutorial first choices
			for (Timeslot tut : s.getFirstChoiceTuts()){
				//If assigned lab time intersects with a time of a tutorial
				if(tut.intersectsWith(assignedLab)){
					//Remove that tutorial from student's first choices
					firsts.remove(tut);
					removals = true;
					//Printspam the removal
					System.out.println("First choice tutorial removed: " + tut.getDay() + ", " + tut.getStartTime() + "-" + tut.getEndTime());
				}
			}
			//Set new list of first choices
			s.setFirstChoiceTuts(firsts);

			//Create a copy of student's tutorial second choices
			ArrayList<Timeslot> seconds = new ArrayList<Timeslot>(s.getSecondChoiceTuts());
			//For each of the student's tutorial second choices
			for (Timeslot tut : s.getSecondChoiceTuts()){
				//If assigned lab time intersects with a time of a tutorial
				if(tut.intersectsWith(assignedLab)){
					//Remove that tutorial from student's second choices
					seconds.remove(tut);
					removals = true;
					//Printspam the removal
					System.out.println("Second choice tutorial removed: " + tut.getDay() + ", " + tut.getStartTime() + "-" + tut.getEndTime());
				}
			}
			//Set new list of second choices
			s.setSecondChoiceTuts(seconds);

			//Create a copy of student's tutorial third choices
			ArrayList<Timeslot> thirds = new ArrayList<Timeslot>(s.getThirdChoiceTuts());
			//For each of the student's tutorial third choices
			for (Timeslot tut : s.getThirdChoiceTuts()){
				//If assigned lab time intersects with a time of a tutorial
				if(tut.intersectsWith(assignedLab)){
					//Remove that tutorial from student's third choices
					thirds.remove(tut);
					removals = true;
					//Printspam the removal
					System.out.println("Third choice tutorial removed: " + tut.getDay() + ", " + tut.getStartTime() + "-" + tut.getEndTime());
				}
			}
			//Set new list of third choices
			s.setThirdChoiceTuts(thirds);
			//If no changes have been made to this student
			if(!removals){
				//Printspam lack of removals
				System.out.println("No tutorials removed");
			}
		}
		System.out.println("\n");
	}

	public ArrayList<Student> getStudents() {
		return students;
	}
	
}
