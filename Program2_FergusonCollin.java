/*
Collin L. Ferguson
Program 2
CSCI 280 	
1/24/23
Purpose: positive number summation from 1 to user inputed value.
*/
import java.util.Scanner;


class Program2_FergusonCollin 
{
	public static void main(String[] args) 
	{
		Scanner getInput = new Scanner(System.in);
		int userValue = -1, finalValue = 0;
		
		while(userValue != 0)
		{
			while(true)
			{
				System.out.print("Enter a positive integer value or enter '0' to stop: ");
				userValue = getInput.nextInt();
				
				if(userValue>=0)
				{
					break;
				}
				
				System.out.println("Please only use positive values.");
			}
			finalValue += userValue;
		}		
		getInput.close();
		System.out.println(finalValue);
	}
}
