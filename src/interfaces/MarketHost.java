package interfaces;

import java.util.Map;

import market.BusinessOrder;
import market.Receipt;

public interface MarketHost {
	
	public void CustomerWantsThis(MarketCustomer c, Map<String, Integer> orderList);
	public void CustomerLeaving(MarketCustomer c, Receipt receipt, Map<String, Integer> groceries);
	public void BusinessWantsThis(BusinessOrder order);

}
