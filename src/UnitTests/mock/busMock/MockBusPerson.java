package UnitTests.mock.busMock;

import role.Role;
import util.BusStop;
import util.Loc;
import interfaces.Person;
import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;

public class MockBusPerson extends Mock implements Person{

	
	
	
	
	
	@Override
	public void msgStateChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgBusAtStop(BusStop stop) {
		// TODO Auto-generated method stub
		
		log.add(new LoggedEvent("Told that the bus just arrived at stop with "
				+ "location ("+stop.location.x+","+stop.location.y+")"));
		
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
	public void msgCarArrivedAtLoc(Loc destination) {
		// TODO Auto-generated method stub
		
	}

}
