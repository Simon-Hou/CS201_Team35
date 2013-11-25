package util;

import role.Role;
import interfaces.Person;
import interfaces.PlaceOfWork;

public class RestaurantMapLoc extends Place implements PlaceOfWork{

	public PlaceOfWork restaurant;

	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
		// TODO Auto-generated method stub
		return null;
	}

}
