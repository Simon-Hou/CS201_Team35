package restaurant.restaurantLinda;

import restaurant.ProducerConsumerMonitor;
import restaurant.restaurantLinda.WaiterRole.CustomerState;
import restaurant.restaurantLinda.WaiterRole.MyCustomer;
import interfaces.restaurantLinda.Cashier;
import interfaces.restaurantLinda.Cook;
import interfaces.restaurantLinda.Host;

public class ProducerConsumerWaiterRole extends WaiterRole{

	ProducerConsumerMonitor<RestaurantOrder> orderMonitor;
	
	public ProducerConsumerWaiterRole(String name, Host host, Cook cook, Cashier cashier, ProducerConsumerMonitor orders) {
		super();
		this.host=host;
		this.name = name;
		this.cook = cook;
		this.cashier = cashier;
		orderMonitor = orders;
	}
	
	//messages, scheduler, and most actions belong in base waiter class
	
	//Single function that needs to be overwritten
	protected void SendOrder(MyCustomer mc){
		RestaurantOrder order = new RestaurantOrder(this, mc.table, mc.choice);
		
		Do("Sending " + mc.c.getName() + "'s order to cook");
		waiterGui.DoGoToCook();
		try{
			atDestination.acquire();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		mc.state=CustomerState.orderSent;

		orderMonitor.insert(order);
	}
}