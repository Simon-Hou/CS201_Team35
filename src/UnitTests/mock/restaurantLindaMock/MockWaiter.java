package UnitTests.mock.restaurantLindaMock;

import java.util.List;

import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;

import interfaces.restaurantLinda.*;


public class MockWaiter extends Mock implements Waiter{
	
	public MockWaiter(String name){
		super(name);
	}

	@Override
	public void msgPleaseServeCustomer(Customer c, int table) {
		log.add(new LoggedEvent("Received message to serve customer " + c.getName() + " at table " + table));
		
	}

	@Override
	public void msgReadyToOrder(Customer c) {
		log.add(new LoggedEvent("Received message that customer " + c.getName() + " is ready to order"));
		
	}

	@Override
	public void msgHereIsChoice(Customer c, String choice) {
		log.add(new LoggedEvent("Customer " + c.getName() + " has ordered " + choice));
		
	}

	@Override
	public void msgOutOfChoice(String choice, int table, List<String> unavailable) {
		log.add(new LoggedEvent("Received message from cook saying choice " + choice + " ordered by table " + table + " is out. List of all unavailable foods: " + unavailable));
	}

	@Override
	public void msgOrderDone(String choice, int table) {
		log.add(new LoggedEvent("Received message from cook saying that the order for " + choice + " from table " + table + " is done"));
	}

	@Override
	public void msgHereIsBill(String choice, Customer c, int total) {
		log.add(new LoggedEvent("Received message from cashier with the newly computed bill for customer " + c.getName() + " who ordered " + choice + ". Total is " + total));		
	}

	@Override
	public void msgLeaving(Customer c) {
		log.add(new LoggedEvent("Received message Leaving from customer " + c.getName()));
	}

	@Override
	public void msgDoneAndPaying(Customer c) {
		log.add(new LoggedEvent("Received message DoneAndPaying from customer " + c.getName()));
		
	}

	@Override
	public void msgBreakStatus(boolean permission) {
		if (permission)
			log.add(new LoggedEvent("Received permission from the host to go on break"));
		else
			log.add(new LoggedEvent("Received message from host denying request to go on break"));
		
	}

}
