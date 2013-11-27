package market;

import interfaces.MarketCashier;
import market.gui.*;
import interfaces.MarketCustomer;
import interfaces.MarketHost;
import interfaces.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

import UnitTests.mock.EventLog;
import UnitTests.mock.LoggedEvent;
import person.PersonAgent;
import public_Object.Food;
import role.Role;
import testAgents.testPerson;

public class MarketCustomerRole extends Role implements MarketCustomer {

	
	public EventLog log = new EventLog();
	
	public Person p;
	public RoleState state;
	public enum RoleState {JustEnteredMarket, Ordered, ReceivedItems, WaitingForTotal, Paying, Leaving, Done}
	public RoleEvent event;
	public enum RoleEvent {none, itemsArrived, askedToPay, paymentReceived, allowedToLeave }
	public Map<String, Integer> shoppingList = new HashMap<String, Integer>();    
	public Map<String, Integer> groceries = new HashMap<String,Integer>();
	public int bill;
	public Receipt receipt;
	public Market market;

	MarketHost host;
	MarketCashier cashier;
	public String name;
	public boolean sad = false;
	
	public MarketCustomerGui gui;
	private Semaphore atDestination = new Semaphore(0,true);
	
	public void addToShoppingList(String food, int amount){
		this.shoppingList.put(food, amount);
	}

	public MarketCustomerRole(String name, Person p){		
		this.name = name;
		this.p = p;
	}
	
	public void msgHereAreItems(Map<String, Integer> groceries){
		log.add(new LoggedEvent("got msgHereAreItems"));
		Do("Got my MARKET items");
		this.event = RoleEvent.itemsArrived;
	    this.groceries = groceries;
	    //this.cashier = cashier;
	    p.msgStateChanged();
	}

	public void msgHereIsTotal(int total){
		log.add(new LoggedEvent("got msgHereIsTotal"));
		Do("Got the MARKET bill");
	    bill = total;
	    event = RoleEvent.askedToPay;
	    p.msgStateChanged();
	}

	public void msgHereIsYourChange(Receipt receipt, int change){
		log.add(new LoggedEvent("got msgHereIsYourChange"));
		Do("got change");
	    this.receipt = receipt;
	    p.addToWallet(change);
	    event = RoleEvent.paymentReceived;
	    p.msgStateChanged();
	}

	public void msgYouOweMoney(Receipt receipt, int debt){
		log.add(new LoggedEvent("got msgYouOweMoney"));
	    this.receipt = receipt;
	    event = RoleEvent.paymentReceived;
	    p.msgStateChanged();
	}

	public void msgYouCanLeave(){
	
		log.add(new LoggedEvent("got msgYouCanLeave"));
	    event = RoleEvent.allowedToLeave;
	    p.msgStateChanged();
	}

	public void msgOutOfStock(Map<String, Integer> unfullfillable){
		//what do I do if they don't have what I want??
		p.msgStateChanged();
	}
	
	public void msgWeHaveNothing(){
		log.add(new LoggedEvent("got msgWeHaveNothing"));
		sad = true;
		p.msgStateChanged();
	}
	
	public void msgYouAreAtMarket(Market m){
		log.add(new LoggedEvent("got msgYouAreAtMarket"));
		Do("I'm at the market.");
		setMarket(m);
		state = RoleState.JustEnteredMarket;
		p.msgStateChanged();
	}
	
	//from animation
	public void msgAtDestination(){
		atDestination.release();
		//p.msgStateChanged();
		
	}
	//--------Scheduler-------

	
	public boolean pickAndExecuteAnAction() {
		if (state==RoleState.JustEnteredMarket && host!=null){
		    state = RoleState.Ordered;        
		    MakeOrder();
		    return true;
		}
		if (state==RoleState.Ordered && event == RoleEvent.itemsArrived){
		    state = RoleState.WaitingForTotal;         
		    GoPay();
		    return true;
		}
		if (state == RoleState.WaitingForTotal && event == RoleEvent.askedToPay){
		    state = RoleState.Paying;         
		    MakePayment();
		    return true;
		}
		if (state == RoleState.Paying && event == RoleEvent.paymentReceived){
		    state = RoleState.Leaving;
		    TryToLeave();
		    return true;
		}
		if (state == RoleState.Leaving && event == RoleEvent.allowedToLeave){
		    state = RoleState.Done;
			LeaveMarket();
		    return true;
		}

		if (sad){
			LeaveSad();
			return true;
		}
		return false;
	}
	
	
	//Actions
	private void MakeOrder(){
		
		log.add(new LoggedEvent("MakeOrder action"));
		//enter door
		if(gui!=null){
			gui.DoGoToExit();
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
		//go to host
		if(gui!=null){
			gui.DoGoToHost();
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
		
		Do("Giving my order to the host.");
     	host.msgCustomerWantsThis(this, shoppingList);
     	if(gui!=null){
     		gui.DoGoToItemDrop();
     	}
	}

	private void GoPay(){
		log.add(new LoggedEvent("action GoPay"));
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

		Do("Hi " + cashier.getName() + ", how much do I owe you?");
	    cashier.msgServiceCustomer(this, groceries);
	}

	private void MakePayment(){                //Right now markets letting customer do an IOU
	    
		log.add(new LoggedEvent("action MakePayment"));
		Do("Making payment");
		int payment;
	    if (p.getWalletAmount()>=bill)
	        payment = bill;
	    else
	        payment = p.getWalletAmount();

	    p.takeFromWallet(payment);
	    cashier.msgCustomerPayment(this, payment);    
	}

	private void TryToLeave(){
		log.add(new LoggedEvent("action TryToLeave"));
		
		if(gui!=null){
			gui.DoGoToExit();
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
		
		Do("Okay, time to leave!");
	    host.msgCustomerLeaving(this, receipt, groceries);
	}

	private void LeaveMarket(){
		log.add(new LoggedEvent("action LeaveMarket"));
		for (String item: groceries.keySet()){
			p.putInBag(item, groceries.get(item));
		}
		p.msgThisRoleDone(this);
	    
		if(gui!=null){
			gui.DoExitRestaurant();
		}
		
	}
	
	private void LeaveSad(){
		log.add(new LoggedEvent("action LeaveSad"));
		if(gui!=null){
			gui.DoGoToExit();
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
		market.removeCustomer(this);
		//JUST ADDED
		if(gui!=null){
			gui.DoExitRestaurant();
		}

		
		p.msgThisRoleDone(this);
	    
		sad = false;
		
		
		//HACK--------give yourself some food
	
	
		
	}
	//Utilities
	public Person getPerson(){
		return p;
	}
	public void setHost(MarketHost host2) {
		host = host2;
		
	}public void setGui(MarketCustomerGui g){
		gui = g;
	}
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setCashier(MarketCashier cashier){
		this.cashier = cashier;
	}
	
	public void setMarket(Market m){
		this.market = m;
		this.host = m.host;
		this.cashier = m.cashier;
	}
}
