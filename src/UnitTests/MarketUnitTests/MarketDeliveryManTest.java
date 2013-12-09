package UnitTests.MarketUnitTests;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import market.Market;
import market.MarketDeliveryManRole;
import market.MarketHostRole;
import market.MarketInvoice;

import org.junit.Test;

import UnitTests.mock.MarketMock.MockMarketCustomer;
import UnitTests.mock.MarketMock.MockMarketEmployee;
import UnitTests.mock.MarketMock.MockMarketPerson;

public class MarketDeliveryManTest extends TestCase{

	MarketDeliveryManRole deliveryMan;

	Market market;
	MockMarketPerson person;
	MarketHostRole host;

	MockMarketCustomer customer;
	MockMarketEmployee employee;



	public void setUp() throws Exception{
		super.setUp();	

		person = new MockMarketPerson("P0");
		market = new Market();
		host = new MarketHostRole("Host", person, market);

		customer = new MockMarketCustomer("C1");
		employee = new MockMarketEmployee("E1");

		deliveryMan = new MarketDeliveryManRole("deliveryMan", person, market);

		customer.host = host;
		employee.host = host;


	}



	@Test
	public void testOne_NormalDeliveryManDeliveryOrderScenario() {
		//Preconditions
		assertEquals("Delivery Man's event log should be empty.", 0, deliveryMan.log.size());
		assertEquals("Delivery Man's orders should be empty.", 0, deliveryMan.orders.size());
		assertEquals("Delivery Man's paymentsshould be empty.", 0, deliveryMan.payments.size());

		assertFalse("Delivery Man's sheduler should return false", deliveryMan.pickAndExecuteAnAction());

		//Step 1
		MarketInvoice tempOrder = new MarketInvoice(null, null, null, 5);
		deliveryMan.msgDeliverThisOrder(tempOrder);

		//Post Step 1
		assertEquals("Delivery Man's orders should have size 1 but are " + deliveryMan.orders.size(), 1, deliveryMan.orders.size());
		assertTrue("Delivery Man's log should have received mesage 'got msgDeliverThisOrder', but it reads " + deliveryMan.log.getLastLoggedEvent(), deliveryMan.log.getLastLoggedEvent().toString().endsWith("got msgDeliverThisOrder"));

		//Setp 2
		assertTrue("Delivery Man's scheduler should return true", deliveryMan.pickAndExecuteAnAction());

		//Post Step 2
		assertTrue("Delivery Man's log should have received mesage 'action DeliverOrder', but it reads " + deliveryMan.log.getLastLoggedEvent(), deliveryMan.log.getLastLoggedEvent().toString().endsWith("action DeliverOrder"));
		
		//Step 3
		assertFalse("Delivery Man's scheduler should return false", deliveryMan.pickAndExecuteAnAction());
	}

	public void testTwo_NormalDeliveryManDeliveryOrderScenario() {
		//Preconditions
		assertEquals("Delivery Man's event log should be empty.", 0, deliveryMan.log.size());
		assertEquals("Delivery Man's orders should be empty.", 0, deliveryMan.orders.size());
		assertEquals("Delivery Man's paymentsshould be empty.", 0, deliveryMan.payments.size());

		assertFalse("Delivery Man's sheduler should return false", deliveryMan.pickAndExecuteAnAction());

		//Step 1
		MarketInvoice tempOrder = new MarketInvoice(null, null, null, 5);
		deliveryMan.msgHereIsPayment(10, tempOrder);

		//Post Step 1
		assertEquals("Delivery Man's payments should have size 1 but are " + deliveryMan.payments.size(), 1, deliveryMan.payments.size());
		assertTrue("Delivery Man's log should have received mesage 'got msgHereIsPayment', but it reads " + deliveryMan.log.getLastLoggedEvent(), deliveryMan.log.getLastLoggedEvent().toString().endsWith("got msgHereIsPayment"));

		//Setp 2
		assertTrue("Delivery Man's scheduler should return true", deliveryMan.pickAndExecuteAnAction());

		//Post Step 2
		assertEquals("Delivery Man's payments should ow be empty", 0, deliveryMan.payments.size());
		assertTrue("Delivery Man's log should have received mesage 'action DeliverPayment', but it reads " + deliveryMan.log.getLastLoggedEvent(), deliveryMan.log.getLastLoggedEvent().toString().endsWith("action DeliverPayment"));
		
		//Step 3
		assertFalse("Delivery Man's scheduler should return false", deliveryMan.pickAndExecuteAnAction());
	}

}
