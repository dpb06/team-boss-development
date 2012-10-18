package UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import testing.JUnitTestingData;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class NaughtyList extends JFrame {
	private JTable table;
	private List<Student> students;
	private List<Timeslot> timeslots;//Just for knowing 'index'


	public NaughtyList(List<Student> allStudents, List<Timeslot> timeslots) {
		students = new ArrayList<Student>();
		for(Student s: allStudents){
			//		this.students = students;
			if(s.getNumCanAttendLabs() < 2){
				students.add(s);
			}
		}
		this.timeslots = timeslots;
		Object[] titles = new Object[timeslots.size()+1];
		titles[0] = "Student ID";
		for (int i = 1; i<=timeslots.size(); ++i){
			Timeslot t = timeslots.get(i-1);
			//Construct a meaningful name for the timeslot
			titles[i] = t.toString();
			System.out.println(titles[i]);
		}
		//TODO: Replace this nastiness with a table model
		Object[][]  rowData= new Object[students.size()][timeslots.size()+1];
		for (int i = 0; i<students.size(); ++i){
			Student s = students.get(i);
			Arrays.fill(rowData[i], false);
			rowData[i][0] = s.getStudentNum();
			if(s.getNumCanAttendLabs() == 1){
				rowData[i][timeslots.indexOf(s.getAssignedLab())+2] = true;
			}
		}
		table = new JTable(rowData, titles);
		
		add(new JScrollPane(table));
		this.pack();
	}
	
	public static void main(String[] args){
		JUnitTestingData j = new JUnitTestingData();
		new NaughtyList(j.getStudents(), j.getLabs()).setVisible(true);
	}
}
