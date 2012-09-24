package algorithmDataStructures;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class AlgorithmOutput extends HashMap<Timeslot,ArrayList<Student>>{

	private ArrayList<Student> flagged = new ArrayList<Student>();
	
	public void addFlagged(Student s){
		flagged.add(s);
	}
	public ArrayList<Student> getFlagged(){
		return flagged;
	}
}
