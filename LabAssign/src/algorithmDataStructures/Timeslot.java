package algorithmDataStructures;
import java.util.ArrayList;


public abstract class Timeslot {
	
	private int uID;
	private int timeStart;
	private int timeEnd;
	private Day day;
	private int maxStudents;
	private ArrayList<Student> assigned=new ArrayList<Student>();
	
	public Timeslot(int UID, int timeS,int timeE, Day d, int maxStud){
		this.uID=UID;
		this.timeStart=timeS;
		this.timeEnd=timeE;
		this.day=d;
		this.maxStudents=maxStud;
	}
	
	
	


	public void timeslotPrint() {
		//Print time/day
		System.out.printf("\n\n%s %d", getDay(), getTime());
		//For every assigned student
		for (int i = 0; i < getAssigned().size(); i++){
			//If lab is oversize
			if(i > maxStudents-1){
				//Indent overflowed student print messages
				System.out.printf("\n\t");
			} else {
				System.out.println();
			}
			//Print student details
			assigned.get(i).printStudent();
		}
	}
	
	public int getuID() {
		return uID;
	}
	
	public int getStartTime() {
		return timeStart;
	}
	public int getEndTime() {
		return timeEnd;
	}
	public void setStartTime(int time) {
		this.timeStart = time;
	}
	public void setEndTime(int time) {
		this.timeEnd = time;
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
	public boolean addStudent(Student s) {
		if (assigned.size() >= maxStudents){
			return false;
		} else {
		this.assigned.add(s);
		return true;
		}
	}

	
}
