package interfaces;

import java.util.Map;

import market.MarketCashierRole.MyCustomer;

public interface MarketCashier {
	
	public void msgPleaseServiceCustomer(MarketCustomer c, Map<String, Integer> groceries);
	public void msgFinishedComputing(MyCustomer mc);
	public void msgCustomerPayment(MarketCustomer c, int payment);
	public void msgHereIsBusinessPayment(int payment);

	public abstract boolean YouAreDoneWithShift();

}

