package restaurant;

import role.Role;
import util.JobType;
import cityGui.CityRestaurant;
import interfaces.BaseRestaurantCashier;
import interfaces.BaseRestaurantCook;
import interfaces.Person;
import interfaces.PlaceOfWork;

public class Restaurant implements PlaceOfWork{
	
	public CityRestaurant cityRestaurant;
	
	public BaseRestaurantCashier cashier;
	public BaseRestaurantCook cook;
	public int cash;
	
	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
		// TODO Auto-generated method stub
		return null;
	}
}
