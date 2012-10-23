package algorithmDataStructures;

import java.util.ArrayList;

public class PermuLeafNode{	

	private PermuLeafNode parent;
	private PermuLeafNode next;
	private Student student;
	private ArrayList<Timeslot> attendableLabs = new ArrayList<Timeslot>();
	
	private Timeslot CurrentlyAssignedLab;
	private int numberTimeslot;
	private boolean visited = false;
	
	public PermuLeafNode(Student student, PermuLeafNode parent){
		this.student = student;
		this.parent = parent;
		attendableLabs.addAll(student.getFirstChoiceLabs());
		attendableLabs.addAll(student.getSecondChoiceLabs());
		attendableLabs.addAll(student.getThirdChoiceLabs());
	}
	
	public Student getStudent(){
		return student;
	}
	
	public void setNext(PermuLeafNode next){
		this.next = next;
	}
	
	public PermuLeafNode getNext(){
		return next;
	}

	@Override
	public String toString(){
		return student.toString();
	}
	
	public ArrayList<Timeslot> getAttendableLabs(){
		return attendableLabs;
	}
	
	public void setCurrentlyAssignedLab(Timeslot currentlyAssignedLab){
		this.CurrentlyAssignedLab = currentlyAssignedLab;
	}
	
	public Timeslot getCurrentlyAssignedLab(){
		return CurrentlyAssignedLab;
	}

	public PermuLeafNode getParent() {
		return parent;
	}
}