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
	
	
	
	
	
	
	
	
	
}
