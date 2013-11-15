package interfaces;

import java.util.Map;

import market.BusinessOrder;

public interface MarketEmployee {
	
	public void msgGetItemsForCustomer(MarketCustomer c, Map<String, Integer> orderList);
	public void msgGetThis(BusinessOrder order);
	public void msgDeliverThisOrder(BusinessOrder order);
	public void msgHereIsPayment(int payment);

}
