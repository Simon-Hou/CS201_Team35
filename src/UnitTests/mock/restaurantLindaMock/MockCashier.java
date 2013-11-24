package UnitTests.mock.restaurantLindaMock;

import java.util.Map;

import restaurantLinda.Check;
import interfaces.restaurantLinda.*;

public class MockCashier extends Mock implements Cashier{
	
	public MockCashier(String name){
		super(name);
	}
	
	@Override
	public void msgPleaseComputeBill(Waiter w, String choice, Customer cust) {
		log.add(new LoggedEvent("Received message from " + w.getName() + " to compute bill for customer " + cust.getName() + " who ordered " + choice));
		
	}

	@Override
	public void msgHereIsPayment(Customer cust, Check bill, int payment) {
		log.add(new LoggedEvent("Received payment of " + payment + " from customer " + cust.getName() + " for bill " + bill));
		
	}

	@Override
	public void msgPleasePay(Market m, Map<String, Integer> order, int bill) {
		// TODO Auto-generated method stub
		
	}

}
