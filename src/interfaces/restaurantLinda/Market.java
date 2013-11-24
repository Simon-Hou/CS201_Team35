package interfaces.restaurantLinda;

import java.util.Map;

public interface Market {
	
	public abstract void msgHereIsOrder(Cook cook, Cashier cashier, Map<String,Integer> list);
	
	public void msgHereIsPayment(Cashier c, Map<String, Integer> list, int payment);
	
	public abstract String toString();

}
