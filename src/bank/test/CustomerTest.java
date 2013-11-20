package bank.test;

import util.Bank;
import bank.BankCustomerRole;
import bank.test.mock.MockPerson;
import junit.framework.*;

public class CustomerTest extends TestCase{
	
	//Unit test for the BankCustomerRole
	
	BankCustomerRole customer;
	Bank bank;
	MockPerson person;
	
	
	public void setUp() throws Exception {
		super.setUp();
		
		customer = new BankCustomerRole("c0",person);
		bank = new Bank();
		
	}
	
	
	/** This test will examine the case of a single customer with no tasks
	 * 
	 */
	public void testNothingToDo(){
		
		//step 1 - put the customer in the bank
		customer.msgYouAreAtBank(bank);
		
		//check post of 1 and pre of 2
		assertTrue("Customer should be in bank.",customer.state == BankCustomerRole.CustState.inBank);
		assertTrue("Customer should have bank pointer", customer.bank != null);
		assertTrue("Person should still have an empty event log",person.log.isEmpty());
		
		//step 2 - call the customer scheduler
		customer.pickAndExecuteAnAction();
		
		//check post of 2 and pre of 3
		
		
		
		assertTrue(true);
		
	}
	
	
	
	
	
	
	
	
	
}
