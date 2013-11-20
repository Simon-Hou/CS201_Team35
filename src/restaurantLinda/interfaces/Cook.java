package restaurantLinda.interfaces;

import java.util.Map;

public interface Cook {
	
	public abstract void msgHereIsOrder(Waiter w, String choice, int table);
	
	public abstract void msgCannotFulfillOrder(Market m, Map<String, Integer> items);
	
	public abstract void msgFoodShipment(Map<String, Integer> items);

}
