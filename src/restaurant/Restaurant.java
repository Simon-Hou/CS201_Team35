package restaurant;

import java.util.List;

import cityGui.CityRestaurant;
import interfaces.BaseRestaurantCashier;
import interfaces.BaseRestaurantCook;
import interfaces.BaseRestaurantCustomer;
import interfaces.BaseRestaurantHost;
import interfaces.BaseRestaurantWaiter;
import interfaces.PlaceOfWork;


public abstract class Restaurant implements PlaceOfWork{
	
	public BaseRestaurantHost host;
	public BaseRestaurantCashier cashier;
	public BaseRestaurantCook cook;
	protected List<BaseRestaurantWaiter> waiters;
	public CityRestaurant cityRestaurant;
	public int cash;
	
	public abstract void customerEntering(BaseRestaurantCustomer c);
	
	public boolean unStaffed(){
		return !host.isPresent() || !cook.isPresent() || !cashier.isPresent() || waiters.isEmpty();
	}
	
}
