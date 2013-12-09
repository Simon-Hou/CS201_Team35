package UnitTests.MarketUnitTests;

import static org.junit.Assert.*;
import interfaces.MarketCustomer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import market.Market;
import market.MarketCashierRole;
import market.OrderItem;

import org.junit.Test;

import restaurant.Restaurant;
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
	Restaurant rest;
	
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
		try {
			Thread.sleep(groceries.size() + 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Cashier's log should have received message 'done computing bill' but reads : " + cashier.log.getLastLoggedEvent(), cashier.log.containsString("done computing bill"));

		//Pre Step 3
		assertEquals("Customers log should be empty but its actual size is: " + customer.log.size(), customer.log.size(), 0);
		
		//Step 3 : call scheduler
		assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
		assertTrue("Cashier's log should have record of action AskCustomerToPay but has: " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent().toString().endsWith("action AskCustomerToPay"));
		assertTrue("Customer's log should have record of 'Got total for $0' but has: " + customer.log.getLastLoggedEvent(), customer.log.getLastLoggedEvent().toString().endsWith("Got total for: $0"));

		//Post Step 3
		assertEquals("Customers log should be size 1, but its actual size is: " + customer.log.size(), customer.log.size(), 1);
		
		//Step 4
		cashier.msgCustomerPayment(customer, 0);
		assertTrue("Cashier's log should have received the message 'got msgCustomerPayment for: $0', but got " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent().toString().endsWith("got msgCustomerPayment for: $0"));
		
		//Step 5
		assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
		assertTrue("Cashier's log should have record of action AcceptPayment but has: " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent(2).toString().endsWith("action AcceptPayment"));
		assertTrue("Cashier's log should have record of 'I owe the customer change: $' but has: " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent().toString().endsWith("I owe the customer change: $0"));
		assertTrue("Customer's log should have record of 'Got chage of: $0' but has: " + customer.log.getLastLoggedEvent(), customer.log.getLastLoggedEvent().toString().endsWith("Got chage of: $0"));
		
		//Step 6
		assertFalse("Scheduler should return false", cashier.pickAndExecuteAnAction());
	}
	
	public void testTwo_NormalDeliveryManInteraction() {
		//preconditions
		OrderItem tempOrder = new OrderItem("Steak", 5);
		tempOrder.quantityReceived = 5;
		ArrayList<OrderItem> listItems = new ArrayList<OrderItem>();
		listItems.add(tempOrder);
		assertEquals("Cashier's event log should be empty.", 0, cashier.log.size());
		assertEquals("Cashier's customer list should be empty.", 0, cashier.customers.size());
		assertEquals("Cashier's order list should be empty.", 0, cashier.orders.size());
		
		//Step 1 : send msgCalculateInvoice
		Map<String, Integer> groceries = new HashMap<String, Integer>();
		cashier.msgCalculateInvoice(employee, listItems, rest);
		assertTrue("Cashier's log should have received the message" + cashier.log.toString(), cashier.log.getLastLoggedEvent().toString().endsWith("got msgCalculateInvoice"));
		assertEquals("Cashier's order list should be 1", 1, cashier.orders.size());
		
		//Step 2 : call scheduler
		assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
		assertTrue("Cashier's log should have record of 'action ComputeBusinessPayment', but has: " + cashier.log.getLastLoggedEvent(2), cashier.log.containsString("action ComputeBusinessPayment"));
		assertTrue("Cashiers's log should have 'got msgGiveInvoice for $10' but has : " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent().toString().endsWith("got msgGiveInvoice for $10"));
//		try {
//			Thread.sleep(groceries.size() + 100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		assertTrue("Cashier's log should have received message 'done computing bill' but reads : " + cashier.log.getLastLoggedEvent(), cashier.log.containsString("done computing bill"));

//		//Pre Step 3
//		assertEquals("Customers log should be empty but its actual size is: " + customer.log.size(), customer.log.size(), 0);
		
		//Step 3 : call scheduler
		assertFalse("Scheduler should return false", cashier.pickAndExecuteAnAction());
		assertTrue("Cashier's log should have record of action AskCustomerToPay but has: " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent().toString().endsWith("action AskCustomerToPay"));
		assertTrue("Customer's log should have record of 'Got total for $0' but has: " + customer.log.getLastLoggedEvent(), customer.log.getLastLoggedEvent().toString().endsWith("Got total for: $0"));

		//Post Step 3
		assertEquals("Customers log should be size 1, but its actual size is: " + customer.log.size(), customer.log.size(), 1);
		
		//Step 4 message the  the customer and receive payment for it
		cashier.msgCustomerPayment(customer, 0);
		assertTrue("Cashier's log should have received the message 'got msgCustomerPayment for: $0', but got " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent().toString().endsWith("got msgCustomerPayment for: $0"));
		
		//Step 5 call scheduler to give change to customer
		assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
		assertTrue("Cashier's log should have record of action AcceptPayment but has: " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent(2).toString().endsWith("action AcceptPayment"));
		assertTrue("Cashier's log should have record of 'I owe the customer change: $' but has: " + cashier.log.getLastLoggedEvent(), cashier.log.getLastLoggedEvent().toString().endsWith("I owe the customer change: $0"));
		assertTrue("Customer's log should have record of 'Got chage of: $0' but has: " + customer.log.getLastLoggedEvent(), customer.log.getLastLoggedEvent().toString().endsWith("Got chage of: $0"));
		
		//Step 6
		assertFalse("Scheduler should return false", cashier.pickAndExecuteAnAction());
	}

}
