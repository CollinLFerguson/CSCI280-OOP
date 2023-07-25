/*
Collin L. Ferguson
CSCI 280
Due 4/30/23
Purpose: Create a java program that can read/write to a file using user input.
4/25/23; Start time: 11:15; End time: 12:00;
*/


import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


public class Program10_CollinFerguson {

	public static void main(String[] args)
	{
		System.out.println("OutFile:");
		String temp = "";
		Scanner userInput = new Scanner(System.in);
		byte[] byteArray = new byte[50];
		OutputStream outFile = null;
		try 
		{
			outFile = new FileOutputStream("output.txt");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("The file could not be created.");
			System.exit(0);
		}	
		System.out.println("Enter the data. A max of 25 characters will be read:");
		temp = userInput.nextLine();
		while(temp.length()<=50)
		{
			System.out.println("Keep entering data. A max of 25 characters will be read:");
			temp += "\n";
			temp += userInput.nextLine();
		}
		
		userInput.close();
		
		byteArray = temp.getBytes();
		
		try 
		{
			for(int i = 0; i < 50; i++)
			{
				outFile.write(byteArray[i]);
	
			}
			
			outFile.close();
		}
		catch(IOException e)
		{
		System.out.println("The file could not be written to");
		System.exit(0);
		}	
		
		System.out.println("InFile:");
		
		InputStream inFile = null;
		try 
		{
			inFile = new FileInputStream("output.txt");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("The file could not be found");
			System.exit(0);
		}
		
		byteArray = new byte[50];
		
		try
		{
			inFile.read(byteArray);
			inFile.close();
		} 
		catch (IOException e)
		{
			System.out.println("The file could not be read to");
			System.exit(0);;
		}
		
		temp = new String(byteArray);
		System.out.println(temp);
		
		
	}

}
