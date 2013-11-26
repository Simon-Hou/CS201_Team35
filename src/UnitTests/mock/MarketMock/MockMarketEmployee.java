package UnitTests.mock.MarketMock;

import java.util.Map;

import market.BusinessOrder;
import interfaces.MarketCustomer;
import interfaces.MarketEmployee;
import UnitTests.mock.Mock;

public class MockMarketEmployee extends Mock implements MarketEmployee{

	String name;
	public MarketCustomer customer;
	
	public MockMarketEmployee(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public boolean canLeave() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgGetItemsForCustomer(MarketCustomer c,
			Map<String, Integer> orderList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGetThis(BusinessOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgGiveInvoice(int invoice, BusinessOrder order) {
		// TODO Auto-generated method stub
		
	}

}
