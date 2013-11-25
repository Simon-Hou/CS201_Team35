package interfaces;

import market.BusinessOrder;

public interface MarketDeliveryMan extends Occupation {

	public void msgDeliverThisOrder(BusinessOrder order);
	public void msgHereIsPayment(int payment);
	
}
