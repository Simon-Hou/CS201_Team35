package UnitTests.BankUnitTests;

import java.util.ArrayList;

import util.Bank;
import util.deposit;
import util.openAccount;
import util.withdrawal;
import UnitTests.mock.bankMock.MockBank;
import UnitTests.mock.bankMock.MockBankCustomer;
import UnitTests.mock.bankMock.MockBankPerson;
import bank.BankTellerRole;
import junit.framework.*;

/**
 * 
 * This class is a JUnit test class to unit test the CashierAgent's basic interaction
 * with waiters, customers, and the host.
 * It is provided as an example to students in CS201 for their unit testing lab.
 *
 * @author Monroe Ekilah
 */
public class BankTellerTest extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	
	BankTellerRole teller;
	MockBankCustomer customer;
	MockBankCustomer customer2;
	MockBankPerson person;
	MockBank bank;
	
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		
		bank = new MockBank();
		
		person = new MockBankPerson("c0");
		
		teller = new BankTellerRole("t0");
		teller.setPerson(person);
		teller.setBank(bank);
		
		customer = new MockBankCustomer("c0");
		customer2 = new MockBankCustomer("c1");
		
		
		
//		bank.addMeToQueue(customer);
//		bank.addMeToQueue(customer2);
		
	}	
	
	
	/** This tests when a customers does not have any jobs they need done
	 * 
	 */
	public void testNothingToDo(){
		
//		//step 0 - put the customer in the bank
//		bank.addMeToQueue(customer);
		
		//verify that the customers have an empty log, teller hasn't started working
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller shouldn't be working yet",!teller.startedWorking);
		
		//step 1 - call the teller schedule
		assertTrue("Teller should have acted",!teller.pickAndExecuteAnAction());
		
		//check post of 1 and pre of 2
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller should be working now",teller.startedWorking);
		assertTrue("Teller's person should still be asleep",person.log.isEmpty());
		
		//step 2 - put the customer in the bank
		bank.addMeToQueue(customer);
		teller.msgStateChanged();
		
		//check post of 2 and pre of 3
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller should be working",teller.startedWorking);
		assertTrue("Teller's person should have been woken up by customer entering.",
				person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		
		
		//step 3 - call the teller scheduler
		assertTrue("Teller should have acted",teller.pickAndExecuteAnAction());
		
		//check post of 3 and pre of 4
		assertTrue("Teller should have a current customer now",teller.currentCustomer==customer);
		assertTrue("Customer log should show teller mesaged",customer.log.getLastLoggedEvent().getMessage().equals("Being helped"));
		
		//step 4 - tell the teller the cust is leaving
		teller.msgDoneAndLeaving();
		
		//check post of 4
		assertTrue("Teller shouldn't have a customer",teller.currentCustomer==null);
		assertTrue("Person should have a permit logged",person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		
		
		
		
	}
	
	
	public void testOneCustomerOneDeposit(){
		
//		//step 0 - put the customer in the bank
//		bank.addMeToQueue(customer);
		
		//verify that the customers have an empty log, teller hasn't started working
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller shouldn't be working yet",!teller.startedWorking);
		
		//step 1 - call the teller schedule
		assertTrue("Teller should have acted",!teller.pickAndExecuteAnAction());
		
		//check post of 1 and pre of 2
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller should be working now",teller.startedWorking);
		assertTrue("Teller's person should still be asleep",person.log.isEmpty());
		
		//step 2 - put the customer in the bank
		bank.addMeToQueue(customer);
		teller.msgStateChanged();
		
		//check post of 2 and pre of 3
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller should be working",teller.startedWorking);
		assertTrue("Teller's person should have been woken up by customer entering.",
				person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		assertTrue("Bank should have teller on record",bank.log.getLastLoggedEvent().getMessage().equals("New teller working"));
		
		//step 3 - call the teller scheduler
		assertTrue("Teller should have acted",teller.pickAndExecuteAnAction());
		
		//check post of 3 and pre of 4
		assertTrue("Teller should have a current customer now",teller.currentCustomer==customer);
		assertTrue("Customer log should show teller mesaged",customer.log.getLastLoggedEvent().getMessage().equals("Being helped"));
		
		//step 4 - tell the teller what the cust wants
		deposit d = new deposit(100,0,"passWord");
		teller.msgIWantTo(d);
		
		//check post of 4
		assertTrue("Teller should have a new task",teller.currentTask==d);
		assertTrue("Person should have a permit logged",person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		
		//step 5 - call the teller's scheduler
		assertTrue("Teller should have acted",teller.pickAndExecuteAnAction());
		
		//check post of 5 and pre of 6
		assertTrue("Banks should have a recod of the deposit",bank.log.getLastLoggedEvent().getMessage().equals("Received deposit of $100, account number 0, password passWord"));
		assertTrue("Customer should have been notified",customer.log.getLastLoggedEvent().getMessage().equals("My deposit was completed"));
		assertTrue("Teller should have killed currentTask", teller.currentTask==null);
		
		//step 6 - tell the teller the cust is leaving
		teller.msgDoneAndLeaving();
		
		//check post of 6
		assertTrue("Teller shouldn't have a customer",teller.currentCustomer==null);
		assertTrue("Person should have a permit logged",person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));


		
		
	}
		
		
	public void testOneCustomerOneWithdrawal(){
		
//		//step 0 - put the customer in the bank
//		bank.addMeToQueue(customer);
		
		//verify that the customers have an empty log, teller hasn't started working
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller shouldn't be working yet",!teller.startedWorking);
		
		//step 1 - call the teller schedule
		assertTrue("Teller should have acted",!teller.pickAndExecuteAnAction());
		
		//check post of 1 and pre of 2
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller should be working now",teller.startedWorking);
		assertTrue("Teller's person should still be asleep",person.log.isEmpty());
		
		//step 2 - put the customer in the bank
		bank.addMeToQueue(customer);
		teller.msgStateChanged();
		
		//check post of 2 and pre of 3
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller should be working",teller.startedWorking);
		assertTrue("Teller's person should have been woken up by customer entering.",
				person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		assertTrue("Bank should have teller on record",bank.log.getLastLoggedEvent().getMessage().equals("New teller working"));
		
		//step 3 - call the teller scheduler
		assertTrue("Teller should have acted",teller.pickAndExecuteAnAction());
		
		//check post of 3 and pre of 4
		assertTrue("Teller should have a current customer now",teller.currentCustomer==customer);
		assertTrue("Customer log should show teller mesaged",customer.log.getLastLoggedEvent().getMessage().equals("Being helped"));
		
		//step 4 - tell the teller what the cust wants
		withdrawal d = new withdrawal(100,0,"passWord");
		teller.msgIWantTo(d);
		
		//check post of 4
		assertTrue("Teller should have a new task",teller.currentTask==d);
		assertTrue("Person should have a permit logged",person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		
		//step 5 - call the teller's scheduler
		assertTrue("Teller should have acted",teller.pickAndExecuteAnAction());
		
		//check post of 5 and pre of 6
		System.out.println(bank.log.getLastLoggedEvent().getMessage());
		assertTrue("Banks should have a record of the withdrawal",bank.log.getLastLoggedEvent().getMessage().equals("Processed withdrawal of $100, account number 0, password passWord"));
		assertTrue("Customer should have been notified",customer.log.getLastLoggedEvent().getMessage().equals("My withdrawal was completed"));
		assertTrue("Teller should have killed currentTask", teller.currentTask==null);
		
		//step 6 - tell the teller the cust is leaving
		teller.msgDoneAndLeaving();
		
		//check post of 6
		assertTrue("Teller shouldn't have a customer",teller.currentCustomer==null);
		assertTrue("Person should have a permit logged",person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));


		
		
	}
	
	
	public void testOneCustomerOneOpenAccount(){
		
//		//step 0 - put the customer in the bank
//		bank.addMeToQueue(customer);
		
		//verify that the customers have an empty log, teller hasn't started working
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller shouldn't be working yet",!teller.startedWorking);
		
		//step 1 - call the teller schedule
		assertTrue("Teller should have acted",!teller.pickAndExecuteAnAction());
		
		//check post of 1 and pre of 2
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller should be working now",teller.startedWorking);
		assertTrue("Teller's person should still be asleep",person.log.isEmpty());
		
		//step 2 - put the customer in the bank
		bank.addMeToQueue(customer);
		teller.msgStateChanged();
		
		//check post of 2 and pre of 3
		assertTrue("Customer should have an empty log",customer.log.isEmpty());
		assertTrue("Teller should be working",teller.startedWorking);
		assertTrue("Teller's person should have been woken up by customer entering.",
				person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		assertTrue("Bank should have teller on record",bank.log.getLastLoggedEvent().getMessage().equals("New teller working"));
		
		//step 3 - call the teller scheduler
		assertTrue("Teller should have acted",teller.pickAndExecuteAnAction());
		
		//check post of 3 and pre of 4
		assertTrue("Teller should have a current customer now",teller.currentCustomer==customer);
		assertTrue("Customer log should show teller mesaged",customer.log.getLastLoggedEvent().getMessage().equals("Being helped"));
		
		//step 4 - tell the teller what the cust wants
		openAccount d = new openAccount(100,"c0");
		teller.msgIWantTo(d);
		
		//check post of 4
		assertTrue("Teller should have a new task",teller.currentTask==d);
		assertTrue("Person should have a permit logged",person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		
		//step 5 - call the teller's scheduler
		assertTrue("Teller should have acted",teller.pickAndExecuteAnAction());
		
		//check post of 5 and pre of 6
		//System.out.println(bank.log.getLastLoggedEvent().getMessage());
		String passWord = customer.passWord;
		assertTrue("Banks should have a record of the account opnening",bank.log.getLastLoggedEvent().getMessage().equals("Opened account with name c0, amount 100, password "+passWord));
		assertTrue("Customer should have been notified",customer.log.getLastLoggedEvent().getMessage().equals("My openAccount was completed"));
		assertTrue("Teller should have killed currentTask", teller.currentTask==null);
		
		//step 6 - tell the teller the cust is leaving
		teller.msgDoneAndLeaving();
		
		//check post of 6
		assertTrue("Teller shouldn't have a customer",teller.currentCustomer==null);
		assertTrue("Person should have a permit logged",person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));


		
		
	}
	
	
	
}
