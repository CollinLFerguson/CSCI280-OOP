/*
Collin L. Ferguson
CSCI 280
Due 5/5/23
Purpose: Create a java program that can read/write to a file.
4/30/23; Start time: 4:33p; End time:;
*/


import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.PrintWriter;


public class Project11_CollinFerguson {

	public static void main(String[] args)
	{
		System.out.println("Part 1:");
		String storage = "";
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader("input.txt"));
			
			while(input.ready())
			{
				storage += input.readLine() + "\n";
			}
			System.out.println(storage);
			input.close();
		}
		catch(IOException e)
		{
			System.out.println("The file could not be read");
		}
		
		//---1---//
		System.out.println("Part 2:");
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.print("Enter some text: ");
			System.out.println(input.readLine());
			input.close();
		} catch (IOException e) {
			System.out.println("The file could not be read");
		}
		
		//---2---//
		System.out.println("Part 3:");
		
		StringReader reader = new StringReader(storage);
		try {
			for(int x = 0; x< storage.length(); x++)
			{
				System.out.println((char) reader.read());
			}
			
			reader.reset();
		} catch (IOException e) {
			System.out.println("If you've seen this, I have no clue how it broke in this way.");
		}
		
		System.out.println(storage);
		
		//---3---//
		
		System.out.println("Part 4:");
		try 
		{
			PrintWriter printer = new PrintWriter("output.txt");
			
			printer.print(storage);
			printer.close();
			
			System.out.println("File successfully created");
		} 
		catch (FileNotFoundException e) 
		{
			
			System.out.println("File creation was unsuccessful");
		}
		
		
		
		
		
		
	}

}
