package UnitTests.mock.MarketMock;

import java.util.List;
import java.util.Map;

import restaurant.Restaurant;
import market.OrderItem;
import interfaces.MarketCustomer;
import interfaces.MarketDeliveryMan;
import interfaces.MarketEmployee;
import interfaces.MarketHost;
import UnitTests.mock.EventLog;
import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;

public class MockMarketEmployee extends Mock implements MarketEmployee{

	public EventLog log = new EventLog();
	String name;
	public MarketCustomer customer;
	public MarketHost host;
	
	public MockMarketEmployee(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public boolean canLeave() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgGetItemsForCustomer(MarketCustomer c,
			Map<String, Integer> orderList) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("got msgGetItemsForCustomer"));
		
	}



	@Override
	public void msgGetThis(List<OrderItem> order, Restaurant r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGiveInvoice(List<OrderItem> order, Restaurant r, int total) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDeliveryMan(MarketDeliveryMan m) {
		// TODO Auto-generated method stub
		
	}

}
