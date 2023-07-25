/*
Collin L. Ferguson
Assignment 7
3/28/2023 
Start time 19:42 End Time: 20:00
Purpose: Create a class with objects that can change other objects.
*/

class Account
{
	private String id;
	private String name;
	private int balance;
	Account(String id, String name, int balance)
	{
		this.id = id;
		this.name = name;
		this.balance = balance;
	}
	Account(String id, String name)
	{
		this.id = id;
		this.name = name;
		this.balance = 0;
	}
	String getName(){return name;}
	String getId(){return id;}
	int getBalance(){return balance;}
	
	void setName(String name) {this.name=name;}
	void setId(String id) {this.id=id;}
	void setBalance(int balance) {this.balance=balance;}
	
	
	int credit(int amount)
	{
		balance += amount;
		return balance;
	}
	int debit(int amount)
	{
		if(balance < amount)
		{
			System.out.println("amount exceeds the available balance");
			return balance;
		}
		
		balance -= amount;
		return balance;
	}
	int transferTo(Account anotherAccount, int amount)
	{
		if(balance < amount)
		{
			System.out.println("amount exceeds the available balance");
			return balance;
		}
		
		balance-=amount;
		
		anotherAccount.setBalance(anotherAccount.getBalance()+amount);	
		
		return balance;
	}
}


public class Assignment7_CollinFerguson
{

	public static void main(String[] args) 
	{		
		Account account1 = new Account("001", "Collin", 100);
		Account account2 = new Account("002", "Ferguson");
		account2.credit(100);
		
		System.out.format("%-15s %-15s %d\n", account1.getName(), account1.getId(), account1.getBalance());
		System.out.format("%-15s %-15s %d\n", account2.getName(), account2.getId(), account2.getBalance());
		
		account1.credit(20);
		account2.debit(101);
		account2.debit(20);
		
		System.out.format("%-15s %-15s %d\n", account1.getName(), account1.getId(), account1.getBalance());
		System.out.format("%-15s %-15s %d\n", account2.getName(), account2.getId(), account2.getBalance());
		
		account1.transferTo(account2, 400);
		account1.transferTo(account2, 100);
		
		System.out.format("%-15s %-15s %d\n", account1.getName(), account1.getId(), account1.getBalance());
		System.out.format("%-15s %-15s %d\n", account2.getName(), account2.getId(), account2.getBalance());
	}
}
