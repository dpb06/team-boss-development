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
	private boolean kill=false;
	//private double purposedFitness=0;
	private int numStudents;
	private ArrayList<Student> flagged= new ArrayList<Student>();
	private static HashMap<Timeslot, Integer>timeslotSize=new HashMap<Timeslot, Integer>();
	private double mill=1000;
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
		Killthread k= new Killthread();
		k.start();
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
		Integer i;
		int j=0;
		for(Timeslot t: c.timeslots){
			System.out.println(timeslotSize.get(t)+" : "+t.getPreferredMax());
			if(timeslotSize.get(t)<t.getPreferredMax()){
				c.setTimeslot(t);
				if(c.t!=null){
					if(c.next!=null){
						if(!kill){
							System.out.println(c.getStudent()+" : "+c.t.toString());
							permuSortPart2(c.next);
						}

					}
					else{
						//	System.out.println("found a solution");
						//					count++;
						//					if(count==mill ){
						//						System.out.println("number of solutions so far: "+mill);
						//						mill+=1000000;
						//					}
						//purposedFitness=-1;
						printSpam();
						FitnessFunctionFirstChoice(c);
						//System.out.println("found some solution");
					}
				}
			}
		}
	}


	private void printSpam() {
		for(Timeslot t: timeslotSize.keySet()){
			System.out.println(t.toString()+" : "+timeslotSize.get(t).toString());
		}

	}

	private void FitnessFunctionFirstChoice(permuDataLeafNode c) {

		int countFirst=0;
		int countThird=0;
		int countSecond=0;
		double purposedFitness=0;
		boolean full=false;
		Integer i1;
		//	System.out.println("Purposed fitness Start: "+purposedFitness);
		if(c.getStudent().getFirstChoiceLabs().contains(c.t)){
			countFirst++;
		}
		else if(c.getStudent().getSecondChoiceLabs().contains(c.t)){
			countSecond++;
		}
		else if(c.getStudent().getThirdChoiceLabs().contains(c.t)){
			countThird++;
		}
		while(c.parent!=null){
			if(c.getStudent().getFirstChoiceLabs().contains(c.t)){
				countFirst++;
			}
			else if(c.getStudent().getSecondChoiceLabs().contains(c.t)){
				countSecond++;
			}
			else if(c.getStudent().getThirdChoiceLabs().contains(c.t)){
				countThird++;
			}
			c=c.parent;
		}

		if(c.getStudent().getFirstChoiceLabs().contains(c.t)){
			countFirst++;
		}
		else if(c.getStudent().getSecondChoiceLabs().contains(c.t)){
			countSecond++;
		}
		else if(c.getStudent().getThirdChoiceLabs().contains(c.t)){
			countThird++;
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
			else{
				average=(double)timeslotSize.get(t)/(double)t.getPreferredMax();
			}

			//	System.out.println("average :"+average);
		}
		purposedFitness=(average+percentFirst+(1-percentThird))/3;
		for(Timeslot t:labs){
			if(t.getAssigned().size()>t.getPreferredMax()){
				//System.out.println("you fool");
				purposedFitness=0;
				break;
			}
		}
		//		System.out.println("Purposed fitness end: "+purposedFitness);
		if(purposedFitness>Fitness && !over()){
			//	System.out.println("found a better Solution");
			//				System.out.println(percentFirst+" percent first choice");
			//				System.out.println(percentThird+" percent third choice "+(1-percentThird)+" fitness value");
			//				System.out.println("average value: "+average);
			Fitness=purposedFitness;
			System.out.println("Highest fitness function value so far: "+Fitness);
			for(Student s: students){
				if(s.getAssignedLab()!=null){
					s.setAssignedLab(null);
				}
			}
			for( Timeslot t: labs){
				t.getAssigned().clear();
			}
			boolean n=true;
			while(n){
				labs.indexOf(c.t);
				labs.get(labs.indexOf(c.t)).addStudent(c.getStudent());
				c.getStudent().setAssignedLab(c.t);
				if(c.next!=null){
					c=c.next;
				}
				else 
					n=false;
			}
			for(Timeslot t:labs){
				if(t.isOverfilled()){
					System.out.println("You are overfilling labs");
				}
			}
		}
	}
	private boolean over() {
		boolean w= false;
		for(Timeslot t: timeslotSize.keySet()){
			if(timeslotSize.get(t)>t.getPreferredMax()){
				w=true;
				System.out.println("AAAAAAAWWWWWWWWWWWWWWWWWWW");
			}
		}
		return w;
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

	private class Killthread extends Thread{
		private final int timeToRun = 6000; //4 min
		public void run(){
			try {
				sleep(timeToRun);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			kill=true;
			//this.interrupt();
		}


	}

	private class permuDataLeafNode {

		permuDataLeafNode parent;
		Timeslot t=null;
		Student s;
		ArrayList<Timeslot>timeslots=new ArrayList<Timeslot>();
		permuDataLeafNode next;

		public permuDataLeafNode(Student s,permuDataLeafNode parent) {
			this.s=s;		
			this.parent=parent;

			timeslots.addAll(s.getFirstChoiceLabs());
			timeslots.addAll(s.getSecondChoiceLabs());
			timeslots.addAll(s.getThirdChoiceLabs());

		}
		public Student getStudent(){
			return s;
		}
		public void setTimeslot(Timeslot t){
			int i;
			if(this.t!=t){
				if(this.t==null){
					this.t=t;
					i=timeslotSize.get(t).intValue()+1;
					timeslotSize.put(t, i);
				}
				else{
					if(timeslotSize.get(t)>t.getPreferredMax()){
						System.out.println("This should not be above the preferd size but it is...");
						t=null;
					}
					else{
						i=Integer.valueOf(timeslotSize.get(this.t));
						i=i-1;
						timeslotSize.put(this.t,i);
						i=Integer.valueOf(timeslotSize.get(t));
						i=i+1;
						timeslotSize.put(t,i);
						this.t=t;
					}

				}
			}
		}
		public String toString(){
			return s.toString();
		}


	}
}
 
