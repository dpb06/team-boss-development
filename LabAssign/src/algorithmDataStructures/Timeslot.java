package algorithmDataStructures;
import java.util.ArrayList;


public abstract class Timeslot {
	
	private int uID;
	private int time;
	private Day day;
	private int maxStudents;
	private ArrayList<Student> assigned=new ArrayList<Student>();
	
	public Timeslot(int UID, int time, Day d, int maxStud){
		this.uID=UID;
		this.time=time;
		this.day=d;
		this.maxStudents=maxStud;
	}
	
	
	public int getuID() {
		return uID;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Day getDay() {
		return day;
	}
	public void setDay(Day day) {
		this.day = day;
	}
	public int getMaxStudents() {
		return maxStudents;
	}
	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}
	public ArrayList<Student> getAssigned() {
		return assigned;
	}
	public void setAssigned(ArrayList<Student> assigned) {
		this.assigned = assigned;
	}
	
}
