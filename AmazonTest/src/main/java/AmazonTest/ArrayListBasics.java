package AmazonTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class ArrayListBasics {

	public static void main(String[] args) {
		List<Integer> grades = new LinkedList<Integer>();
		//Integer[] gradearray = new Integer[3];
		
		grades.add(90);
		grades.add(85);
		grades.add(70);

		for(int i = 0; i < grades.size(); i ++){
			System.out.printf("Grade #%d == %d\n", i, grades.get(i));
			//gradearray[i] = grades.get(i);
		}
		grades.remove(1);
		
		for(int i = 0; i < grades.size(); i ++){
			System.out.printf("Grade #%d == %d\n", i, grades.get(i));
			
		}
	}
}