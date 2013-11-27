package UnitTests.MarketUnitTests;

import static org.junit.Assert.*;
import interfaces.Person;
import junit.framework.TestCase;
import market.MarketCustomerRole;

import org.junit.Test;

import UnitTests.mock.MarketMock.*;

public class MarketCustomerTest extends TestCase{

	MockMarketPerson person;
	MarketCustomerRole customer;
	MockMarketHost host;
	MockMarketEmployee employee;
	MockMarketCashier cashier;
	
	
	
	
	public void setUp() throws Exception{
		super.setUp();	
		
		person = new MockMarketPerson("P0");
		customer = new MarketCustomerRole("Cust", person);
		host = new MockMarketHost("Host");
		employee = new MockMarketEmployee("Employee");
		cashier = new MockMarketCashier("Cashier");

		host.customer = customer;
		employee.customer = customer;
		cashier.customer = customer;
		
	}
	
	@Test
	public void test() {
	
		//preconditions
		
		
	}

}
