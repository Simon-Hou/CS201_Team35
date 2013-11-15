package market;

import java.util.Map;

import interfaces.MarketCustomer;
import interfaces.MarketHost;
import role.Role;


public class MarketHostRole extends Role implements MarketHost {

	public void msgCustomerWantsThis(MarketCustomer c, Map<String, Integer> orderList) {
		// TODO Auto-generated method stub
		
	}

	public void msgCustomerLeaving(MarketCustomer c, Receipt receipt,
			Map<String, Integer> groceries) {
		// TODO Auto-generated method stub
		
	}

	public void msgBusinessWantsThis(BusinessOrder order) {
		// TODO Auto-generated method stub
		
	}

	protected boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

}
