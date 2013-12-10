package market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import cityGui.trace.AlertLog;
import cityGui.trace.AlertTag;

import UnitTests.mock.LoggedEvent;
import UnitTests.mock.MarketMock.MockMarketPerson;
import person.PersonAgent;
//import restaurant.Restaurant;
import role.Role;
import interfaces.MarketDeliveryMan;
import interfaces.MarketCashier;
import interfaces.Person;

public class MarketDeliveryManRole extends Role implements MarketDeliveryMan {

	
	//-----------------------------DATA--------------------------------
	public List<MarketInvoice> orders = new ArrayList<MarketInvoice>();
	public List<MyPayment> payments = new ArrayList<MyPayment>();
	private Market market;
	private String name;
	private Person p;
	
	//DeliveryMan is just going to wait for the restaurant cashier
	private Semaphore receivedPayment = new Semaphore(0,true);
	
	public MarketDeliveryManRole(String name, Person p, Market m){
		this.name = name;
		this.p = p;
		this.market = m;
		
	}
	
	public boolean canLeave() {

		return false;
	}
	
	
	//-----------------------------MESSAGES--------------------------------
	public void msgDeliverThisOrder(MarketInvoice order){
		log.add(new LoggedEvent("got msgDeliverThisOrder"));
		DoInfo("Received message to deliver order");
		orders.add(order);
		p.msgStateChanged();
		
	}
	
	public void msgHereIsPayment(int payment, MarketInvoice invoice){
		log.add(new LoggedEvent("got msgHereIsPayment"));
		DoInfo("Received payment from restaurant of $" + payment);
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
		DoMessage("Delivering an order to a restaurant");
		log.add(new LoggedEvent("action DeliverOrder"));
		//DoGoToRestaurant(order.restaurant);

		orders.remove(order);
		if (order.restaurant != null) {
			order.restaurant.cook.msgHereIsDelivery(order);
			order.restaurant.cashier.msgHereIsInvoice(this, order);

			try {
				receivedPayment.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
//	private void DoGoToRestaurant(Restaurant rest){
//		//go to the restaurant
//	}
	private void DeliverPayment(MyPayment payment){
		DoGoToCashier();
		DoMessage(market.cashier.getName() + ", here is a business payment.");
		log.add(new LoggedEvent("action DeliverPayment"));
		if (!(p instanceof MockMarketPerson)) {
			market.cashier.msgHereIsBusinessPayment(payment.amount);
		}
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
	
	public void DoInfo(String message){
		//super.Do(message);
		if (market.gui!=null)
			AlertLog.getInstance().logInfo(AlertTag.MARKET, name, message, market.gui.ID);
	}
	
	public void DoMessage(String message){
		//super.Do(message);
		if (market.gui!=null)
			AlertLog.getInstance().logMessage(AlertTag.MARKET, name, message, market.gui.ID);
	}



	

}
