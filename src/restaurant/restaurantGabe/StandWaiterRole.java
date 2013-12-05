package restaurant.restaurantGabe;

import restaurant.restaurantGabe.util.Order;
import restaurant.restaurantGabe.util.OrderState;
import restaurant.restaurantGabe.util.RevolvingStand;

public class StandWaiterRole extends WaiterRole {

	RevolvingStand stand; 
	
	
	public StandWaiterRole(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public void setStand(RevolvingStand r){
		this.stand = r;
	}
	
	
	@Override
	protected void giveOrderToCook(Order o){
		DoPutOrderOnStand();
		
		//forces waiter to wait until he gets to the cook to ask for food
		try {
			atCook.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Do("Giving order to cook");
		//The cook will change the order state
		//o.s = OrderState.requested;
		//Cook.msgHereIsAnOrder(this,o);
		
		o.s = OrderState.requested;
		o.w = this;
		stand.addOrder(o);
		
		DoGoToHome();
	}
	
	protected void DoPutOrderOnStand(){
		this.waiterGui.doPutOrderOnStand();
	}
	
	

}
