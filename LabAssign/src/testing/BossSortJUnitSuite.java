package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithms.BossSort;

public class BossSortJUnitSuite {

	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	
	public BossSortJUnitSuite(){
		JUnitTestingData j = new JUnitTestingData();
		students = j.getStudents();
		labs = j.getLabs();
		tutorials = j.getTutorials();
		
		
		//TODO: Test input vs output.
		//TODO: Test non-determinism of output.
		//TODO: Are all students in input accounted for by the output?
		//TODO: Sanity check inputs.
		//TODO: Check code coverage.
		
		
	}
	
	@Test
	public void testStudentPersistence(){
		//Tests that every student who enters the algorithm, leaves the algorithm
		BossSort bs = new BossSort(labs, tutorials, students);
		AlgorithmOutput out = bs.getOutput();
		
		for (Student s:students){
			boolean found = true;
			for(Timeslot t: out.keySet()){
				if(t.getAssigned().contains(s)){
					break;
				} else found = false;
			}
			assertTrue(found);
		}
	}
}
