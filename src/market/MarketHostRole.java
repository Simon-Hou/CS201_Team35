package market;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

import interfaces.*;
import restaurant.Restaurant;
import role.Role;
import person.PersonAgent;
import market.gui.*;


public class MarketHostRole extends Role implements MarketHost {

	
	//-----------------------------DATA--------------------------------
	enum CustomerState {waiting, beingServiced, leaving};
	
	public List<MyEmployee> employees = new ArrayList<MyEmployee>();
	private List<MyCustomer> customers= new ArrayList<MyCustomer>();
	private List<MyBusinessOrder> businessOrders = new ArrayList<MyBusinessOrder>();
	
	public Person p;
	public String name;

	Market market;
	
	public MarketHostRole(String name, PersonAgent p, Market m){		
		this.name = name;
		this.p=p;
		this.market=m;		
	}
	
	
	public boolean isPresent(){
		if (p==null)
			return false;
		else return true;
	}
	//-----------------------------MESSAGES--------------------------------
	

	public boolean YouAreDoneWithShift(){
		//TODO make sure people don't leave their shifts early
		if(true){
			p.msgThisRoleDone(this);
			this.p = null;
			market.DefaultName(this);
		}
		return true;
	}
	
	public void msgCustomerWantsThis(MarketCustomer c, Map<String, Integer> orderList) {
	    Do("I received a MARKET order from " + c.getName());
		customers.add(new MyCustomer(c, orderList));
	    if(p!=null){
	    	p.msgStateChanged();
	    }
		
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
		p.msgStateChanged();
		
	}

	public void msgBusinessWantsThis(Restaurant r, Map<String, Integer> order) {
		List<OrderItem> temp = new ArrayList<OrderItem>();
		for (Entry<String,Integer> item: order.entrySet()){
			temp.add(new OrderItem(item.getKey(),item.getValue()));
		}
		
		businessOrders.add(new MyBusinessOrder(r, temp));
		p.msgStateChanged();
		
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
		Do("Serving customer");
		mc.state = CustomerState.beingServiced;
		
		Map<String, Integer> inventory = market.inventory;

		Map<String, Integer> unfulfillable = new HashMap<String, Integer>();
		
		

		List<OrderItem> custOrder = new ArrayList<OrderItem>();
		for (Entry<String,Integer> item : mc.order.entrySet()){
			custOrder.add(new OrderItem(item.getKey(), item.getValue()));
		}

		
		
		for (OrderItem item : custOrder){
			int request = item.quantityOrdered;
			int stock = inventory.get(item.choice);

			inventory.remove(item.choice);

			if (request > stock){
				unfulfillable.put(item.choice, request - stock);
				inventory.put(item.choice, 0);
				if (stock ==0){
					mc.order.remove(item.choice);
				}
				else{
					mc.order.put(item.choice, stock);
				}
			}
			else {//(request <= stock)
				inventory.put(item.choice, stock-request);
			}
		}
		
		Do("GOT HERE");

		if (mc.order.size()==0){
			Do(mc.customer.getName() + ", you didn't order anything, or we are out of everything you ordered.");
			mc.customer.msgWeHaveNothing();
			return;
		}
		
		if (unfulfillable.size()>0){
			mc.customer.msgOutOfStock(unfulfillable);
			Do("Sorry, we don't have some of the items that you requested");
		}



		
		
		//choose employee for load balancing
		if(employees.size()==0){
			System.err.println("No Market employees");
			mc.customer.msgWeHaveNothing();
			return;
		}
		
		MyEmployee e1 = employees.get(0);

		for (int i =1; i< employees.size(); i++){
			if (employees.get(i).orders < e1.orders){
				e1 = employees.get(i);
			}
		}

		market.updateInventory();
	
		
		Do("Giving this order to " + e1.employee.getName());
		e1.employee.msgGetItemsForCustomer(mc.customer, mc.order);
		e1.orders++;
		


	}

	
	private void DelegateBusinessOrder(MyBusinessOrder bo){
		
		Map<String, Integer> inventory = market.inventory;
		
		Do("I got a phone call for a business delivery order.");
		

		Map<String, Integer> unfulfillable = new HashMap<String, Integer>();

		boolean couldntGetAnything = true;
		
		for (OrderItem item : bo.order){
			int request = item.quantityOrdered;
			int stock = inventory.get(item.choice);
			
			if (request > stock){
				if (stock>0)
					couldntGetAnything = false;
				
				unfulfillable.put(item.choice, request-stock);
				item.quantityReceived = stock;
				inventory.put(item.choice, 0);
			}	
			else {
				item.quantityReceived = request;
				couldntGetAnything = false;
				inventory.put(item.choice, stock - request);
			}
			
		}
		
		businessOrders.remove(bo);
		
		//If we had no inventory, just quit. Otherwise, message the cook any unfulfillable parts of the order so they can find another market
		if (couldntGetAnything){
			bo.r.cook.msgCannotFulfillOrder(market, unfulfillable);
			return;
		}
		else if (unfulfillable.size()>0){
			bo.r.cook.msgCannotFulfillOrder(market, unfulfillable);
		}
		
		
		//choose employee for load balancing

		MyEmployee e1 = employees.get(0);

		for (int i =1; i< employees.size(); i++){
			if (employees.get(i).orders < e1.orders){
				e1 = employees.get(i);
			}
		}

		Do("Telling " + e1.employee.getName() + " to fill this business order.");
		e1.employee.msgGetThis(bo.order, bo.r);
		e1.orders++;

		market.updateInventory();
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
		this.market = m;
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
	
	class MyBusinessOrder{
		List<OrderItem> order;
		Restaurant r;
		
		MyBusinessOrder(Restaurant r, List<OrderItem> order){
			this.order = order;
			this.r = r;
		}
	}

	
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
	
	public boolean NewEmployee(MarketEmployee m){
		//Do("This is being called!!!!");
		addEmployee(m);
		return true;
	}
}
