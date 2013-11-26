package interfaces;

import java.util.List;
import java.util.Map;

import market.MarketInvoice;
import market.Market;
import market.OrderItem;

public interface BaseRestaurantCook {
	public abstract void msgHereIsDelivery(MarketInvoice order);

	public abstract void msgCannotFulfillOrder(Market m, Map<String,Integer> unfulfillable);

}
