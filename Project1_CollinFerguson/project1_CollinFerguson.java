/*
Collin L. Ferguson
Project1
due: 3/10/2023
Purpose: Create a UND library management system.
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import LibraryData.DataStorage.*;

public class project1_CollinFerguson {
	
	
	static int checkoutBook(Book book, Subscriber subscriber)
	{
		// can the book be checked out? -- if not return -1
		if(book.getAvailableCopies() < 1)
		{
			return -1;
		}
		
		// can the subscriber get another copy of this book? -- if not return -1
		if(subscriber.getCurrentlyCheckedOut(book) >= 2)
		{
			return -1;
		}
		// reduce available copies
		book.decrementAvailableCopies();
		
		// increase the total times checked out.
		book.incrementTimesCheckedOut();
		
		BorrowerDataNode newNode = new BorrowerDataNode(book, subscriber);
		book.borrowerLL.addBorrower(newNode);
		subscriber.borrowerLL.addBorrower(newNode);
		
		return 0;
	}
	
	
	static int checkinBook(Book book, Subscriber subscriber)
	{
		//Find & delete borrow in the Book's BorrowerLL return -1 if it does not exist
		if(subscriber.deleteBorrow(book) == -1) {return -1;}
		
		//Find & delete borrow in the Sub's BorrowerLL return -1 if it does not exist
		if(book.deleteBorrow(subscriber)== -1) {return -1;}
		
		//increment book's availability
		book.incrementAvailableCopies();
		
		return 0;
	}
	
	
	static int auditAllSubscribers(SubscriberHead subscriberHead, long auditDistance)
	{
		if(subscriberHead.next == null) {return -1;}
		SubscriberLinkedList currentNode = subscriberHead.next;
		BorrowerHead tempHeadStorage = null;
		boolean foundSubs = false;
		
		while (currentNode != null)
		{
			
			tempHeadStorage = currentNode.getData().borrowerLL.auditList(auditDistance);
			
			if(tempHeadStorage.next != null)
			{
				foundSubs = true;
			}
			tempHeadStorage.printList();
			
			currentNode = currentNode.next;
		}
		if(foundSubs){return 0;}
		return -1;
	}
	
	
	static void writeBook(String fileName, BookHead bookHead)
	{
		try
		{
			File file = new File(fileName);
			file.createNewFile(); 
		}
		catch(IOException e)
		{
			System.out.println("There was an issue creating the file for saving. Changes will not be saved.");
		}
		
		try
		{
			FileWriter file = new FileWriter(fileName);
			BookLinkedList currentNode = bookHead.next;
			while(currentNode != null)
			{
				file.write(currentNode.getData().getIsbn() + "\n");
				file.write(currentNode.getData().getTitle()+ "\n");
				file.write(currentNode.getData().getAuthor()+ "\n");
				file.write(Integer.toString(currentNode.getData().getTotalCopies())+ "\n");
				file.write(Integer.toString(currentNode.getData().getAvailableCopies())+ "\n");
				file.write(Integer.toString(currentNode.getData().getTimesCheckedOut())+ "\n");
				currentNode = currentNode.next;
			}
			file.close();			
		}
		catch(IOException e) 
		{
			System.out.println("There was an issue saving the file for saving. Changes will not be saved.");
		}		
	}
	
	
	static void writeSubscriber(String fileName, SubscriberHead subscriberHead)
	{
		try
		{
			File file = new File(fileName);
			file.createNewFile(); 
		}
		catch(IOException e)
		{
			System.out.println("There was an issue creating the file for saving. Changes will not be saved.");
		}
		
		try
		{
			FileWriter file = new FileWriter(fileName);
			SubscriberLinkedList currentNode = subscriberHead.next;
			while(currentNode != null)
			{
				file.write(currentNode.getData().getId() + "\n");
				file.write(currentNode.getData().getFullName()+ "\n");
				file.write(currentNode.getData().getUserType()+ "\n");
				currentNode = currentNode.next;
			}
			file.close();			
		}
		catch(IOException e) 
		{
			System.out.println("There was an issue saving the file for saving. Changes will not be saved.");
		}
	}
	
	
	static void writeBorrows(String fileName, SubscriberHead subscriberHead)
	{ 
		try
		{
			File file = new File(fileName);
			file.createNewFile(); 
		}
		catch(IOException e)
		{
			System.out.println("There was an issue creating the file for saving. Changes will not be saved.");
		}
		
		try
		{
			FileWriter file = new FileWriter(fileName);
			SubscriberLinkedList currentNode = subscriberHead.next;
			BorrowerLinkedList currentBorrow;
			
			while(currentNode != null)
			{
				currentBorrow = currentNode.getData().borrowerLL.next;
				while(currentBorrow != null)
				{
					file.write(currentBorrow.getData().getSubscriberData().getId() + "\n");
					file.write(currentBorrow.getData().getBookData().getIsbn() + "\n");
					file.write(Long.toString(currentBorrow.getData().getBorrowingDate()) + "\n");
					currentBorrow = currentBorrow.next;
				}
				currentNode = currentNode.next;
			}
			
			file.close();			
		}
		catch(IOException e) 
		{
			System.out.println("There was an issue saving the file for saving. Changes will not be saved.");
		}
	}
	
	
	static void readBooks(String fileName, BookHead bookHead)
	{
		try 
		{
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			
			while (scanner.hasNextLine()) 
			{
				Book book = new Book();
				book.setIsbn(scanner.nextLine());
				book.setTitle(scanner.nextLine());
				book.setAuthor(scanner.nextLine());
				book.setTotalCopies(Integer.parseInt(scanner.nextLine()));
				book.setAvailableCopies(Integer.parseInt(scanner.nextLine()));
				book.setTimesCheckedOut(Integer.parseInt(scanner.nextLine()));				
			
				bookHead.addBook(book);
			}
			scanner.close();
		}
		catch(FileNotFoundException e) 
		{
			System.out.println("The Books file could not be found. All previous data, if any, was not loaded.");
			System.out.println("If there are no books on file, disreguard this message.");
			return;
		}
	}
	
	
	static void readSubscribers(String fileName, SubscriberHead subscriberHead)
	{
		try 
		{
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			
			while (scanner.hasNextLine()) 
			{
				Subscriber subscriber = new Subscriber();
				subscriber.setId(scanner.nextLine());
				subscriber.setFullName(scanner.nextLine());
				subscriber.setUserType(scanner.nextLine());				
			
				subscriberHead.addSubscriber(subscriber);
			}
			scanner.close();
		}
		catch(FileNotFoundException e) 
		{
			System.out.println("The Subscriber file could not be found. All previous data, if any, was not loaded.");
			System.out.println("If there are no subscribers on file, disreguard this message.");
			return;
		}	
	}

	
	static void readBorrows(String fileName, SubscriberHead subscriberHead, BookHead bookHead)
	{ //TODO: No time, but this could be changed to run faster by not searching the list every time.
		try 
		{
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			
			while (scanner.hasNextLine()) 
			{
				BorrowerDataNode borrow = new BorrowerDataNode();
				
				Subscriber subscriber = subscriberHead.findSubscriberByString(scanner.nextLine()).next.getData();
				Book book = bookHead.findBookByString(scanner.nextLine()).next.getData();
				
				borrow.setSubscriberData(subscriber);
				borrow.setBookData(book);
				borrow.setDate(Long.parseLong(scanner.nextLine()));
				
				subscriber.borrowerLL.addBorrower(borrow);
				book.borrowerLL.addBorrower(borrow);
				
				
			}
			scanner.close();
		}
		catch(FileNotFoundException e) 
		{
			System.out.println("The borrower file could not be found. All previous data, if any, was not loaded.");
			System.out.println("If there are no borrows on file, disreguard this message.");
			return;
		}	
	}
	

	static BookLinkedList menuGetBook(Scanner scanner, BookHead bookHead)
	{
		boolean validInput = false;
		BookLinkedList currentBookNode = null;
		BookHead foundBooks = null;
		int counter = 0;
		String userInput = "null";
		while(!validInput) 
		{
			
			System.out.print("Enter the ISBN, Title, or Author of the book you are looking for\nor enter \"cancel\" to exit: ");
			userInput = scanner.nextLine();
			System.out.println("");
			
			if(userInput.equals("cancel")) {return null;}
			
			foundBooks = bookHead.findBookByString(userInput);
			if(foundBooks.next==null)
			{
				System.out.println("No book matched that description");
				
			}
			else 
			{
				currentBookNode = foundBooks.next;
				counter = 0;
				while(currentBookNode != null)
				{
					System.out.format("%d.", counter+1);
					currentBookNode.getData().printBook();
					currentBookNode = currentBookNode.next;
					counter++;
				}
				validInput = true;
			}
		}
		validInput = false;
		while(!validInput)
		{
			System.out.print("Enter the number next to the book or enter \"cancel\" to exit: ");
			userInput = scanner.nextLine();
			System.out.println("");
			if(userInput.equals("cancel")) {return null;}
			
			try
			{
				if(Integer.parseInt(userInput) <= counter && Integer.parseInt(userInput) > 0)
				{
					validInput = true;
					counter = Integer.parseInt(userInput);
				}
				else
				{
					System.out.println("Sorry, that wasn't a valid input");
				}
			}
			catch(Exception e)
			{
				System.out.println("Sorry, that wasn't a valid input");
			}
		}		
		currentBookNode = foundBooks.next;
		for(int i = 1; i < counter; i++)
		{
			currentBookNode = currentBookNode.next;
		}
		return currentBookNode;
	}
	
	
	static SubscriberLinkedList menuGetSubscriber(Scanner scanner, SubscriberHead subscriberHead)
	{
		boolean validInput = false;
		SubscriberHead foundSubscriber = null;
		SubscriberLinkedList currentSubscriberNode = null;
		
		while(!validInput) 
		{
			System.out.println("Enter the ID of the subscriber you are searching for or cancel to exit");
			String userInput = scanner.nextLine();
			System.out.println("");
			if(userInput.equals("cancel")) {return null;}
			foundSubscriber =  subscriberHead.findSubscriberByString(userInput);
			if(foundSubscriber.next == null){System.out.println("There is no subscriber with that ID.");}
			else {validInput = true;}			
		}
		
		currentSubscriberNode = foundSubscriber.next;
		
		currentSubscriberNode.getData().printSubscriber();
		return currentSubscriberNode;
		
	}
	
	
	static void menuCheckout(Scanner scanner, BookHead bookHead, SubscriberHead subscriberHead)
	{
		boolean validInput = false;
		String userInput = "null";
		BookLinkedList currentBookNode;
		SubscriberLinkedList currentSubscriberNode;
		if((currentBookNode = menuGetBook(scanner, bookHead)) == null) {return;}
		if((currentSubscriberNode = menuGetSubscriber(scanner, subscriberHead)) == null) {return;}
		

		validInput = false;
		while(!validInput) 
		{
			System.out.println("which operation would you like to perform:");
			System.out.println("1. Check-out");
			System.out.println("2. Check-in");
			System.out.println("or enter \"cancel\" to exit");
			userInput = scanner.nextLine();
			System.out.println("");
			
			if(userInput.equals("cancel"))
			{
				return;
			}
			else if(userInput.equals("1"))
			{
				validInput = true;
				if(checkoutBook(currentBookNode.getData(), currentSubscriberNode.getData()) == 0)
				{
					System.out.println("The book was successfully borrowed.");
				}
				else 
				{
					System.out.println("This subscriber cannot check out this book.");
				}	
			}
			else if(userInput.equals("2"))
			{
				validInput = true;
				if(checkinBook(currentBookNode.getData(), currentSubscriberNode.getData()) == 0)
				{
					System.out.println("The book was successfully returned.");

				}
				else
				{
					System.out.println("That subscriber does not have this book checked out.");
				}
			}
			else 
			{
				System.out.println("The was not a valid input.");
			}	
		}	
	}	
		
	
	static void menuBookOperations(Scanner scanner, BookHead bookHead)
	{
		BookLinkedList bookNode;
		String userInput = "null";
		boolean validInput = false;
		
		System.out.println("Which operation would you like to perform?");
		System.out.println("1.Add a new book.");
		System.out.println("2.Update an existing book.");
		System.out.println("3.Delete an existing book.");
		System.out.println("4.Get subscribers holding book");
		System.out.println("5.Find a book.");
		System.out.println("6.Get most popular books.");
		System.out.println("7.Print all books.");
		
		userInput = scanner.nextLine();
		System.out.println("");
		
		if(userInput.equals("1"))
		{
			
			String isbn="", title="", author="";
			int totalCopies = 0;
			
			
			
			while(!validInput)
			{
				validInput = false;
				while(!validInput)
				{
					System.out.print("Enter the ISBN or \"cancel\" to exit:");
					userInput = scanner.nextLine();
					if(userInput.equals("cancel")) {return;}
					
					if(!userInput.equals("") && bookHead.findBookByString(userInput).next!=null)
					{
						System.out.println("Sorry, another book in the database already has that isbn.\nIf you are trying to add copies to a book, please select the \"update\" function");
					}
					else 
					{
						validInput = true;
						isbn = userInput;
					}
				}
				
				validInput = false;
				while(!validInput)
				{
					System.out.print("Enter the title of the book or \"cancel\" to exit:");
					userInput = scanner.nextLine();
					System.out.print("");
					if(userInput.equals("cancel")) {return;}
					if(!userInput.equals(""))
					{
						validInput = true;
						title = userInput;
					}
				}
				
				validInput = false;
				while(!validInput)
				{
					System.out.print("Enter the author of the book or \"cancel\" to exit:");
					userInput = scanner.nextLine();
					System.out.print("");
					if(userInput.equals("cancel")) {return;}
					if(!userInput.equals(""))
					{
						validInput = true;
						author = userInput;
					}
				}
				validInput = false;
				while(!validInput)
				{
					System.out.print("Enter the total copies of the book or \"cancel\" to exit:");
					userInput = scanner.nextLine();
					System.out.print("");
					if(userInput.equals("cancel")) {return;}
					try
					{
						totalCopies = Integer.parseInt(userInput);
						validInput = true;
						
					}
					catch(Exception e)
					{
						System.out.println("Please only enter a number with no other characters.");
					}
				}
				validInput = false;
				while(!validInput)
				{
					System.out.println("Is this info correct?");
					System.out.format("ISBN: %s\n", isbn);
					System.out.format("Title: %s\n", title);
					System.out.format("Author: %s\n", author);
					System.out.format("Total copies: %d\n", totalCopies);
					System.out.print("(y/n/cancel): ");
					userInput = scanner.nextLine();
					System.out.println("");
					if(userInput.equals("cancel")) {return;}
					if(userInput.equals("y"))
					{
						bookHead.addBook(isbn, title, author, totalCopies);
						//userInput = scanner.nextLine();
						validInput = true;
					}
					else if(userInput.equals("n"))
					{
						break;
					}
					else
					{
						System.out.println("Sorry, that wasn't a valid input");
					}
				}				
			}	

		}
		else if(userInput.equals("2"))
		{
			validInput = false;
			while(!validInput)
			{
				bookNode = menuGetBook(scanner, bookHead);
				if(bookNode == null) {return;}
				//bookNode.getData().printBook();
				System.out.println("What would you like to change?");
				System.out.format("1.ISBN: %s\n", bookNode.getData().getIsbn());
				System.out.format("2.Title: %s\n", bookNode.getData().getTitle());
				System.out.format("3.Author: %s\n", bookNode.getData().getAuthor());
				System.out.format("4.Total copies: %d\n", bookNode.getData().getTotalCopies());		
				System.out.println("Or enter \"cancel\" to exit.");
				userInput = scanner.nextLine();
				if(userInput.equals("cancel")) {return;}
				if(userInput.equals("1"))
				{
						validInput = false;
						while(!validInput)
						{
							System.out.print("Enter the ISBN or \"cancel\" to exit:");
							userInput = scanner.nextLine();
							if(userInput.equals("cancel")) {return;}
							
							if(!userInput.equals("") && bookHead.findBookByString(userInput).next!=null)
							{
								System.out.println("Sorry, another book in the database already has that isbn.\nIf you are trying to add copies to a book, please select the \"update\" function");
							}
							else 
							{
								validInput = true;
								bookNode.getData().setIsbn(userInput);
							}
						}
				}
				else if(userInput.equals("2"))
				{
					validInput = false;
					while(!validInput)
					{
						System.out.print("Enter the title of the book or \"cancel\" to exit:");
						userInput = scanner.nextLine();
						System.out.print("");
						if(userInput.equals("cancel")) {return;}
						if(!userInput.equals(""))
						{
							validInput = true;
							bookNode.getData().setTitle(userInput);
						}
					}
				}
				else if(userInput.equals("3"))
				{
					validInput = false;
					while(!validInput)
					{
						System.out.print("Enter the author of the book or \"cancel\" to exit:");
						userInput = scanner.nextLine();
						System.out.print("");
						if(userInput.equals("cancel")) {return;}
						if(!userInput.equals(""))
						{
							validInput = true;
							bookNode.getData().setAuthor(userInput);
						}
					}
				}		
				else if(userInput.equals("4"))
				{
					int tempStorage = 0;
					validInput = false;
					while(!validInput)
					{
						
						System.out.print("Enter the total copies of the book or \"cancel\" to exit:");
						userInput = scanner.nextLine();
						System.out.print("");
						if(userInput.equals("cancel")) {return;}
						try
						{
							tempStorage = Integer.parseInt(userInput);
							
							if(bookNode.getData().getAvailableCopies()!= bookNode.getData().getTotalCopies() && tempStorage < bookNode.getData().getAvailableCopies())
							{
								System.out.println("This number is less than the currently available stock.");
							}
							else 
							{
								bookNode.getData().setAvailableCopies(bookNode.getData().getAvailableCopies() + (tempStorage-bookNode.getData().getTotalCopies()));
								bookNode.getData().setTotalCopies(tempStorage);
								validInput = true;
							}										
						}
						catch(Exception e)
						{
							System.out.println("Please only enter a number with no other characters.");
						}
					}
				}				
			}
		}
		
		else if(userInput.equals("3"))
		{
			bookNode = menuGetBook(scanner, bookHead); //Inefficient, but left as is for time.
			if(bookNode == null) {return;}			
			
			if(bookHead.removeBook(bookNode.getData().getIsbn()) == 0)
			{
				System.out.println("The book was successfully removed.");
				return;
			}
			else 
			{
				System.out.println("The book was not removed as it still has at least one copy checked out.");
				return;
			}
		}
		
		else if(userInput.equals("4"))
		{
			bookNode = menuGetBook(scanner, bookHead);
			if(bookNode == null) {return;}
			bookNode.getData().borrowerLL.printList();
			
		}	
		
		else if(userInput.equals("5"))
		{
			bookNode = menuGetBook(scanner, bookHead);
			if(bookNode == null) {return;}
			bookNode.getData().printBook();
		}
		
		else if(userInput.equals("6"))
		{
			Book topTen[] = new Book[10];
			BookLinkedList currentNode = bookHead.next;
			while(currentNode!=null)
			{
				for(int i = 0; i<10;i++)
				{
					if(topTen[i] == null)
					{
						topTen[i] = currentNode.getData();
						break;
					}
					else if(currentNode.getData().getTimesCheckedOut() > topTen[i].getTimesCheckedOut())
					{
						Book temp = topTen[i];
						
						topTen[i] = currentNode.getData();
						for(int j = i + 1; j < 10; j++)
						{
							Book shift = topTen[j];
							topTen[j] = temp;
							temp = shift;							
						}
						break;
					}
					
				}
				currentNode = currentNode.next;
			}
			for(int i = 0; i < 10; i++)
			{
				if(topTen[i]!=null) {topTen[i].printBook();}
			}
			
		}
		else if(userInput.equals("7"))
		{
			bookHead.printList();
		}
		else 
		{
			System.out.println("Sorry, that wasn't a valid input.");
			return;
		}
	}
	
	
	static void menuSubscriberOperations(Scanner scanner, SubscriberHead subscriberHead)
	{
		boolean validInput = false;
		
		
		System.out.println("Which operation would you like to perform?");
		System.out.println("1. Add Subscriber");
		System.out.println("2. Update Subscriber");
		System.out.println("3. Delete Subscriber");
		System.out.println("4. Print Borrows of a Subscriber");
		System.out.println("5. Print all subscribers");
		System.out.println("Or enter \"cancel\" to exit.");
		String userInput = scanner.nextLine();		
		System.out.println("");
		
		if(userInput.equals("cancel")){return;}
		if(userInput.equals("1"))
		{
			String id = "";
			String fullName = "";
			String subscriberType = "";
			
			while(!validInput)
			{
				while(!validInput)
				{
					System.out.print("Enter the subscriber's ID or enter cancel to exit:");
					userInput = scanner.nextLine();
					System.out.println("");
					if(userInput.equals("cancel")){return;}
					if(userInput.equals("")) 
					{
						System.out.println("Please enter a value");
					}
					else if(subscriberHead.findSubscriberByString(userInput).next != null)
					{
						System.out.println("Sorry, that ID is already in use.");
					}
					else
					{
						id = userInput;
						validInput = true;
					}
				}
				validInput = false;
				while(!validInput)
				{
					System.out.print("Enter the subscriber's full name or enter cancel to exit:");
					userInput = scanner.nextLine();
					System.out.println("");
					if(userInput.equals("cancel")){return;}
					if(userInput.equals("")) 
					{
						System.out.println("Please enter a value");
					}
					else
					{
						fullName = userInput;
						validInput = true;
					}
				}
				validInput = false;
				while(!validInput)
				{
					System.out.print("Enter the subscriber's type or enter cancel to exit:");
					userInput = scanner.nextLine();
					System.out.println("");
					if(userInput.equals("cancel")){return;}
					if(userInput.equals("")) 
					{
						System.out.println("Please enter a value");
					}
					else
					{
						subscriberType = userInput;
						validInput = true;
					}
				}
				validInput = false;
				while(!validInput)
				{
					System.out.println("Is this info correct?");
					System.out.format("ID: %s\n", id);
					System.out.format("Full Name: %s\n", fullName);
					System.out.format("User Type: %s\n", subscriberType);
					System.out.print("(y/n/cancel):");
					userInput = scanner.nextLine();
					System.out.println("");
					
					if(userInput.equals("cancel")){return;}
					else if(userInput.equals("y"))
					{
						subscriberHead.addSubscriber(id, fullName, subscriberType);
						validInput = true;
					}
					else if(userInput.equals("n"))
					{
						break;
					}	
					else
					{
						System.out.println("Sorry, that wasn't a valid input.");
					}
				}	
			}
		}
		else if(userInput.equals("2"))
		{
			validInput = false;
			while(!validInput)
			{
				
				SubscriberLinkedList subscriber = menuGetSubscriber(scanner, subscriberHead);
				if(subscriber == null){return;}
				
				System.out.println("What would you like to change");
				System.out.format("1. ID: %s\n", subscriber.getData().getId());
				System.out.format("2. Full Name: %s\n", subscriber.getData().getFullName());
				System.out.format("3. User Type: %s\n", subscriber.getData().getUserType());
				System.out.println("or enter \"cancel\" to exit.");
				userInput = scanner.nextLine();
				System.out.println("");
				
				if(userInput.equals("cancel")){return;}
				if(userInput.equals("")) 
				{
					System.out.println("Please enter a value");
				}
				else if(userInput.equals("1"))
				{
					validInput = false;
					while(!validInput)
					{
						System.out.print("Enter the subscriber's ID or enter cancel to exit:");
						userInput = scanner.nextLine();
						System.out.println("");
						if(userInput.equals("cancel")){return;}
						if(userInput.equals("")) 
						{
							System.out.println("Please enter a value");
						}
						else if(subscriberHead.findSubscriberByString(userInput).next != null)
						{
							System.out.println("Sorry, that ID is already in use.");
						}
						else
						{
							subscriber.getData().setId(userInput);
							validInput = true;
						}
					}
				}
				else if(userInput.equals("2"))
				{
					validInput = false;
					while(!validInput)
					{
						System.out.print("Enter the subscriber's full name or enter cancel to exit:");
						userInput = scanner.nextLine();
						System.out.println("");
						if(userInput.equals("cancel")){return;}
						if(userInput.equals("")) 
						{
							System.out.println("Please enter a value");
						}
						else
						{
							subscriber.getData().setFullName(userInput);
							validInput = true;
						}
					}
				}
				else if(userInput.equals("3"))
				{
					validInput = false;
					while(!validInput)
					{
						System.out.print("Enter the subscriber's type or enter cancel to exit:");
						userInput = scanner.nextLine();
						System.out.println("");
						if(userInput.equals("cancel")){return;}
						if(userInput.equals("")) 
						{
							System.out.println("Please enter a value");
						}
						else
						{
							subscriber.getData().setUserType(userInput);
							validInput = true;
						}
					}
				}
				else
				{
					System.out.println("Sorry, that wasn't a valid input.");
				}
			}
		}
		else if(userInput.equals("3"))
		{
			SubscriberLinkedList subscriberNode = menuGetSubscriber(scanner, subscriberHead);
			if(subscriberNode == null) {return;}
			if(subscriberHead.removeSubscriber(subscriberNode.getData().getId()) == 0)
			{
				System.out.println("The subscriber was successfully removed.");
				return;
			}
			else
			{
				System.out.println("The subscriber was not removed as they still has at least one book checked out.");
				return;
			}
		}
		
		else if(userInput.equals("4"))
		{
			SubscriberLinkedList subscriberNode = menuGetSubscriber(scanner, subscriberHead);
			if(subscriberNode == null) {return;}
			
			subscriberNode.getData().borrowerLL.printList();			
		}
		
		else if(userInput.equals("5"))
		{
			subscriberHead.printList();
		}
		else 
		{
			System.out.println("Sorry, that wasn't a valid input");
		}		
	}
	
	
	static void menuAuditOperations(Scanner scanner, SubscriberHead subscriberHead)
	{
		String userInput = "null";
		boolean validInput = false;
		int auditSuccessful = 1;
		while(!validInput)
		{
			System.out.print("Enter the audit distance (in days) or enter \"cancel\" to exit:");
			userInput = scanner.nextLine();
			System.out.println();
			
			if(userInput.equals("cancel")){return;}
			
			try
			{
				auditSuccessful = auditAllSubscribers(subscriberHead, (Long.parseLong(userInput) * 86400000));
				validInput = true;
				
				if( auditSuccessful == -1)
				{
					System.out.println("There are no outstanding borrows.");
				}
				
			}
			catch(Exception e)
			{
				System.out.println("Sorry, that wasn't a valid input.");
			}
		}		
	}	
	
	
	public static void main(String[] args) 
	{
		
		BookHead bookHead = new BookHead();
		readBooks("books.txt", bookHead);
		
		SubscriberHead subscriberHead = new SubscriberHead();
		readSubscribers("Subscriber.txt", subscriberHead);
		
		readBorrows("Borrows.txt", subscriberHead, bookHead);		
		
		Scanner scanner = new Scanner(System.in);
		/*
		///FOR TESTING///
		bookHead.printList();
		subscriberHead.printList();
		auditAllSubscribers(subscriberHead, -1);
		System.out.println("--------------------------");
		///FOR TESTING///
		*/		
		
		String userInput = "null";
		boolean exitLoop = false;
		while(!exitLoop)
		{
			System.out.println("\nWhich operation would you like to perform:");
			System.out.println("1. Check Out/In Book");
			System.out.println("2. Book Operations");
			System.out.println("3. Subscriber Operations");
			System.out.println("4. Audit All Users");
			System.out.println("5. Exit");
			
			userInput = scanner.nextLine();
			System.out.println("");
			if (userInput.equals("1")) 
			{
				menuCheckout(scanner, bookHead, subscriberHead);
				
				System.out.println("");
				
				writeBook("books.txt", bookHead);
				writeBorrows("Borrows.txt", subscriberHead);	
			}
			else if(userInput.equals("2"))
			{
				menuBookOperations(scanner, bookHead);
				//scanner.nextLine();
				writeBook("books.txt", bookHead);
				writeBorrows("Borrows.txt", subscriberHead);
			}
			else if (userInput.equals("3"))
			{
				menuSubscriberOperations(scanner, subscriberHead);
				writeSubscriber("Subscriber.txt", subscriberHead);
				writeBorrows("Borrows.txt", subscriberHead);
			}
			else if (userInput.equals("4"))
			{
				menuAuditOperations(scanner, subscriberHead);
			}
			else if (userInput.equals("5"))
			{
				exitLoop = true;
				break;
			}
			
			else 
			{
				System.out.println("Sorry, that wasn't a valid input.");
			}
			
		}
		
		scanner.close();
		writeBook("books.txt", bookHead);
		writeSubscriber("Subscriber.txt", subscriberHead);
		writeBorrows("Borrows.txt", subscriberHead);	
	}
}