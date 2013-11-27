package restaurant.restaurantLinda;

import restaurant.Restaurant;
import restaurant.restaurantLinda.gui.CustomerGui;
import role.Role;
import agent.Agent;

import interfaces.Person;
import interfaces.restaurantLinda.Cashier;
import interfaces.restaurantLinda.Customer;
import interfaces.restaurantLinda.Host;
import interfaces.restaurantLinda.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Restaurant customer agent.
 */
public class CustomerRole extends Role implements Customer{
	private String name;
	private int hungerLevel = 5;        // determines length of meal
	Timer timer = new Timer();
	private CustomerGui customerGui;
	MyMenu menu;
	Check check;
	String choice;

	public enum AgentState
	{DoingNothing, WaitingInRestaurant, BeingSeated, Seated, WaitingForWaiter, Ordering, Eating, GoingToPay, Paying, Leaving};
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent 
	{none, atRestaurant, impatient, followWaiter, seated, askedToOrder, redoChoice, noFood, foodHere, doneEating, atCashier, paymentReceived, doneLeaving};
	AgentEvent event = AgentEvent.none;
	
	// agent correspondents
	private Restaurant r;
	private Waiter waiter;
	private Cashier cashier;
	//private Table table=null; //hack(?) for gui

	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public CustomerRole(String name, Person p){
		super();
		this.name = name;
		this.p = p;
	}


	// Messages
	public void atRestaurant(Restaurant r) {
		event = AgentEvent.atRestaurant;
		print("Arrived at restaurant");
		
		this.r = r;
		p.msgStateChanged();
	}
	
	public void msgRestaurantFull(){
		if (name.contains("wait")){
			Do("I will wait");
			r.host.msgIWillWait(this, true);
		}
		else
			event = AgentEvent.impatient;
		
		p.msgStateChanged();
	}

	public void msgFollowMe(Waiter w, Menu m) {
		print("Received followMe");
		waiter=w;
		menu=new MyMenu(m);
		event = AgentEvent.followWaiter;
		p.msgStateChanged();
	}

	public void msgAnimationFinishedGoToSeat() {
		Do("Got to seat");
		
		//from animation
		event = AgentEvent.seated;
		p.msgStateChanged();
	}
	
	public void msgWhatDoYouWant(){
		event=AgentEvent.askedToOrder;
		p.msgStateChanged();
	}
	
	public void msgRedoOrder(Menu menu,String food){
		if (food != choice)
			customerGui.DoTalk("???");
		else
			customerGui.DoTalk("");
		
		choice=null;
		event=AgentEvent.redoChoice;
		this.menu=new MyMenu(menu);
		p.msgStateChanged();
	}
	
	public void msgHereIsFood(String food){
		event=AgentEvent.foodHere;
		if (food!=choice)
			customerGui.DoTalk("WRONG FOOD!");
			
		customerGui.DoReceiveFood(food);
		p.msgStateChanged();
	}
	
	public void msgHereIsCheck(Check bill, Cashier cashier){
		check=bill;
		this.cashier=cashier;
		p.msgStateChanged();
	}
	
	public void msgAnimationFinishedGoToCashier(){
		event = AgentEvent.atCashier;
		p.msgStateChanged();
	}
	
	public void msgPaymentReceived(int owed){
		event = AgentEvent.paymentReceived;
		p.msgStateChanged();
	}
	
	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		event = AgentEvent.doneLeaving;
		p.msgStateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		//	CustomerAgent is a finite state machine

