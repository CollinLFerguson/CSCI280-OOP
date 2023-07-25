/*
ADD 1 ELEMENT TO THE ARRAY FROM THE EXERCISE.
Collin L. Ferguson
2/16/2023
 */
/*
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
*/

public class ExtraCredit3_CollinFerguson
{
	public static void main(String[] args)
	{
		//STUCK WITH THIS!
		CsCourse[] array = {
				new CsCourse(100 ,3,"John Doe"),  //Maybe it's allocating space for the pointer to an object, then the second new is for a pointer to the object?
				new CsCourse(200 ,4,"Jane Doe"),
				new CsCourse(230 ,3,"Lukas M"),
				new CsCourse(260 ,2,"Hara McCan"),
				new CsCourse(300 ,3,"Kobi Allen")
				};
		//STUCK WITH THIS!
		
		CsCourse[] secondArray = new CsCourse[6];
		System.arraycopy(array, 0, secondArray, 0, 5);
		
		secondArray[5] = new CsCourse(400, 4, "collin Ferguson");
		
		
		for(int i = 0; i<6;i++)
		{
			secondArray[i].printDetails();
		}
	}
}
