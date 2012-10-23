package algorithmDataStructures;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Stores information about a single lab or tutorial, including:
 *   A unique ID.
 *   The hour the lab/tut starts (in 24 hour time).
 *   The hour the lab/tut ends.
 *   The day the lab/tut is on.
 *   The number of students that can/should be in this lab/tut.
 *   The students assigned to this lab/tut.
 */
public abstract class Timeslot{

	//-----FIELDS-----\\
	private int uID;
	private int startTime;
	private int endTime;
	private Day day;
	private int minStudents = 0;
	private int preferredMin = 0;
	private int preferredMax = 15;
	private int maxStudents = 20;
	private ArrayList<Student> assigned=new ArrayList<Student>();


	//-----CONSTRUCTOR-----\\
	public Timeslot(int UID, int startTime, int endTime, Day day){
		this.uID = UID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;

	}


	//-----FUNCTIONALITIES-----\\
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

	/**
	 * This method checks to see whether this timeslot intersects with the given timeslot, by comparing their start and finish times
	 * @param otherTimeSlot the timeslot to compare this with
	 * @return whether or not the two slots intersect in time
	 */
	public boolean intersectsWith(Timeslot otherTimeslot){
		if (otherTimeslot == null){
			return false;
		}
		//If on different days, return false
		if (this.day != otherTimeslot.getDay()){
			return false;
		}
		//If this ends before the other begins, return 1.
		if(this.endTime <= otherTimeslot.getStartTime()){
			return false;
		}
		//If this begins after the other ends, return -1.
		else if(this.startTime >= otherTimeslot.getEndTime()){
			return false;
		}
		//If the times intersect, return 0.
		else{
			return true;
		}
	}
	public void sortAssigned(){
		Collections.sort(assigned,new Comparator<Student>(){

			@Override
			public int compare(Student o1, Student o2) {
				// TODO Auto-generated method stub
				if(o1.getNumCanAttendLabs()>o2.getNumCanAttendLabs()){
					return 1;
				}
				else if(o1.getNumCanAttendLabs()<o2.getNumCanAttendLabs()){
					return -1;
				}
				else
					return 0;
			}

		});
	}
	@Override
	public String toString(){
		return day.toString().substring(0,3) +": "+ ((startTime>=1000)?startTime:("0"+startTime))+ " - "+((endTime>=1000)?endTime:("0"+endTime)) ;
	}

	public boolean isOverfilled(){
		return (assigned.size()>preferredMax);
	}


	//-----ADD METHODS-----\\
	public void addStudent(Student s) {
		this.assigned.add(s);
	}


	//-----REMOVE METHODS-----\\
	public void removeStudent(Student s){
		this.assigned.remove(s);
	}


	//-----SET METHODS-----\\
	public void setThresholds (int[] threshold){
		this.minStudents = threshold[0];
		this.preferredMin = threshold[1];
		this.preferredMax = threshold[2];
		this.maxStudents = threshold[3];
	}

	public void setMinStudents(int minStudents) {
		this.minStudents = minStudents;
	}

	public void setPreferredMin(int preferredMin) {
		this.preferredMin = preferredMin;
	}

	public void setPreferredMax(int preferredMax) {
		this.preferredMax = preferredMax;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}


	//-----GET METHODS-----\\
	public int getuID(){
		return uID;
	}

	public int getStartTime() {
		return startTime;
	}
	public int getEndTime() {
		return endTime;
	}

	public Day getDay() {
		return day;
	}

	public int getMinStudents() {
		return minStudents;
	}

	public int getPreferredMin() {
		return preferredMin;
	}	

	public int getPreferredMax() {
		return preferredMax;
	}

	public int getMaxStudents() {
		return maxStudents;
	}

	public ArrayList<Student> getAssigned() {
		return assigned;
	}

}
