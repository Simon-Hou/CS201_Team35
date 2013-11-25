package interfaces;

import java.util.Map;

import market.BusinessOrder;
import market.Market;
import market.Receipt;

public interface MarketHost extends Occupation{
	
	public void msgCustomerWantsThis(MarketCustomer c, Map<String, Integer> orderList);
	public void msgCustomerLeaving(MarketCustomer c, Receipt receipt, Map<String, Integer> groceries);
	public void msgBusinessWantsThis(BusinessOrder order);

	public abstract boolean YouAreDoneWithShift();
	public abstract boolean NewEmployee(MarketEmployee m);
	
	public abstract void setMarket(Market m);
	
	
}
