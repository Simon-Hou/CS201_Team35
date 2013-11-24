package restaurantLinda;

import agent.Agent;

import interfaces.restaurantLinda.Cashier;
import interfaces.restaurantLinda.Cook;
import interfaces.restaurantLinda.Market;

import java.util.*;

import role.Role;


/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class MarketRole extends Role implements Market{
	private String name;
	private List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	private Map<String,Food> inventory = new HashMap<String,Food>();
	private Timer timer = new Timer();
	
	public MarketRole(int steaks, int chickens, int salads, int pizzas, int identifier) {
		super();
		inventory.put("Steak", new Food("Steak",steaks, 800));
		inventory.put("Chicken", new Food("Chicken",chickens, 600));
		inventory.put("Salad", new Food("Salad",salads, 200));
		inventory.put("Pizza", new Food("Pizza",pizzas, 400));
		name = "Market"+identifier;
	}
	
	//messages
	public void msgHereIsOrder(Cook cook, Cashier cashier, Map<String,Integer> list){
		Do("Received order");
		orders.add(new Order(cook, cashier, list));
		stateChanged();
	}
	
	public void msgTimerDone(Order o){
		o.status=OrderState.waiting;
		stateChanged();
	}
	
	public void msgHereIsPayment(Cashier c, Map<String, Integer> foodList, int payment){
		synchronized(orders){
			for (Order o: orders){
				if (o.status==OrderState.sent && o.foodList.equals(foodList) && o.cashier == c){
					o.paid+=payment;
					if (o.paid >= o.total)
						o.status = OrderState.paid;
					Do("Received payment of $" + payment/100 + "." + payment%100 + ". Still owed $" + (o.total-o.paid)/100 + "." + (o.total-o.paid)%100 + " for order" + foodList);
					stateChanged();
				}
			}
		}
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		for (Order o: orders){
			if (o.status == OrderState.none){
				ProcessOrder(o);
				return true;
			}
		}
		for (Order o: orders){
			if (o.status==OrderState.waiting){
				SendOrder(o);
				return true;
			}
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	//actions
	private void ProcessOrder(Order o){
		final Order order = o;
		Map<String, Integer> unfulfillable = new HashMap<String, Integer>();
		o.status=OrderState.processing;
		String message = "Fulfilling ";
		for (String f: o.foodList.keySet()){
			int request = o.foodList.get(f);
			Food stock = inventory.get(f);
			if (request>stock.quantity){
				unfulfillable.put(f,request-stock.quantity);
				message+= stock.quantity+" out of "+request+" "+f+"s, 0 left in stock\n\t";
				
				//If we have no inventory, then there's no point in keeping this in the order list
				if (stock.quantity==0)
					o.foodList.remove(f);
				else{
					o.foodList.put(f, stock.quantity);
					o.total+= stock.cost*stock.quantity;
				}
				
				stock.quantity=0;
			}
			else{
				stock.quantity-=request;
				message+= request+" "+f+"s, "+stock.quantity+"left\n\t";
				o.total+= stock.cost*request;
			}
		}
		
		Do(message);
		
		if (unfulfillable.size()>0)
			o.cook.msgCannotFulfillOrder(this, unfulfillable);
		
		//Mark the order as done if we were unable to fulfill anything
		if (o.foodList.size()==0){
			o.status=OrderState.sent;
			Do("Cannot do anything for order");
			return;
		}
		
		Random time = new Random();
		timer.schedule(new TimerTask(){
			public void run(){
				msgTimerDone(order);
			}
		}, time.nextInt(6)*o.foodList.size()*1000+10000);		//Processing the order takes 10-30 seconds, also dependent on size of order
		
	}
	
	private void SendOrder(Order o){
		o.status=OrderState.sent;
		Do("shipping food");
		o.cook.msgFoodShipment(o.foodList);
		o.cashier.msgPleasePay(this, o.foodList, o.total);
	}
	
	
	//inner classes
	private class Order{
		Cook cook;
		Cashier cashier;
		Map<String,Integer> foodList;
		OrderState status = OrderState.none;
		int total=0;
		int paid=0;
		
		Order(Cook c, Cashier ch, Map<String,Integer> list){
			cook = c;
			cashier = ch;
			foodList = list;
		}
	}
	enum OrderState {none, processing, waiting, sent, paid}
	
	
	private class Food{
		String type;
		int quantity;
		int cost;
		
		Food(String t, int quantity, int cost){
			type=t;
			this.quantity = quantity;
			this.cost = cost;
		}
	}	
	
	//utilities
	public String toString() {
		return name;
	}
}


	

