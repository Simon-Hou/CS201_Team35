package restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import market.MarketHostRole;

import restaurant.restaurantLinda.CashierRole;
import restaurant.restaurantLinda.CookRole;
import restaurant.restaurantLinda.CustomerRole;
import restaurant.restaurantLinda.HostRole;
import restaurant.restaurantLinda.RestaurantOrder;
import restaurant.restaurantLinda.WaiterRole;
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
	}
	
	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
		if (type == JobType.RestaurantHost){
			if (CanIBeHost(p)!=null){
				return (Role) host;
			}
		}
		else if (type == JobType.RestaurantWaiter){
			if (r instanceof WaiterRole){
				return r;
			}
		}
		else if (type == JobType.RestaurantCook){
			
		}
		else if (type == JobType.RestaurantCashier){
			
		}
		return null;
	}
	
	public Host CanIBeHost(Person person){
		((HostRole)host).changeShifts(person);
		return host;
	}
	
	public void waiterComingToWork(Waiter m){
		WaiterGui g = ((WaiterRole)m).getGui();
		host.addWaiter(m);
		cityRestaurant.animationPanel.addGui(g);
	}
	
	public BaseRestaurantCook cookComingToWork(Person p){
		((CookRole)cook).changeShifts(p);
		return cook;
	}
	
	public void leaveRestaurant(Gui gui){
		cityRestaurant.animationPanel.removeGui(gui);
	}
	
}
