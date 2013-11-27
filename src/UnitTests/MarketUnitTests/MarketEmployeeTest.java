package UnitTests.MarketUnitTests;

import static org.junit.Assert.*;
import interfaces.MarketCustomer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import market.MarketEmployeeRole;
import market.OrderItem;

import org.junit.Test;

import cityGui.CityRestaurant;
import restaurant.Restaurant;
import UnitTests.mock.MarketMock.MockMarketCashier;
import UnitTests.mock.MarketMock.MockMarketCustomer;
import UnitTests.mock.MarketMock.MockMarketDeliveryMan;
import UnitTests.mock.MarketMock.MockMarketPerson;

public class MarketEmployeeTest extends TestCase{

	MockMarketPerson person;
	MarketEmployeeRole employee;
	
	MockMarketCustomer customer;
	MockMarketCashier cashier;
	MockMarketDeliveryMan deliveryMan;
	
	
	public void setUp() throws Exception{
		super.setUp();	
	
		person = new MockMarketPerson("P0");
		employee = new MarketEmployeeRole("Emp", person);
		
		customer = new MockMarketCustomer("Customer");
		cashier = new MockMarketCashier("Cashier");
		deliveryMan = new MockMarketDeliveryMan("DeliveryMan");
		
		employee.cashier = cashier;
		
	}
	
	
	@Test
	public void testOne_CustomerOrder() {
		//preconditions
		assertEquals("Employees customerOrders list should be empty", 0 , employee.customerOrders.size() );
		assertEquals("Employees businessOrders list should be empty", 0 ,  employee.businessOrders.size());
		assertEquals("Employee's name should be \"Emp\" ", "Emp", employee.getName());
		assertFalse("Scheduler should return false", employee.pickAndExecuteAnAction());
		
		//Step 1:  send msgGetItemsForCustomer
		Map<String, Integer> orderList = new HashMap<String, Integer>();
		orderList.put("Pizza", new Integer(4));
		employee.msgGetItemsForCustomer(customer, orderList);
		assertTrue("Employees log should have recorded getting message", employee.log.containsString("got msgGetItemsForCustomer"));
		assertEquals("Employees customerOrders list should have 1", 1 , employee.customerOrders.size() );
		
		//Step 2:  call scheduler
		assertTrue("Scheduler should return true", employee.pickAndExecuteAnAction());
		assertTrue("Employees log should have recorded action CollectItems", employee.log.containsString("action CollectItems"));
		assertTrue("Employees log should have recorded collecting item", employee.log.containsString("collecting item"));
		
		//Step 3:  call scheduler again
		assertTrue("Scheduler should return true", employee.pickAndExecuteAnAction());
		assertTrue("Employees log should have recorded action GiveItemsToCustomer", employee.log.containsString("action GiveItemsToCustomer"));
		assertEquals("Employees customerOrders list should have 0", 0 , employee.customerOrders.size() );
		assertTrue("Customer's log should have recorded msgHereAreItems", customer.log.containsString("got msgHereAreItems"));
		
	}

	@Test
	public void testTwo_BusinessOrder() {
		
		//preconditions
		assertEquals("Employees customerOrders list should be empty", 0 , employee.customerOrders.size() );
		assertEquals("Employees businessOrders list should be empty", 0 ,  employee.businessOrders.size());
		assertEquals("Employee's name should be \"Emp\" ", "Emp", employee.getName());
		assertFalse("Scheduler should return false", employee.pickAndExecuteAnAction());

		//Step 1:  send msgGetThis
		List<OrderItem> order = new ArrayList<OrderItem>();
		employee.msgGetThis(order, null);
		assertTrue("Employees log should have recorded getting message", employee.log.containsString("got msgGetThis"));
		assertEquals("Employees businessOrders list should have 1", 1 , employee.businessOrders.size() );
	
		//Step 2:  run scheduler
		assertTrue("Scheduler should return true", employee.pickAndExecuteAnAction());
		assertTrue("Employees log should have recorded action", employee.log.containsString("action GetBusinessOrder"));
		assertTrue("Cashier should have recorded getting message", cashier.log.containsString("got msgCalculateInvoice"));
		
		//Step 3:  send msgGiveInvoice
		employee.msgGiveInvoice(order, null, 10);
		assertTrue("Employees log should have recorded getting message", employee.log.containsString("got msgGiveInvoice"));
		
	}
	
}
