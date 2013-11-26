package market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import person.PersonAgent;
//import restaurant.Restaurant;
import role.Role;
import interfaces.MarketDeliveryMan;
import interfaces.MarketCashier;

public class MarketDeliveryManRole extends Role implements MarketDeliveryMan {

	
	//-----------------------------DATA--------------------------------
	private List<MarketInvoice> orders = new ArrayList<MarketInvoice>();
	private List<MyPayment> payments = new ArrayList<MyPayment>();
	private Market market;
	private String name;
	private PersonAgent p;
	
	//DeliveryMan is just going to wait for the restaurant cashier
	private Semaphore receivedPayment = new Semaphore(0,true);
	
	public MarketDeliveryManRole(String name, PersonAgent p, Market m){
		this.name = name;
		this.p = p;
		this.market = m;
		
	}
	
	public boolean canLeave() {

		return false;
	}
	
	
	//-----------------------------MESSAGES--------------------------------
	public void msgDeliverThisOrder(MarketInvoice order){
		orders.add(order);
		p.msgStateChanged();
		
	}
	
	public void msgHereIsPayment(int payment, MarketInvoice invoice){
		MyPayment pay = new MyPayment(payment, invoice);
		payments.add(pay);
		receivedPayment.release();
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
		
		orders.remove(order);
		
		order.restaurant.cook.msgHereIsDelivery(order);
		order.restaurant.cashier.msgHereIsInvoice(this, order);
		
		try {
			receivedPayment.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	private void DoGoToRestaurant(Restaurant rest){
//		//go to the restaurant
//	}
	private void DeliverPayment(MyPayment payment){
		DoGoToCashier();
		Do(market.cashier.getName() + ", here is a business payment.");
		market.cashier.msgHereIsBusinessPayment(payment.amount);
		payments.remove(payment);
	
		
	}
	
	private void DoGoToCashier(){
		//go back to the market, go to the cashier
		
	}
	
	
	
	//-----------------------------UTILITIES--------------------------------
	public String getName(){
		return name;
	}
	
	public void setMarket(Market m){
		market = m;
	}
	
	private class MyPayment {
		int amount;
		MarketInvoice invoice;
	
		MyPayment(int payment, MarketInvoice invoice){
			amount = payment;
			this.invoice = invoice;			
		}
	}



	

}
