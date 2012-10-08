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
			if(s.getFirstChoiceLabs().isEmpty()&& s.getSecondChoiceLabs().isEmpty() && !s.getThirdChoiceLabs().isEmpty()){
				s.setFirst(s.getThirdChoiceLabs());			
				s.clearChoice(THIRDCHOICE);
				s.setChangedChoices();
			}
			else if(s.getFirstChoiceLabs().isEmpty()&& !s.getSecondChoiceLabs().isEmpty() && s.getThirdChoiceLabs().isEmpty()){
				s.setFirst(s.getSecondChoiceLabs());
				s.clearChoice(SECONDCHOICE);
				s.setChangedChoices();
			}
			else if(s.getFirstChoiceLabs().isEmpty()&& !s.getSecondChoiceLabs().isEmpty() && !s.getThirdChoiceLabs().isEmpty()){
				s.setFirst(s.getSecondChoiceLabs());
				s.setSecond(s.getThirdChoiceLabs());
				s.clearChoice(THIRDCHOICE);
				s.setChangedChoices();
			}
			else if(!s.getFirstChoiceLabs().isEmpty()&& s.getSecondChoiceLabs().isEmpty() && !s.getThirdChoiceLabs().isEmpty()){
				s.setSecond(s.getThirdChoiceLabs());
				s.clearChoice(THIRDCHOICE);
				s.setChangedChoices();
			}
			else if(s.getFirstChoiceLabs().isEmpty()&& s.getSecondChoiceLabs().isEmpty() && s.getThirdChoiceLabs().isEmpty()){
				s.setFlagged();
			}
		}
		
	}
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
}
