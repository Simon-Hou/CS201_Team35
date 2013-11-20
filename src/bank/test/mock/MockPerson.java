package bank.test.mock;

import role.Role;
import bank.interfaces.Person;

public class MockPerson extends Mock implements Person {

	public EventLog log = new EventLog();
	
	public MockPerson(String name) {
		super(name);

	}

	public void msgStateChanged() {
		log.add(new LoggedEvent("Just got a new permit"));
		//System.out.println(log.getLastLoggedEvent());
	}

	public void addToWallet(int amount) {
		log.add(new LoggedEvent("Added to wallet"));
	}

	public void takeFromWallet(int amount) {
		log.add(new LoggedEvent("Took from wallet"));
	}

	public void addToAccount(int accNumber, int amount) {
		log.add(new LoggedEvent("Added to account"));
	}

	public void takeFromAccount(int accNumber, int amount) {
		log.add(new LoggedEvent("Took from account"));
	}

	public void createAccount(int accountNumber, int amount, String name,
			String passWord) {
		
		log.add(new LoggedEvent("Created a new account"));
		
	}

	public void addLoan(int accountNumber, int cash, int loanNumber) {
		
	}

	public void msgThisRoleDone(Role role) {
		log.add(new LoggedEvent("My BankCustomerRole just finished"));
	}

}