		if (state == AgentState.DoingNothing && event == AgentEvent.atRestaurant ){
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.impatient){
			state = AgentState.Leaving;
			Do("Restaurant full, leaving");
			LeaveRestaurant();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followWaiter ){
			state = AgentState.BeingSeated;	
			//No action b/c the seating is happening from waiter/gui's side
			return true;
		}
		if (state == AgentState.BeingSeated && event == AgentEvent.seated){
			state = AgentState.Seated;
			ReadMenu();
			return true;
		}
		if (state == AgentState.Seated && event == AgentEvent.noFood){
			state = AgentState.Leaving;
			customerGui.DoTalk("No food");
			LeaveWithoutEating();
			return true;
		}
		if (state == AgentState.Seated && choice != null){
			state = AgentState.WaitingForWaiter;
			CallWaiter();
			return true;
		}
		if (state == AgentState.WaitingForWaiter && event == AgentEvent.askedToOrder){
			MakeOrder();
			return true;
		}
		if (state == AgentState.Ordering && event == AgentEvent.foodHere){
			state = AgentState.Eating;
			EatFood();
			return true;
		}
		if (state == AgentState.Ordering && event == AgentEvent.redoChoice){
			state = AgentState.Seated;
			ReadMenu();
			return true;
		}
		if (state == AgentState.Eating && event == AgentEvent.doneEating && check!=null){
			state = AgentState.GoingToPay;
			LeaveTable();
			return true;
		}
		if (state == AgentState.GoingToPay && event == AgentEvent.atCashier){
			state = AgentState.Paying;
			MakePayment();
			return true;
		}
		if (state == AgentState.Paying && event == AgentEvent.paymentReceived){
			state = AgentState.Leaving;
			LeaveRestaurant();
			return true;
		}
		if (state == AgentState.Leaving && event == AgentEvent.doneLeaving){
			state = AgentState.DoingNothing;
			FinishLeaving();
			return true;
		}
		return false;
	}

	// Actions
	private void goToRestaurant() {
		Do("Going to restaurant");
		r.host.msgIWantFood(this);//send our instance, so he can respond to us
	}
	
	private void ReadMenu(){
		Do("Reading menu");
		timer.schedule(new TimerTask(){
			public void run(){
				choice=pickFood();
				if (choice.compareToIgnoreCase("Error")==0){
					event = AgentEvent.noFood;
				}
				p.msgStateChanged();
			}
		},1000);
	}
	
	private void CallWaiter(){		
		customerGui.DoTalk("Ready!");
		waiter.msgReadyToOrder(this);
	}
	
	private void MakeOrder(){
		customerGui.DoTalk(choice+"?");
		state=AgentState.Ordering;
		waiter.msgHereIsChoice(this, choice);		
	}
	
	private void EatFood() {
		Do("Eating Food");
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not allow us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				p.msgDoneEating();
				event = AgentEvent.doneEating;
				print("Done eating, cookie=" + cookie);
				customerGui.DoFinishFood();
				p.msgStateChanged();
			}
		},
		hungerLevel*1000);
	}

	private void LeaveWithoutEating(){
		waiter.msgLeaving(this);
		LeaveRestaurant();
	}
	
	private void LeaveTable() {
		waiter.msgDoneAndPaying(this);
		customerGui.DoGoToCashier();
	}
	
	
	private void MakePayment(){
		int payment;
		if (p.getWalletAmount()>=check.getTotal()){
			payment = check.getTotal();
		}
		else{
			payment = p.getWalletAmount();
		}
		
		cashier.msgHereIsPayment(this, check, payment);
		p.takeFromWallet(payment);
		check=null;
	}
	
	private void LeaveRestaurant(){
		Do("Leaving.");
		customerGui.DoExitRestaurant();
	}
	
	public void FinishLeaving(){
		choice=null;
		check=null;
		menu=null;
		waiter=null;
		
		customerGui.isPresent=false;
		r.leaveRestaurant(customerGui);
		r=null;
		
		p.msgThisRoleDone(this);
	}
	
	private String pickFood(){
		//Hacks for debugging
		if (name.contains("steak") && menu.m.containsKey("Steak"))
			return "Steak";
		else if (name.contains("chicken") && menu.m.containsKey("Chicken"))
			return "Chicken";
		else if (name.contains("salad") && menu.m.containsKey("Salad"))
			return "Salad";
		else if (name.contains("pizza") && menu.m.containsKey("Pizza"))
			return "Pizza";
		
		
		Random num = new Random();
		List<String> choiceNames = new ArrayList<String>(menu.m.keySet());
		
		String tempChoice = "Error";
		if (choiceNames.size()>0){
			for (String food: choiceNames){
				if (menu.m.get(food)>p.getWalletAmount())
					menu.m.remove(food);
			}
			
			choiceNames = new ArrayList<String>(menu.m.keySet());
			
			if (choiceNames.size()>0)
				tempChoice= choiceNames.get(num.nextInt(choiceNames.size()));
		}
		return tempChoice;
	}

	// Accessors, etc.
	/**
	 * hack to establish connection to Host agent.
	 */
	public String getCustomerName() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getHungerLevel() {
		return hungerLevel;
	}

	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
		//could be a state change. Maybe you don't
		//need to eat until hunger lever is > 5?
	}

	public String toString() {
		return "customer " + getName();
	}

	public void setGui(CustomerGui g) {
		customerGui = g;
	}

	public CustomerGui getGui() {
		return customerGui;
	}
	
	//Inner Classes
	class MyMenu{
		Map<String, Integer> m;
		
		MyMenu(Menu m){
			this.m = m.getMenu();
		}		
	}
}

