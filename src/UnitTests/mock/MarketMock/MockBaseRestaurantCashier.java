package UnitTests.mock.MarketMock;

import market.MarketInvoice;
import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;
import interfaces.BaseRestaurantCashier;
import interfaces.MarketDeliveryMan;

public class MockBaseRestaurantCashier extends Mock implements BaseRestaurantCashier{

	MockBaseRestaurantCashier(String name){
		super(name);
	}
	
	@Override
	public boolean canLeave() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgHereIsInvoice(MarketDeliveryMan deliveryMan,	MarketInvoice order) {
		log.add(new LoggedEvent("Received invoice from deliveryMan " + deliveryMan.getName() + " to pay for order " + order.order));
		
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
