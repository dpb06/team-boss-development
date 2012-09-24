package algorithms;

import java.util.ArrayList;

import Deprecated.StaticTimeslotMap;
import algorithmDataStructures.Day;
import algorithmDataStructures.Lab;
import algorithmDataStructures.Student;

public class HowardSortTest {
private StaticTimeslotMap stm= new StaticTimeslotMap();
	public HowardSortTest(){
		ArrayList<Student> students=new ArrayList<Student>();
		int[] threshOne = {0,0,3,3};
		int[] threshTwo = {0,0,2,2};
		stm.put(0, (new Lab(1, 1310,1510, Day.Monday, threshOne)));
		stm.put(1,new Lab(2, 1310,1510, Day.Tuesday, threshOne));
		stm.put(2,(new Lab(3, 1310, 1510,Day.Wednesday, threshTwo)));
			int[] labs={3,1,2};
			students.add(new Student(0,labs));			
			labs[0]=1;
			labs[1]=2;
			labs[2]=3;
			students.add(new Student(1,labs));		
			labs[0]=2;
			labs[1]=3;
			labs[2]=1;
			students.add(new Student(2,labs));		
			labs[0]=2;
			labs[1]=1;
			labs[2]=3;
			students.add(new Student(3,labs));		
			labs[0]=0;
			labs[1]=3;
			labs[2]=1;
			students.add(new Student(4,labs));		
			labs[0]=1;
			labs[1]=2;
			labs[2]=3;
			students.add(new Student(5,labs));		
			labs[0]=2;
			labs[1]=1;
			labs[2]=3;
			students.add(new Student(6,labs));		
			labs[0]=3;
			labs[1]=2;
			labs[2]=1;
			students.add(new Student(7,labs));					
			labs[0]=1;
			labs[1]=2;
			labs[2]=3;
			students.add(new Student(8,labs));		
			labs[0]=2;
			labs[1]=3;
			labs[2]=1;
			students.add(new Student(9,labs));		
			labs[0]=2;
			labs[1]=1;
			labs[2]=3;
			students.add(new Student(10,labs));		
			labs[0]=0;
			labs[1]=3;
			labs[2]=1;
			students.add(new Student(11,labs));		
			labs[0]=1;
			labs[1]=2;
			labs[2]=3;
			students.add(new Student(12,labs));		
			labs[0]=2;
			labs[1]=1;
			labs[2]=3;
			students.add(new Student(13,labs));		
			labs[0]=3;
			labs[1]=2;
			labs[2]=1;
			students.add(new Student(14,labs));						
			new HowardsSort(students,stm);
	}
	
	
	public static void main(String[] args){
		new HowardSortTest();
	}
}
