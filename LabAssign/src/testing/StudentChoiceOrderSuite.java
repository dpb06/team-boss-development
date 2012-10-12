package testing;

import java.util.ArrayList;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import algorithmDataStructures.Student;
import algorithms.StudentChoiceOrder;

public class StudentChoiceOrderSuite {

	private ArrayList<Student> students;
	
	public StudentChoiceOrderSuite(){
		JUnitTestingData j = new JUnitTestingData();
		students = j.getStudents();
	}
	
	
	@Test
	public void testStudentPersistence() {
		int startSize = students.size();
		StudentChoiceOrder sco = new StudentChoiceOrder(students);
		int endSize = sco.getStudents().size();
		assertTrue(startSize == endSize);
	}
	
	@Test
	public void testCheckLabChoices(){
		//Initialize a boolean saying there are no students who have no first choices and aren't flagged.
		boolean startEmptyFirstChoices = true;
		//For each student
		for(Student s: students){
			//If a student has no first choices and isn't flagged
			if(s.getFirstChoiceLabs().isEmpty()){
				if(s.getFlagged() == false){
					//Set boolean to false
					startEmptyFirstChoices = false;
				}
			}
		}
		//Run data through a StudentChoiceOrder
		StudentChoiceOrder sco = new StudentChoiceOrder(students);
		//Initialize a boolean saying there are no students who have no first choices and aren't flagged.
		boolean endEmptyFirstChoices = true;
		//For each student
		for (Student s : sco.getStudents()){
			//If a student has no first choices
			if(s.getFirstChoiceLabs().isEmpty()){
				//Ignore if they have no choices and are flagged
				if(s.getFlagged()==true && s.getSecondChoiceLabs().isEmpty() && s.getThirdChoiceLabs().isEmpty()){}
				//Otherwise
				else {
					//Set boolean to false
					endEmptyFirstChoices = false;
				}
			}
		}
		//Show that the method started with students lacking first choices, and was modified so all students were
		assertTrue(!startEmptyFirstChoices);
		assertTrue(endEmptyFirstChoices);
	}
	
	
	
	@Test
	public void testCheckTutChoices(){
		//Initialize a boolean saying there are no students who have no first choices and aren't flagged.
		boolean startEmptyFirstChoices = true;
		//For each student
		for(Student s: students){
			//If a student has no first choices and isn't flagged
			if(s.getFirstChoiceTuts().isEmpty()){
				if(s.getFlagged() == false){
					//Set boolean to false
					startEmptyFirstChoices = false;
				}
			}
		}
		//Run data through a StudentChoiceOrder
		StudentChoiceOrder sco = new StudentChoiceOrder(students);
		//Initialize a boolean saying there are no students who have no first choices and aren't flagged.
		boolean endEmptyFirstChoices = true;
		//For each student
		for (Student s : sco.getStudents()){
			//If a student has no first choices
			if(s.getFirstChoiceTuts().isEmpty()){
				//Ignore if they have no choices and are flagged
				if(s.getFlagged()==true && s.getSecondChoiceTuts().isEmpty() && s.getThirdChoiceTuts().isEmpty()){}
				//Otherwise
				else {
					//Set boolean to false
					endEmptyFirstChoices = false;
				}
			}
		}
		assertTrue(!startEmptyFirstChoices);
		assertTrue(endEmptyFirstChoices);
	}
	
	
	
	
}
