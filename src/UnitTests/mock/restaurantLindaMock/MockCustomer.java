package UnitTests.mock.restaurantLindaMock;


import UnitTests.mock.LoggedEvent;
import UnitTests.mock.Mock;
import restaurant.Restaurant;
import restaurant.restaurantLinda.*;
import restaurant.restaurantLinda.gui.CustomerGui;
import interfaces.restaurantLinda.*;

/**
* A sample MockCustomer built to unit test a CashierAgent.
*
* @author Monroe Ekilah
*
*/
public class MockCustomer extends Mock implements Customer {

	public MockCustomer(String name){
		super(name);
	}
	
	@Override
	public void msgAtRestaurant(Restaurant r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgRestaurantFull() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgFollowMe(Waiter w, Menu m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgWhatDoYouWant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgRedoOrder(Menu menu, String food) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsFood(String food) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsCheck(Check bill, Cashier cashier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPaymentReceived(int owed) {
		log.add(new LoggedEvent("Received message from cashier and owe " + owed));
		
	}

	@Override
	public CustomerGui getGui() {
		// TODO Auto-generated method stub
		return null;
	}

}