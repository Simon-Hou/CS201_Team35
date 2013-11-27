package restaurant;

import java.util.ArrayList;
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
import interfaces.MarketEmployee;
import interfaces.MarketHost;
import interfaces.Person;
import interfaces.PlaceOfWork;
import interfaces.restaurantLinda.Cook;
import interfaces.restaurantLinda.Customer;
import interfaces.restaurantLinda.Host;
import interfaces.restaurantLinda.Waiter;

public class Restaurant implements PlaceOfWork{
	
	public CityRestaurant cityRestaurant;	
	
	ProducerConsumerMonitor<RestaurantOrder> orderMonitor = new ProducerConsumerMonitor<RestaurantOrder>();
	
	public Host host = new HostRole("RestaurantHost"); 
	public BaseRestaurantCashier cashier =  new CashierRole("RestaurantCashier", this);
	public BaseRestaurantCook cook = new CookRole("Cook", orderMonitor, this);
	//private Vector<CustomerRole> customers = new Vector<CustomerRole>();
	private List<WaiterRole> waiters = new ArrayList<WaiterRole>();
	
	public int cash;	
	
	public Restaurant(CityRestaurant cr){
		this.cityRestaurant = cr;
		CookGui cg = new CookGui((CookRole)cook);
		((CookRole)cook).setGui(cg);
		cityRestaurant.animationPanel.addGui(cg);
	}
	
	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
		if (type == JobType.RestaurantHost){
			((HostRole)host).changeShifts(p);
			return (Role)host;
		}
		else if (type == JobType.RestaurantWaiter1){
			((WaiterRole)r).setRestaurant(this);
			waiterComingToWork((Waiter) r);
			return r;
		}
		else if (type == JobType.RestaurantWaiter2){
			((WaiterRole)r).setRestaurant(this);
			((ProducerConsumerWaiterRole)r).setMonitor(orderMonitor);
			waiterComingToWork((Waiter) r);
			return r;
		}
		else if (type == JobType.RestaurantCook){
			((CookRole)cook).changeShifts(p);
			return (Role)cook;
		}
		else if (type == JobType.RestaurantCashier){
			((CashierRole)cashier).changeShifts(p);
			return (Role) cashier;
		}
		
		System.out.println("Unrecognized job type: " + type);
		return null;
	}
		
	public void waiterComingToWork(Waiter r){
		WaiterGui wg = new WaiterGui((WaiterRole)r, waiters.size(), new AStarTraversal(cityRestaurant.grid));
		((WaiterRole)r).setGui(wg);
		waiters.add((WaiterRole)r);
		host.addWaiter(r);
		cityRestaurant.animationPanel.addGui(wg);
	}
	
	public BaseRestaurantCook cookComingToWork(Person p){
		((CookRole)cook).changeShifts(p);
		return cook;
	}
	
	public void leaveRestaurant(Gui gui){
		cityRestaurant.animationPanel.removeGui(gui);
	}
	
	public void customerEntering(Customer c){
		CustomerGui cg = new CustomerGui((CustomerRole)c);
		((CustomerRole)c).setGui(cg);
		cityRestaurant.animationPanel.addGui(cg);
		
	}
	
}
