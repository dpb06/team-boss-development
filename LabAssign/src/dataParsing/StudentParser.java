package dataParsing;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import algorithmDataStructures.Student;

public class StudentParser {

	
	Scanner studentScan;
	
	

	
	public List<StudentSelections> parseSelectionData() throws FileNotFoundException{
		return null;
		
	}
	
	public List<DataTimeslot> parseTimeslotData() throws FileNotFoundException{
		return null;
		
	}
	
	
	/**
	 * A Scanner-style parser which takes text and when next() is called returns a Student
	 * @param text the body of text to be parsed
	 */
	public StudentParser (String text) {
		
		
		
	}
	
	
	/**
	 * When called attempts to read the next student from the body of text
	 * 
	 * @return
	 */
	public Student next(){
		return null;
		
	}
	
	/**
	 * Checks to see if there is another student to be parsed in the file
	 * @return
	 */
	public boolean hasNext() {
		return false;
	}
}
