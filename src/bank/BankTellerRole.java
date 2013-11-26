package bank;

import interfaces.BankCustomer;
import interfaces.BankInterface;
import interfaces.BankTeller;
import interfaces.Occupation;
import interfaces.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import org.apache.commons.lang.RandomStringUtils;

import java.util.concurrent.Semaphore;

import bank.BankCustomerRole;
import bank.gui.BankTellerGui;
import person.PersonAgent;
import role.Role;
import UnitTests.mock.bankMock.MockBank;
import agent.Agent;
import util.*;

public class BankTellerRole extends Role implements BankTeller, Occupation{
	
	//Constructor
	
	//quick and dirty
	public BankTellerRole(String name,Person p){
		this.name = name;
		this.person = person;
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
	
	public void setBank(BankInterface b){
		bank = b;
	}
	
	public void setPerson(Person p){
		person = p;
	}
	
	public void setGui(BankTellerGui g) {
		bankTellerGui = g;
	}
	
	//GETTERS
	
	public String getName(){
		return name;
	}
	
	public boolean canLeave(){
		if (currentCustomer == null) {
			if (bankTellerGui!=null) {
				bankTellerGui.DoExitBank();
				try {
					atDestination.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return currentCustomer == null;
	}

	//DATA
	
	String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	Person person;
	
	String name;
	
	public BankCustomer currentCustomer;
	
	//THIS will not get clobbered if the following design assumption holds:
	//BankCustomers will (according to their design, feed the teller one task
	//at a time, waiting for the teller to say that he's ready to process
	//the next task before messaging him again.
	public Task currentTask;
	
	public boolean startedWorking = false;
	BankInterface bank;
	public Semaphore atDestination = new Semaphore(0, true);
	
	public BankTellerGui bankTellerGui;
	
	
	
	//MSG
	
	public void msgStateChanged(){
		stateChanged();
		person.msgStateChanged();
	}
	
	public void msgIWantTo(Task t){
		currentTask = t;
		//Do("Got customer's request to process a " + t.getType());
		person.msgStateChanged();
	}
	
	public void msgDoneAndLeaving(){
		//Do("Customer just left");
		currentCustomer = null;
		person.msgStateChanged();
		
	}
	
	public void msgAtDestination() {
		atDestination.release();
		person.msgStateChanged();
	}
	
	
	//SCHED
	
	public boolean pickAndExecuteAnAction(){
		//If not started working, tell BankInterface you're starting
		if(!startedWorking){
			//Do("I'll start working.");
			startedWorking = bank.startTellerShift(this);
			//GoToPosition();
			return true;
		}
		
		//if you don't have a customer, take one from the queue
		if(currentCustomer == null){
			//Do("Need a new customer");
			currentCustomer = bank.getCustomer();
			//System.out.println(currentCustomer==null);
			if(currentCustomer!=null){
				//Do("Sending "+ currentCustomer.getName()+" a message to start helping him");
				System.out.flush();
				callCustomer(currentCustomer);
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
			return true;
		}
		if (currentTask instanceof rob) {
			Rob();
			return true;
		}
		
		
		return false;
		
		
	}
	
	
	//ACT
	
	private void callCustomer(BankCustomer c) {
		GoToPosition();
		if (c instanceof BankCustomerRole) {
			((BankCustomerRole)c).msgHowCanIHelpYou(this, bankTellerGui.xPos, bankTellerGui.yPos + 50 + bankTellerGui.BankTellerHeight);//TODO fix these numbers!
		}
		else
			c.msgHowCanIHelpYou(this);
	}
	
	private void GoToPosition() {
		Do("Going to my position to work");
		if(bankTellerGui!=null){
			bankTellerGui.DoGoToPosition();
			System.err.println();
		}
		else{
			atDestination.release();
		}
		
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void OpenAccount(){
		Do("Helping "+currentCustomer.getName()+ " to open an account");
		//String passWord = generateString(new Random(),alphabet,10);
		
		int pass =  (int) (100000*Math.random());
		String passWord = "p"+pass;
		passWord = "passWord";
		//Do(passWord);
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
	
	private void LeaveBank() {
		Do("Leaving the bank now");
		((Bank)bank).finishTellerShift(this);
		if (bankTellerGui!=null) {
			bankTellerGui.DoExitBank();
			try {
				atDestination.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		person.msgThisRoleDone(this);
		return;
		//TODO message the bank here and figure out how to seal the deal.
	}
	
	
}
