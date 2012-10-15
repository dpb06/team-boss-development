package testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithmDataStructures.Tutorial;
import algorithms.BossSort;

public class BossSortJUnitSuite {
	
	
	
	//TODO: To keep track of the current code coverage, update this fairly regularly.
	//Current code coverage for BossSort is 96.8%
		
	
	
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;

	public BossSortJUnitSuite() {
		JUnitTestingData j = new JUnitTestingData();
		students = j.getStudents();
		labs = j.getLabs();
		tutorials = j.getTutorials();

		// TODO: Test input vs output.
		// TODO: Test non-determinism of output.
		// TODO: Sanity check inputs.
		// TODO: Check code coverage.

	}

	@Test
	public void testStudentPersistence() {
		// Tests that every student who enters the algorithm, leaves the
		// algorithm
		System.out.println("testStudentPersistence() in BossSortJUnitSuite\n");
		BossSort bs = new BossSort(labs, tutorials, students);
		AlgorithmOutput out = bs.start();
		for (Student s : students) {
			boolean found = true;
			if (out.getFlagged().contains(s)) {}
			else {
				
				for (Timeslot t : out.keySet()) {
					if (t.getAssigned().contains(s)) {
						found = true;
						break;
					} 
					found = false;

				}
			}
			if(!found){
				System.out.println("--------------------NOT FOUND STUDENT-----------------------------------");
				s.printDebug();
			}
			assertTrue(found);
		}
	}
	
	
	

	@Test
	public void testObviousCase() {
		// Tests a very basic case of three students with a single, differing
		// choice each
		Timeslot ts1 = new Lab(10, 1000, 1100, Day.Wednesday);
		Timeslot ts2 = new Lab(11, 1100, 1200, Day.Wednesday);
		Timeslot ts3 = new Lab(12, 1200, 1300, Day.Wednesday);
		Student s1 = new Student(1, "a");
		s1.addFirstLab(ts1);
		Student s2 = new Student(2, "b");
		s2.addFirstLab(ts2);
		Student s3 = new Student(3, "c");
		s3.addFirstLab(ts3);
		ArrayList<Student> studs = new ArrayList<Student>();
		studs.add(s1);
		studs.add(s2);
		studs.add(s3);
		ArrayList<Timeslot> labos = new ArrayList<Timeslot>();
		labos.add(ts1);
		labos.add(ts2);
		labos.add(ts3);
		BossSort bs = new BossSort(labos, new ArrayList<Timeslot>(), studs);
		AlgorithmOutput out = bs.start();
		assertTrue(out.get(ts1).contains(s1) && out.get(ts1).size() == 1);
		assertTrue(out.get(ts2).contains(s2) && out.get(ts2).size() == 1);
		assertTrue(out.get(ts3).contains(s3) && out.get(ts3).size() == 1);
	}
	
	
	/**
	 * Ensures BossSort assigns tutorials to students (however poorly)
	 */
	@Test
	public void testTutorialAssign(){
		//Create tutorials and labs
		Timeslot ts1 = new Lab(10, 1000, 1100, Day.Wednesday);
		Timeslot ts2 = new Lab(11, 1100, 1200, Day.Wednesday);
		Timeslot ts3 = new Lab(12, 1200, 1300, Day.Wednesday);
		Timeslot ts4 = new Tutorial(13, 1200, 1300, Day.Wednesday);
		Timeslot ts5 = new Tutorial(13, 1300, 1400, Day.Wednesday);
		Timeslot ts6 = new Tutorial(13, 1400, 1500, Day.Wednesday);
		//Create students
		Student s1 = new Student(1, "a");
		s1.addFirstLab(ts3);
		s1.addFirstTut(ts4);
		Student s2 = new Student(2, "b");
		s2.addFirstTut(ts5);
		s2.addFirstLab(ts2);
		Student s3 = new Student(3, "c");
		s3.addFirstLab(ts3);
		s3.addFirstTut(ts6);
		//Create ArrayLists to pass to BossSort
		ArrayList<Student> studs = new ArrayList<Student>();
		studs.add(s1);
		studs.add(s2);
		studs.add(s3);
		ArrayList<Timeslot> labos = new ArrayList<Timeslot>();
		labos.add(ts1);
		labos.add(ts2);
		labos.add(ts3);
		ArrayList<Timeslot> tutos = new ArrayList<Timeslot>();
		tutos.add(ts4);
		tutos.add(ts5);
		tutos.add(ts6);
		//Run BossSort
		BossSort bs = new BossSort(labos, tutos, studs);
		AlgorithmOutput out = bs.start();
		//Assertions
		assertTrue(out.get(ts3).contains(s1) && out.get(ts3).size() == 2);
		assertTrue(out.get(ts2).contains(s2) && out.get(ts2).size() == 1);
		assertTrue(out.get(ts3).contains(s3) && out.get(ts3).size() == 2);
		assertFalse(out.get(ts4).contains(s1) && out.get(ts4).size() == 1);
		assertTrue(out.get(ts4).size() == 0);
		assertTrue(out.get(ts6).contains(s3) && out.get(ts6).size() == 1);
		assertTrue(out.getFlagged().contains(s1) && out.getFlagged().size() == 1);
		
	}
	
	
	
	
	
	
	
	
}
