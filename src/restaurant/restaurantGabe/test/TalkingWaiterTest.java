package restaurant.restaurantGabe.test;

import java.util.ArrayList;

import person.PersonAgent;
import market.MarketInvoice;
import UnitTests.mock.MarketMock.MockMarketDeliveryMan;
import restaurant.restaurantGabe.CashierRole;
import restaurant.restaurantGabe.CookRole;
import restaurant.restaurantGabe.CustomerRole;
import restaurant.restaurantGabe.TalkingWaiterRole;
import restaurant.restaurantGabe.interfaces.Cook;
import restaurant.restaurantGabe.interfaces.Customer;
import restaurant.restaurantGabe.test.mock.MockCook;
//import restaurant.CashierAgent.cashierBillState;
//import restaurant.WaiterAgent.Bill;
import restaurant.restaurantGabe.test.mock.MockCustomer;
import restaurant.restaurantGabe.test.mock.MockMarket;
import restaurant.restaurantGabe.test.mock.MockWaiter;
import restaurant.restaurantGabe.util.Check;
import junit.framework.*;

/**
 * 
 * This class is a JUnit test class to unit test the CashierAgent's basic interaction
 * with waiters, customers, and the host.
 * It is provided as an example to students in CS201 for their unit testing lab.
 *
 * @author Monroe Ekilah
 */
public class TalkingWaiterTest extends TestCase {
	
	public TalkingWaiterRole waiter;
	public PersonAgent deadPerson;
	public CookRole cook;
	public CustomerRole customer;
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();		
		deadPerson = new PersonAgent("Dead",null);
		waiter = new TalkingWaiterRole("waiter0",deadPerson);
		cook = new CookRole("cook");
		cook.person = deadPerson;
		customer  = new CustomerRole("customer",deadPerson);
		
		waiter.Cook = cook;
	}
	
	
	/**
	 * This will simply test that the talking waiter is giving the cook the order correctly
	 */
	public void testTalkingWaiterOneOrder(){
		
		//step 0 - set up the scenario
		waiter.addNewCustomer(customer);
		waiter.msgHereIsMyChoice(customer, "Steak");
		
		
		//check post of 0 and pre of 1
		assertTrue("Waiter should have a customer.",waiter.MyCustomers.size()==1);
		assertTrue("Waiter's only customer should be in the ordered state",waiter.ordered(0));
		assertTrue("Waiter should have put an order in his list of orders",waiter.Orders.size()==1);
		assertTrue("Waiter should have a permit in the waiting for order sempahore",waiter.waitingForOrder.availablePermits()>0);
		
		
		//step 1 - call the waiter scheduler
		assertTrue("Waiter scheduler should have been called", waiter.pickAndExecuteAnAction());
		
		//check post of 1 and pre of 2
		//System.out.println(cook.log.getLastLoggedEvent().getMessage());
		assertTrue("Cook not getting correct log message",
				cook.log.getLastLoggedEvent().getMessage().equals("Got "
						+ "an order from waiter waiter0 for Steak"));
		
		
		
		
		
		
		
		
		
	}
	
	
	
	

}