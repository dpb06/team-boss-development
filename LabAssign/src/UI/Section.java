package UI;

import java.util.ArrayList;
import java.util.List;

import algorithmDataStructures.Student;

public class Section {

	private String sectionTime = "";
	private int size = 0;
	List<Student> studentList = new ArrayList<Student>();
	private int min;
	private int prefMin;
	private int prefMax;
	private int max;
	
	private int top;
	private int left;
	private int width;
	private int height;	

	public Section(String sT, List<Student> sL, int[] thresholds) {
		sectionTime = sT;
		size = sL.size();
		studentList = sL;
		
		min = thresholds[0];
		prefMin = thresholds[1];
		prefMax = thresholds[2];
		max = thresholds[3];
		
	}

	public String getSectionTime(){
		return sectionTime;
	}
	
	public int getSize(){
		return size;
	}

	public List<Student> getStudentList(){
		return studentList;
	}

	public String toString(){
		String list = sectionTime + ":\n--------------------";
		for(Student s: studentList){
			list = list + "\n" + s.getFirstName() + " " + s.getLastName() + " " + s.getUID();
		}
		return list;
	}
	
	public int getMin(){
		return min;
	}
	
	public int getPrefMin(){
		return prefMin;
	}
	
	public int getPrefMax(){
		return prefMax;
	}
	
	public int getMax(){
		return max;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean hoverOver(double x, double y){
		if(x >= left && x <= (x + width) && y >= top && y <= (y + height))
			return true;
		else
			return false;
	}
}
