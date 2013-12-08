package restaurant.restaurantLinda;

import interfaces.BaseRestaurantCashier;
import interfaces.BaseRestaurantCook;
import interfaces.BaseRestaurantCustomer;
import interfaces.BaseRestaurantHost;
import interfaces.BaseRestaurantWaiter;
import interfaces.Person;
import interfaces.restaurantLinda.Cashier;
import interfaces.restaurantLinda.Cook;
import interfaces.restaurantLinda.Customer;
import interfaces.restaurantLinda.Host;
import interfaces.restaurantLinda.Waiter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import astar.AStarTraversal;
import cityGui.CityRestaurant;
import cityGui.CityRestaurantLinda;
import restaurant.ProducerConsumerMonitor;
import restaurant.Restaurant;
import restaurant.restaurantLinda.gui.CookGui;
import restaurant.restaurantLinda.gui.CustomerGui;
import restaurant.restaurantLinda.gui.Gui;
import restaurant.restaurantLinda.gui.WaiterGui;
import role.Role;
import util.JobType;

public class RestaurantLinda extends Restaurant{
		
	ProducerConsumerMonitor<RestaurantOrder> orderMonitor = new ProducerConsumerMonitor<RestaurantOrder>();
	
	public CityRestaurantLinda cityRestaurant;
	public HostRole host;
	public CashierRole cashier;
	public CookRole cook;
	public List<BaseRestaurantWaiter> waiters;
	
	public RestaurantLinda(CityRestaurantLinda cr){
		this.cityRestaurant = cr;
		
		cash = 5000;
		
		host = new HostRole("RestaurantHost"); 
		cashier =  new CashierRole("RestaurantCashier", this);
		cook = new CookRole("Cook", orderMonitor, this);
		waiters = Collections.synchronizedList(new ArrayList<BaseRestaurantWaiter>());
		
		super.cityRestaurant = this.cityRestaurant;
		super.host = this.host;
		super.cashier = this.cashier;
		super.cook = this.cook;
		super.waiters = this.waiters;
		
		CookGui cg = new CookGui(cook);
		//cg.setPlates(cityRestaurant.animationPanel.platedFoods);
		cook.setGui(cg);
		cityRestaurant.animationPanel.addGui(cg);
	}
	
	//dummy constructor for agent-only unit tests
	public RestaurantLinda(){
		
	}
	
	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
		if (type == JobType.RestaurantHost){
			host.changeShifts(p);
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
			cook.changeShifts(p);
			return (Role)cook;
		}
		else if (type == JobType.RestaurantCashier){
			cashier.changeShifts(p);
			return (Role) cashier;
		}
		
		System.out.println("Unrecognized job type: " + type);
		return null;
	}
		
	public void waiterComingToWork(Waiter r){
		WaiterGui wg = new WaiterGui((WaiterRole)r, waiters.size(), new AStarTraversal(cityRestaurant.grid));
		waiters.add((WaiterRole)r);
		((WaiterRole)r).setGui(wg);
		wg.setTables(((CityRestaurantLinda)cityRestaurant).getTables());
		//wg.setPlates(cityRestaurant.animationPanel.platedFoods);
		((HostRole)host).addWaiter(r);
		cityRestaurant.animationPanel.addGui(wg);
	}
	
	public BaseRestaurantCook cookComingToWork(Person p){
		cook.changeShifts(p);
		return cook;
	}
	
	public void waiterLeaving(Waiter w){
		waiters.remove(w);
		leaveRestaurant(((WaiterRole)w).getGui());
	}
	
	public void leaveRestaurant(Gui gui){
		cityRestaurant.animationPanel.removeGui(gui);
	}
	
	//Override this to get your restaurant started
	public void customerEntering(BaseRestaurantCustomer c){
		CustomerGui cg = new CustomerGui((CustomerRole)c, new AStarTraversal(cityRestaurant.grid));
		((CustomerRole)c).setGui(cg);
		cityRestaurant.animationPanel.addGui(cg);
		((CustomerRole)c).msgAtRestaurant(this);
	}
	
	public boolean unStaffed(){
		return !host.isPresent() || !cook.isPresent() || !cashier.isPresent() || waiters.isEmpty();
	}

}
