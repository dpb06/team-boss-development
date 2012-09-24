package algorithmDataStructures;

public class TimeSlotTotals {
	private int numFirstChoice=0;
	private int numSecondChoice=0;
	private int numThirdChoice=0;
	private int numCannotAttend=0;
	private int numOnlyAttend=0;
	private int total=0;
	
	public void incrementFirst(int choice){
		switch(choice){
		case 0:
			numCannotAttend++;
			break;
		case 1:
			numFirstChoice++;
			total++;
			break;
		case 2:
			numSecondChoice++;
			total++;
			break;
		case 3:
			numThirdChoice++;
			total++;
			break;
		
		case 4:
			numOnlyAttend++;
			break;
		}
	}

	public int getNumFirstChoice() {
		return numFirstChoice;
	}

	public int getNumSecondChoice() {
		return numSecondChoice;
	}

	public int getNumThirdChoice() {
		return numThirdChoice;
	}

	public int getNumCannotAttend() {
		return numCannotAttend;
	}

	public int getTotal() {
		return total;
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("First Choices: "+numFirstChoice+"\n");
		sb.append("Second Choices: "+numSecondChoice+"\n");
		sb.append("Third Choices: "+numThirdChoice+"\n");
		sb.append("Cannot attend: "+numCannotAttend+"\n");
		sb.append("Can only attend: "+numOnlyAttend+"\n");
		sb.append("Total Students :"+ total+"\n");
		return sb.toString();
	}
	
	
}
