package market;

import interfaces.MarketCashier;
import interfaces.MarketCustomer;
import interfaces.MarketHost;

import java.util.HashMap;
import java.util.Map;

import person.PersonAgent;
import role.Role;
import testAgents.testPerson;

public class MarketCustomerRole extends Role implements MarketCustomer {
	PersonAgent p;
	RoleState state;
	enum RoleState {JustEnteredMarket, Ordered, ReceivedItems, WaitingForTotal, Paying, Leaving, Done}
	RoleEvent event;
	enum RoleEvent {none, itemsArrived, askedToPay, paymentReceived, allowedToLeave }
	Map<String, Integer> shoppingList = new HashMap<String, Integer>();    
	Map<String, Integer> groceries = new HashMap<String,Integer>();
	int bill;
	Receipt receipt;
	Market market;
	MarketHost host;
	MarketCashier cashier;
	String name;
	
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
	
	public MarketCustomerRole(String name, PersonAgent p){
		this.name = name;
		this.p = p;
	}
	
	public void msgHereAreItems(Map<String, Integer> groceries){
		Do("Got my MARKET items");
		this.event = RoleEvent.itemsArrived;
	    this.groceries = groceries;
	}

	public void msgHereIsTotal(int total){
		Do("Got the MARKET bill");
	    bill = total;
	    event = RoleEvent.askedToPay;
	}

	public void msgHereIsYourChange(Receipt receipt, int change){
	    this.receipt = receipt;
	    p.addToWallet(change);
	    event = RoleEvent.paymentReceived;
	}

	public void msgYouOweMoney(Receipt receipt, int debt){
	    this.receipt = receipt;
	    event = RoleEvent.paymentReceived;
	}

	public void msgYouCanLeave(){
		Do("Allowed to leave");
	    event = RoleEvent.allowedToLeave;
	}

	public void msgOutOfStock(Map<String, Integer> unfullfillable){
		//what do I do if they don't have what I want??
	}
	
	public void msgYouAreAtMarket(Market m){
		Do("I'm at the market.");
		host = m.host;
		cashier = m.cashier;
		state = RoleState.JustEnteredMarket;
		p.msgStateChanged();
	}
	
	//Scheduler

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
		    LeaveMarket();
		    return true;
		}

		return false;
	}
	
	
	//Actions
	private void MakeOrder(){
		Do("Making my order.");
     	host.msgCustomerWantsThis(this, shoppingList);
	}

	private void GoPay(){
	    //DoGoToCashier();
	    cashier.msgPleaseServiceCustomer(this, groceries);
	}

	private void MakePayment(){                //Right now markets letting customer do an IOU
	    int payment;
	    if (p.purse.wallet>=bill)
	        payment = bill;
	    else
	        payment = p.purse.wallet;

	    p.purse.wallet -= payment;
	    cashier.msgCustomerPayment(this, payment);    
	}

	private void TryToLeave(){
	    host.msgCustomerLeaving(this, receipt, groceries);
	}

	private void LeaveMarket(){
		for (String item: groceries.keySet()){
			p.purse.bag.put(item, groceries.get(item));
		}
	    
	}
	
	//Utilities
	public PersonAgent getPerson(){
		return p;
	}

}
