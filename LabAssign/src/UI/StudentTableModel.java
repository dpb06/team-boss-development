package UI;

import javax.swing.table.AbstractTableModel;

public class StudentTableModel extends AbstractTableModel {
    private String[] columnNames;
    private Object[][] data;
    public StudentTableModel(String [] columnNames, Object[][] data){
    	this.columnNames = columnNames;
    	this.data = data;
    }
    
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
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

}
