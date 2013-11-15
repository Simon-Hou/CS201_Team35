package interfaces;

import java.util.Map;

import market.BusinessOrder;
import market.Receipt;

public interface MarketHost {
	
	public void msgCustomerWantsThis(MarketCustomer c, Map<String, Integer> orderList);
	public void msgCustomerLeaving(MarketCustomer c, Receipt receipt, Map<String, Integer> groceries);
	public void msgBusinessWantsThis(BusinessOrder order);

}
