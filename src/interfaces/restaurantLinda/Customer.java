package interfaces.restaurantLinda;

import restaurantLinda.Check;
import restaurantLinda.Menu;
import restaurantLinda.gui.CustomerGui;

public interface Customer {
	
	public abstract void msgRestaurantFull();

	public abstract void msgFollowMe(Waiter w, Menu m);
	
	public abstract void msgWhatDoYouWant();
	
	public abstract void msgRedoOrder(Menu menu,String food);
	
	public abstract void msgHereIsFood(String food);
	
	public abstract void msgHereIsCheck(Check bill, Cashier cashier);
	
	public abstract void msgPaymentReceived(int owed);
	
	public abstract String getName();
	
	public abstract CustomerGui getGui();

}
