package dataParsing;

import algorithmDataStructures.Day;
import algorithmDataStructures.Timeslot;

public class DataTimeslot extends Timeslot {

	



		
		private int endTime;
		public DataTimeslot(int UID, int startTime, int endTime, Day d){
			super(UID, startTime, d, 0);
			this.endTime=endTime;
			
		}
		
		
		


		public void dataTimeslotPrint() {
			//Print time/day
			System.out.printf("\n\n%s %d", getDay(), getTime());
			//For every assigned student
		}
		
		


	
}
