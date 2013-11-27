package UnitTests.mock.MarketMock;

import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;
import market.MarketInvoice;
import interfaces.MarketDeliveryMan;

public class MockMarketDeliveryMan extends Mock implements MarketDeliveryMan {
	boolean canLeave = true;
	
	@Override
	public boolean canLeave() {
		return canLeave;
	}

	@Override
	public void msgDeliverThisOrder(MarketInvoice order) {
		log.add(new LoggedEvent("Received message to deliver order: " + order.order.toString() + " with total " + order.total + " to restaurant."));		
	}

	@Override
	public void msgHereIsPayment(int payment, MarketInvoice invoice) {
		log.add(new LoggedEvent("Received payment of " + payment + " for invoice which includes order " + invoice.order.toString() + " with total " + invoice.total));		
	}

	@Override
	public String getName() {
		return "MockMarketDeliveryMan";
	}

}
