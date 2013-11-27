package UnitTests.mock.MarketMock;

import market.MarketInvoice;
import interfaces.MarketDeliveryMan;
import UnitTests.mock.EventLog;
import UnitTests.mock.Mock;

public class MockMarketDeliveryMan extends Mock implements MarketDeliveryMan{
	
	public EventLog log = new EventLog();
	String name;
	
	public MockMarketDeliveryMan(String name){
		this.name = name;
	}

	@Override
	public boolean canLeave() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgDeliverThisOrder(MarketInvoice order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsPayment(int payment, MarketInvoice invoice) {
		// TODO Auto-generated method stub
		
	}
	
	

}
