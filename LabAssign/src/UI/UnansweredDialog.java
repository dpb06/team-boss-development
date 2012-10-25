package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;
import dataParsing.StudentDataParser;

public class UnansweredDialog extends JDialog {
	private List<Student> unans;
	private List<Student> students;

	public UnansweredDialog(List<Student> unanswered,List<Student> students, GUI parent) {
		super(parent.getFrame(), true);
		unans = new ArrayList<Student>(unanswered);
		this.students = students;
		create();
	}

	private void create() {
		TableModel model = new UnansweredTableModel();
		JTable table = new JTable(model);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JScrollPane(table));
		JButton exit = new JButton("Exit");
		panel.add(exit);
		exit.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Student s: unans){
					students.add(s);
				}
				UnansweredDialog.this.dispose();
			}
		});
		this.setContentPane(panel);
	}

	private class UnansweredTableModel extends AbstractTableModel {

		String[] titles = new String[] { "ID", "Name" };
		
		@Override
		public String getColumnName(int col){
			return titles[col];
		}
		
		@Override
		public boolean isCellEditable(int row, int col){
			return true;
		}

		@Override
		public int getRowCount() {
			return unans.size();
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				if (unans.get(rowIndex).getStudentNum() > 0) {
					return unans.get(rowIndex).getStudentNum();
				} else {
					return "Unknown";
				}
			} else if (columnIndex == 1) {
				return unans.get(rowIndex).getName();
			}
			return null;
		}

		public void setValueAt(Object data, int row, int col) {
			String in = "";
			if (data instanceof String) {
				in = (String) data;
			} else {
				return;
			}
			if (col == 0) {
				unans.get(row).setStudentNum(Integer.parseInt(in));
			} else if (col == 1) {
				unans.get(row).setName(in);
			}
		}

		public void tableChanged(TableModelEvent e) {
			int row = e.getFirstRow();
			int col = e.getColumn();
			TableModel model = (TableModel) e.getSource();
			Object data = model.getValueAt(row, col);
			model.setValueAt(data, row, col);
		}

	}

}
