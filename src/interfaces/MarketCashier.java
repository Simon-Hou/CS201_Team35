package interfaces;

import java.util.Map;

import market.BusinessOrder;
import market.MarketCashierRole.MyCustomer;

public interface MarketCashier extends Occupation{
	
	public void msgServiceCustomer(MarketCustomer c, Map<String, Integer> groceries);
	public void msgFinishedComputing(MyCustomer mc);
	public void msgCustomerPayment(MarketCustomer c, int payment);
	public void msgHereIsBusinessPayment(int payment);
	public void msgCalculateInvoice(BusinessOrder order, MarketEmployee employee);

	public String getName();
	public abstract boolean YouAreDoneWithShift();
	

}

