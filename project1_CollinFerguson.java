/*
Collin L. Ferguson
Project1
due: 3/10/2023
Purpose: Create a UND library management system.
*/

import java.util.Date;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
//import Borrower.*;

//TODO: Separate classes into files so the project isn't 1000+ lines
class BorrowerHead
{
	BorrowerLinkedList next = null;
	
	void addBorrower(BorrowerDataNode node)
	{
		if(next == null)
		{
			next = new BorrowerLinkedList(node);
			return;
		}
		
		BorrowerLinkedList currentNode = next;
		while(currentNode.next != null)
		{
			currentNode = currentNode.next;
		}
		currentNode.next = new BorrowerLinkedList(node);

	}
	
	int deleteBorrow(Book book, Subscriber subscriber)
	//Searches for the transaction with the matching book/sub combo.
	//return 0 on success -1 on failure
	{
		if(next == null)
		{
			return -1;
		}
		if(next.getData().getBookData() == book && next.getData().getSubscriberData() == subscriber)
		{
			next = next.next;
			return 0;
		}
		
		BorrowerLinkedList currentNode = next;
		
		while(currentNode.next!=null)
		{
			if(currentNode.next.getData().getBookData() == book && currentNode.next.getData().getSubscriberData() == subscriber)
			{
				currentNode.next = currentNode.next.next;
				return 0;
			}
		}
		return -1;
		
		
		//return next.deleteBorrow(book, subscriber);	
	}		
	BorrowerHead auditList(long auditDistance)
	{
		if(next == null) {return new BorrowerHead();}
		
		Long currentDate = new Date().getTime();
		BorrowerHead pastDueBorrows = new BorrowerHead();
		BorrowerLinkedList currentNode = next;
		
		while(currentNode != null)
		{
			if(currentDate - currentNode.getData().getBorrowingDate() > auditDistance)
			{
				pastDueBorrows.addBorrower(currentNode.getData());
			}
			currentNode = currentNode.next; 
		}	
		return pastDueBorrows;
	}
	
	void printList()
	{
		BorrowerLinkedList currentNode = next;
		
		while(currentNode != null)
		{
			currentNode.getData().printBorrowInfo();
			currentNode = currentNode.next;
		}
	}
}


class BorrowerLinkedList
{
	BorrowerLinkedList next;
	private BorrowerDataNode data;
	
	BorrowerLinkedList(BorrowerDataNode data)
	{
		this.data = data;
	}
	
	BorrowerDataNode getData()
	{
		return data;
	}
}


class BorrowerDataNode //made it's own class so that one object can be associated with both book and sub objects. 
{
	private Book bookData;
	private Subscriber subscriberData;
	private long borrowingDate;	
	
	BorrowerDataNode(Book bookData, Subscriber subscriberData)
	{
		this.bookData = bookData;
		this.subscriberData = subscriberData;
		this.borrowingDate = new Date().getTime();
	}
	BorrowerDataNode(Book bookData, Subscriber subscriberData, long date)
	{
		this.bookData = bookData;
		this.subscriberData = subscriberData;
		this.borrowingDate = date;
	}
	BorrowerDataNode()
	{
		bookData = null;
		subscriberData = null;
		borrowingDate = 0;
	}
	
	Book getBookData() {return bookData;}
	Subscriber getSubscriberData() {return subscriberData;}
	long getBorrowingDate() {return borrowingDate;}
	
	void setBookData(Book bookData) {this.bookData = bookData;}
	void setSubscriberData(Subscriber subscriberData) {this.subscriberData = subscriberData;}
	void setDate(long borrowingDate) {this.borrowingDate = borrowingDate;}
	
	void printBorrowInfo()
	{
		System.out.format("Book: %-40s | Subscriber: %s, ID: %s\n", (bookData.getTitle() + " by " + bookData.getAuthor()), subscriberData.getFullName(), subscriberData.getId());
	}
}



class SubscriberHead
{
	SubscriberLinkedList next = null;
	
	void addSubscriber(String id, String fullName, String userType)
	{
		if (next == null)
		{
			next = new SubscriberLinkedList(new Subscriber(id, fullName, userType));
			return;
		}
		
		SubscriberLinkedList lastNode = next;
		while(lastNode.next!=null)
		{
			lastNode = lastNode.next;
		}
		
		lastNode.next = new SubscriberLinkedList(new Subscriber(id, fullName, userType));
	}
	
