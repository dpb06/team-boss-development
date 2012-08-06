package dataParsing;

import algorithmDataStructures.Day;

public class DataTimeslot {

	


		private int uID;
		private int time;
		private Day day;
		
		public DataTimeslot(int UID, int time, Day d){
			this.uID=UID;
			this.time=time;
			this.day=d;
			
		}
		
		
		


		public void dataTimeslotPrint() {
			//Print time/day
			System.out.printf("\n\n%s %d", getDay(), getTime());
			//For every assigned student
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
		

	
}
