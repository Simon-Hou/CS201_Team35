package interfaces;

import java.util.Map;

public interface MarketEmployee {
	
	public void GetItemsForCustomer(MarketCustomer c, Map<String, Integer> orderList);
	public void msgGetThis(BusinessOrder order);
	public void DeliverThisOrder(BusinessOrder order);
	public void HereIsPayment(int payment);

}
