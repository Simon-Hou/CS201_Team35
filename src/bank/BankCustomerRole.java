package bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import interfaces.BankCustomer;
import interfaces.BankTeller;
import interfaces.Person;
import role.Role;
import agent.Agent;
import testAgents.testPerson;
import util.*;

public class BankCustomerRole extends Role implements BankCustomer {

	
	//Constructor
	
	//quick and dirty
	public BankCustomerRole(String name,Person p){
		this.person = p;
		this.name = name;
		System.out.println(getName());
	}
	
	
	//SETTERS
	
	public void setBank(Bank b){
		bank = b;
	}
	
	public void setPerson(Person p){
		person = p;
	}
	
	public void addTask(Task t){
		Tasks.add(t);
	}
	
	//GETTERS
	
	public String getName(){
		return name;
	}
	
	
	
	//DATA
	
	public String passWord;
	
	public String name;
	
	public Bank bank;
	
	Person person;
	
	
	public BankTeller teller;
	
	public enum CustState {init,inBank,inLine,goingToWindow,beingServed,leaving};
	public enum CustEvent {tellerReady,taskPending};
	
	public CustState state = CustState.init;
	public CustEvent event;
	
	public List<Task> Tasks = new ArrayList<Task>();
	public Semaphore atDestination = new Semaphore(0, true);
	
	Task pendingTask = null;
	
	
	
	//MSG
	
	public void msgYouAreAtBank(Bank b){
		this.bank = b;
		this.state = CustState.inBank;
	}
	
	public void msgHowCanIHelpYou(BankTeller t){
		Do("Just got asked how I can be helped.");
		
		teller = t;
		event = CustEvent.tellerReady;
		//state = CustState.beingServed;
		person.msgStateChanged();
		
	}
	
	public void msgDepositCompletedAnythingElse(int amount){
		Do("Told that my deposit was successful");
		int accNum = ((deposit) pendingTask).accountNumber;
		person.takeFromWallet(amount);
		person.addToAccount(accNum,amount);
		event = CustEvent.tellerReady;
		pendingTask = null;
		person.msgStateChanged();
	}
	
	public void msgHereIsWithdrawalAnythingElse(int amount){
		Do("Told that my withdrawal was successful");
		int accNum = ((withdrawal) pendingTask).accountNumber;
		person.addToWallet(amount);
		person.takeFromAccount(accNum,amount);
		event = CustEvent.tellerReady;
		pendingTask = null;
		person.msgStateChanged();
	}
	
	public void msgAccountOpenedAnythingElse(int amount, int accountNumber,String passWord){
		Do("I've got a new account with amount "+ amount);
		String name = ((openAccount) pendingTask).custName;
		person.takeFromWallet(amount);
		person.createAccount(accountNumber,amount,name,passWord);
		event = CustEvent.tellerReady;
		//this.passWord = passWord;
		//Do(passWord);
		pendingTask = null;
		person.msgStateChanged();
	}
	
	public void msgLoanApprovedAnythingElse(int cash, int accountNumber, int loanNumber){
		Do("Loan approved for $"+ cash);
		person.addToWallet(cash);
		person.addLoan(accountNumber,cash,loanNumber);
		event = CustEvent.tellerReady;
		pendingTask = null;
		person.msgStateChanged();
	}
	
	public void msgHereIsMoneyAnythingElse(int cash){
		person.addToWallet(cash);
		event = CustEvent.tellerReady;
		pendingTask = null;
		person.msgStateChanged();
	}
	
	public void msgAtDestination() {
		atDestination.release();
		person.msgStateChanged();
	}
	
	
	
	//SCHED
	
	public boolean pickAndExecuteAnAction(){
		//if you're inBank, get in line
		if(state == CustState.inBank){
			
			getInLine();
			return true;
		}
		
		//if you're being served & the event is that teller is read, do next task
		if(state == CustState.inLine && event == CustEvent.tellerReady) {
			goToWindow();
			return true;
		}
		
		if(state == CustState.beingServed && event == CustEvent.tellerReady){
			NextTask();
			return true;
		}
		
		return false;
		
	}
	
	
	//ACT
	
	private void getInLine(){
		Do("Getting in line!");
		if(bank.addMeToQueue(this)){
			doGetInLine();
			state = CustState.inLine;
			
		}
		else{
			System.err.println("Customer wasn't allowed in line");
		}
	}
	
	private void goToWindow() {
		Do("Going to the teller's window");
		doGoToWindow();
		state = CustState.goingToWindow;
		//atDestination.release();
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		state = CustState.beingServed;
	}
	
	private void NextTask(){
		
		Do("Telling teller the next task");
		
		//Task t = Tasks.get(0);
		if(Tasks.isEmpty()){
			Do("Finished what I needed done. I'm leaving");
			doLeaveBank();
			teller.msgDoneAndLeaving();
			state = CustState.leaving;
			person.msgThisRoleDone(this);
			return;
		}
		
		
		Task t = Tasks.get(0);
		
		Do(t.getType());
		
		Task tCopy = t.copyTask();
		teller.msgIWantTo(tCopy);
		
		event = CustEvent.taskPending;
		pendingTask = t;
		Tasks.remove(0);
		
		
		
	}
	
	
	//GUI
	
	private void doGetInLine(){
		
	}
	
	private void doGoToWindow() {
		
	}
	
	private void doLeaveBank(){
		
	}
	
	
	
}
