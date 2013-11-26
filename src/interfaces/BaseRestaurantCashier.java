package interfaces;

import market.MarketInvoice;

public interface BaseRestaurantCashier {
	
	public abstract void msgHereIsInvoice(MarketDeliveryMan deliveryMan, MarketInvoice order);

}
