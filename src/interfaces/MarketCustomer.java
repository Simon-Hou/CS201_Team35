package interfaces;

import java.util.Map;

import market.Receipt;

public interface MarketCustomer {

	public void HereAreItems(Map<String, Integer> groceries);
	public void HereIsTotal(int total);
	public void HereIsYourChange(Receipt receipt, int change);
	public void YouOweMoney(Receipt receipt, int debt);
	public void YouCanLeave();
}
