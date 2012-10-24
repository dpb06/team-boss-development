package UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import algorithmDataStructures.Tutorial;

public class StudentTableModel extends AbstractTableModel {
	private String[] columnNames;
	private Object[][] data;

	private boolean DEBUG = false;

	private Map<Integer, Student> studentsMap = new HashMap<Integer, Student>();
	private Map<Integer, Timeslot> timeslotsMap = new HashMap<Integer, Timeslot>();
	private List<Student> students;
	//    List<Timeslot> timeslots;

	public StudentTableModel(List<Student> students, List<Timeslot> timeslots){
		this.columnNames = createColumnNames(timeslots);
		this.data = createData(students, timeslots);
		
		this.students = students;
		//    	this.timeslots = timeslots;
	}

	private String[] createColumnNames(List<Timeslot> timeslots){
		String[] titles = new String[timeslots.size() + 1];
		titles[0] = "Student ID";
		for (int i = 1; i <= timeslots.size(); ++i) {
			Timeslot t = timeslots.get(i - 1);
			// Construct a meaningful name for the timeslot
			titles[i] = t.toString();
			if(timeslotsMap.containsKey(i)){
				if(!(timeslotsMap.get(i).compareTo(t) == 0))
					System.out.println("Key Error!!");
			}
			else if(timeslotsMap.containsValue(t)){
				System.out.println("Object Error!!");
			}
			else
				timeslotsMap.put(i, t);
		}
		return titles;
	}

	private Object[][] createData(List<Student> students, List<Timeslot> timeslots){
		Object[][] rowData = new Object[students.size()][timeslots.size() + 1];
		if (timeslots.isEmpty()) return rowData;
		if (timeslots.get(0) instanceof Lab) {
			for (int i = 0; i < students.size(); ++i) {
				Student s = students.get(i);
				Arrays.fill(rowData[i], false);
				ArrayList<Timeslot> canAttends = new ArrayList<Timeslot>(
						s.getFirstChoiceLabs());
				canAttends.addAll(s.getSecondChoiceLabs());
				canAttends.addAll(s.getThirdChoiceLabs());
				rowData[i][0] = s.getStudentNum();
				for (Timeslot t : canAttends) {
					rowData[i][timeslots.indexOf(t) + 1] = true;
				}
			}
		} else if (timeslots.get(0) instanceof Tutorial) {
			for (int i = 0; i < students.size(); ++i) {
				Student s = students.get(i);
				Arrays.fill(rowData[i], false);
				ArrayList<Timeslot> canAttends = new ArrayList<Timeslot>(
						s.getFirstChoiceTuts());
				canAttends.addAll(s.getSecondChoiceTuts());
				canAttends.addAll(s.getThirdChoiceTuts());
				rowData[i][0] = s.getStudentNum();
				for (Timeslot t : canAttends) {
					rowData[i][timeslots.indexOf(t) + 1] = true;
				}
			}
		}
		return rowData;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return Math.min(columnNames.length, data[0].length);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	public Class getColumnClass(int c){
		return getValueAt(0, c).getClass();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public boolean isCellEditable(int row, int col) {
		//Only Student IDs are uneditable
		return (col > 0);
	}

	public void setValueAt(Object value, int row, int col) {
		if (DEBUG) {
			System.out.println("Setting value at " + row + "," + col
					+ " to " + value
					+ " (an instance of "
					+ value.getClass() + ")");
		}

		data[row][col] = value;
		
		if((Boolean) value){
			students.get(row).addThird(timeslotsMap.get(col));
		}
		else
			students.get(row).removeTimeslot(timeslotsMap.get(col));

		fireTableCellUpdated(row, col);

		if (DEBUG) {
			System.out.println("New value of data:");
			printDebugData();
		}
	}

	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();

		for (int i=0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j=0; j < numCols; j++) {
				System.out.print("  " + data[i][j]);
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

}
