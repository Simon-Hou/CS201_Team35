package bank.test;

import util.Bank;
import bank.BankCustomerRole;
import bank.BankTellerRole;
import bank.test.mock.MockPerson;
import bank.test.mock.MockTeller;
import junit.framework.*;

public class CustomerTest extends TestCase{
	
	//Unit test for the BankCustomerRole
	
	BankCustomerRole customer;
	MockTeller teller;
	Bank bank;
	
	MockPerson person;
	
	
	public void setUp() throws Exception {
		super.setUp();
		
		person = new MockPerson("p0");
		customer = new BankCustomerRole("c0",person);
		teller = new MockTeller("t0");
		bank = new Bank();
		
		
		
	}
	
	
	/** This test will examine the case of a single customer with no tasks
	 * 
	 */
	public void testNothingToDo(){
		
		//step 0 - make sure the customer doesn't do anythign yet
		assertFalse("Customer shouldn't be doing anything",customer.pickAndExecuteAnAction());
		
		//step 1 - put the customer in the bank
		customer.msgYouAreAtBank(bank);
		
		//check post of 1 and pre of 2
		assertTrue("Customer should be in bank.",customer.state == BankCustomerRole.CustState.inBank);
		assertTrue("Customer should have bank pointer", customer.bank != null);
		assertTrue("Person should still have an empty event log",person.log.isEmpty());
		
		//step 2 - call the customer scheduler
		assertTrue("Customer should've acted",customer.pickAndExecuteAnAction());
		
		//check post of 2 and pre of 3
		assertTrue("Bank should have customer in line.",bank.bankCustomers.get(0) == customer);
		assertTrue("Customer should know he's waiting in line",customer.state==BankCustomerRole.CustState.inLine);
		
		
		//step 3 - send him the message that he's being helped
		customer.msgHowCanIHelpYou(teller);	
		
		//check post of 3 and pre of 4
		assertTrue("Customer event should know he's being waited on",customer.event == BankCustomerRole.CustEvent.tellerReady);
		assertTrue("Cusomter should know who the teller who's helping him",customer.teller != null);
		System.out.println(person.log.getLastLoggedEvent());
		assertTrue("Person that holds customer should have a new permit",person.log.getLastLoggedEvent().getMessage().equals("Just got a new permit"));
		
		//step 4 - call the person scheduler
		assertTrue("Customer should've acted",customer.pickAndExecuteAnAction());
		
		//check post of 4 and pre of 5
		assertTrue("Customer should know he's being helped",customer.state == BankCustomerRole.CustState.beingServed);
		assertTrue("Teller should not have been asked for anything yet.",teller.log.isEmpty());
		
		//step 5 - call the scheduler
		assertTrue("Customer should've acted",customer.pickAndExecuteAnAction());
		
		//check post of 5 and pre of 6
		assertTrue("Customer should have tole teller he's leaving",teller.log.getLastLoggedEvent().getMessage().equals("My customer just left"));
		assertTrue("Customer should know he's leaving",customer.state == BankCustomerRole.CustState.leaving);
		assertTrue("Person should have been told that the role finished",person.log.getLastLoggedEvent().getMessage().equals("My BankCustomerRole just finished"));

		
		
		assertTrue(true);
		
	}
	
	
	
	
	
	
	
	
	
}
