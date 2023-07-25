/*
Collin L. Ferguson
Program 4
CSCI 280 	
2/8/23
Purpose: Create a class with a constructor and a method.
*/

class Holiday
{
	String name, month;
	int day;
	
	public Holiday(String holidayName, String holidayMonth, int holidayDay)
	{
		name = holidayName;
		month = holidayMonth;
		day = holidayDay;
	}
	
	
	public Holiday()
	{
		name = "";
		month = "";
		day = 0;
	}
	
	
	public static boolean inSameMonth(Holiday firstHoliday, Holiday secondHoliday) 
	{
		return firstHoliday.month.equals(secondHoliday.month);
	}
}


class Program4_CollinFerguson
{
	public static void main(String[] args)
	{
		Holiday firstHoliday = new Holiday("Independence Day", "July", 4);
		Holiday secondHoliday = new Holiday("Labor Day", "September", 5);
		
		System.out.format("First Holiday:  %-20s %9s, %d\n", firstHoliday.name, firstHoliday.month, firstHoliday.day);
		System.out.format("Second Holiday: %-20s %9s, %d\n", secondHoliday.name, secondHoliday.month, secondHoliday.day);
		System.out.format("inSameMonth:    %5b\n", Holiday.inSameMonth(firstHoliday, secondHoliday));	
	}
}
