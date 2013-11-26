package UnitTests.mock.MarketMock;

import java.util.Map;

import market.BusinessOrder;
import market.Market;
import market.MarketCashierRole.MyCustomer;
import interfaces.MarketCashier;
import interfaces.MarketCustomer;
import interfaces.MarketEmployee;
import UnitTests.mock.Mock;

public class MockMarketCashier extends Mock implements MarketCashier{

	String name;
	public MarketCustomer customer;
	
	
	public MockMarketCashier(String n){
		name = n;
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
	public void msgServiceCustomer(MarketCustomer c,
			Map<String, Integer> groceries) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgFinishedComputing(MyCustomer mc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgCustomerPayment(MarketCustomer c, int payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsBusinessPayment(int payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgCalculateInvoice(BusinessOrder order, MarketEmployee employee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean YouAreDoneWithShift() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMarket(Market m) {
		// TODO Auto-generated method stub
		
	}

}
