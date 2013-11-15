package market;

import java.util.List;
import java.util.Map;

import interfaces.MarketCustomer;

public class MarketEmployeeRole {

	List<CustomerOrder> customerOrders;
	class CustomerOrder{
	    MarketCustomer c;
	    Map<String, Integer> order;
	CustomerOrderState status;
	}
	enum CustomerOrderState {none,  fulfilled}

	List<BusinessOrder> businessOrders;
	List<MarketDeliveryMan> deliveryMen;
}
