package bank.test.mock;

import role.Role;
import bank.interfaces.Person;

public class MockPerson extends Mock implements Person {

	public EventLog log = new EventLog();
	
	public MockPerson(String name) {
		super(name);

	}

	public void msgStateChanged() {
		
	}

	public void addToWallet(int amount) {
		
	}

	public void takeFromWallet(int amount) {
		
	}

	public void addToAccount(int accNumber, int amount) {
		
	}

	public void takeFromAccount(int accNumber, int amount) {
		
	}

	public void createAccount(int accountNumber, int amount, String name,
			String passWord) {
		
	}

	public void addLoan(int accountNumber, int cash, int loanNumber) {
		
	}

	public void msgThisRoleDone(Role role) {
		
	}

}
