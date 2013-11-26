package util;

import restaurant.Restaurant;
import role.Role;
import interfaces.Person;
import interfaces.PlaceOfWork;

public class RestaurantMapLoc extends Place implements PlaceOfWork{

	public Restaurant restaurant;

	public RestaurantMapLoc(Restaurant restaurant) {
		// TODO Auto-generated constructor stub
		this.restaurant = restaurant;
	}

	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
		// TODO Auto-generated method stub
		return null;
	}

}
