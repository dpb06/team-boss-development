package algorithms;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import algorithmDataStructures.AlgorithmOutput;
import algorithmDataStructures.Student;
import algorithmDataStructures.Timeslot;

public class permuSort implements Algorithm{
	private ArrayList<Student> students;
	private ArrayList<Timeslot> labs;
	private ArrayList<Timeslot> tutorials;
	private AlgorithmOutput output = new AlgorithmOutput();
	private permuDataLeafNode start;
	private permuDataLeafNode current;
	private static double count=0;
	private double Fitness=0;
	//private double purposedFitness=0;
	private int numStudents;
	private ArrayList<Student> flagged= new ArrayList<Student>();
	private HashMap<Timeslot, Integer> timeslotSize=new HashMap<Timeslot, Integer>();

	private double mill=10000000;
	public permuSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
		for(Timeslot t:labs){
			timeslotSize.put(t, 0);
		}
		numStudents=students.size();
		Collections.sort(students,new Comparator<Student>(){
			@Override
			public int compare(Student arg0, Student arg1) {
				Student s1=arg0;
				Student s2=arg1;
				if(s1.getNumCanAttendLabs()>s2.getNumCanAttendLabs()){
					return 1;
				}
				else if(s1.getNumCanAttendLabs()<s2.getNumCanAttendLabs()){
					return -1;
				}
				return 0;
			}

		});
	}

	public void permSort(){
		for(Student s:students){
			if(!s.getFlagged()){
				if(start==null){
					start=new permuDataLeafNode(s,null);
					current=start;
				}
				else{
					permuDataLeafNode blah=new permuDataLeafNode(s,current);
					current.next=blah;
					current=blah;
				}
			}
		}
		long startTime=System.currentTimeMillis();
		System.out.println("Starting PermuSort");
		permuSortPart2(start);
		long endTime= System.currentTimeMillis();
		System.out.println("End PermuSort");
		long time=endTime-startTime;
		System.out.println("Run time: "+time+" Millisecs");
		time=time/1000;
		System.out.println("Run time: "+time+" secs");
		time=time/60;
		System.out.println("Run time: "+time+" mins");
	}

	public void permuSortPart2(permuDataLeafNode c){
		for(Timeslot t: c.timeslots){
			if(!(t.getAssigned().size()>t.getPreferredMax())){
				c.t=t;
				if(c.next!=null){
					//System.out.println("student :"+c.toString()+" Timeslot :"+t.toString());
					permuSortPart2(c.next);
				}
				else{
					count++;
					if(count==mill ){
						System.out.println("number of solutions so far: "+mill);
						mill+=1000000;
					}
					//purposedFitness=-1;
					FitnessFunctionFirstChoice(c);
				}
			}
			//			else{
			////				count++;
			////				if(count==mill){
			////				System.out.println("we have gotten to the end "+count+" number of times");
			////				mill+=10000000;
			//				}
		}
	}


	private void FitnessFunctionFirstChoice(permuDataLeafNode c) {
		for(Timeslot t:labs){
			timeslotSize.put(t, 0);
		}
		int countFirst=0;
		int countThird=0;
		int countSecond=0;
		double purposedFitness=0;
		Integer i1;
		//	System.out.println("Purposed fitness Start: "+purposedFitness);
//		if(c.getStudent().getFirstChoiceLabs().contains(c.t)){
//			countFirst++;
//			i1 = timeslotSize.get(c.t);
//			i1 = Integer.valueOf(i1.intValue()+1);
//			timeslotSize.put(c.t, i1);
//
//		}
//		else if(c.getStudent().getSecondChoiceLabs().contains(c.t)){
//			countSecond++;
//			i1= timeslotSize.get(c.t);
//			i1 = Integer.valueOf(i1.intValue()+1);
//			timeslotSize.put(c.t, i1);
//		}
//		else if(c.getStudent().getThirdChoiceLabs().contains(c.t)){
//			countThird++;
//			i1 = timeslotSize.get(c.t);
//			i1 = Integer.valueOf(i1.intValue()+1);
//			timeslotSize.put(c.t, i1);
//		}
		while(c.parent!=null){
			if(c.getStudent().getFirstChoiceLabs().contains(c.t)){
				countFirst++;
				i1 = timeslotSize.get(c.t);
				i1 = Integer.valueOf(i1.intValue()+1);
				timeslotSize.put(c.t, i1);
			}
			else if(c.getStudent().getSecondChoiceLabs().contains(c.t)){
				countSecond++;
				i1 = timeslotSize.get(c.t);
				i1 = Integer.valueOf(i1.intValue()+1);
				timeslotSize.put(c.t, i1);
			}
			else if(c.getStudent().getThirdChoiceLabs().contains(c.t)){
				countThird++;
				i1 = timeslotSize.get(c.t);
				i1 = Integer.valueOf(i1.intValue()+1);
				timeslotSize.put(c.t, i1);
			}
			c=c.parent;
		}

		if(c.getStudent().getFirstChoiceLabs().contains(c.t)){
			countFirst++;
			i1 = timeslotSize.get(c.t);
			i1 = Integer.valueOf(i1.intValue()+1);
			timeslotSize.put(c.t, i1);
		}
		else if(c.getStudent().getSecondChoiceLabs().contains(c.t)){
			countSecond++;
			i1 = timeslotSize.get(c.t);
			i1 = Integer.valueOf(i1.intValue()+1);
			timeslotSize.put(c.t, i1);
		}
		else if(c.getStudent().getThirdChoiceLabs().contains(c.t)){
			countThird++;
			i1 = timeslotSize.get(c.t);
			i1 = Integer.valueOf(i1.intValue()+1);
			timeslotSize.put(c.t, i1);
		}
		double percentFirst=(double)countFirst/(double)numStudents;
		//	System.out.println("First choice :"+percentFirst);
	//	double percentSecond=countSecond/numStudents;
		//	System.out.println("Second choice :"+countSecond);

		double percentThird=(double)countThird/(double)numStudents;
		//System.out.println("Third choice :"+percentThird);
		//double total=percentFirst+percentSecond+percentThird;


		double average=1;
		for(Timeslot t:timeslotSize.keySet()){
			//	System.out.println(t.toString()+" :" +timeslotSize.get(t));
			if(timeslotSize.get(t)==0){
				average+=1;
				average=average/2;
			}
			else if(timeslotSize.get(t)==t.getPreferredMax()){
				average+=1;
				average=average/2;
			}
			else if(timeslotSize.get(t)<(t.getPreferredMax()/2)){
				average+=0.2;
				average=average/2;
			}
			else if((timeslotSize.get(t)<(t.getPreferredMax()))&&(timeslotSize.get(t)>(t.getPreferredMax()/2) )){
				average+=0.75;
				average=average/2;
			}
			//	System.out.println("average :"+average);
		}
		purposedFitness=(average+percentFirst+(1-percentThird))/3;
		//		System.out.println("Purposed fitness end: "+purposedFitness);
		if(purposedFitness>Fitness){
			System.out.println("found a better Solution");
			System.out.println(percentFirst+" percent first choice");
			System.out.println(percentThird+" percent third choice "+(1-percentThird)+" fitness value");
			System.out.println("average value: "+average);
			Fitness=purposedFitness;
			System.out.println("Highest fitness function value so far: "+Fitness);
			while(c.next!=null){
				if(!(c.t.getAssigned().contains(c.getStudent()))){
					c.t.addStudent(c.getStudent());
					for(Timeslot z:labs){
						if(!(z==c.t) && z.getAssigned().contains(c.getStudent())){
							z.removeStudent(c.getStudent());
						}
					}
				}
				c=c.next;

			}
			if(!(c.t.getAssigned().contains(c.getStudent()))){
				c.t.addStudent(c.getStudent());
				for(Timeslot z:labs){
					if(!(z==c.t) && z.getAssigned().contains(c.getStudent())){
						z.removeStudent(c.getStudent());
					}
				}
			}
		}
		for( Student s: students){
			if(s.getFlagged()){
				System.out.println("Flagged Student :" +s.toString());
			}
		}
			System.out.println("Purposed fitness: "+purposedFitness);
	}
	private void generateAlgorithmOutput() {
		//Begin console output.
		System.out.println("generateAlgorithmOutput() in BossSort");
		//Iterate through Labs.
		System.out.println("Labs:");
		for(Timeslot t:labs){
			//Add the lab and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the lab and its assigned students.
			System.out.println(t.getDay() + ", " + t.getStartTime() + "-" + t.getEndTime());
			for(Student s: t.getAssigned()){
				System.out.println(s.getStudentNum() + " - " + s.getName());
			}
		}
		System.out.println();

		//Iterate through Tutorials.
		System.out.println("Tuts:");
		for(Timeslot t:tutorials){
			//Add the tutorial and its assigned students to the output hashmap.
			output.put(t,  t.getAssigned());
			//Printspam the tutorial and its assigned students.
			System.out.println(t.getDay() + ", " + t.getStartTime() + "-" + t.getEndTime());
			for(Student s: t.getAssigned()){
				System.out.println(s.getStudentNum() + " - " + s.getName());
			}
		}
		System.out.println();

		//Printspam the flagged students.
		System.out.println("Flagged:");
		for(Student s: flagged){
			System.out.println(s.getStudentNum() + " - " + s.getName());
			output.addFlagged(s);
		}
	}

	@Override
	public AlgorithmOutput start() {
		permSort();
		generateAlgorithmOutput();
		return output;
	}


	private class permuDataLeafNode {



		permuDataLeafNode parent;
		Timeslot t;
		Student s;
		ArrayList<Timeslot>timeslots=new ArrayList<Timeslot>();
		int numberTimeslot;
		boolean visited=false;
		permuDataLeafNode next;

		public permuDataLeafNode(Student s,permuDataLeafNode parent) {
			this.s=s;		
			if(parent!=null){
				this.parent=parent;
			}
			timeslots.addAll(s.getFirstChoiceLabs());
			timeslots.addAll(s.getSecondChoiceLabs());
			timeslots.addAll(s.getThirdChoiceLabs());

		}
		public Student getStudent(){
			return s;
		}

		public String toString(){
			return s.toString();
		}

	}

}
