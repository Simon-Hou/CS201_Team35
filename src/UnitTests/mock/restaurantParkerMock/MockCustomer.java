package UnitTests.mock.restaurantParkerMock;

import restaurant.interfaces.Customer;
import restaurant.CashierAgent;

public class MockCustomer extends Mock implements Customer{

	public CashierRole cashier;
	
	//had to add myself
	public EventLog log = new EventLog();
	
	public MockCustomer(String name) {
		super(name);
	}

	@Override
	public void msgGiveChange(int change) {
		log.add(new LoggedEvent("Received change of $" + change));
	}
	
	
}




