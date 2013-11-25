package interfaces;

import java.util.List;

import market.BusinessOrder;
import market.Market;
import market.OrderItem;

public interface BaseRestaurantCook {
	public abstract void msgHereIsDelivery(BusinessOrder order);

	public abstract void msgCannotFulfillOrder(Market m, List<OrderItem> unfulfillable);

}
