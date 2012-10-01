package dataParsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class StudentDataParser {
	
	
	//TODO: Fix everything I've broken.
	//TODO: Ensure student IDs are unique.
	
	
	/**
	 * Data file format:
	 * 
	 * 
	 * --------------file start---------------------
	 * 
	 * "Question ID"+ "\t" +"Question"+ "\t" +"Answer"
	 * "Question ID 1"+ "\t" +"<Day1> <timestart1> - <timeend1> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * "Question ID 2"+ "\t" +"<Day2> <timestart2> - <timeend2> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * "Question ID 3"+ "\t" +"<Day3> <timestart3> - <timeend3> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * ...
	 * ...
	 * "<div class=\"\"vtbegenerated\"\"> + Y + "</div>" // to be stripped down to Y and checked for 
	 * 		Y ->  "<DayX> <timestartX> - <timeendX> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice" 
	 * 
	 */
	
	
	//Step 1: strip slot options
	//Step 2: create students

	List<String> tokens = new LinkedList<String>();
	Scanner studentScan;
	
	
	
	/**
	 * A Scanner-style parser which takes text and when next() is called returns a Student
	 * @param text the body of text to be parsed
	 */
	public StudentDataParser (String startText) {
	
		
		String text = startText.replace("<div class=\"\"vtbegenerated\"\">", "");
		text = text.replace("</div>", "");
		
		
		Scanner tempScan = new Scanner(text);		
		tempScan.useDelimiter("[\\t\"]");
		if(tempScan.hasNext());
			tempScan.nextLine();
		
		while(tempScan.hasNext()){
			String temp = tempScan.next();
			if(!temp.equals(""))
				tokens.add(temp);				
		}
		studentScan = new Scanner(text);
	}
	
	
	public StudentDataParser(File f) throws FileNotFoundException  {

		String startText = "";
		
		Scanner scan = new Scanner(f);
		while(scan.hasNext()){
			String t = scan.nextLine();
			startText += " "+t;
		}
		
		
		String text = startText.replace("<div class=\"\"vtbegenerated\"\">", "");
		text = text.replace("</div>", "");
		// remove the first line of the file, (the line stating the format)
		text = text.substring(34);
		
		Scanner tempScan = new Scanner(text);		
		tempScan.useDelimiter("[\\t\"]");
	
		
		
		while(tempScan.hasNext()){
			String temp = tempScan.next();
			if(!temp.equals(""))
				tokens.add(temp);			
		}
		studentScan = new Scanner(text);
	}


	/**
	 * When called attempts to read the next student from the body of text
	 * 
	 * @return
	 */
	public Student next(){
		return null;
		
	}
	
	/**
	 * Checks to see if there is another student to be parsed in the file
	 * @return
	 */
	public boolean hasNext() {
		return false;
	}
	
	
	
	/**
	 * Data file format:
	 * 
	 * 
	 * --------------file start---------------------
	 * 
	 * "Question ID"+ "\t" +"Question"+ "\t" +"Answer"
	 * "Question ID 1"+ "\t" +"<Day1> <timestart1> - <timeend1> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * "Question ID 2"+ "\t" +"<Day2> <timestart2> - <timeend2> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * "Question ID 3"+ "\t" +"<Day3> <timestart3> - <timeend3> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice"
	 * ...
	 * ...
	 * "<div class=\"\"vtbegenerated\"\"> + Y + "</div>" // to be stripped down to Y and checked for 
	 * 		Y ->  "<DayX> <timestartX> - <timeendX> is a"+" "+"Cannot Attend/First Choice/Second Choice/Third Choice" 
	 * 
	 */
	
	/**
	 * 
	 * @return gives the list of timeslots from the file, with each having a max student count of 0
	 * @throws Throwable 
	 */
	public List<Timeslot> getTimeslots() throws IllegalArgumentException{
		
		List<Timeslot> timeslots = new ArrayList<Timeslot>();
		
		Iterator<String> iter = tokens.iterator();
		int quID = -1;
		String token = "";//iter.next();
		int iTimeStart = -1;
		int iTimeEnd = -1;
		Day eDay = null;
		
		while(iter.hasNext()){
			token = iter.next();
			
			if(token.contains("Question ID")){ // indicates the id is in this string
				
				//checks to see if we have reached the next round of the same set of questions
				// if we were up to question 11 and now are back at question 1
				if(quID  < Integer.parseInt(token.substring(11).trim().replace("\"", "")))
					quID =Integer.parseInt(token.substring(11).trim().replace("\"", ""));
				else
					break;
				
			}
			if(token.contains("is a")){ // indicates this token has the day, timestart and end of this timeslot
				String timeStart = "";
				Scanner timeScan =  new Scanner(token);
				String dayAndTime = timeScan.next();
				String day = "";
				int spaceIndex = dayAndTime.length();
				
				// looks for the index where the space is
				// 		had an issue where the character indicating the 
				//		space does not count as whitespace
				// - need to look for the first character not part of the day name 
				for(int i = 0 ; i < dayAndTime.length()  ; i++ ){
					
					if (!Character.isLetter(dayAndTime.charAt(i))){
						spaceIndex = i;
						break;
					}
				}
				if(spaceIndex != dayAndTime.length())
					timeStart = dayAndTime.substring(spaceIndex+1).trim();
				
				day = dayAndTime.substring(0, spaceIndex).trim();
				
				
				 
				if(timeStart.equals("")){
					timeStart = timeScan.next();
				}
				
				eDay = parseDay(day);
				if(eDay == null)  // checks to see if day was parsed incorrectly
					throw new IllegalArgumentException("Expected a day as the first token in: "+dayAndTime);
				
				
				iTimeStart = parseTime(timeStart);
				if(iTimeStart == -1){
					throw new IllegalArgumentException("Expected a time as the second token in: "+token);
				}
				String dashAndTime = timeScan.next();
				String timeEnd = "";
				int secSpaceIndex = 1;
				
				//	same issue as above, spaces not appearing as whitespace to the scanner.
				//	need to manually find the position of the time.
				//
				if((dashAndTime.charAt(0) == '-')){
					if (dashAndTime.length() == 1){
						timeEnd = timeScan.next();
					} else
						timeEnd = dashAndTime.substring(secSpaceIndex+1);
				}else
					throw new IllegalArgumentException("Expected - as the third token in: "+token);
					
				
				iTimeEnd = parseTime(timeEnd);
				if(iTimeEnd == -1){
					throw new IllegalArgumentException("Expected a time as the fourth token in: "+token);
				}
			Timeslot t = new Lab(quID, iTimeStart, iTimeEnd, eDay);
			//t.setMaxStudents(20);
			timeslots.add(t);
			}
		}
		return timeslots;
	}


	private int parseTime(String timeStart) {
		
		timeStart = timeStart.trim();
		String[] timeTokens = timeStart.split(":"); 
		String hours = timeTokens[0];
		String mins = timeTokens[1];
		
		int h = Integer.parseInt(hours);
		if (0 > h || h > 24)
			return -1;
		int m = Integer.parseInt(mins);
		if (0 > m || m > 59)
			return -1;
		
		
		
		return Integer.parseInt(hours+mins);
	}

	
	
	public List<Student> parseSelections(List<Timeslot> timeslots){
	
		List<Student> students = new ArrayList<Student>();
		Iterator<String> iter = tokens.iterator();
		
		boolean DEBUG = false;
		
		
		int quID = -1;
		String token = "";
		String firstName = null;
		String lastName = null;
		int studentID = -1;
		
		ArrayList<Timeslot> firstChoice = new ArrayList<Timeslot>();
		ArrayList<Timeslot> secondChoice = new ArrayList<Timeslot>();
		ArrayList<Timeslot> thirdChoice = new ArrayList<Timeslot>();
		
		int count = 0;
		
		
		while(iter.hasNext()){
			token = iter.next();

			if(token.contains("Question ID")){ // indicates the id is in this string
				
				quID =Integer.parseInt(token.substring(11).trim());	
				
			}else if (token.contains("First Choice")){

				Timeslot t = getTimeslot(timeslots, quID);
				if (t == null){
					if(DEBUG)
						System.out.println("Fail" + token);
				}
				else
					firstChoice.add(t);
			}else if (token.contains("Second Choice")){
				Timeslot t = getTimeslot(timeslots, quID);
				if (t == null){
					if(DEBUG)
						System.out.println("Fail" + token);
				}else
					secondChoice.add(t);
			}else if (token.contains("Third Choice")){
				Timeslot t = getTimeslot(timeslots, quID);
				if (t == null){
					if(DEBUG)
						System.out.println("Fail" + token);
				}else
					thirdChoice.add(t);
			}else if (token.contains("Please enter your first name")){
				firstName = iter.next().trim();
			}else if (token.contains("Please enter your last (family) name")){
				lastName = iter.next().trim();
			}else if (token.contains("Please enter the last three digits of your student ID number.")){
				studentID = Integer.parseInt( iter.next().replace('\"', ' ') );
				
				//Student student = new Student(count, firstName, lastName, studentID);
				String name = firstName + " " + lastName;
				Student student = new Student(studentID, name);
				students.add(student);
				
				count++; // acting as unique ID
				for(Timeslot t : firstChoice){
					student.addFirstLab(t);
				}
				for(Timeslot t : secondChoice){
					student.addSecondLab(t);
				}
				for(Timeslot t : thirdChoice){
					student.addThirdLab(t);
				}
				firstChoice = new ArrayList<Timeslot>();
				secondChoice = new ArrayList<Timeslot>();
				thirdChoice = new ArrayList<Timeslot>();
			}
		}
		return students;
	}
	

	private Timeslot getTimeslot(List<Timeslot> timeslots, int quID) {
	
		for(Timeslot t : timeslots){
			if(quID == t.getuID()){
				return t;
				
			}
		}
		return null;
	}


	private Day parseDay(String day) {

		if(day.equalsIgnoreCase("Monday"))
			return Day.Monday;
		else if(day.equalsIgnoreCase("Tuesday"))
			return Day.Tuesday;
		else if(day.equalsIgnoreCase("Wednesday"))
			return Day.Wednesday;
		else if(day.equalsIgnoreCase("Thursday"))
			return Day.Thursday;
		else if(day.equalsIgnoreCase("Friday"))
			return Day.Friday;
		else 
			return null;
		
	}

}
