package restaurant.restaurantGabe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import agent.Agent;
//import restaurant.OldCustomerAgent.AgentEvent;
import restaurant.restaurantGabe.gui.CustomerGui;
import restaurant.restaurantGabe.interfaces.Customer;
import restaurant.restaurantGabe.util.Check;
import restaurant.restaurantGabe.util.Menu;

public class CustomerRole extends Agent implements Customer{
	
	//INITIALIZATION
	public CustomerRole(String name){
		this.name = name;
		if(name.equals("Poor")){
			money = 0;
		}
		if(name.equals("Cheap")){
			money = 6;
		}
		if(name.equals("Flake")){
			money = 0;
		}
	}
	
	
	//SETTERS
	public void setCashier(CashierRole c){
		this.cashier = c;
	}
	
	public void setHost(HostRole h){
		host = h;
	}
	
	public void setGui(CustomerGui cGui){
		customerGui = cGui;
	}
	
	//GETTERS
	public String getName(){
		return name;
	}
	
	public CustomerGui getGui(){
		return customerGui;
	}
	
	//METHODS
	public String toString() {
		return "customer " + getName();
	}
	
	
	
	
	//DATA
	private String name;
	//Cust's menu
	private Menu menu;
	//Timer that will be used for deciding order and eating
	Timer timer = new Timer();
	
	
	
	//Will keep the Customer from saying he's left until his gui is offscreen
	//private Semaphore outOfRestaurant = new Semaphore(0,true);
	
	public CustomerGui customerGui; 
	
	//States and events values used to implement the customer as a state machine
	private enum CustState {out,asking,waiting,beingSeated,seated,thinking,readyToOrder,ordered,eating,delinquent,needChange,paid,leaving};
	private enum CustEvent {none,gotHungry,full,followHost,atTable,newChoice,tooExpensive,readyToOrder,asked,served,done,gotChange,dismissed,left};

	//Agent's state and event
	private CustState state = CustState.out;
	private CustEvent event = CustEvent.none;
	
	
	//Hack to give the customer contact with the host and waiter
	private HostRole host;
	private WaiterRole waiter;
	private CashierRole cashier;
	
	//the customer's choice on the menu
	private String choice;
	//what the customer has tried ordering that they don't have
	private List<String> outOfFoods = Collections.synchronizedList(new ArrayList<String>());
	
	private Check check;
	private boolean haveCheck = false;
	
	
	int money = 100;
	
	//MESSAGES
	
	public void msgPayNextTime(){
		event = CustEvent.dismissed;
		stateChanged();
	}
	
	//tells customer that restaurant is full
	public void msgRestaurantFull(){
		event = CustEvent.full;
		//Do("I might need to leave");
		stateChanged();
	}
	
	//sets customer state to hungry - comes from gui
	public void msgGotHungry(){
		//System.out.println("In the agent Message");
		event = CustEvent.gotHungry;
		stateChanged();
	}
	
	//waiter tells customer to go to table
	public void msgFollowMeToTable(WaiterRole w,Menu m){
		menu = m;
		event = CustEvent.followHost;
		waiter = w;
		stateChanged();
	}
	
	//GUI message that cust is at table
	public void msgAnimationFinishedGoToSeat(){
		event = CustEvent.atTable;
		stateChanged();
	}
	
	public void msgOutOfChoice(Menu m){
		this.menu = m;
		state = CustState.seated;
		event = CustEvent.newChoice;
		outOfFoods.add(choice);
		stateChanged();
	}
	
	//waiter asks customer what he wants
	public void msgWhatWouldYouLike(){
		//Do("Thanks for asking");
		event = CustEvent.asked;
		stateChanged();
	}
	
	//waiter tells customer food is here
	public void msgHereIsYourFood(){
		event = CustEvent.served;
		stateChanged();
	}
	
	public void msgHereIsCheck(Check check){
		Do("Got my check");
		this.check = check;
		haveCheck = true;
		stateChanged();
	}
	
	public void msgHereIsChange(int cash){
		Do("Being given change");
		event = CustEvent.gotChange;
		stateChanged();
	}
	
	//GUI message that tells customer he's gone
	public void msgAnimationFinishedLeaveRestaurant(){
		state = CustState.out;
		stateChanged();
	}
	
	//SCHEDULER
	protected boolean pickAndExecuteAnAction(){
		
		if(state == CustState.delinquent && event == CustEvent.dismissed){
			Do("Sorry");
			LeaveRestaurant();
		}
		
		//if restaurant is full, possibly leave
		if(state == CustState.asking && event == CustEvent.full){
			Do("Deciding whether or not to leave");
			PossiblyLeave();
			return true;
		}
		
		//if out and gotHungry
		if(state == CustState.out && event == CustEvent.gotHungry){
			//goToRestaurant()
			goToRestaurant();
			return true;
		}
		//if waiting and followHost
		if(state==CustState.waiting || state==CustState.asking && event == CustEvent.followHost){
			//sitDown()
			sitDown();
			return true;
		}
		//if beingSeated and atTable
		if(state == CustState.beingSeated && event == CustEvent.atTable){
			//beSeated
			state = CustState.seated;
			//System.out.println(name+": I'm seated");
			thinkOfOrder();
			return true;
		}
		
		//if seated and out of choice, given new menu, think of order
		if(state==CustState.seated && event == CustEvent.newChoice){
			Do("Shoot. Ok, let me think of what else I want.");
			thinkOfOrder();
			return true;
		}
		
		//if seated and readyToOrder
		if(state==CustState.thinking && event == CustEvent.readyToOrder){
			//askToOrder()
			askToOrder();
			return true;
		}
		//if readyToOrder and asked
		if(state== CustState.readyToOrder && event == CustEvent.asked){
			//Do("In this condition");
			//Order()
			Order();
			return true;
		}
		//if Ordered and served
		if(state==CustState.ordered && event==CustEvent.served){
			Eat();
		}
		//if eat and done
		if(state==CustState.eating && event==CustEvent.done && haveCheck){
			//Do("Leaving now");
			//LeaveRestaurant();
			//LeaveRestaurant();
			PayCheck();
			return true;
		}
		if(state==CustState.needChange && event==CustEvent.gotChange){
			Do("Leaving now");
			LeaveRestaurant();
		}
		if(state==CustState.thinking && event == CustEvent.tooExpensive){
			LeaveRestaurant();
		}
		
		
		
		return false;
	}
	
