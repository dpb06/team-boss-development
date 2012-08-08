package dataParsing;

import java.util.Scanner;

import algorithmDataStructures.Student;

public class StudentDataParser {
	
	/**
	 * Data file format:
	 * 
	 * 
	 * --------------file start---------------------
	 * 
	 * "Question ID"+ "\t" +"Question"+ "\t" +"Answer"
	 * "Question ID 1"+ "\t" +"<Day1> <timestart1> - <timeend1> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * "Question ID 2"+ "\t" +"<Day2> <timestart2> - <timeend2> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * "Question ID 3"+ "\t" +"<Day3> <timestart3> - <timeend3> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * ...
	 * ...
	 * "<div class=\"\"vtbegenerated\"\"> + Y + "</div>" // to be stripped down to Y and checked for 
	 * 		Y ->  "<DayX> <timestartX> - <timeendX> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice" 
	 * 
	 */
	
	
	//Step 1: strip slot options
	//Step 2: create students

	
	Scanner studentScan;
	
	
	
	/**
	 * A Scanner-style parser which takes text and when next() is called returns a Student
	 * @param text the body of text to be parsed
	 */
	public StudentDataParser (String text) {
		
		
		
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
