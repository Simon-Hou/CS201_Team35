package interfaces;

import market.BusinessOrder;

public interface MarketDeliveryMan {

	public void msgDeliverThisOrder(BusinessOrder order);
	public void msgHereIsPayment(int payment);
	
}
