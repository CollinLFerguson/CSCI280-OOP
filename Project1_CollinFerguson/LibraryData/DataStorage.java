package LibraryData;
import java.util.Date;

public class DataStorage{
	public static class BorrowerHead
	{
		public BorrowerLinkedList next = null;
		
		public void addBorrower(BorrowerDataNode node)
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
		
		public int deleteBorrow(Book book, Subscriber subscriber)
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
		public BorrowerHead auditList(long auditDistance)
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
		
		public void printList()
		{
			BorrowerLinkedList currentNode = next;
			
			while(currentNode != null)
			{
				currentNode.getData().printBorrowInfo();
				currentNode = currentNode.next;
			}
		}
	}
	
	
	public static class BorrowerLinkedList
	{
		public BorrowerLinkedList next;
		private BorrowerDataNode data;
		
		BorrowerLinkedList(BorrowerDataNode data)
		{
			this.data = data;
		}
		
		public BorrowerDataNode getData()
		{
			return data;
		}
	}
	
	public static class BorrowerDataNode //made it's own class so that one object can be associated with both book and sub objects. 
	{
		private Book bookData;
		private Subscriber subscriberData;
		private long borrowingDate;	
		
		public BorrowerDataNode(Book bookData, Subscriber subscriberData)
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
		public BorrowerDataNode()
		{
			bookData = null;
			subscriberData = null;
			borrowingDate = 0;
		}
		
		public Book getBookData() {return bookData;}
		public Subscriber getSubscriberData() {return subscriberData;}
		public long getBorrowingDate() {return borrowingDate;}
		
		public void setBookData(Book bookData) {this.bookData = bookData;}
		public void setSubscriberData(Subscriber subscriberData) {this.subscriberData = subscriberData;}
		public void setDate(long borrowingDate) {this.borrowingDate = borrowingDate;}
		
		void printBorrowInfo()
		{
			System.out.format("Book: %-40s | Subscriber: %s, ID: %s\n", (bookData.getTitle() + " by " + bookData.getAuthor()), subscriberData.getFullName(), subscriberData.getId());
		}
	}

	public static class SubscriberHead
	{
		public SubscriberLinkedList next = null;
		
		public void addSubscriber(String id, String fullName, String userType)
		//Creates a new subscriber object and adds it to the end of the linked list.
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
		
		public void addSubscriber(Subscriber subscriber)
		//Takes an existing subscriber object and adds it to the end of the linked list.
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
		
		public int removeSubscriber(String id)
		//Removes the subscriber with the matching id returns a 0 on success and a -1 on failure. 
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
		
		public SubscriberHead findSubscriberByString(String subscriberString)
		//Returns a subscriberHead containing the suvscriber with the id that matches the subscriberString
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
		
		
		public void printList()
		{
			SubscriberLinkedList currentNode = next;
			
			while(currentNode != null)
			{
				currentNode.getData().printSubscriber();
				currentNode = currentNode.next;
			}
		}
	}


	public static class SubscriberLinkedList
	{
		public SubscriberLinkedList next;
		private Subscriber data;
		
		public SubscriberLinkedList(Subscriber data)
		{
			this.data = data;
		}
		
		public Subscriber getData()
		{
			return data;
		}
		public void setData(Subscriber data)
		{
			this.data = data;
		}
	}

	public static class BookHead
	{
		public BookLinkedList next = null;
		
		public void addBook(String isbn, String title, String author, int totalCopies)
		//creates a new Book object and adds it to the end of the linked list.
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
		
		public void addBook(Book book)
		//takes an existing book as input and adds it to the end of the linked list.
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
		
		public int removeBook(String isbn)
		//Searches for a book and removes it from the LL returns a 0 on success and a -1 on failure
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
		
		public BookHead findBookByString(String bookString)
		//Takes a string as input and checks the ISBN, title, and author. returns a linked list of all matching book
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
		
		public void printList()
		{
			BookLinkedList currentNode = next;
			
			while(currentNode != null)
			{
				currentNode.getData().printBook();
				currentNode = currentNode.next;
			}
		}
	}

	public static class BookLinkedList
	{
		public BookLinkedList next = null;
		private Book data;
		
		BookLinkedList(Book data)
		{
			this.data = data;
		}
		///Getters///
		public Book getData()
		{
			return data;
		}
		///Setters///
		void setData(Book data)
		{
			this.data = data;
		}
	}


	public static class Book
	{
		private String isbn, title, author;
		private int totalCopies, availableCopies, timesCheckedOut;
		public BorrowerHead borrowerLL;
		
		Book(String isbn, String title, String author, int totalCopies)
		{
			this.isbn = isbn;
			this.title = title;
			this.author = author;
			this.totalCopies = totalCopies;
			availableCopies = totalCopies;
			borrowerLL = new BorrowerHead();
		}
		public Book()
		{
			isbn = "noISBN";
			title = "noTitle";
			author = "noAuthor";
			totalCopies = 0;
			availableCopies = totalCopies;
			borrowerLL = new BorrowerHead();
		}

		///Getters///
		public String getIsbn(){return isbn;}
		public String getTitle(){return title;}
		public String getAuthor(){return author;}
		public int getTotalCopies(){return totalCopies;}
		public int getAvailableCopies(){return availableCopies;}
		public int getTimesCheckedOut(){return timesCheckedOut;}
		///Setters///
		public void setIsbn(String isbn){this.isbn = isbn;}
		public void setTitle(String title) {this.title = title;}
		public void setAuthor(String author) {this.author = author;}
		public void setTotalCopies(int totalCopies){this.totalCopies = totalCopies;}
		public void setAvailableCopies(int availableCopies){this.availableCopies = availableCopies;}
		public void setTimesCheckedOut(int timesCheckedOut) {this.timesCheckedOut = timesCheckedOut;}
		
		public void incrementTimesCheckedOut(){timesCheckedOut += 1;}
		public void incrementAvailableCopies(){availableCopies += 1;}
		public void decrementAvailableCopies(){availableCopies -= 1;}
		
		public int deleteBorrow(Subscriber subscriber){return borrowerLL.deleteBorrow(this, subscriber);}
		//Removes the transaction from it's borrowerLL
		
		
		public void printBook()
		{
			System.out.format("Title: %s | Author: %s\n",title, author);
			System.out.format("IDBN: %s\n", isbn);
			System.out.format("Total Copies / Available Copies: %d / %d\n", totalCopies, availableCopies);
			System.out.format("Times Checked Out: %d\n\n", timesCheckedOut);
		}
		//public void printSubscribers(){borrowerLL.printList();}
	}

	public static class Subscriber
	{
		private String id, fullName, userType;
		public BorrowerHead borrowerLL;
		
		Subscriber(String id, String fullName, String userType)
		{
			this.id = id;
			this.fullName = fullName;
			this.userType = userType;
			borrowerLL = new BorrowerHead();
		}
		public Subscriber()
		{
			id = "noID";
			fullName = "noName";
			userType = "noType";
			borrowerLL = new BorrowerHead();
		}
		
		///Getters///
		public String getId(){return id;}
		public String getFullName(){return fullName;}
		public String getUserType(){return userType;}
		///Setters///
		public void setId(String id) {this.id = id;}
		public void setFullName(String fullName) {this.fullName = fullName;}
		public void setUserType(String userType) {this.userType = userType;}
		
		
		
		public int deleteBorrow(Book book){return borrowerLL.deleteBorrow(book, this);}
		//public void printBooks() {borrowerLL.printList();} //prints all books held by the Subscriber
		public int getCurrentlyCheckedOut(Book book)
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
		
		
		public void printSubscriber()
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

}