	void addSubscriber(Subscriber subscriber)
	{
		if (next == null)
		{
			next = new SubscriberLinkedList(subscriber);
			return;
		}
		
		SubscriberLinkedList lastNode = next;
		while(lastNode.next!=null)
		{
			lastNode = lastNode.next;
		}
		
		lastNode.next = new SubscriberLinkedList(subscriber);
	}
	
	int removeSubscriber(String id)
	{
		if(next==null)
		{
			return -1;
		}
		if(id.equals(next.getData().getId()))
		{
			if(next.getData().borrowerLL.next == null)
			{
				next = next.next;
				return 0;
			}
			return -1;
		}
		
		SubscriberLinkedList currentNode = next.next;
		while(currentNode.next != null)
		{
			if(id.equals(currentNode.next.getData().getId()))
			{
				if(currentNode.next.getData().borrowerLL.next == null) 
				{
					currentNode.next = currentNode.next.next;
					return 0;
				}
				else{return -1;}
			}
			currentNode = currentNode.next;
		}
		return -1;
	}
	
	SubscriberHead findSubscriberByString(String subscriberString)
	{
		SubscriberHead subscriberHead = new SubscriberHead();
		
		SubscriberLinkedList currentNode = next;
		while(currentNode != null)
		{ 
			if(currentNode.getData().getId().equals(subscriberString))
			{
				subscriberHead.addSubscriber(currentNode.getData());
			}
			currentNode = currentNode.next;	
		}	
		return subscriberHead;
	}
	
	
	void printList()
	{
		SubscriberLinkedList currentNode = next;
		
		while(currentNode != null)
		{
			currentNode.getData().printSubscriber();
			currentNode = currentNode.next;
		}
	}
}


class SubscriberLinkedList
{
	SubscriberLinkedList next;
	private Subscriber data;
	
	SubscriberLinkedList(Subscriber data)
	{
		this.data = data;
	}
	
	Subscriber getData()
	{
		return data;
	}
	void setData(Subscriber data)
	{
		this.data = data;
	}
}

class BookHead
{
	BookLinkedList next = null;
	
	void addBook(String isbn, String title, String author, int totalCopies)
	{
		if (next == null)
		{
			next = new BookLinkedList(new Book(isbn, title, author, totalCopies));
			return;
		}
		
		BookLinkedList lastNode = next;
		
		while(lastNode.next != null)
		{
			lastNode = lastNode.next;
		}
		
		lastNode.next = new BookLinkedList(new Book(isbn, title, author, totalCopies));		
	}
	
	void addBook(Book book)
	{
		if (next == null)
		{
			next = new BookLinkedList(book);
			return;
		}
		BookLinkedList lastNode = next;
		
		while(lastNode.next != null)
		{
			lastNode = lastNode.next;
		}
		
		lastNode.next = new BookLinkedList(book);
	}
	
	int removeBook(String isbn)
	{
		if(next==null)
		{
			return -1;
		}
		if(isbn.equals(next.getData().getIsbn()))
		{
			if(next.getData().borrowerLL.next == null)
			{
				next = next.next;
				return 0;
			}
			return -1;
		}
		
		BookLinkedList currentNode = next.next;
		while(currentNode.next != null)
		{
			if(isbn.equals(currentNode.next.getData().getIsbn()))
			{
				if(currentNode.next.getData().borrowerLL.next == null) 
				{
					currentNode.next = currentNode.next.next;
					return 0;
				}
				else 
				{
					return -1;
				}
			}
			currentNode = currentNode.next;
		}
		return -1;
	}
	
	BookHead findBookByString(String bookString)
	{
		BookHead bookHead = new BookHead();
		
		BookLinkedList currentNode = next;
		while(currentNode != null)
		{ 
			if(currentNode.getData().getIsbn().equals(bookString) || currentNode.getData().getTitle().equals(bookString)
					|| currentNode.getData().getAuthor().equals(bookString))
			{
				bookHead.addBook(currentNode.getData());
			}
			
			currentNode = currentNode.next;			
		}	
		return bookHead;
	}
	
	void printList()
	{
		BookLinkedList currentNode = next;
		
		while(currentNode != null)
		{
			currentNode.getData().printBook();
			currentNode = currentNode.next;
		}
	}
}

