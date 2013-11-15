package interfaces;

import java.util.Map;

import market.BusinessPayment;
import market.MarketCashierRole.MyCustomer;

public interface MarketCashier {
	
	public void PleaseServiceCustomer(MarketCustomer c, Map<String, Integer> groceries);
	public void msgFinishedComputing(MyCustomer mc);
	public void CustomerPayment(MarketCustomer c, int payment);
	public void HereIsBusinessPayment(BusinessPayment payment);

}
