package testing;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithms.BossSort;
import algorithms.HowardsSort;

public class HowardSortJUnitSuite {
	
	
	
	//TODO: To keep track of the current code coverage, update this fairly regularly.
	//Current code coverage for StudentChoiceOrder is 31.1%! Weak.
	//testStudentPersistence is failing, too.
		
	
	
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;

	public HowardSortJUnitSuite() {
		JUnitTestingData j = new JUnitTestingData();
		students = j.getStudents();
		labs = j.getLabs();
		tutorials = j.getTutorials();

		// TODO: Test input vs output.
		// TODO: Test non-determinism of output.
		// TODO: Are all students in input accounted for by the output?
		// TODO: Sanity check inputs.
		// TODO: Check code coverage.

	}

	@Test
	public void testStudentPersistence() {
		// Tests that every student who enters the algorithm, leaves the
		// algorithm
		HowardsSort bs = new HowardsSort(labs, tutorials, students);
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
		HowardsSort bs = new HowardsSort(labos, new ArrayList<Timeslot>(), studs);
		AlgorithmOutput out = bs.start();
		assertTrue(out.get(ts1).contains(s1) && out.get(ts1).size() == 1);
		assertTrue(out.get(ts2).contains(s2) && out.get(ts2).size() == 1);
		assertTrue(out.get(ts3).contains(s3) && out.get(ts3).size() == 1);
	}
}
