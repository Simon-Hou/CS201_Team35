package UnitTests.mock.MarketMock;

import java.util.Map;

import market.Receipt;
import UnitTests.mock.EventLog;
import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;
import interfaces.MarketCustomer;
import interfaces.MarketHost;
import interfaces.Person;

public class MockMarketCustomer extends Mock implements MarketCustomer{

	public EventLog log = new EventLog();
	
	String name;
	public MarketHost host;
	
	public MockMarketCustomer(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public void msgHereAreItems(Map<String, Integer> groceries) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("got msgHereAreItems"));
	}

	@Override
	public void msgHereIsTotal(int total) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsYourChange(Receipt receipt, int change) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgYouOweMoney(Receipt receipt, int debt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgYouCanLeave() {
		// TODO Auto-generated method stub
		
		log.add(new LoggedEvent("got msgYouCanLeave"));
		
	}

	@Override
	public void msgOutOfStock(Map<String, Integer> unfullfillable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWeHaveNothing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Person getPerson() {
		// TODO Auto-generated method stub
		return null;
	}


}
