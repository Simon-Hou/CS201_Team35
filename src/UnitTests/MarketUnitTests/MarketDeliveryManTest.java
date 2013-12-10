package UnitTests.MarketUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import market.Market;
import market.MarketDeliveryManRole;
import market.MarketHostRole;
import market.MarketInvoice;
import market.OrderItem;

import org.junit.Test;

import restaurant.Restaurant;

import UnitTests.mock.MarketMock.MockMarketCashier;
import UnitTests.mock.MarketMock.MockMarketCustomer;
import UnitTests.mock.MarketMock.MockMarketEmployee;
import UnitTests.mock.MarketMock.MockMarketPerson;
import UnitTests.mock.RestaurantMock.MockBaseRestaurantCashier;
import UnitTests.mock.RestaurantMock.MockBaseRestaurantCook;
import UnitTests.mock.RestaurantMock.MockRestaurant;

public class MarketDeliveryManTest extends TestCase{

	MarketDeliveryManRole deliveryMan;

	Market market;
	MockMarketPerson person;
	MarketHostRole host;

	MockMarketCustomer customer;
	MockMarketEmployee employee;
	
	MockRestaurant restaurant;
	MockBaseRestaurantCook cook;
	MockBaseRestaurantCashier restaurantCashier;
	
	MockMarketCashier marketCashier;



	public void setUp() throws Exception{
		super.setUp();	

		person = new MockMarketPerson("P0");
		market = new Market();
		host = new MarketHostRole("Host", person, market);

		customer = new MockMarketCustomer("C1");
		employee = new MockMarketEmployee("E1");
		
		restaurant = new MockRestaurant();
		cook = new MockBaseRestaurantCook("Cook");
		restaurantCashier = new MockBaseRestaurantCashier("restaurantCashier");
		restaurant.cashier = restaurantCashier;
		restaurant.cook = cook;
		
		marketCashier = new MockMarketCashier("marketCashier");
		market.cashier = marketCashier;
		deliveryMan = new MarketDeliveryManRole("deliveryMan", person, market);

		customer.host = host;
		employee.host = host;


	}



	@Test
	public void testOne_NormalDeliveryManDeliveryOrderScenario() {
		//Preconditions		
		assertEquals("Delivery Man's event log should be empty.", 0, deliveryMan.log.size());
		assertEquals("Delivery Man's orders should be empty.", 0, deliveryMan.orders.size());
		assertEquals("Delivery Man's payments should be empty.", 0, deliveryMan.payments.size());
		
		assertEquals("RestaurantCashier's event log should be empty.", 0, restaurantCashier.log.size());
		assertEquals("Cook's event log should be empty.", 0, cook.log.size());
		assertEquals("MarketCashier's event log should be empty.", 0, marketCashier.log.size());

		assertFalse("Delivery Man's sheduler should return false", deliveryMan.pickAndExecuteAnAction());

		List<OrderItem> order = new ArrayList<OrderItem>();
		order.add(new OrderItem("Steak", 5));
		
		//Step 1
		MarketInvoice tempOrder = new MarketInvoice(order, market, restaurant, 20);
		deliveryMan.msgDeliverThisOrder(tempOrder);

		//Post Step 1
		assertEquals("Delivery Man's orders should have size 1 but are " + deliveryMan.orders.size(), 1, deliveryMan.orders.size());
		assertTrue("Delivery Man's log should have received mesage 'got msgDeliverThisOrder', but it reads " + deliveryMan.log.getLastLoggedEvent(), deliveryMan.log.getLastLoggedEvent().toString().endsWith("got msgDeliverThisOrder"));

		//Step 2
		deliveryMan.msgHereIsPayment(20, tempOrder); //Do this to release the semaphore beforehand
		assertTrue("Delivery Man's scheduler should return true", deliveryMan.pickAndExecuteAnAction());
		

		//Post Step 2
		assertTrue("Delivery Man's log should have received message 'action DeliverOrder', but it reads " + deliveryMan.log.getLastLoggedEvent(), deliveryMan.log.getLastLoggedEvent().toString().endsWith("action DeliverOrder"));
		
		
		assertEquals("Restaurant cashier's log should now be size 1 but is instead " + restaurantCashier.log.size(), 1, restaurantCashier.log.size());
		assertTrue("Restaurant cashier's log should contain the message about the invoice from the deliveryMan. Instead, the last logged event was " + restaurantCashier.log.getLastLoggedEvent(), restaurantCashier.log.containsString("Received invoice from deliveryMan " + deliveryMan.getName() + " to pay $" + tempOrder.total + " for order " + tempOrder.order));
		
		assertEquals("Cook's log should now be size 1 but is instead " + cook.log.size(), 1, cook.log.size());
		
		String list = "{";
		for (OrderItem o: tempOrder.order){
			list+=tempOrder.order.toString();
		}
		list+="}";
		
		assertTrue("Cook's log should contain the message about the delivery of food. Instead, the last logged event " + cook.log.getLastLoggedEvent(), cook.log.containsString("Received delivery of food: " + list));

		
	}

	public void testTwo_NormalDeliveryManDeliveryOrderScenario() {
		//Preconditions
		assertEquals("Delivery Man's event log should be empty.", 0, deliveryMan.log.size());
		assertEquals("Delivery Man's orders should be empty.", 0, deliveryMan.orders.size());
		assertEquals("Delivery Man's paymentsshould be empty.", 0, deliveryMan.payments.size());
		
		assertEquals("Market cashier's event log should be empty.", 0, marketCashier.log.size());

		assertFalse("Delivery Man's sheduler should return false", deliveryMan.pickAndExecuteAnAction());

		//Step 1
		List<OrderItem> order = new ArrayList<OrderItem>();
		order.add(new OrderItem("Steak", 5));
		MarketInvoice tempOrder = new MarketInvoice(order, market, restaurant, 20);
		
		deliveryMan.msgHereIsPayment(20, tempOrder);
		
		//Post Step 1
		assertEquals("Delivery Man's payments should have size 1 but are " + deliveryMan.payments.size(), 1, deliveryMan.payments.size());
		assertTrue("Delivery Man's log should have received mesage 'got msgHereIsPayment', but it reads " + deliveryMan.log.getLastLoggedEvent(), deliveryMan.log.getLastLoggedEvent().toString().endsWith("got msgHereIsPayment"));

		//Setp 2
		assertTrue("Delivery Man's scheduler should return true", deliveryMan.pickAndExecuteAnAction());

		//Post Step 2
		assertEquals("Delivery Man's payments should now be empty", 0, deliveryMan.payments.size());
		assertTrue("Delivery Man's log should have received message 'action DeliverPayment', but it reads " + deliveryMan.log.getLastLoggedEvent(), deliveryMan.log.getLastLoggedEvent().toString().endsWith("action DeliverPayment"));

		assertEquals("Market cashier's log should be size 1, instead it is size " + marketCashier.log.size(), 1, marketCashier.log.size());
		assertTrue("Market cashier's log should contain a message about receiving a business payment. Intead, the last logged message was " + marketCashier.log.getLastLoggedEvent(), marketCashier.log.containsString("Received business payment of $" + 20));

		//Step 3
		assertFalse("Delivery Man's scheduler should return false", deliveryMan.pickAndExecuteAnAction());
		
		
	}

}
