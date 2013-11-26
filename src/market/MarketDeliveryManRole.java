package market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import person.PersonAgent;
//import restaurant.Restaurant;
import role.Role;
import interfaces.MarketDeliveryMan;
import interfaces.MarketCashier;

public class MarketDeliveryManRole extends Role implements MarketDeliveryMan {

	
	
	
	
	//-----------------------------DATA--------------------------------
	private List<MarketInvoice> orders = new ArrayList<MarketInvoice>();
	private List<MyPayment> payments = new ArrayList<MyPayment>();
	private MarketCashier cashier;
	private String name;
	private PersonAgent p;
	
	
	
	public MarketDeliveryManRole(String name, PersonAgent p){
		this.name = name;
		this.p = p;
		
	}
	
	public boolean canLeave() {

		return false;
	}
	
	
	//-----------------------------MESSAGES--------------------------------
	public void msgDeliverThisOrder(MarketInvoice order){
		orders.add(order);
		p.msgStateChanged();
		
	}
	
	public void msgHereIsPayment(int payment){
		MyPayment pay = new MyPayment(payment);
		payments.add(pay);
		p.msgStateChanged();
		
	}
	
	
	//-----------------------------SCHEDULER--------------------------------
	public boolean pickAndExecuteAnAction() {
		
		if (!orders.isEmpty()){
			DeliverOrder(orders.get(0));
			return true;
		}
		
		if (!payments.isEmpty()){
			DeliverPayment(payments.get(0));
			return true;
		}
		
		
		return false;
	}

	
	//-----------------------------ACTIONS--------------------------------
	private void DeliverOrder(MarketInvoice order){
		Do("Deliverying an order to a restaurant");
		//DoGoToRestaurant(order.restaurant);
		//order.restaurant.cook.msgHereIsDelivery(order);
		//order.restaurant.cashier.msgHereIsInvoice(order.invoice);
		orders.remove(order);
		
		
		//FOR TESTING:::::
		msgHereIsPayment(order.invoice);
	}
	
//	private void DoGoToRestaurant(Restaurant rest){
//		//go to the restaurant
//	}
	private void DeliverPayment(MyPayment payment){
		DoGoToCashier();
		Do(cashier.getName() + ", here is a business payment.");
		cashier.msgHereIsBusinessPayment(payment.amount);
		payments.remove(payment);
	
		
	}
	
	private void DoGoToCashier(){
		//go back to the market, go to the cashier
		
	}
	
	
	
	//-----------------------------UTILITIES--------------------------------
	public String getName(){
		return name;
	}
	
	public void setCashier(MarketCashier c){
		cashier = c;
	}
	
	private class MyPayment {
		int amount;
		//Restaurant restaurant;
	
		MyPayment(int payment){
			amount = payment;
	
			
		}
	}



	

}
