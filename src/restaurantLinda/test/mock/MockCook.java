package restaurantLinda.test.mock;

import java.util.Map;

import restaurant.interfaces.Cook;
import restaurant.interfaces.Market;
import restaurant.interfaces.Waiter;

public class MockCook extends Mock implements Cook {
	
	public MockCook(String name){
		super(name);
	}
	
	@Override
	public void msgHereIsOrder(Waiter w, String choice, int table) {
		log.add(new LoggedEvent("Received message from waiter " + w.getName() + " to cook " + choice + " for table " + table));
		
	}

	@Override
	public void msgCannotFulfillOrder(Market m, Map<String, Integer> items) {
		log.add(new LoggedEvent("Received message from market " + m + " that these orders cannot be fulfilled: " + items));
		
	}

	@Override
	public void msgFoodShipment(Map<String, Integer> items) {
		log.add(new LoggedEvent("Received shipment of items: " + items));
		
	}

	
}
