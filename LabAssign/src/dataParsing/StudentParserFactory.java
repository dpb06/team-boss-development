package dataParsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import algorithmDataStructures.Day;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithms.BossSort;

public class StudentParserFactory {

	

	public StudentParserFactory (File file) {
		
		
	}
	
	public StudentParserFactory (String text) {
		
		
	}
	
	public StudentDataParser getParser(){
		
		return null;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		File f = new File("/u/students/boothanto/git/team-boss-development/LabAssign/src/ExampleData-Cleaned");
		
		StudentDataParser sdp = new StudentDataParser(f);
		List<Timeslot> test = sdp.getTimeslots();
		List<Student> selections =sdp.parseSelections(test);
		BossSort b= new BossSort((ArrayList<Timeslot>)test, new ArrayList<Timeslot>(),(ArrayList<Student>) selections);
		//System.out.println(selections);
	}
}

