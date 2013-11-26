package market;

import java.util.ArrayList;
import java.util.List;

import restaurant.Restaurant;

//import restaurant.Restaurant;

public class MarketInvoice {
	
	//Restaurant restaurant;
    List<OrderItem> order = new ArrayList<OrderItem>();
    int total;
    int paid=0;
    
    Market market;
    Restaurant restaurant;
    
    public MarketInvoice (List<OrderItem> o, Market m, Restaurant r, int total){
    	this.order = o;
    	this.market = m;
    	this.restaurant = r;
    	this.total = total;
    }
    
    public void updatePayment(int payment){
    	paid += payment;
    }
}