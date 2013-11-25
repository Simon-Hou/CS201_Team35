package interfaces.restaurantLinda;

import interfaces.BaseRestaurantCook;

import java.util.Map;

public interface Cook extends BaseRestaurantCook{
	
	public abstract void msgHereIsOrder(Waiter w, String choice, int table);
	
	public abstract void msgCannotFulfillOrder(Market m, Map<String, Integer> items);
	
	public abstract void msgFoodShipment(Map<String, Integer> items);

}
