package market;

import java.util.List;
import java.util.Map;

import role.Role;

import interfaces.MarketCustomer;
import interfaces.MarketDeliveryMan;
import interfaces.MarketEmployee;

public class MarketEmployeeRole extends Role implements MarketEmployee{

	List<CustomerOrder> customerOrders;
	List<BusinessOrder> businessOrders;
	List<BusinessOrder> deliveryList;
	
	//Messages
	public void msgGetItemsForCustomer(MarketCustomer c, Map<String, Integer> orderList){
	    customerOrders.add(new CustomerOrder(c, orderList));
	}

	public void msgGetThis(BusinessOrder order){
	    businessOrders.add(order);
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction() {
		for (CustomerOrder co: customerOrders){
			if (co.status == CustomerOrderState.none){
				CollectItems(co);
				return true;
			}
		}
		for (CustomerOrder co: customerOrders){
			if (co.status == CustomerOrderState.fulfilled){
				GiveItemsToCustomer(co);
				return true;
			}
		}
		if (!businessOrders.isEmpty()){
			    GetBusinessOrder(businessOrders.get(0));
			    return true;
		}
		return false;
	}
	
	//Actions
	private void CollectItems(CustomerOrder co){
	    for (String item: co.order.keySet()){
	    	//DoCollectItem(item, co.order.get(item));
	    	Do("Collecting " + co.order.get(item) + " " + item + "s.");
	    }
	    co.status = CustomerOrderState.fulfilled;
	}

	private void GiveItemsToCustomer(CustomerOrder co){
	    customerOrders.remove(co);
	    co.c.msgHereAreItems(co.order);
	}

	private void GetBusinessOrder(BusinessOrder order){
		
		//Discuss changing this so that BusinessOrder is no longer public
		for (OrderItem item: order.order){
	    	Do("Collecting " + item.quantityOrdered + " " + item.choice + "s.");
	    }
		
	    //after getting the order
	    Do("Placing business order in delivery man's list");
	    deliveryList.add(order);
	    businessOrders.remove(order);
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
