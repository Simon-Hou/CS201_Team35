package market;

import java.util.ArrayList;

import interfaces.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import market.gui.MarketEmployeeGui;
import person.PersonAgent;
import role.Role;
import interfaces.MarketCustomer;
import interfaces.MarketDeliveryMan;
import interfaces.MarketEmployee;
import interfaces.Person;

public class MarketEmployeeRole extends Role implements MarketEmployee{

	List<CustomerOrder> customerOrders = Collections.synchronizedList(new ArrayList<CustomerOrder>());
	List<BusinessOrder> businessOrders = new ArrayList<BusinessOrder>();
	List<BusinessOrder> deliveryList = new ArrayList<BusinessOrder>();
	PersonAgent p;
	public String name;
	private Market market;
	
	List<MarketDeliveryMan> deliveryMen = new ArrayList<MarketDeliveryMan>();
	MarketCashier cashier; 
	
	MarketEmployeeGui gui;
	
	//SETTERS
	public void setName(String name){
		this.name = name;
	}
	
	public void setGui(MarketEmployeeGui g){
		gui = g;
	}
	public void setCashier(MarketCashier cash){
		cashier = cash;
	}
	
	public void addDeliveryMan(MarketDeliveryMan d){
		deliveryMen.add(d);
	}
	
	public void setPerson(PersonAgent p){
		this.p= p;
	}
	
	//GETTERS
	public String getName(){
		return name;
	}
	
	public boolean canLeave() {
		return (customerOrders.isEmpty() && businessOrders.isEmpty());
	}
	
	
	//constructor
	public MarketEmployeeRole(String name, PersonAgent p){
		this.name = name;
		this.p = p;
	}
	
	//Messages
	public void msgGetItemsForCustomer(MarketCustomer c, Map<String, Integer> orderList){
	    Do("Better get the customer's item's");
		customerOrders.add(new CustomerOrder(c, orderList));
		p.msgStateChanged();
	}

	public void msgGetThis(BusinessOrder order){
	    businessOrders.add(order);
	    p.msgStateChanged();
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction() {
		
		//Do("SCHEDULER");
		synchronized(customerOrders){
			for (CustomerOrder co: customerOrders){
				if (co.status == CustomerOrderState.none){
					CollectItems(co);
					return true;
				}
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
	    	status = CustomerOrderState.none;
	    }
	}
	enum CustomerOrderState {none,  fulfilled}
	
	public void setMarket(Market market) {
		// TODO Auto-generated method stub
		this.market = market;
		
	}

	
}
