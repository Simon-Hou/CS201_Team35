package interfaces;

import market.MarketInvoice;

public interface MarketDeliveryMan extends Occupation {

	public void msgDeliverThisOrder(MarketInvoice order);
	public void msgHereIsPayment(int payment);
	
	public String getName();
	
}
