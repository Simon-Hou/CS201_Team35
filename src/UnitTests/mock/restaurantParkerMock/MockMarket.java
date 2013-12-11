package UnitTests.mock.restaurantParkerMock;

import restaurant.interfaces.Market;
import restaurant.CashierAgent;

public class MockMarket extends Mock implements Market{

	public CashierRole cashier;
	public EventLog log = new EventLog();
	
	public MockMarket(String name){
		super(name);
	}
		
	@Override
	public void msgBillPayment(int payment) {
		log.add(new LoggedEvent("Received payment."));
		
	}

}
