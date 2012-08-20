package dataParsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import algorithmDataStructures.Day;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class StudentParserFactory {

	

	public StudentParserFactory (File file) {
		
		
	}
	
	public StudentParserFactory (String text) {
		
		
	}
	
	public StudentDataParser getParser(){
		
		return null;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		File f = new File("ExampleData-Cleaned");
		
		StudentDataParser sdp = new StudentDataParser(f);
		List<Timeslot> test = sdp.getTimeslots();
		List<Student> selections =sdp.parseSelections(test);
		
		System.out.println(selections);
	}
}

