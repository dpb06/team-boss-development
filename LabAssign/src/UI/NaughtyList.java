package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import dataParsing.StudentDataParser;

public class NaughtyList extends JFrame {
	private JTable table;
	private List<Student> students;
	private List<Timeslot> timeslots;// Just for knowing 'index'
	private String title = "";
	private Object[][] rowData;


	public NaughtyList(List<Student> flaggedStudents, List<Timeslot> timeslots, String title) {
		//Constructor for Students with One or less Choices
		students = new ArrayList<Student>(flaggedStudents);
		this.timeslots = timeslots;
		this.title = title;
		create();
	}

	public NaughtyList(List<Student> flaggedStudents, List<Timeslot> timeslots) {
		students = new ArrayList<Student>(flaggedStudents);
		this.timeslots = timeslots;
		create();
	}

	public List<Student> getStudents(){
		return students;
	}
	private void create(){
		if(title.equals("")){
			if(timeslots.get(0) instanceof Lab)
				title = "Students Flagged for Labs";
			else
				title = "Students Flagged for Tutorials";
		}
		
		this.setTitle(title);

		String[] titles = new String[timeslots.size() + 1];
		titles[0] = "Student ID";
		for (int i = 1; i <= timeslots.size(); ++i) {
			Timeslot t = timeslots.get(i - 1);
			// Construct a meaningful name for the timeslot
			titles[i] = t.toString();
		}
		// TODO: Replace this nastiness with a table model
		rowData = new Object[students.size()][timeslots.size() + 1];
		for (int i = 0; i < students.size(); ++i) {
			Student s = students.get(i);
			Arrays.fill(rowData[i], false);
			ArrayList<Timeslot> canAttends = new ArrayList<Timeslot>(
					s.getFirstChoiceLabs());
			canAttends.addAll(s.getSecondChoiceLabs());
			canAttends.addAll(s.getThirdChoiceLabs());
			rowData[i][0] = s.getStudentNum();
			for (Timeslot t : canAttends) {
				rowData[i][timeslots.indexOf(t)+1] = true;
			}
		}
		TableModel model = new StudentTableModel(titles, rowData);
		table = new JTable(model);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(new JScrollPane(table));
		JButton returnButton = new JButton("Return");
		add(returnButton);
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NaughtyList.this.dispose();
			}
		});
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		// JUnitTestingData j = new JUnitTestingData();
		StudentDataParser p;
		try {
			p = new StudentDataParser(new File("~/FullInputData.txt"));
			List<Timeslot> ts = p.getTimeslots();
			List<Student> studs;
			new NaughtyList(studs = p.parseSelections(ts),ts).setVisible(true);
			for (Student s:studs){
				s.printDebug();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
