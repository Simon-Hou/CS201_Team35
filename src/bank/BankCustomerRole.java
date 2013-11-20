package bank;

import java.util.ArrayList;
import java.util.List;

import agent.Agent;
import util.Bank;
import util.*;

public class BankCustomerRole extends Agent{

	
	//Constructor
	
	//quick and dirty
	public BankCustomerRole(String name){
		this.name = name;
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
	
	String name;
	
	Bank bank;
	
	Person person;
	
	BankTellerRole teller;
	
	enum CustState {inBank,inLine,beingServed,leaving};
	enum CustEvent {tellerReady,taskPending};
	
	CustState state = CustState.inBank;
	CustEvent event;
	
	List<Task> Tasks = new ArrayList<Task>();
	Task pendingTask = null;
	
	
	
	//MSG
	
	public void msgHowCanIHelpYou(BankTellerRole t){
		Do("Just got asked how I can be helped.");
		
		teller = t;
		event = CustEvent.tellerReady;
		state = CustState.beingServed;
		stateChanged();
		
	}
	
	public void msgDepositCompletedAnythingElse(int amount){
		Do("Told that my deposit was successful");
		int accNum = ((deposit) pendingTask).accountNumber;
		person.purse.wallet -= amount;
		person.addToAccount(accNum,amount);
		event = CustEvent.tellerReady;
		pendingTask = null;
		stateChanged();
	}
	
	public void msgHereIsWithdrawalAnythingElse(int amount){
		Do("Told that my withdrawal was successful");
		int accNum = ((withdrawal) pendingTask).accountNumber;
		person.purse.wallet += amount;
		person.takeFromAccount(accNum,amount);
		event = CustEvent.tellerReady;
		pendingTask = null;
		stateChanged();
	}
	
	public void msgAccountOpenedAnythingElse(int amount, int accountNumber,String passWord){
		Do("I've got a new account with amount "+ amount);
		String name = ((openAccount) pendingTask).custName;
		person.purse.wallet -= amount;
		person.createAccount(accountNumber,amount,name,passWord);
		event = CustEvent.tellerReady;
		this.passWord = passWord;
		Do(passWord);
		pendingTask = null;
		stateChanged();
	}
	
	public void msgLoanApprovedAnythingElse(int cash, int accountNumber, int loanNumber){
		Do("Loan approved for $"+ cash);
		person.purse.wallet += cash;
		person.addLoan(accountNumber,cash,loanNumber);
		event = CustEvent.tellerReady;
		pendingTask = null;
		stateChanged();
	}
	
	public void msgHereIsMoneyAnythingElse(int cash){
		person.purse.wallet+= cash;
		event = CustEvent.tellerReady;
		pendingTask = null;
		stateChanged();
	}
	
	
	
	//SCHED
	
	public boolean pickAndExecuteAnAction(){
		//if you're inBank, get in line
		if(state == CustState.inBank){
			getInLine();
			return true;
		}
		
		//if you're being served & the event is that teller is read, do next task
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
	
	private void doLeaveBank(){
		
	}
	
	
	
}
