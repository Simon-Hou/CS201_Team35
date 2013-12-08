package UnitTests.MarketUnitTests;

import static org.junit.Assert.*;
import interfaces.MarketCustomer;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import market.Market;
import market.MarketCashierRole;

import org.junit.Test;

import UnitTests.mock.EventLog;
import UnitTests.mock.MarketMock.MockMarketCustomer;
import UnitTests.mock.MarketMock.MockMarketDeliveryMan;
import UnitTests.mock.MarketMock.MockMarketEmployee;
import UnitTests.mock.MarketMock.MockMarketPerson;

public class MarketCashierTest extends TestCase{
	

	Market market;
	MockMarketPerson person;
	MarketCashierRole cashier;
	
	MockMarketEmployee employee;
	MockMarketCustomer customer;
	MockMarketDeliveryMan deliveryMan;
	
	public void setUp() throws Exception{
		super.setUp();	
		market = new Market();
		person = new MockMarketPerson("P0");
		cashier = new MarketCashierRole("Cashier", person, market);
		
		employee = new MockMarketEmployee("Employee");
		customer = new MockMarketCustomer("Customer");
		deliveryMan = new MockMarketDeliveryMan("DeliveryMan");
		
	}
	
	@Test
	public void testOne_NormalCustomerInteraction() {
		//preconditions
		assertEquals("Cashier's event log should be empty.", 0, cashier.log.size());
		assertEquals("Cashier's customer list should be empty.", 0, cashier.customers.size());
		assertEquals("Cashier's order list should be empty.", 0, cashier.orders.size());
		
		//Step 1 : send msgServiceCustomer
		Map<String, Integer> groceries = new HashMap<String, Integer>();
		cashier.msgServiceCustomer(customer,  groceries);
		assertTrue("Cashier's log should have received the message" + cashier.log.toString(), cashier.log.containsString("got msgServiceCustomer"));
		assertEquals("Cashier's customer list should be 1", 1, cashier.customers.size());
		
		//Step 2 : call scheduler
		assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
		assertTrue("Cashier's log should have record of action but has: " + cashier.log.getLastLoggedEvent(), cashier.log.containsString("action ComputeTotal"));
		
		

	}

}
