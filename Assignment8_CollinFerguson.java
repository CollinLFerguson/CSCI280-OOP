/*
Collin L. Ferguson
Assignment 8
3/29/2023; Start Time: 1905; End time: 1930
Purpose: Create a java class with a static variable.
*/

class SavingsAccount
{
	private static float annualInterestRate = 10;
	private float savingsBalance;
	
	SavingsAccount(float savingsBalance)
	{
		this.savingsBalance = savingsBalance;
	}
	
	
	float getAnnualInterestRate() {return annualInterestRate;}
	float getSavingsBalance() {return savingsBalance;}
	void setSavingsBalance(float amount) {savingsBalance = amount;}
	
	float calculateMonhlyInterest()
	{
		savingsBalance = savingsBalance + (savingsBalance * ((annualInterestRate / 100)/12));
		return savingsBalance;
	}	
	void modifyInterestRate(float amount)
	{
		annualInterestRate = amount;
	}
}


public class Assignment8_CollinFerguson {

	public static void main(String[] args)
	{
		SavingsAccount saver1 = new SavingsAccount(2000);
		SavingsAccount saver2 = new SavingsAccount(3000);
		
		System.out.format("Saver 1: InterestRate:%1.2f Balance:%1.2f\n", saver1.getAnnualInterestRate(), saver1.getSavingsBalance());
		System.out.format("Saver 2: InterestRate:%1.2f Balance:%1.2f\n\n", saver2.getAnnualInterestRate(), saver2.getSavingsBalance());
		
		saver1.modifyInterestRate(4);
		saver1.calculateMonhlyInterest();
		saver2.calculateMonhlyInterest();
		
		System.out.format("Saver 1: InterestRate:%1.2f Balance:%1.2f\n", saver1.getAnnualInterestRate(), saver1.getSavingsBalance());
		System.out.format("Saver 2: InterestRate:%1.2f Balance:%1.2f\n\n", saver2.getAnnualInterestRate(), saver2.getSavingsBalance());

		saver1.modifyInterestRate(5);
		saver1.calculateMonhlyInterest();
		saver2.calculateMonhlyInterest();
		
		System.out.format("Saver 1: InterestRate:%1.2f Balance:%1.2f\n", saver1.getAnnualInterestRate(), saver1.getSavingsBalance());
		System.out.format("Saver 2: InterestRate:%1.2f Balance:%1.2f\n\n", saver2.getAnnualInterestRate(), saver2.getSavingsBalance());
		
	}

}
