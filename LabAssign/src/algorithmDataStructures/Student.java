package algorithmDataStructures;
import java.util.ArrayList;


public class Student {

	private int UID;
	private String firstName;
	private String lastName;
	private int studentNum;
	private ArrayList<Timeslot> assignedLabs;
	private ArrayList<Timeslot> assignedTuts;
	private ArrayList<Timeslot> firstLabs;
	private ArrayList<Timeslot> firstTuts;
	private ArrayList<Timeslot> secondLabs;
	private ArrayList<Timeslot> secondTuts;
	private ArrayList<Timeslot> thirdLabs;
	private ArrayList<Timeslot> thirdTuts;
	private ArrayList<Timeslot> cannotAttendLabs;
	private ArrayList<Timeslot> cannotAttendTuts;
	
	
	
	
	
	
	
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}
	public ArrayList<Timeslot> getAssignedLabs() {
		return assignedLabs;
	}
	public void setAssignedLabs(ArrayList<Timeslot> assignedLabs) {
		this.assignedLabs = assignedLabs;
	}
	public ArrayList<Timeslot> getAssignedTuts() {
		return assignedTuts;
	}
	public void setAssignedTuts(ArrayList<Timeslot> assignedTuts) {
		this.assignedTuts = assignedTuts;
	}
	public ArrayList<Timeslot> getFirstLabs() {
		return firstLabs;
	}
	public void setFirstLabs(ArrayList<Timeslot> firstLabs) {
		this.firstLabs = firstLabs;
	}
	public ArrayList<Timeslot> getFirstTuts() {
		return firstTuts;
	}
	public void setFirstTuts(ArrayList<Timeslot> firstTuts) {
		this.firstTuts = firstTuts;
	}
	public ArrayList<Timeslot> getSecondLabs() {
		return secondLabs;
	}
	public void setSecondLabs(ArrayList<Timeslot> secondLabs) {
		this.secondLabs = secondLabs;
	}
	public ArrayList<Timeslot> getSecondTuts() {
		return secondTuts;
	}
	public void setSecondTuts(ArrayList<Timeslot> secondTuts) {
		this.secondTuts = secondTuts;
	}
	public ArrayList<Timeslot> getThirdLabs() {
		return thirdLabs;
	}
	public void setThirdLabs(ArrayList<Timeslot> thirdLabs) {
		this.thirdLabs = thirdLabs;
	}
	public ArrayList<Timeslot> getThirdTuts() {
		return thirdTuts;
	}
	public void setThirdTuts(ArrayList<Timeslot> thirdTuts) {
		this.thirdTuts = thirdTuts;
	}
	public ArrayList<Timeslot> getCannotAttendLabs() {
		return cannotAttendLabs;
	}
	public void setCannotAttendLabs(ArrayList<Timeslot> cannotAttendLabs) {
		this.cannotAttendLabs = cannotAttendLabs;
	}
	public ArrayList<Timeslot> getCannotAttendTuts() {
		return cannotAttendTuts;
	}
	public void setCannotAttendTuts(ArrayList<Timeslot> cannotAttendTuts) {
		this.cannotAttendTuts = cannotAttendTuts;
	}

	

	
}
