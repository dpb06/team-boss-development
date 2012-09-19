package algorithmDataStructures;
import java.util.ArrayList;


public abstract class Timeslot {
	
	private int uID;
	private int timeStart;
	private int timeEnd;
	private Day day;
	private int minStudents;
	private int preferredMin;
	private int preferredMax;
	private int maxStudents;
	private ArrayList<Student> assigned=new ArrayList<Student>();
	
	//TODO: Check if range is okay as percentage and possibly refactor.

	public Timeslot(int UID, int startTime, int endTime, Day day, int[] threshold){
		this.uID = UID;
		this.timeStart = startTime;
		this.timeEnd = endTime;
		this.day = day;
		this.minStudents = threshold[0];
		this.preferredMin = threshold[1];
		this.preferredMax = threshold[2];
		this.maxStudents = threshold[3];
	}

	public void timeslotPrint() {
		//Print time/day
		System.out.printf("\n\n%s %d %d", getDay(), getStartTime(),getEndTime());
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
			System.out.println(assigned.get(i).getStudentNum());
		}
	}
	

	public String toString(){
		return uID +":  "+ timeStart+ " - "+timeEnd ;
	}
	
	

	
	public int getStartTime() {
		return timeStart;
	}
	public int getEndTime() {
		return timeEnd;
	}
	public Day getDay() {
		return day;
	}
	public int getMaxStudents() {
		return maxStudents;
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

	public int getRangeMin() {
		return minStudents;
	}

	public void setRangeMin(int rangeMin) {
		this.minStudents = rangeMin;
	}

	public int getRangePrefHigh() {
		return preferredMax;
	}

	public void setRangePrefHigh(int rangePrefHigh) {
		this.preferredMax = rangePrefHigh;
	}

	public int getRangePrefLow() {
		return preferredMin;
	}

	public void setRangePrefLow(int rangePrefLow) {
		this.preferredMin = rangePrefLow;
	}
	public int getSize(){
		return assigned.size();
	}
	public boolean isOverFilled(){
		return assigned.size()>maxStudents;
	}
	public void removeStudent(Student s){
		assigned.remove(s);
	}
	

	public void dataTimeslotPrint() {
		//Print time/day
		System.out.printf("\n\n%s %d %d", getDay(), getStartTime(),getEndTime());
		//For every assigned student
	}
	
	public boolean isOverfilled(){
		return (assigned.size()>maxStudents);
	}
	
}
