/*
CREATE AN ARRAY OF 5 CsCourses AND PRINT EACH ELEMENT OF THE ARRAY. ASSIGN THE VALUES TO THE ARRAY IN THE PROGRAM. NO INPUT FROM USER_EXCEPTION
Collin L. Ferguson
2/16/2023
 */
 
class CsCourse
{
	private int courseId;
	private int courseCredit;
	private String courseInstructor;

	CsCourse()
	{
		courseId = 0;
		courseCredit = 0;
		courseInstructor = "John";
	}
	CsCourse(int courseId, int courseCredit, String courseInstructor)
	{
		this.courseId = courseId; 
		this.courseCredit = courseCredit; 
		this.courseInstructor = courseInstructor; 
	}
	
	//Getters
	public int getCourseId()
	{
		return courseId;
	}
	public int getCourseCredit()
	{
		return courseCredit;
	}
	public String getCourseInstructor()
	{
		return courseInstructor;
	}
	
	public void printDetails()
	{
		System.out.format("Course ID: %d\n", courseId);
		System.out.format("Course Credit: %d\n", courseCredit);
		System.out.format("Course Instructor: %s\n\n", courseInstructor);
	}
	//setters not needed for example.
	
}

public class ExtraCredit2_CollinFerguson
{
	public static void main(String[] args)
	{
		CsCourse[] array = new CsCourse[5];  //if "new" allocates space in memory for the array, why do I need another new in line 56...?
											//wouldn't that be like allocating memory for a pointer (in c) twice?
		array[0] = new CsCourse(280 ,3,"Collin Ferguson");  //Maybe it's allocating space for the pointer to an object, then the second new is for a pointer to the object?
		array[1] = new CsCourse(281 ,4,"Dollin Gerguson");
		array[2] = new CsCourse(282 ,3,"Eollin Herguson");
		array[3] = new CsCourse(283 ,2,"Follin Ierguson");
		array[4] = new CsCourse(284 ,3,"Gollin Jerguson");
		
		for(int i = 0; i<5;i++)
		{
			array[i].printDetails();
		}
	}
}
