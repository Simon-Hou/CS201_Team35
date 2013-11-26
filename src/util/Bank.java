package util;

import interfaces.BankCustomer;
import interfaces.BankInterface;
import interfaces.BankTeller;
import interfaces.Person;
import interfaces.PlaceOfWork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cityGui.CityBank;
import role.Role;
import bank.BankCustomerRole;
import bank.BankTellerRole;
import bank.gui.BankCustomerGui;
import bank.gui.BankGui;
import bank.gui.BankTellerGui;

public class Bank implements BankInterface, PlaceOfWork{
	
	//stores all the people in the queue
	public List<BankCustomer> bankCustomers = Collections.synchronizedList(new ArrayList<BankCustomer>());
	
	public List<BankTeller> myTellers = new ArrayList<BankTeller>();
	public List<BankTeller> currentTellers = new ArrayList<BankTeller>();
	public List<BankAccount> accounts = new ArrayList<BankAccount>();
	public BankGui bankGui = new BankGui(this);
	
	public List<TellerSpot> tellerSpots = new ArrayList<TellerSpot>();
	
	public CityBank cityBankGui;
	
	int totalAmount = 1000000000;
	
	public class BankAccount{
		public BankAccount(int amount,int accountNumber,String custName,String passWord){
			this.amount = amount;
			this.accountNumber = accountNumber;
			this.custName = custName;
			this.passWord = passWord;
			tellerSpots.add(new TellerSpot(100 + 300/2-12, 75));//x value of the counter + center of counter - half width
			tellerSpots.add(new TellerSpot(100 + 300/2-12, 445));//x value of the counter + center of counter - half width
			tellerSpots.add(new TellerSpot(100 + 300, 175));//x value of the counter + counterWidth
			tellerSpots.add(new TellerSpot(100 + 300, 325));//x value of the counter + counterWidth
			
		}
		public int amount;
		public int accountNumber;
		public String custName;
		public String passWord;
		public List<loan> myLoans = new ArrayList<loan>();
	}
	
	public class loan{

		public loan(int totalAmount,int loanNumber) {
			total = totalAmount;
			amountLeft = total;
			this.loanNumber = loanNumber;
		}
		public int total;
		int amountLeft;//to be paid off.
		public int loanNumber;

		public void payOff(int payment) {
			amountLeft -= payment;
		}
	}
	
	public class TellerSpot {
		public TellerSpot(int x, int y) {
			xPos = x;
			yPos = y;
		}
		BankTeller currentTeller;
		int xPos;
		int yPos;
	}
	
	public boolean startTellerShift(BankTeller t){
		currentTellers.add(t);
		BankTellerGui g = new BankTellerGui(((BankTellerRole)t));
		if (!tellerSpots.isEmpty())
			g.initialSpot(tellerSpots.get(0).xPos, tellerSpots.get(0).yPos);
		((BankTellerRole)t).setGui(g);
		bankGui.bankPanel.tellerPanel.addListButton(((BankTellerRole)t).getName());
		bankGui.bankAnimationPanel.addGui(g);
		//t.msgStateChanged();
		return true;
	}
	
	
	public BankCustomer getCustomer(){
		if(!bankCustomers.isEmpty()){
			BankCustomer b = bankCustomers.get(0);
			bankCustomers.remove(0);
			return b;
		}
		return null;
	}
	
	public boolean addMeToQueue(BankCustomer c){
		//System.out.println("Here");
		bankCustomers.add(c);
		BankCustomerGui g = new BankCustomerGui(((BankCustomerRole)c));
		((BankCustomerRole)c).setGui(g);
		bankGui.bankPanel.tellerPanel.addListButton(((BankCustomerRole)c).getName());
		bankGui.bankAnimationPanel.addGui(g);
		//System.out.println("Size of the queue is "+bankCustomers.size());
		for(BankTeller t:currentTellers){
			//System.out.println("Teller messaged");
			t.msgStateChanged();
		}
		return true;
	}
	
	
	//BANKING FUNCTIONS
	
	public synchronized int addAccount(String name,int amount,String passWord){
		int accountNumber = accounts.size();
		accounts.add(new BankAccount(amount,accountNumber,name,passWord));
		bankGui.bankPanel.accountPanel.addListButton("" + accountNumber);
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


	@Override
	public Role canIStartWorking(Person p,JobType jobType,Role r) {
		// TODO Auto-generated method stub
		if(jobType == JobType.BankTeller){
			if(startTellerShift((BankTeller) r)){
				return r;
			}
			return null;
		}
		System.err.println("A non-teller is trying to work at the bank!!");
		return null;
	}


	
	
	
	
	
}
