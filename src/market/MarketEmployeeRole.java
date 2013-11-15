package market;

import java.util.List;
import java.util.Map;

import interfaces.MarketCustomer;
import interfaces.MarketDeliveryMan;
import interfaces.MarketEmployee;

public class MarketEmployeeRole implements MarketEmployee{

	List<CustomerOrder> customerOrders;
	List<BusinessOrder> businessOrders;
	List<MarketDeliveryMan> deliveryMen;
	
	//Messages
	public void msgGetItemsForCustomer(MarketCustomer c, Map<String, Integer> orderList){
	    customerOrders.add(new CustomerOrder(c, orderList));
	}

	public void msgGetThis(BusinessOrder order){
	    businessOrders.add(order);
	}
	
	public void msgDeliverThisOrder(BusinessOrder order){
		
	}
	
	public void msgHereIsPayment(int payment){
		
	}
	
	//Inner classes
	class CustomerOrder{
	    MarketCustomer c;
	    Map<String, Integer> order;
	    CustomerOrderState status;
	    
	    CustomerOrder(MarketCustomer c, Map<String, Integer> order){
	    	this.c = c;
	    	this.order = order;
	    }
	}
	enum CustomerOrderState {none,  fulfilled}
}
