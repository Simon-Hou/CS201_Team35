package UnitTests.mock.MarketMock;

<<<<<<< HEAD
import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;
import market.MarketInvoice;
import interfaces.MarketDeliveryMan;

public class MockMarketDeliveryMan extends Mock implements MarketDeliveryMan {
	boolean canLeave = true;
	
	@Override
	public boolean canLeave() {
		return canLeave;
=======
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
>>>>>>> d19cceb95cb6aa68bf66e3860d3fbb391c16afaa
	}

	@Override
	public void msgDeliverThisOrder(MarketInvoice order) {
<<<<<<< HEAD
		log.add(new LoggedEvent("Received message to deliver order: " + order.order.toString() + " with total " + order.total + " to restaurant."));		
=======
		// TODO Auto-generated method stub
		
>>>>>>> d19cceb95cb6aa68bf66e3860d3fbb391c16afaa
	}

	@Override
	public void msgHereIsPayment(int payment, MarketInvoice invoice) {
<<<<<<< HEAD
		log.add(new LoggedEvent("Received payment of " + payment + " for invoice which includes order " + invoice.order.toString() + " with total " + invoice.total));		
	}

	@Override
	public String getName() {
		return "MockMarketDeliveryMan";
	}
=======
		// TODO Auto-generated method stub
		
	}
	
	
>>>>>>> d19cceb95cb6aa68bf66e3860d3fbb391c16afaa

}
