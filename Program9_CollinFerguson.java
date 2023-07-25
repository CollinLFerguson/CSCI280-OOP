/*
Collin L. Ferguson
Assignment 9
4/18/2023 Start Time:10:41; End Time: 10:56
Purpose: USe the try/catch block
*/

import java.util.Scanner;

public class Program9_CollinFerguson {
	
	public static void main(String[] args) {
		System.out.println("You killed my father, time to die.");
		
		Scanner scanner = new Scanner(System.in);
		int number1, number2;
		
		while(true)
		{
			try
			{
				System.out.print("Enter the first integer value: ");
				number1 = Integer.parseInt(scanner.next());
				System.out.println();
				break;
			}
			catch(Exception e) 
			{
				System.out.println("Sorry, that wasn't a valid integer.");
			}
		}
		
		while(true)
		{
			try
			{
				System.out.print("Enter the second integer value: ");
				number2 = Integer.parseInt(scanner.next());
				System.out.println();
				break;
			}
			catch(Exception e) 
			{
				System.out.println("Sorry, that wasn't a valid integer.");
			}
		}
		System.out.print("Total: ");
		System.out.println(number1 + number2);	
	}
}
