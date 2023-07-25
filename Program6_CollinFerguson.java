/*
Collin L.Ferguson
CSCI 280: OOP
3/22/2023
Purpose: Create and use subclasses and superclasses
*/

class Person
{
	private String name;
	private String address;
	Person(String name, String address)
	{
		this.name = name;
		this.address = address;
	}
	String getName()
	{
		return name;
	}
	String getAddress() 
	{
		return address;
	}
	void setName(String name)
	{
		this.name = name;
	}
	void setAddress(String address)
	{
		this.address = address;
	}
}


class Student extends Person
{
	int numCourses;
	String[] courses;
	int[] grades;
	
	Student(String name, String address)
	{
		super(name, address);	
		numCourses = 0;
		courses = new String[30];
		grades = new int[30];	
	}
	void addCourseGrade(String course, int grade)
	{
		courses[numCourses] = course;
		grades[numCourses] = grade;
		numCourses += 1;
	}
	void printGrades()
	{
		System.out.format("Student: %s\n-----------------------\n", this.getName());
		
		for(int i = 0; i < numCourses; i++)
		{
			System.out.format("%s: %d\n", courses[i], grades[i]);
		}
	}
	int getAverageGrade()
	{
		int total = 0;
		for(int i = 0; i < numCourses; i++)
		{
			total += grades[i];
		}
		return total / numCourses;
	}
	
}


class Teacher extends Person
{
	int numCourses;
	String[] courses;
	
	Teacher(String name, String address)
	{
		super(name, address);
		numCourses = 0;
		courses = new String[5];
	}

	Boolean addCourse(String courseName)
	{
		if(numCourses == 5) {return false;}
		
		for(int i = 0; i < numCourses; i++)
		{
			if(courses[i].equals(courseName))
			{
				return false;
			}
		}
		courses[numCourses] = courseName;
		numCourses++;
		return true;
	}
	
	Boolean removeCourse(String courseName)
	{
		for(int i = 0; i < numCourses; i++)
		{
			if(courses[i].equals(courseName))
			{
				int j = i;
				while( j < numCourses && courses[j] != null)
				{
					if(j+1 == numCourses)
					{
						courses[j] = null;
						j++;
					}
					else 
					{
						courses[j] = courses[j+1];
						j++;
					}
				}
				numCourses--;
				return true;
			}
		}
		return false;
	}
}


public class Program6_CollinFerguson {

	public static void main(String[] args) {
		Student student = new Student("Collin", "1234 College rd.");
		student.addCourseGrade("Intro to Programming", 100);
		student.addCourseGrade("Quantum Physics", 0);
		student.printGrades();
		System.out.println(student.getAverageGrade());

		Teacher teacher = new Teacher("Charles", "1234 Nice house");		
		String[] courses = {"CS101", "CS102","CS101"};
		for(int i = 0; i <= 2; i++)
		{
			if(teacher.addCourse(courses[i]))
			{
				System.out.format("Course %s was added.\n", courses[i]);
			}
			else
			{
				System.out.format("The course %s cannot be added.\n", courses[i]);
			}	
		}
		
		for(int i = 0; i <= 2; i++)
		{
			if(teacher.removeCourse(courses[i]))
			{
				System.out.format("Course %s was removed.\n", courses[i]);
			}
			else
			{
				System.out.format("The course %s cannot be removed.\n", courses[i]);
			}	
		}
	}
}
