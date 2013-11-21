package UnitTests.mock.bankMock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;
import interfaces.BankCustomer;
import interfaces.BankInterface;
import interfaces.BankTeller;

public class MockBank extends Mock implements BankInterface{

	List<BankCustomer> bankCustomers = Collections.synchronizedList(new ArrayList<BankCustomer>());
	
	@Override
	public boolean addMeToQueue(BankCustomer b) {
		bankCustomers.add(b);
		return false;
	}

	@Override
	public boolean startTellerShift(BankTeller t) {
		log.add(new LoggedEvent("New teller working"));
		return true;
	}

	@Override
	public BankCustomer getCustomer() {
		// TODO Auto-generated method stub
		
		BankCustomer c = bankCustomers.get(0);
		bankCustomers.remove(0);
		return c;
	}

	@Override
	public int addAccount(String custName, int amount, String passWord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deposit(int amount, int accountNumber, String passWord) {
		
		log.add(new LoggedEvent("Received deposit"));
		
		return true;
	}

	@Override
	public boolean withdraw(int amount, int accountNumber, String passWord) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int giveLoan(int amount, int accountNumber, String passWord) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int rob(int amount) {
		// TODO Auto-generated method stub
		return 1;
	}

}
