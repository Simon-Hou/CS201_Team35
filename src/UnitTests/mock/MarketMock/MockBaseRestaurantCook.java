package UnitTests.mock.MarketMock;

import java.util.Map;

import market.Market;
import market.MarketInvoice;
import interfaces.BaseRestaurantCook;
import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;

public class MockBaseRestaurantCook extends Mock implements BaseRestaurantCook{

	MockBaseRestaurantCook(String name){
		super(name);
	}
	
	
	@Override
	public boolean canLeave() {
		return false;
	}

	@Override
	public void msgHereIsDelivery(MarketInvoice order) {
		log.add(new LoggedEvent("Received delivery of food"));		
	}

	@Override
	public void msgCannotFulfillOrder(Market m,	Map<String, Integer> unfulfillable) {
		log.add(new LoggedEvent("Received message from market that these items from the order are unfulfillable: " + unfulfillable.toString()));
		
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
