package UI;

import java.awt.BorderLayout;
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
		
		TableModel model = new StudentTableModel(students, timeslots);
		table = new JTable(model);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(new JScrollPane(table));
		JButton returnButton = new JButton("Save Changes & Close");
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
			new NaughtyList(studs = p.parseSelections(ts,true),ts).setVisible(true);
			for (Student s:studs){
				s.printDebug();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
