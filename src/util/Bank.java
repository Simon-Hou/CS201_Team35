package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bank.BankCustomerRole;
import bank.BankTellerRole;

public class Bank {
	
	
	

	//stores all the people in the queue
	List<BankCustomerRole> bankCustomers = Collections.synchronizedList(new ArrayList<BankCustomerRole>());
	
	List<BankTellerRole> myTellers = new ArrayList<BankTellerRole>();
	List<BankTellerRole> currentTellers = new ArrayList<BankTellerRole>();
	List<BankAccount> accounts = new ArrayList<BankAccount>();
	
	int totalAmount = 1000000000;
	
	class BankAccount{
		public BankAccount(int amount,int accountNumber,String custName,String passWord){
			this.amount = amount;
			this.accountNumber = accountNumber;
			this.custName = custName;
			this.passWord = passWord;
		}
		int amount;
		int accountNumber;
		String custName;
		String passWord;
		List<loan> myLoans = new ArrayList<loan>();
	}
	
	class loan{

		public loan(int totalAmount,int loanNumber) {
			total = totalAmount;
			amountLeft = total;
			this.loanNumber = loanNumber;
		}
		int total;
		int amountLeft;//to be paid off.
		int loanNumber;

		public void payOff(int payment) {
			amountLeft -= payment;
		}
	}
	
	
	public boolean startTellerShift(){
		return true;
	}
	
	
	public BankCustomerRole getCustomer(){
		if(!bankCustomers.isEmpty()){
			BankCustomerRole b = bankCustomers.get(0);
			bankCustomers.remove(0);
			return b;
		}
		return null;
	}
	
	public boolean addMeToQueue(BankCustomerRole c){
		bankCustomers.add(c);
		for(BankTellerRole t:currentTellers){
			t.msgStateChanged();
		}
		return true;
	}
	
	
	
	
	//BANKING FUNCTIONS
	
	public synchronized int addAccount(String name,int amount,String passWord){
		int accountNumber = accounts.size();
		accounts.add(new BankAccount(amount,accountNumber,name,passWord));
		return accountNumber;
	}
	
	public synchronized boolean deposit(int amount,int accountNumber,String passWord){
		if(accountNumber>=accounts.size()){
			return false;
		}
		BankAccount ba = accounts.get(accountNumber);
		if(passWord.equals(ba.passWord)){
			accounts.get(accountNumber).amount += amount;
			return true;
		}
		return false;
	}
	
	public synchronized boolean withdraw(int amount,int accountNumber,String passWord){
		if(accountNumber>=accounts.size()){
			return false;
		}
		BankAccount ba = accounts.get(accountNumber);
		if(passWord.equals(ba.passWord) && ba.amount>=amount){
			accounts.get(accountNumber).amount -= amount;
			return true;
		}
		return false;
	}
	
	public synchronized int giveLoan(int amount,int accountNumber,String passWord){
		if(accountNumber>=accounts.size()){
			return -1;
		}
		BankAccount ba = accounts.get(accountNumber);
		int l = ba.myLoans.size();
		ba.myLoans.add(new loan(amount,l));
		return l;
		
	}
	
	public synchronized int rob(int amount){
		if(totalAmount>=amount){
			totalAmount -= amount;
			return amount;
		}
		
		int a = totalAmount;
		totalAmount = 0;
		return a;
		
	}
	
	
	
	
	
}
