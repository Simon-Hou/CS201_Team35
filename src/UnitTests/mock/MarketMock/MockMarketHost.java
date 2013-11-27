package UnitTests.mock.MarketMock;

import java.util.Map;

import restaurant.Restaurant;
import market.Market;
import market.Receipt;
import interfaces.MarketCustomer;
import interfaces.MarketEmployee;
import interfaces.MarketHost;
import UnitTests.mock.Mock;

public class MockMarketHost extends Mock implements MarketHost{

	String name;
	public MarketCustomer customer;
	
	public MockMarketHost(String n){
		name = n;
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
	public void msgCustomerWantsThis(MarketCustomer c,
			Map<String, Integer> orderList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgCustomerLeaving(MarketCustomer c, Receipt receipt,
			Map<String, Integer> groceries) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean YouAreDoneWithShift() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean NewEmployee(MarketEmployee m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMarket(Market m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgBusinessWantsThis(Restaurant r, Map<String, Integer> order) {
		// TODO Auto-generated method stub
		
	}

}