class BookLinkedList
{
	BookLinkedList next = null;
	private Book data;
	
	BookLinkedList(Book data)
	{
		this.data = data;
	}
	
	Book getData()
	{
		return data;
	}
	void setData(Book data)
	{
		this.data = data;
	}
}


class Book
{
	private String isbn, title, author;
	private int totalCopies, availableCopies, timesCheckedOut;
	BorrowerHead borrowerLL;
	
	Book(String isbn, String title, String author, int totalCopies)
	{
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.totalCopies = totalCopies;
		availableCopies = totalCopies;
		borrowerLL = new BorrowerHead();
	}
	Book()
	{
		isbn = "noISBN";
		title = "noTitle";
		author = "noAuthor";
		totalCopies = 0;
		availableCopies = totalCopies;
		borrowerLL = new BorrowerHead();
	}

	///Getters///
	String getIsbn(){return isbn;}
	String getTitle(){return title;}
	String getAuthor(){return author;}
	int getTotalCopies(){return totalCopies;}
	int getAvailableCopies(){return availableCopies;}
	int getTimesCheckedOut(){return timesCheckedOut;}
	
	void setIsbn(String isbn){this.isbn = isbn;}
	void setTitle(String title) {this.title = title;}
	void setAuthor(String author) {this.author = author;}
	void setTotalCopies(int totalCopies){this.totalCopies = totalCopies;}
	void setAvailableCopies(int availableCopies){this.availableCopies = availableCopies;}
	void setTimesCheckedOut(int timesCheckedOut) {this.timesCheckedOut = timesCheckedOut;}
	
	void incrementTimesCheckedOut(){timesCheckedOut += 1;}
	void incrementAvailableCopies(){availableCopies += 1;}
	void decrementAvailableCopies(){availableCopies -= 1;}
	int deleteBorrow(Subscriber subscriber){return borrowerLL.deleteBorrow(this, subscriber);}
	
	void printBook()
	{
		System.out.format("Title: %s | Author: %s\n",title, author);
		System.out.format("IDBN: %s\n", isbn);
		System.out.format("Total Copies / Available Copies: %d / %d\n", totalCopies, availableCopies);
		System.out.format("Times Checked Out: %d\n\n", timesCheckedOut);
	}
	void printSubscribers()
	{
		borrowerLL.printList();
	}
}

class Subscriber
{
	private String id, fullName, userType;
	BorrowerHead borrowerLL;
	
	Subscriber(String id, String fullName, String userType)
	{
		this.id = id;
		this.fullName = fullName;
		this.userType = userType;
		borrowerLL = new BorrowerHead();
	}
	Subscriber()
	{
		id = "noID";
		fullName = "noName";
		userType = "noType";
		borrowerLL = new BorrowerHead();
	}
	
	///Getters///
	String getId(){return id;}
	String getFullName(){return fullName;}
	String getUserType(){return userType;}
	///Setters///
	void setId(String id) {this.id = id;}
	void setFullName(String fullName) {this.fullName = fullName;}
	void setUserType(String userType) {this.userType = userType;}
	
	
	
	int deleteBorrow(Book book){return borrowerLL.deleteBorrow(book, this);}
	
	int getCurrentlyCheckedOut(Book book)
	//gets current number of the book this subscriber checked out.
	{
		BorrowerLinkedList currentNode = borrowerLL.next;
		//BorrowerHead transactions = new BorrowerHead();
		int currentlyCheckedOut = 0;
		
		while(currentNode != null)
		{
			if (currentNode.getData().getBookData() == book)
			{
				//transactions.addBorrower(currentNode.getData());
				currentlyCheckedOut +=1;
			}
			currentNode = currentNode.next;
			
		}
		return currentlyCheckedOut;	
	}
	
	
	void printSubscriber()
	{
		System.out.format("User ID:   %s\n", id);
		System.out.format("User Name: %s\n", fullName);
		System.out.format("User Type: %s\n\n", userType);
	}
	void printBorrowedBooks() 
	{
		borrowerLL.printList();
	}
}


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
			System.out.println("The Books file could not be found. All previous data, if any, was not loaded.");
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
			System.out.println("The Books file could not be found. All previous data, if any, was not loaded.");
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
		System.out.println("5.Print all books.");
		
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
		
		else if(userInput.equals("6"))
		{
			
		}
		
		else if(userInput.equals("6"))
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