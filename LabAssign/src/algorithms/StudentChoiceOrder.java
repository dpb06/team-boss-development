package algorithms;

import java.util.ArrayList;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class StudentChoiceOrder {

	private ArrayList<Student> students;

	@SuppressWarnings("unchecked")
	public StudentChoiceOrder(ArrayList<Student>students){
		//Clone students List
		this.students = (ArrayList<Student>) students.clone();
		checkLabChoices();
		checkTutChoices();
	}

	private void checkLabChoices() {
		//For every student
		for(Student s:students){
			//If they have no first choices
			if(s.getFirstChoiceLabs().isEmpty()){
				//If they have no second choices
				if(s.getSecondChoiceLabs().isEmpty()){
					//If they have no third choices
					if(s.getThirdChoiceLabs().isEmpty()){
						//Flag that student
						s.setFlaggedForLabs(true);
						s.setReasonForFlagging("Student has no first, second, or third lab choices");
					}
					//If they have third, but not first or second choices
					else {
						//Bump their third choices up to firsts
						s.setFirstChoiceLabs(s.getThirdChoiceLabs());
						//Clear third choices
						s.setThirdChoiceLabs(new ArrayList<Timeslot>());
						//Note changes have been made
						s.setChangedChoices(true);
					}
				}
				//If they have second choices
				else {
					//Bump their second choices to firsts
					s.setFirstChoiceLabs(s.getSecondChoiceLabs());
					//Bump their third choices to seconds
					s.setSecondChoiceLabs(s.getThirdChoiceLabs());
					//Clear third choices
					s.setThirdChoiceLabs(new ArrayList<Timeslot>());
					//Note changes have been made
					s.setChangedChoices(true);
				}
			}
		}
	}

	private void checkTutChoices() {
		//For every student
		for(Student s:students){
			//If they have no first choices
			if(s.getFirstChoiceTuts().isEmpty()){
				//If they have no second choices
				if(s.getSecondChoiceTuts().isEmpty()){
					//If they have no third choices
					if(s.getThirdChoiceTuts().isEmpty()){
						//Flag that student
						s.setFlaggedForTuts(true);
						s.setReasonForFlagging("Student has no first, second, or third tutorial choices");
					}
					//If they have third, but not first or second choices
					else {
						//Bump their third choices up to firsts
						s.setFirstChoiceTuts(s.getThirdChoiceTuts());
						//Clear third choices
						s.setThirdChoiceTuts(new ArrayList<Timeslot>());
						//Note changes have been made
						s.setChangedChoices(true);
					}
				}
				//If they have second choices
				else {
					//Bump their second choices to firsts
					s.setFirstChoiceTuts(s.getSecondChoiceTuts());
					//Bump their third choices to seconds
					s.setSecondChoiceTuts(s.getThirdChoiceTuts());
					//Clear third choices
					s.setThirdChoiceTuts(new ArrayList<Timeslot>());
					//Note changes have been made
					s.setChangedChoices(true);
				}
			}
		}
	}

	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
}
