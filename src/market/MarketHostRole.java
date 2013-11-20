package market;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import interfaces.*;
import role.Role;


public class MarketHostRole extends Role implements MarketHost {

	//-----------------------------DATA--------------------------------
	enum CustomerState {waiting, beingServiced, leaving};
	
	private List<MyEmployee> employees = new ArrayList<MyEmployee>();
	private List<MyCustomer> customers= new ArrayList<MyCustomer>();
	
	private List<BusinessOrder> businessOrders = new ArrayList<BusinessOrder>();
	
	//-----------------------------MESSAGES--------------------------------
	
	public void msgCustomerWantsThis(MarketCustomer c, Map<String, Integer> orderList) {
	    customers.add(new MyCustomer(c, orderList));
	    stateChanged();
		
	}

	public void msgCustomerLeaving(MarketCustomer c, Receipt receipt, Map<String, Integer> groceries) {
		for (MyCustomer mc : customers){
			if (mc.customer == c){
				mc.state = CustomerState.leaving;
				mc.receipt = receipt;
				mc.groceries = groceries;
			}
		}
		stateChanged();
		
	}

	public void msgBusinessWantsThis(BusinessOrder order) {
		businessOrders.add(order);
		stateChanged();
		
	}

	
	//-----------------------------SCHEDULER--------------------------------
	protected boolean pickAndExecuteAnAction() {
		
		for (MyCustomer mc : customers){
			if (mc.state == CustomerState.leaving){
				CheckCustomer(mc);
				return true;
			}
		}
		
		for (MyCustomer mc : customers){
			if (mc.state == CustomerState.waiting){
				ServeCustomer(mc);
				return true;
			}
		}
		
		if (!businessOrders.isEmpty() && !employees.isEmpty()){
		    DelegateBusinessOrder(businessOrders.get(0));
		    return true;
		}
		
		return false;
	}

	
	//-----------------------------ACTIONS--------------------------------
	
	private void CheckCustomer(MyCustomer mc){
		if ((mc.receipt == mc.groceries)  ||  (mc.receipt == null && mc.groceries == null) ){
			mc.customer.YouCanLeave();
			customers.remove(mc);
			return;
		}
		
		
		else {
			 //error handling for robbers/scammers
		 } 
		 
	}
	
	private void ServeCustomer(MyCustomer mc){
		
	}
	
	private void DelegateBusinessOrder(BusinessOrder order){
		
	}
	//-----------------------------UTILITIES--------------------------------
	private class MyEmployee{
		
	    MarketEmployee employee;
	    int orders;            //Used to keep track of how busy an employee is
	    
	    MyEmployee(MarketEmployee me){
	    	employee = me;
	    }
	}
	
	private class MyCustomer{
	    MarketCustomer customer;
	    Map<String, Integer> order;
	    Map<String, Integer> groceries;
	    Receipt receipt;
	    CustomerState state;
	    Map<String, Integer> inventory;
	    
	    MyCustomer(MarketCustomer cust, Map<String, Integer> orderList){
	    	customer = cust;
	    	order = orderList;
	    	state = CustomerState.waiting;
	    	
	    }
	}
	
}
