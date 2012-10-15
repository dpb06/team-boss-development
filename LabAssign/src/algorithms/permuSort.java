package algorithms;

import java.util.ArrayList;

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
	private static int count=0;

	public permuSort(ArrayList<Timeslot> labs, ArrayList<Timeslot> tutorials, ArrayList<Student> students){
		this.students = students;
		this.labs = labs;
		this.tutorials = tutorials;
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
		float startTime=System.currentTimeMillis();
		System.out.println("Starting PermuSort");
		permuSortPart2(start);
		float endTime= System.currentTimeMillis();
		System.out.println("End PermuSort");
		System.out.println("Run time: "+(startTime-endTime));
	}

	public void permuSortPart2(permuDataLeafNode c){
		for(Timeslot t: c.timeslots){
			c.t=t;
			if(c.next!=null){
				//System.out.println("student :"+c.toString()+" Timeslot :"+t.toString());
				permuSortPart2(c.next);
			}
			else{
				count++;
				//System.out.println("we have gotten to the end "+count+" number of times");
			}
		}

	}

	@Override
	public AlgorithmOutput start() {
		// TODO Auto-generated method stub
		permSort();

		current=start;
		while(current.next!=null){
			System.out.println(current.getStudent().toString());
			current=current.next;
		}

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

		public boolean isEmpty(){
			if(s==null){
				return true;
			}
			else{
				return false;
			}
		}
		public Student getStudent(){
			return s;
		}

		public String toString(){
			return s.toString();
		}

	}

}
