package restaurant;

import java.util.Vector;

import market.MarketHostRole;

import restaurant.restaurantLinda.CashierRole;
import restaurant.restaurantLinda.CookRole;
import restaurant.restaurantLinda.CustomerRole;
import restaurant.restaurantLinda.HostRole;
import restaurant.restaurantLinda.RestaurantOrder;
import restaurant.restaurantLinda.WaiterRole;
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
	//private Vector<WaiterRole> waiters = new Vector<WaiterRole>();
	
	public int cash;	
	
	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
		if (type == JobType.RestaurantHost){
			
		}
		else if (type == JobType.RestaurantHost){
			
		}
		else if (type == JobType.RestaurantWaiter){
			
		}
		else if (type == JobType.RestaurantCook){
			
		}
		else if (type == JobType.RestaurantCashier){
			
		}
		return null;
	}
	
	public Host CanIBeHost(Person person){
		if(((HostRole) host).getPerson()==null){
			((HostRole) host).setName(person.getName()+"RestaurantHost");
			((HostRole) host).setPerson(person);
			return host;
		}
		System.err.println("New host wasn't allowded to take over");
		return null;
	}
	
	public void waiterComingToWork(Waiter m){
		//waiters.add(m);
		host.addWaiter(m);
	}
	
	public BaseRestaurantCook cookComingToWork(Person p){
		((CookRole) cook).setPerson(p);
		((CookRole)cook).setName(p.getName() + "Cook");
		
		return cook;
	}
	
}
