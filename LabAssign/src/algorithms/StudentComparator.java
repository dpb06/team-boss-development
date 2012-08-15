package algorithms;

import java.util.Comparator;

import algorithmDataStructures.Student;

public class StudentComparator implements Comparator<Student>{

	@Override
	public int compare(Student o1, Student o2) {
	Student s1;
	Student s2;
	if(o1 instanceof Student && o2 instanceof Student ){
		s1=(Student)o1;
		s2=(Student)o2;

		if(s1.getPriority()>s2.getPriority()){
			return 1;
		}
		if(s1.getPriority()<s2.getPriority()){
			return -1;
		}
		return 0;
	}
	return -1;
	}
}
