package algorithms;

import java.util.ArrayList;

import algorithmDataStructures.Student;

public class StudentChoiceOrder {

	private ArrayList<Student> students;
	
	public StudentChoiceOrder(ArrayList<Student>students){
		//cloned students List
				this.students=(ArrayList<Student>) students.clone();
				checkChoices();
	}

	private void checkChoices() {
		final int THIRDCHOICE=2;
		final int SECONDCHOICE=1;
		
		for(Student s:students){
			if(s.getFirstChoices().isEmpty()&& s.getSecondChoices().isEmpty() && !s.getThirdChoices().isEmpty()){
				s.setFirst(s.getThirdChoices());			
				s.clearChoice(THIRDCHOICE);
				s.setChangedChoices();
			}
			else if(s.getFirstChoices().isEmpty()&& !s.getSecondChoices().isEmpty() && s.getThirdChoices().isEmpty()){
				s.setFirst(s.getSecondChoices());
				s.clearChoice(SECONDCHOICE);
				s.setChangedChoices();
			}
			else if(s.getFirstChoices().isEmpty()&& !s.getSecondChoices().isEmpty() && !s.getThirdChoices().isEmpty()){
				s.setFirst(s.getSecondChoices());
				s.setSecond(s.getThirdChoices());
				s.clearChoice(THIRDCHOICE);
				s.setChangedChoices();
			}
			else if(!s.getFirstChoices().isEmpty()&& s.getSecondChoices().isEmpty() && !s.getThirdChoices().isEmpty()){
				s.setSecond(s.getThirdChoices());
				s.clearChoice(THIRDCHOICE);
				s.setChangedChoices();
			}
			else if(s.getFirstChoices().isEmpty()&& s.getSecondChoices().isEmpty() && s.getThirdChoices().isEmpty()){
				s.setFlagged();
			}
		}
		
	}
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
}
