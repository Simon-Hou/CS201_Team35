package restaurant.restaurantYocca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import astar.AStarTraversal;
import market.MarketHostRole;
import restaurant.ProducerConsumerMonitor;
import restaurant.Restaurant;
import restaurant.restaurantYocca.CashierRole;
import restaurant.restaurantYocca.CookRole;
import restaurant.restaurantYocca.CustomerRole;
import restaurant.restaurantYocca.HostRole;
import restaurant.restaurantYocca.WaiterRole;
import restaurant.restaurantYocca.gui.CookGui;
import restaurant.restaurantYocca.gui.CustomerGui;
import restaurant.restaurantYocca.RestaurantOrder;
import restaurant.restaurantYocca.ProducerConsumerWaiterRole;
import person.PersonAgent;
import public_Gui.Gui;
import restaurant.restaurantYocca.gui.WaiterGui;
import role.Role;
import util.JobType;
import cityGui.CityRestaurant;
import cityGui.CityRestaurantCardGabe;
import cityGui.CityRestaurantYocca;
import cityGui.CityRestaurantYoccaCard;
import interfaces.BaseRestaurantCashier;
import interfaces.BaseRestaurantCook;
import interfaces.BaseRestaurantCustomer;
import interfaces.BaseRestaurantWaiter;
import interfaces.MarketEmployee;
import interfaces.MarketHost;
import interfaces.Person;
import interfaces.PlaceOfWork;
import interfaces.restaurantYocca.Cook;
import interfaces.restaurantYocca.Customer;
import interfaces.restaurantYocca.Host;
import interfaces.restaurantYocca.Waiter;

public class RestaurantYocca extends Restaurant implements PlaceOfWork {
	
	ProducerConsumerMonitor<RestaurantOrder> orderMonitor = new ProducerConsumerMonitor<RestaurantOrder>();
	
	public CityRestaurantYocca cityRestaurant;
	public HostRole host;
	public CashierRole cashier;
	public CookRole cook;
	public List<BaseRestaurantWaiter> waiters;
	public List<BaseRestaurantCustomer> customers;
	
	public RestaurantYocca(CityRestaurantYocca cr){
		this.cityRestaurant = cr;
		
		cash = 5000;
		
		host = new HostRole("RestaurantHost", this); 
		cashier =  new CashierRole("RestaurantCashier", this);
		cook = new CookRole("Cook", orderMonitor, this);
		waiters = Collections.synchronizedList(new ArrayList<BaseRestaurantWaiter>());
		customers = Collections.synchronizedList(new ArrayList<BaseRestaurantCustomer>());
		
		super.cityRestaurant = this.cityRestaurant;
		super.host = this.host;
		super.cashier = this.cashier;
		super.cook = this.cook;
		super.waiters = this.waiters;
		
		CookGui cg = new CookGui(cook);
		cook.setGui(cg);
		cityRestaurant.animationPanel.addGui(cg);
	}
	
	//dummy constructor for agent-only unit tests
	public RestaurantYocca(){
		
	}
	
	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
//		if (type == JobType.RestaurantHost){
//			((HostRole)host).changeShifts(p);
//			return (Role)host;
//		}
		if (type == JobType.RestaurantHost){
			if(((HostRole) host).p==null || ((HostRole)host).YouAreDoneWithShift()){
				((HostRole) host).name = p.getName()+"RestaurantHost";
				((HostRole) host).p = (PersonAgent) p;
				System.out.println("Changing HOST");
				//System.out.println(host==null);
				return (Role) host;
			}
			System.err.println("New host wasn't allowded to take over");
			return null;
		}
		else if (type == JobType.RestaurantYoccaWaiter1){
			((WaiterRole)r).setRestaurant(this);
			((ProducerConsumerWaiterRole)r).setMonitor(orderMonitor);
			waiterComingToWork((Waiter) r);
			return r;
		}
		else if (type == JobType.RestaurantYoccaWaiter2){
			((WaiterRole)r).setRestaurant(this);
			waiterComingToWork((Waiter) r);
			return r;
		}
//		else if (type == JobType.RestaurantCook){
//			((CookRole)cook).changeShifts(p);
//			return (Role)cook;
//		}
		else if (type == JobType.RestaurantCook){
			if(((CookRole) cook).p==null || ((CookRole)cook).YouAreDoneWithShift()){
				((CookRole) cook).name = p.getName()+"RestaurantCook";
				((CookRole) cook).p = (PersonAgent) p;
				System.out.println("Changing COOK");
				//System.out.println(host==null);			
				//CookGui cGui = new CookGui((CookRole) r);
				return (Role) cook;
			}
			System.err.println("New cook wasn't allowded to take over");
			return null;
		}
//		else if (type == JobType.RestaurantCashier){
//			((CashierRole)cashier).changeShifts(p);
//			return (Role) cashier;
//		}
		else if (type == JobType.RestaurantCashier){
			if(((CashierRole) cashier).p==null || ((CashierRole)cashier).YouAreDoneWithShift()){
				((CashierRole) cashier).name = p.getName()+"RestaurantCashier";
				((CashierRole) cashier).p = (PersonAgent) p;
				System.out.println("Changing CASHIER");
				//System.out.println(host==null);
				return (Role) cashier;
			}
			System.err.println("New cashier wasn't allowded to take over");
			return null;
		}
		
		System.out.println("Unrecognized job type: " + type);
		return null;
	}
		
	public void waiterComingToWork(Waiter r){
//		WaiterGui wg = new WaiterGui((WaiterRole)r, waiters.size(), new AStarTraversal(cityRestaurant.grid));
		WaiterGui wg = new WaiterGui((WaiterRole)r,this);
		waiters.add((WaiterRole)r);
		((WaiterRole)r).setGui(wg);
//		wg.setTables(cityRestaurant.getTables());
//		wg.setPlates(cityRestaurant.animationPanel.platedFoods);
		((HostRole)host).addWaiter((WaiterRole)r);
		cityRestaurant.animationPanel.addGui(wg);
	}
	
	public BaseRestaurantCook cookComingToWork(Person p){
		((CookRole)cook).changeShifts(p);
		return cook;
	}
	
//	public void waiterLeaving(Waiter w){
//		waiters.remove(w);
//		leaveRestaurant(((WaiterRole)w).getGui());
//	}
//	
//	public void leaveRestaurant(Gui gui){
//		cityRestaurant.animationPanel.removeGui(gui);
//	}
	
	public void leaveRestaurant(CustomerGui cg){
		cityRestaurant.animationPanel.removeGui(cg);
		customers.remove(cg.agent);
	}
	
	public void leaveRestaurant(WaiterRole wr){
		System.out.println("Leaving THE RESTAURANT!!!");
		cityRestaurant.animationPanel.removeGui(wr.waiterGui);
		waiters.remove(wr);
	}
	
//	public boolean leaveWaiterList(WaiterRole r){
//		return c
//		
//	}
//	
	public void customerEntering(BaseRestaurantCustomer c){
		//System.out.println("Customer is entering!");
		CustomerGui cg = new CustomerGui((CustomerRole)c,this);
		((CustomerRole)c).setGui(cg);
		customers.add(c);
		((CityRestaurantYoccaCard) cityRestaurant.animationPanel).addGui(cg);
	}
//	
//	public boolean unStaffed(){
//		return !host.isPresent() || !cook.isPresent() || !cashier.isPresent() || waiters.isEmpty();
//	}

}