	//ACTIONS
	
	private void PossiblyLeave(){
		int leave = (int) (2*Math.random());
		//Do("d"+leave);
		leave = 0;
		if(name.equals("Leave")){
			leave = 1;
		}
		if(name.equals("Stay")){
			leave = 0;
		}
		
		if(leave==1){
			Do("I can't wait. Leaving");
			host.msgStayOrNot(this,false);
			LeaveRestaurant();
			return;
		}
		state = CustState.waiting;
		Do("I'll stay");
		host.msgStayOrNot(this,true);
	}
	
	private void PayCheck(){
		int cash = check.amount;
		if(cash>this.money){
			
			cashier.msgICantPay(this.check,cash);
			this.state = CustState.delinquent;
			return;
		}
		Do("Paying $" + cash);
		cashier.msgPayment(this.check,cash);
		this.state = CustState.needChange;
	}
	
	//when cust gets hungry
	private void goToRestaurant(){
		Do("Entering restaurant");
		DoGoToRestaurant();
		host.msgIWantToEat(this);
		state = CustState.asking;
		//System.out.println("I came to the Restaurant");
	}
	
	


	//when told to follow waiter
	private void sitDown(){
		state = CustState.beingSeated;
		//Do("Being seated. Going to table");
	}
	
	//process for deciding what to order
	private void thinkOfOrder(){
		//Do("Deciding what to order");
		state = CustState.thinking;
		
		boolean canAffordLeft = false;
		boolean afford = false;
		boolean foodLeft = false;
		int start = (int) (Math.random()*menu.foods.size());
		//for(Menu.food option:menu.foods){
		for(int i = 0;i<menu.foods.size();++i){
			Menu.food option = menu.foods.get((i+start)%menu.foods.size());
			//can = true;
			boolean in = true;
			synchronized(outOfFoods){
				for(String out:outOfFoods){
					if(out.equals(option.choice)){
						in = false;
						break;
					}
				}
			}
			if(in){
				foodLeft = true;
				if(in && option.price<=money){
				
					choice = option.choice;
					canAffordLeft = true;
					break;
				}

			}
		}
		
		for(Menu.food f:Menu.foods){
			if(f.price<this.money){
				afford = true;
			}
		}
		
		
		
		
		if(!canAffordLeft && !name.equals("Flake")){
			waiter.msgFoodTooExpensiveLeaving(this);
			event = CustEvent.tooExpensive;
			Do("Too expensive");
		}
		if(!foodLeft){
			Do("You're out of everything! I'm leaving");
			LeaveRestaurant();
			state = CustState.leaving;
		}

		
	
		
		/*int menuChoice = (int) (Math.random()*menu.foods.size());
		choice = menu.foods.get(menuChoice).choice;*/
		
		//Timer that tracks customer's progress in deciding order
		timer.schedule(new TimerTask() {
			public void run() {
				//print("Done deciding");
				event = CustEvent.readyToOrder;
				//isHungry = false;
				stateChanged();
			}
		},
		700);
	
		
		if(name.equals("Steak") || name.equals("Flake")){
			choice = "Steak";
			
		}
		if(name.equals("Chicken")){
			choice = "Chicken";
			
		}
		if(name.equals("Pizza")){
			choice = "Pizza";
			
		}
		if(name.equals("Salad")){
			choice = "Salad";
			
		}
		
		
		
		//choice = "pizza";
	}
	
	//when ready to order
	private void askToOrder(){
		state = CustState.readyToOrder;
		DoAskToOrder();
		waiter.msgImReadyToOrder(this);
	}
	
	//tell waiter the order
	private void Order(){
		DoOrder(choice);
		waiter.msgHereIsMyChoice(this,choice);
		state = CustState.ordered;
	}
	
	//eat the food
	private void Eat(){
		state = CustState.eating;
		//Do("Got my food!");

		DoEat();
		timer.schedule(new TimerTask() {
			public void run() {
				//print("Done eating");
				event = CustEvent.done;
				customerGui.setFood(null);
				//isHungry = false;
				stateChanged();
			}
		},
		1000);
	}
	
	//when finished, leave, notify waiter
	private void LeaveRestaurant(){
		state = CustState.leaving;
		haveCheck = false;
		if(waiter!=null){
			waiter.msgDoneEatingAndLeaving(this);
		}
		DoLeaveRestaurant();
	}
	
	//GUI
	
	private void DoGoToRestaurant() {
		customerGui.DoGoToRestaurant();
		
	}
	
	private void DoAskToOrder(){
		print("I'd like to order");
	}
	
	private void DoOrder(String choice){
		Do("I'll have "+choice);
		customerGui.setFood(choice.substring(0,2)+"?");
	}
	
	private void DoEat(){
		//eat
		customerGui.setFood(choice.substring(0,2));
	}
	
	private void DoLeaveRestaurant(){
		customerGui.DoExitRestaurant();
	}
	

}
