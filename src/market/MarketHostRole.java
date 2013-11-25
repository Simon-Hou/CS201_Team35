package market;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import interfaces.*;
import role.Role;
import person.PersonAgent;
import market.gui.*;


public class MarketHostRole extends Role implements MarketHost {

	Market market;
	//-----------------------------DATA--------------------------------
	enum CustomerState {waiting, beingServiced, leaving};
	
	public List<MyEmployee> employees = new ArrayList<MyEmployee>();
	private List<MyCustomer> customers= new ArrayList<MyCustomer>();
	
	private List<BusinessOrder> businessOrders = new ArrayList<BusinessOrder>();
	
	Map<String, Integer> inventory = new HashMap<String, Integer>();
	
	public Person p;
	public String name;
	
	Market m;
	
	//SETTERS
	public void setName(String name){
		this.name = name;
	}
	
	public void setPerson(PersonAgent p){
		this.p = p;
	}
	
	
	//GETTERS
	public String getName(){
		return name;
	}
	

	public boolean canLeave() {
		return true;
	}
	
	public MarketHostRole(String name, PersonAgent p){
		inventory.put("Steak", 1000);
		inventory.put("Chicken", 1000);
		inventory.put("Pizza", 1000);
		inventory.put("Salad", 1000);
		inventory.put("Car", 5);
		this.name = name;
		this.p=p;
		
	}
	
	public boolean NewEmployee(MarketEmployee m){
		addEmployee(m);
		return true;
	}
	
	//-----------------------------MESSAGES--------------------------------
	
	
	
	public boolean YouAreDoneWithShift(){
		//TODO make sure people don't leave their shifts early
		p.msgThisRoleDone(this);
		return true;
	}
	
	public void msgCustomerWantsThis(MarketCustomer c, Map<String, Integer> orderList) {
	    Do("I received a MARKET order from " + c.getName());
		customers.add(new MyCustomer(c, orderList));
	    StateChanged();
		
	}

	public void msgCustomerLeaving(MarketCustomer c, Receipt receipt, Map<String, Integer> groceries) {
		
		//Do("Cust trying to leave");
		for (MyCustomer mc : customers){
			if (mc.customer == c){
				mc.state = CustomerState.leaving;
				mc.receipt = receipt;
				mc.groceries = groceries;
			}
		}
		StateChanged();
		
	}

	public void msgBusinessWantsThis(BusinessOrder order) {
		businessOrders.add(order);
		StateChanged();
		
	}

	
	
	//-----------------------------SCHEDULER--------------------------------
	public boolean pickAndExecuteAnAction() {
		//Do("SCHEDULER");
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

		Do("May I see your receipt please, " + mc.customer.getName() + "?");
		if (mc.groceries.isEmpty()  || (mc.receipt.order == mc.groceries)  ||  (mc.receipt == null && mc.groceries == null) ){

			mc.customer.msgYouCanLeave();
			Do("Okay, you may leave.");
			customers.remove(mc);
			market.removeCustomer(mc.customer);
			return;
		}
		
		
		else {
			 //error handling for robbers/scammers
		 } 
		 
	}
	
	private void ServeCustomer(MyCustomer mc){
		mc.state = CustomerState.beingServiced;

		Map<String, Integer> unfulfillable = new HashMap<String, Integer>();
		for (Entry<String,Integer> item : mc.order.entrySet()){
			int request = item.getValue();
			int stock = inventory.get(item.getKey());

			inventory.remove(item.getKey());

			if (request > stock){
				unfulfillable.put(item.getKey(), request - stock);
				inventory.put(item.getKey(), 0);
				if (stock ==0){
					mc.order.remove(item.getKey());
				}
				else{
					mc.order.put(item.getKey(), stock);
				}
			}
			else {//(request <= stock)
				inventory.put(item.getKey(), stock-request);
			}
		}

		if (unfulfillable.size()>0){
			mc.customer.msgOutOfStock(unfulfillable);
		}

		if (mc.order.size()==0){
			return;
		}

		
		
		//choose employee for load balancing
		
		MyEmployee e1 = employees.get(0);

		for (int i =1; i< employees.size(); i++){
			if (employees.get(i).orders < e1.orders){
				e1 = employees.get(i);
			}
		}
		Do("Giving this order to " + e1.employee.getName());
		e1.employee.msgGetItemsForCustomer(mc.customer, mc.order);
		e1.orders++;

	}

	
	private void DelegateBusinessOrder(BusinessOrder order){
		

		Do("I got a phone call for a business delivery order.");
		

		List<OrderItem> unfulfillable = new ArrayList<OrderItem>();

		boolean couldntGetAnything = true;
		
		for (OrderItem item : order.order){
			int request = item.quantityOrdered;
			int stock = inventory.get(item.choice);
			
			if (request > stock){
				if (stock>0)
					couldntGetAnything = false;
				
				unfulfillable.add(new OrderItem(request, stock, item.choice));
				item.quantityReceived = stock;
				inventory.put(item.choice, 0);
			}	
			else {
				item.quantityReceived = request;
				couldntGetAnything = false;
				inventory.put(item.choice, stock - request);
			}
			
		}
		
		businessOrders.remove(order);
		
		//If we had no inventory, just quit. Otherwise, message the cook any unfulfillable parts of the order so they can find another market
		if (couldntGetAnything){
			return;
		}
		else if (unfulfillable.size()>0){
			//order.restaurant.cook.msgCannotFulfillOrder(m, unfulfillable);
		}
		
		
		//choose employee for load balancing

		MyEmployee e1 = employees.get(0);

		for (int i =1; i< employees.size(); i++){
			if (employees.get(i).orders < e1.orders){
				e1 = employees.get(i);
			}
		}

		Do("Telling " + e1.employee.getName() + " to fill this business order.");
		e1.employee.msgGetThis(order);
		e1.orders++;


	}
	
	
	//-----------------------------UTILITIES--------------------------------
	private class MyEmployee{
		
	    MarketEmployee employee;
	    int orders;            //Used to keep track of how busy an employee is
	    
	    MyEmployee(MarketEmployee emp){
	    	employee = emp;
	    }
	}
	
	public void addEmployee(MarketEmployee m){
		this.employees.add(new MyEmployee(m));
	}
	
	public void setMarket(Market m){
		market = m;
	}
	
	/*String name;
	public String getName(){
		return this.name;
	}*/
	
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
