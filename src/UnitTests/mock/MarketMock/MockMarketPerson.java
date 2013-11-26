package UnitTests.mock.MarketMock;

import role.Role;
import util.BusStop;
import util.Loc;
import interfaces.Person;
import UnitTests.mock.Mock;

public class MockMarketPerson extends Mock implements Person {

	
	
	public MockMarketPerson(String name) {
		super(name);

	}
	
	@Override
	public void msgStateChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgBusAtStop(BusStop stop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgCarArrivedAtLoc(Loc destination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToWallet(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeFromWallet(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWalletAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addFoodToBag(String type, int quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToAccount(int accNumber, int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeFromAccount(int accNumber, int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createAccount(int accountNumber, int amount, String name,
			String passWord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLoan(int accountNumber, int cash, int loanNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgThisRoleDone(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneEating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putInBag(String item, int amount) {
		// TODO Auto-generated method stub
		
	}

}
