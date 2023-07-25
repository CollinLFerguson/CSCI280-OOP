/*
Collin L. Ferguson
Program 3
CSCI 280 	
2/1/23
Purpose: Create an object in Java with built in attributes.
*/

import java.lang.String;

class Student
{
	String studentName = "null";
	int enrollNumber = 0;
}


class Program3_CollinFerguson {

	public static void main(String[] args)
	{
		Student brianObject = new Student();
		brianObject.enrollNumber = 5;
		brianObject.studentName = "Brian";
		
		Student johnObject = new Student();
		johnObject.enrollNumber = 5;
		johnObject.studentName = "John";
		
		System.out.format("Student Name: %-10s Enroll Number: %3d\n",johnObject.studentName, johnObject.enrollNumber);
		System.out.format("Student Name: %-10s Enroll Number: %3d\n",brianObject.studentName, brianObject.enrollNumber);
		
	}

}
