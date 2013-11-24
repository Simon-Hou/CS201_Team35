package interfaces.restaurantLinda;

import java.util.Map;

import restaurantLinda.Check;

public interface Cashier {
	
	public abstract void msgPleaseComputeBill(Waiter w, String choice, Customer cust);
	
	public abstract void msgHereIsPayment(Customer cust, Check bill, int payment);
	
	public abstract void msgPleasePay(Market m, Map<String,Integer> order, int bill);
	
}
