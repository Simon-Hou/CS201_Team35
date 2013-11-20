package bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import org.apache.commons.lang.RandomStringUtils;


import role.Role;
import agent.Agent;
import util.*;

public class BankTellerRole extends Role{
	
	//Constructor
	
	//quick and dirty
	public BankTellerRole(String name){
		this.name = name;
		//startedWorking = true;
	}
	
	//USEFUL METHODS
	public static String generateString(Random rng, String characters, int length){
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    //Do(String (text));
	    return new String(text);
	}
	
	
	
	//SETTERS
	
	public void setBank(Bank b){
		bank = b;
	}
	
	public void setPerson(Person p){
		person = p;
	}
	
	//GETTERS
	
	public String getName(){
		return name;
	}

	//DATA
	
	String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	Person person;
	
	String name;
	
	BankCustomerRole currentCustomer;
	
	//THIS will not get clobbered if the following design assumption holds:
	//BankCustomers will (according to their design, feed the teller one task
	//at a time, waiting for the teller to say that he's ready to process
	//the next task before messaging him again.
	Task currentTask;
	
	boolean startedWorking = false;
	Bank bank;
	
	
	
	//MSG
	
	public void msgStateChanged(){
		stateChanged();
	}
	
	public void msgIWantTo(Task t){
		currentTask = t;
		Do("Got customer's request to process a " + t.getType());
		stateChanged();
	}
	
	public void msgDoneAndLeaving(){
		currentCustomer = null;
		stateChanged();
		
	}
	
	
	//SCHED
	
	public boolean pickAndExecuteAnAction(){
		
		//If not started working, tell bank you're starting
		if(!startedWorking){
			Do("I'll start working.");
			startedWorking = bank.startTellerShift(this);
			return false;
		}
		
		//if you don't have a customer, take one from the queue
		if(currentCustomer == null){
			Do("Need a new customer");
			currentCustomer = bank.getCustomer();
			if(currentCustomer!=null){
				Do("Sending "+ currentCustomer.getName()+" a message to start helping him");
				currentCustomer.msgHowCanIHelpYou(this);
				return true;
			}
			return false;
		}
		
		//if you don't have anything to do, return null
		if(currentTask == null){
			return false;
		}
		
		//Task currentTask = currentTasks.get(0);
		//if you have a task, do the right thing:
		if (currentTask instanceof openAccount) {
			OpenAccount();
			return true;
		}
		if (currentTask instanceof deposit) {
			Deposit();
			return true;
		}
		if (currentTask instanceof withdrawal) {
			Withdraw();
			return true;
		}
		if (currentTask instanceof takeLoan) {
			GiveLoan();
		}
		if (currentTask instanceof rob) {
			Rob();
		}
		
		
		return false;
		
		
	}
	
	
	//ACT
	
	private void OpenAccount(){
		Do("Helping "+currentCustomer.getName()+ " to open an account");
		//String passWord = generateString(new Random(),alphabet,10);
		
		int pass =  (int) (100000*Math.random());
		String passWord = "p"+pass;
		Do(passWord);
		int accountNumber = bank.addAccount(((openAccount) currentTask).custName,currentTask.amount,passWord);
		
		currentCustomer.msgAccountOpenedAnythingElse(currentTask.amount,accountNumber,passWord);
		currentTask = null;
	}
	
	private void Deposit(){
		
		boolean done = bank.deposit(currentTask.amount, ((deposit) currentTask).accountNumber, ((deposit) currentTask).passWord);
		if(done){
			currentCustomer.msgDepositCompletedAnythingElse(currentTask.amount);
			
		}
		else{
			System.err.println("Deposit failed");
		}
		currentTask = null;
	}
	
	private void Withdraw(){
		boolean done = bank.withdraw(currentTask.amount, ((withdrawal) currentTask).accountNumber, ((withdrawal) currentTask).passWord);
		if(done){
			currentCustomer.msgHereIsWithdrawalAnythingElse(currentTask.amount);
			
		}
		else{
			System.err.println("Withdrawal failed");
		}
		currentTask = null;
	}
	
	private void GiveLoan(){
		int loanNumber = bank.giveLoan(currentTask.amount,((takeLoan) currentTask).accountNumber,((takeLoan) currentTask).passWord);
		if(loanNumber>=0){
			currentCustomer.msgLoanApprovedAnythingElse(currentTask.amount, ((takeLoan) currentTask).accountNumber, loanNumber);

		}
		else{
			System.err.println("Loan failed");
		}
		currentTask = null;
	}
	
	private void Rob(){
		Do("Getting robbed int the amount of $"+ currentTask.amount);
		int a = bank.rob(currentTask.amount);
		currentCustomer.msgHereIsMoneyAnythingElse(a);
		currentTask = null;
	}
	
	
}
