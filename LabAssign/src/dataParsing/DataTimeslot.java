package dataParsing;

import algorithmDataStructures.Day;
import algorithmDataStructures.Timeslot;

public class DataTimeslot extends Timeslot {

	

		
		public DataTimeslot(int UID, int startTime, int endTime, Day d){
			super(UID, startTime,endTime, d, 20);
			
			
		}
		
		
		


		public void dataTimeslotPrint() {
			//Print time/day
			System.out.printf("\n\n%s %d %d", getDay(), getStartTime(),getEndTime());
			//For every assigned student
		}
		


	
}
