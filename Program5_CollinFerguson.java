/*
Collin L. Ferguson
Program 5
CSCI 280 	
2/16/23
Purpose: Create a class with constructors, methods, getters, & setters.
*/


class Employee
{
	private int id;	
	private String firstName, secondName;
	private float monthlySalary;
	
	public Employee()
	{	
		id = 0;
		firstName = "null";
		secondName = "null";
		monthlySalary = 0;
	}
	public Employee(int id, String firstName, String secondName, float monthlySalary)
	{	
		this.id = id;
		this.firstName = firstName;
		this.secondName = secondName;
		this.monthlySalary = monthlySalary;
	}
	public int getId()
	{
		return id;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public String getSecondName()
	{
		return secondName;
	}
	public float getMonthlySalary()
	{
		return monthlySalary;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public void setSecondName(String secondName)
	{
		this.secondName= secondName;
	}
	public void setMonthlySalary(float monthlySalary)
	{
		this.monthlySalary = monthlySalary;
	}
	
	public String getFullName()
	{
		return firstName + " " + secondName;
	}
	public float calculateAnnualSalary()
	{
		return monthlySalary * 12;
	}
	public float increaseSalary(float percent)
	{
		float newSalary = this.getMonthlySalary() + (this.getMonthlySalary() * (percent/100));
		
		this.setMonthlySalary(newSalary);
		
		return newSalary;
	}
}


public class Program5_CollinFerguson {

	public static void main(String[] args)
	{
		//System.out.println("I am collin ferguson, you killed my father. time to die");
		
		Employee employee = new Employee(2345895, "Chase", "Cody", 12000);
		
		System.out.println("The employee details:");
		System.out.format("       ID: %7d\n", employee.getId());
		System.out.format("       First Name: %s\n", employee.getFirstName());
		System.out.format("       Last Name: %s\n", employee.getSecondName());
		printFormattedSalary("       Salary: ", employee.getMonthlySalary());
		System.out.println("-------------------------------------------------------------");
		System.out.println("Updating the employee details....");
		System.out.println("-------------------------------------------------------------");
		
		employee.setFirstName("John Cody");
		employee.setSecondName("Mark");
		employee.setId(2345899);
		employee.setMonthlySalary(14000);
		
		System.out.println("The new employee details:");
		System.out.format("       ID: %7d\n", employee.getId());
		System.out.format("       Full Name: %s \n", employee.getFullName());
		printFormattedSalary("       Salary: ", employee.getMonthlySalary());
		printFormattedSalary("       Annual Salary: ", employee.calculateAnnualSalary());
	
		System.out.println("-------------------------------------------------------------");
		System.out.println("Applying an increase of salary of 10%");
		System.out.println("-------------------------------------------------------------");
		
		employee.increaseSalary(10);
		
		System.out.println("The updated employee details after the salary increase:");
		System.out.format("       ID: %7d\n", employee.getId());
		System.out.format("       Full Name: %s \n", employee.getFullName());
		printFormattedSalary("       Salary: ", employee.getMonthlySalary());
		printFormattedSalary("       Annual Salary: ", employee.calculateAnnualSalary());
		
		
		
	}
	
	static void printFormattedSalary(String prefix, float salary) //used to get the formatting of the salary to perfectly match the requirements.
	{
		String formattedSalary = String.valueOf((int) salary);
		int strLen = formattedSalary.length(), offset = 0;
		
		for(int i = 1; i < strLen; i++) 
		{
			if((strLen-i)%3 == 0) 
			{
				formattedSalary = formattedSalary.substring(0,i+offset) + " " + formattedSalary.substring(i+offset);
				offset++;
			}
		}
		
		System.out.println(prefix+formattedSalary);
	}
	
	
}
