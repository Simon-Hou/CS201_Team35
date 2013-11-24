package restaurantLinda;

import agent.Agent;

import java.util.*;
import java.util.concurrent.Semaphore;

import restaurantLinda.gui.CookGui;
import restaurantLinda.interfaces.Cashier;
import restaurantLinda.interfaces.Cook;
import restaurantLinda.interfaces.Market;
import restaurantLinda.interfaces.Waiter;


public class CookAgent extends Agent implements Cook{
	String name;
	List<Order> orders = Collections.synchronizedList(new ArrayList<Order>());
	Timer timer = new Timer();
	Map<String,Food> foodMap = new HashMap<String,Food>();
	List<Market> markets = Collections.synchronizedList(new ArrayList<Market>());
	boolean checkInventory=false;
	Cashier cashier;
	
	private Semaphore atDestination = new Semaphore(0,true);
	private CookGui cookGui;
	
	public CookAgent(String name) {
		super();
		foodMap.put("Steak", new Food("Steak",5000,5,5,1));
		foodMap.put("Chicken", new Food("Chicken",4000,5,5,1));
		foodMap.put("Salad", new Food("Salad",2000,5,5,1));
		foodMap.put("Pizza", new Food("Pizza",3000,5,5,1));
		this.name = name;
		//checkInventory=true;
	}
	
	//messages
	public void msgHereIsOrder(Waiter w, String choice, int table){
		Do(w.getName() + " says table " + table + " wants " + choice);
		orders.add(new Order(w,choice,table,OrderState.pending));
		stateChanged();
	}
	
	public void msgTimerDone(Order o){
		o.state=OrderState.done;
		stateChanged();		
	}
	
	public void msgCannotFulfillOrder(Market m, Map<String, Integer> items)	{
		Do("Need to reorder from another market");
		
		for (String f: items.keySet()){
			Food food = foodMap.get(f);
			food.status=FoodState.partialOrdering;
			food.uselessMarkets.add(m);
			food.onOrder-=items.get(f);
			if (food.uselessMarkets.size()==markets.size()){
				food.status=FoodState.impossible;
				Do("Cannot order any more "+f+" because all markets out");
			}
		}
		stateChanged();		
	}
	
	public void msgFoodShipment(Map<String, Integer> items){
		Do("Received a food shipment");
		String shipmentMessage="Received ";
		
		for (String f: items.keySet()){
			Food food = foodMap.get(f);
			int shipment = items.get(f);
			if (shipment==food.onOrder && food.status==FoodState.fullOrdering)
				food.status=FoodState.none;
			
			shipmentMessage+=shipment+f+"s, ";
			food.quantity+=shipment;
			food.onOrder-=shipment;
		}
		Do(shipmentMessage);
		
		stateChanged();
	}
	
	public void msgAtDestination() {//from animation
		atDestination.release();// = true;
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		if (checkInventory && markets.size()>0){
			OrderFoodThatIsLow();
			return true;
		}
		synchronized(orders){
			for (Order o: orders){
				if (o.state==OrderState.done){
					PlateIt(o);
					return true;
				}
			}
		}
		synchronized(orders){
			for (Order o: orders){
				if (o.state==OrderState.pending){
					TryToCookIt(o);
					return true;
				}
			}
		}
		for (String f: foodMap.keySet()){
			if (foodMap.get(f).status==FoodState.partialOrdering){
				OrderFoodThatIsLow();
				return true;
			}
		}
		cookGui.DoGoToDefault();
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	//actions
	private void TryToCookIt(Order o){
		Food food = foodMap.get(o.choice);
		Do("Try cooking of " + food.type);
		
		if (food.quantity==0){
			orders.remove(o);
			Do("Out of " + food.type);
			o.w.msgOutOfChoice(o.choice,o.table, getEmptyFoodList());
			return;
		}
		if (food.quantity<0){
			Do("Error: less than 0 " + food.type);
			orders.remove(o);
			o.w.msgOutOfChoice(o.choice,o.table, getEmptyFoodList());
			return;
		}
		
		food.quantity--;
		Do(food.quantity + " " + food.type + "left");
		if (food.quantity == food.low && food.status != FoodState.fullOrdering){
			OrderFoodThatIsLow();
		}
		
		cookGui.DoCooking();
		try{
			atDestination.acquire();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		
		Do("Started cooking");
		final Order order = o;
		timer.schedule(new TimerTask(){
			public void run(){
				msgTimerDone(order);
			}
		},foodMap.get(o.choice).cookingTime);
		o.state=OrderState.cooking;
		
	}
	
	private void PlateIt(Order o){
		Do(foodMap.get(o.choice).type + " is done");
		cookGui.DoPlating(o.choice);
		try{
			atDestination.acquire();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		
		o.w.msgOrderDone(o.choice,o.table);
		orders.remove(o);
	}
	
	private void OrderFoodThatIsLow(){
		checkInventory=false;
		Map<String,Integer> shoppingList = new HashMap<String, Integer>();				
		String message = "Need ";
		
		for (String f: foodMap.keySet()){
			Food food = foodMap.get(f);
			if (food.status==FoodState.impossible){
				continue;
			}
			
			if (food.quantity<=food.low && food.status!=FoodState.fullOrdering){
				shoppingList.put(food.type, food.capacity-food.quantity-food.onOrder);
				message += food.capacity-food.quantity-food.onOrder+" "+food.type+", ";
				food.onOrder=food.capacity-food.quantity;
				food.status=FoodState.fullOrdering;
			}
		}
		
		Do(message);
		
		if (shoppingList.size()==0){
			Do("Cannot order any foods because markets are out or no foods are low");
			return;
		}
		
		if (markets.size()==1){
			Do("ordering");
			markets.get(0).msgHereIsOrder(this, cashier, shoppingList);
		}
		else if (markets.size()>1){
			Market m = markets.remove(0);		//Rearrange markets, kind of like a circular queue?
			markets.add(m);
			m.msgHereIsOrder(this, cashier, shoppingList);
		}
	}
	
	
	//inner classes
	private class Order{
		Waiter w;
		String choice;
		int table;
		OrderState state;
		
		Order(Waiter waiter, String ch, int t, OrderState s){
			w=waiter;
			choice=ch;
			table=t;
			state=s;
		}		
	}
	enum OrderState{pending,cooking,done,finished}
	
	private class Food{
		String type;
		int cookingTime;
		int quantity;
		int capacity;
		int low;
		int onOrder=0;
		FoodState status = FoodState.none;
		List<Market> uselessMarkets = new ArrayList<Market>();
		
		Food(String t, int time, int quantity, int capacity, int low){
			type=t;
			cookingTime=time;
			this.quantity = quantity;
			this.capacity = capacity;
			this.low = low;
		}
	}
	enum FoodState{none,fullOrdering,partialOrdering,impossible};
	
	//utilities
	private List<String> getEmptyFoodList(){
		List<String> emptyFoods = new ArrayList<String>();
		for (String food: foodMap.keySet()){
			if (foodMap.get(food).quantity<=0)
				emptyFoods.add(food);
		}
		return emptyFoods;
	}
	
	public void setCashier (Cashier c){
		cashier = c;
	}
	
	public void addMarket(Market m){
		markets.add(m);
		stateChanged();
	}
	
	public void setGui(CookGui cg){
		cookGui = cg;
	}
	
	public String getName() {
		return name;
	}
	
}

