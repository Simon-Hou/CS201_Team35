package restaurant.restaurantYocca;

import java.util.concurrent.Semaphore;

import restaurant.ProducerConsumerMonitor;
import restaurant.restaurantYocca.WaiterRole.CustomerState;
import restaurant.restaurantYocca.WaiterRole.MyCustomer;
import interfaces.Person;
import interfaces.restaurantYocca.Cashier;
import interfaces.restaurantYocca.Cook;
import interfaces.restaurantYocca.Host;

public class ProducerConsumerWaiterRole extends WaiterRole{

	ProducerConsumerMonitor<RestaurantOrder> orderMonitor;
		
	public ProducerConsumerWaiterRole(String name, Person p) {
		super();
		this.name = name;
		this.p = p;
	}
	
	//messages, scheduler, and most actions belong in base waiter class
	
	@Override
	public void submitOrder(Order o){
		RestaurantOrder order = new RestaurantOrder(this, o.getTable(), o.getChoice());
		
		waiterGui.DoGoToTheOrderStand();
		waiterGui.setArrived(false);
		try{
			atTheOrderStand.acquire();
			p.msgStateChanged();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		//mc.state=CustomerState.orderSent;

		orderMonitor.insert(order);
		waiterGui.DoLeaveCustomer();
	}
	
	public void setMonitor(ProducerConsumerMonitor<RestaurantOrder> orders){
		this.orderMonitor = orders;
	}
}
