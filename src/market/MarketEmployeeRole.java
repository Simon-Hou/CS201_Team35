package market;

import java.util.ArrayList;

import interfaces.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import market.BusinessOrder.OrderState;
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
	
	private Semaphore atDestination = new Semaphore(0,true);
	
	private Semaphore receivedInvoice = new Semaphore(0,true);
	
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
	    Do("Better get the customer's items");
		customerOrders.add(new CustomerOrder(c, orderList));
		p.msgStateChanged();
	}

	public void msgGetThis(BusinessOrder order){
	    businessOrders.add(order);
	    p.msgStateChanged();
	}
	
	public void msgGiveInvoice(int invoice, BusinessOrder order){
		receivedInvoice.release();
		for(BusinessOrder o : businessOrders){
			if (o == order){
				o.invoice = invoice;
				o.state = OrderState.gotInvoice;
				p.msgStateChanged();
			}
		}
		
	}
	//fromAnimation
	public void msgAtDestination(){
		atDestination.release();
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction() {
		
		//Do("SCHEDULER");
		for (CustomerOrder co: customerOrders){
			if (co.status == CustomerOrderState.fulfilled){
				GiveItemsToCustomer(co);
				return true;
			}
		}
		
		synchronized(customerOrders){
			for (CustomerOrder co: customerOrders){
				if (co.status == CustomerOrderState.none){
					CollectItems(co);
					return true;
				}
			}
		}
		
		for(BusinessOrder bo : businessOrders){
			if (bo.state == OrderState.gotInvoice){
				PlaceOrderOnDock(bo);
				return true;
			}
		}
		
		for(BusinessOrder bo : businessOrders){
			if (bo.state == OrderState.ordered){
			    GetBusinessOrder(bo);
			    return true;
			}
		}
		return false;
	}
	
	//Actions
	private void CollectItems(CustomerOrder co){
		
		
	
		if(gui!=null){
			gui.DoGetItems();
		}
		else{
			atDestination.release();
		}
		
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//^^^this should go inside the for loop for each item needing to be collected
		
	    for (String item: co.order.keySet()){
	    	//DoCollectItem(item, co.order.get(item));
	    	Do("Collecting " + co.order.get(item) + " " + item + "s.");
	    }
	    co.status = CustomerOrderState.fulfilled;
	}

	private void GiveItemsToCustomer(CustomerOrder co){
		
		if(gui!=null){
			gui.DoGiveCustomerItems();
		}
		else{
			atDestination.release();
		}
		
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    customerOrders.remove(co);
	    Do("Giving " + co.c.getName() + " their order.");
	    co.c.msgHereAreItems(co.order);
	    
	    if(gui!=null){
			gui.DoGoHomePosition();
		}
	

	}

	private void GetBusinessOrder(BusinessOrder order){
		Do("Better fill this business order");
		
		if(gui!=null){
			gui.DoGetItems();
		}
		else{
			atDestination.release();
		}
		
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//^^^this should go inside the for loop for each item needing to be collected
		
		
		//Discuss changing this so that BusinessOrder is no longer public
		for (OrderItem item: order.order){
	    	Do("Collecting " + item.quantityReceived + " " + item.choice + "s.");
	    }
		
		/////////----------------------------------------------------------------
	    //after getting the order
	    Do("Got all of the items in the order.");
	    
	    if(gui!=null){
			gui.DoGoToCashier();
		}
		else{
			atDestination.release();
		}
		
		try {
			atDestination.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		//got to cashier
		Do(cashier.getName() + ", can you please calculate the invoice for this order?");
		cashier.msgCalculateInvoice(order, this);
	    
		order.state = OrderState.none;

		
		try {
			receivedInvoice.acquire();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	
	}
	
	private void PlaceOrderOnDock(BusinessOrder order){
		businessOrders.remove(order);
		
		 if(gui!=null){
				gui.DoGoToDock();
			}
			else{
				atDestination.release();
			}
			
			try {
				atDestination.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			Do("Putting completed order and invoice on dock for delivery man.");
			if (deliveryMen.isEmpty()){
				Do("There are no delivery men.");
				gui.DoGoHomePosition();
				return;
			}
			//otherwise...
			//load balance deliverymen
			deliveryMen.get(0).msgDeliverThisOrder(order);
			gui.DoGoHomePosition();
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
	enum CustomerOrderState {none,  fulfilled };
	
	public void setMarket(Market market) {
		// TODO Auto-generated method stub
		this.market = market;
		
	}

	
}
