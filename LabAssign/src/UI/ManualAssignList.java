package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import dataParsing.StudentDataParser;


public class ManualAssignList extends JFrame  { 
 private final boolean DEBUG = false; 

	private String[] columnNames;
	private Object[][] data;
	private Map<Integer, Student> studentsMap = new HashMap<Integer, Student>();
	private Map<Integer, ButtonGroup> buttonMap = new HashMap<Integer, ButtonGroup>();
	private Map<String, Timeslot> timeslotsMap = new HashMap<String, Timeslot>();
	private List<Student> students;
	//    List<Timeslot> timeslots;

	public ManualAssignList(final List<Student> students, List<Timeslot> timeslots, final GUI gui) {
		super("Students to Manually Assign");
		this.setLayout(new BorderLayout());
		UIDefaults ui = UIManager.getLookAndFeel().getDefaults();
		UIManager.put("RadioButton.focus", ui.getColor("control"));

		this.columnNames = createColumnNames(timeslots);
		this.data = createData(students, timeslots);
		Object[][] buts = createButs(students, timeslots);

		this.students = students;
		//    	this.timeslots = timeslots;

		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(buts, columnNames);

		JTable table = new JTable(dm) {
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				repaint();
			}
		};

		for(int i = 0; i < students.size(); ++i){
			ButtonGroup group = new ButtonGroup();
			for (int t = 1; t <= timeslots.size(); ++t) {
				group.add((JRadioButton) dm.getValueAt(i, t));	
			}
			buttonMap.put(i, group);
		}
		for(String s: columnNames){
			table.getColumn(s).setCellRenderer(new RadioButtonRenderer());
			table.getColumn(s).setCellEditor(new RadioButtonEditor(new JCheckBox()));
		}
		JButton button = new JButton("Save Selections");
		button.setBounds(0, 0, 50, 20);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
		}
		button.addActionListener(new ActionListener() {			 
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < students.size(); i++){
					if(buttonMap.get(i).getSelection() != null){
						String action = buttonMap.get(i).getSelection().getActionCommand();					
						Timeslot t = timeslotsMap.get(action);
						if(t instanceof Lab){
							students.get(i).setAssignedLab(t);
							students.get(i).setFlaggedForLabs(false);
							t.addStudent(students.get(i));
						}
						else{
							students.get(i).setAssignedTut(t);
							students.get(i).setFlaggedForTuts(false);
							t.addStudent(students.get(i));
						}
					}
				}
				gui.naughtyCalculation();
				dispose();
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		this.add(scroll, BorderLayout.NORTH);
		this.add(button, BorderLayout.SOUTH);
		this.pack();
		setVisible(true);
	}
	
	public boolean isCellEditable(int row, int col) {
		//Only Student IDs are uneditable
		return (col > 0);
	}

	private String[] createColumnNames(List<Timeslot> timeslots){
		String[] titles = new String[timeslots.size() + 1];
		titles[0] = "Student ID";
		for (int i = 1; i <= timeslots.size(); ++i) {
			Timeslot t = timeslots.get(i - 1);
			// Construct a meaningful name for the timeslot
			titles[i] = t.toString();
			if(timeslotsMap.containsKey(titles[i])){
				if(!(timeslotsMap.get(i).compareTo(t) == 0))
					if(DEBUG){ System.out.println("Key Error!!"); } 
			}
			else if(timeslotsMap.containsValue(t)){
				if(DEBUG){ System.out.println("Object Error!!"); } 
			}
			else
				timeslotsMap.put(titles[i], t);
		}
		return titles;
	}

	private Object[][] createData(List<Student> students, List<Timeslot> timeslots){
		Object[][] rowData = new Object[students.size()][timeslots.size() + 1];
		for (int i = 0; i < students.size(); ++i) {
			Student s = students.get(i);
			Arrays.fill(rowData[i], false);
			ArrayList<Timeslot> canAttends = new ArrayList<Timeslot>(s.getFirstChoiceLabs());
			canAttends.addAll(s.getSecondChoiceLabs());
			canAttends.addAll(s.getThirdChoiceLabs());
			rowData[i][0] = s.getStudentNum();
			for (Timeslot t : canAttends) {
				rowData[i][timeslots.indexOf(t)+1] = true;
			}
		}
		return rowData;
	}

	private Object[][] createButs(List<Student> students, List<Timeslot> timeslots){
		Object[][] rowData = new Object[students.size()][timeslots.size() + 1];
		for (int i = 0; i < students.size(); ++i) {
			Student s = students.get(i);
			rowData[i][0] = new JLabel("" + s.getStudentNum());
			for (int t = 0; t < timeslots.size(); ++t) {
				JRadioButton b = new JRadioButton("");;
				if(s.getFirstChoiceLabs().contains(timeslots.get(t)) || s.getFirstChoiceTuts().contains(timeslots.get(t))){
					b = new JRadioButton("1st");
					b.setBackground(Color.green);
				}
				else if(s.getSecondChoiceLabs().contains(timeslots.get(t)) || s.getSecondChoiceTuts().contains(timeslots.get(t))){
					b = new JRadioButton("2nd");
					b.setBackground(Color.orange);
				}
				else if(s.getThirdChoiceLabs().contains(timeslots.get(t)) || s.getThirdChoiceTuts().contains(timeslots.get(t))){
					b = new JRadioButton("3rd");
					b.setBackground(Color.yellow);
				}
				b.setActionCommand(timeslots.get(t).toString());
				rowData[i][t+1] = b;
			}
		}
		return rowData;
	}
}

class RadioButtonRenderer implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
			return null;
		return (Component) value;
	}
}

class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
	private JRadioButton button;

	public RadioButtonEditor(JCheckBox checkBox) {
		super(checkBox);
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null || column < 1)
			return null;
		button = (JRadioButton) value;
		button.addItemListener(this);
		return (Component) value;
	}

	public Object getCellEditorValue() {
		button.removeItemListener(this);
		return button;
	}

	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}

