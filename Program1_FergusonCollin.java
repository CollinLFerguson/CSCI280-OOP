/*
Collin L. Ferguson
Program 1
CSCI 280 	
1/24/23
Purpose: Summation from user inputed value to 20 values afterwards.
*/

import java.util.Scanner;

class Program1_FergusonCollin
{
	public static void main(String[] args)
	{
		Scanner getInput = new Scanner(System.in);
		System.out.print("Enter an integer value: ");
		int userValue = getInput.nextInt(), summationEnd = userValue+20;
		
		for(int i = userValue+1; i <= summationEnd;i++)
		{
			userValue += i;
		}
		System.out.println(userValue);	
		getInput.close(); //Stops mem leak
	}	
}
