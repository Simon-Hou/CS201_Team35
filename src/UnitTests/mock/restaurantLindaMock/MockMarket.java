package UnitTests.mock.restaurantLindaMock;

import java.util.Map;

import interfaces.restaurantLinda.*;


public class MockMarket extends Mock implements Market{

	public MockMarket(String name){
		super(name);
	}
	
	@Override
	public void msgHereIsOrder(Cook cook, Cashier cashier, Map<String, Integer> list) {
		log.add(new LoggedEvent("Received message from cook " + cook + " to prepare these items: " + list));
		
	}

	@Override
	public void msgHereIsPayment(Cashier c, Map<String, Integer> list, int payment) {
		log.add(new LoggedEvent("Received payment of " + payment + " from cashier " + c + " for order " + list));
		
	}

	

}
