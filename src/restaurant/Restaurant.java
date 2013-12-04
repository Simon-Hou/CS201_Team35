package restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import astar.AStarTraversal;

import market.MarketHostRole;

import restaurant.restaurantLinda.CashierRole;
import restaurant.restaurantLinda.CookRole;
import restaurant.restaurantLinda.CustomerRole;
import restaurant.restaurantLinda.HostRole;
import restaurant.restaurantLinda.OriginalWaiterRole;
import restaurant.restaurantLinda.ProducerConsumerWaiterRole;
import restaurant.restaurantLinda.RestaurantOrder;
import restaurant.restaurantLinda.WaiterRole;
import restaurant.restaurantLinda.gui.CookGui;
import restaurant.restaurantLinda.gui.CustomerGui;
import restaurant.restaurantLinda.gui.Gui;
import restaurant.restaurantLinda.gui.WaiterGui;
import role.Role;
import util.JobType;
import cityGui.CityRestaurant;
import interfaces.BaseRestaurantCashier;
import interfaces.BaseRestaurantCook;
import interfaces.BaseRestaurantCustomer;
import interfaces.BaseRestaurantHost;
import interfaces.BaseRestaurantWaiter;
import interfaces.MarketEmployee;
import interfaces.MarketHost;
import interfaces.Person;
import interfaces.PlaceOfWork;
import interfaces.restaurantLinda.Cook;
import interfaces.restaurantLinda.Customer;
import interfaces.restaurantLinda.Host;
import interfaces.restaurantLinda.Waiter;

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
