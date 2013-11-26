package interfaces;

import java.util.List;
import java.util.Map;

import restaurant.Restaurant;

import market.MarketInvoice;
import market.OrderItem;

public interface MarketEmployee extends Occupation {
	
	public void msgGetItemsForCustomer(MarketCustomer c, Map<String, Integer> orderList);
	public void msgGetThis(List<OrderItem> order, Restaurant r);
	public void msgGiveInvoice(int invoice, MarketInvoice order);
	public String getName();
	

}
