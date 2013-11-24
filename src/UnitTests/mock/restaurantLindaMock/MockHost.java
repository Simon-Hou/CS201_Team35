package UnitTests.mock.restaurantLindaMock;

import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;
import interfaces.restaurantLinda.*;

public class MockHost extends Mock implements Host{

	public MockHost(String name){
		super(name);
	}
	
	@Override
	public void msgIWantFood(Customer cust) {
		log.add(new LoggedEvent("Received IWantFood from customer " + cust.getName()));
		
	}

	@Override
	public void msgIWillWait(Customer cust, boolean wait) {
		log.add(new LoggedEvent("Customer " + cust.getName() + " has decided to wait"));
		
	}

	@Override
	public void msgCustomerLeaving(Waiter waiter, Customer cust, int t) {
		log.add(new LoggedEvent("Waiter " + waiter.getName() + " says customer " + cust.getName() + " is leaving table " + t));
		
	}

	@Override
	public void msgIWantBreak(Waiter waiter) {
		log.add(new LoggedEvent("Received IWantBreak from waiter " + waiter.getName()));
		
	}

	@Override
	public void msgOffBreak(Waiter waiter) {
		log.add(new LoggedEvent("Received OffBreak from waiter " + waiter.getName()));
		
	}

}
