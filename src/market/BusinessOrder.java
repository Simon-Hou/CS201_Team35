package market;

import java.util.ArrayList;
import java.util.List;

//import restaurant.Restaurant;

public class BusinessOrder {
	
	//Restaurant restaurant;
    List<OrderItem> order = new ArrayList<OrderItem>();
    int invoice;
    
    public enum OrderState {ordered, acquired, gotInvoice, none};
    OrderState state = OrderState.ordered;
    
    public void addItem(OrderItem item){
    	order.add(item);
    }
    
}