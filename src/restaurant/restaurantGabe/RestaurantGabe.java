package restaurant.restaurantGabe;

import interfaces.BaseRestaurantCashier;
import interfaces.BaseRestaurantCook;
import interfaces.BaseRestaurantCustomer;
import interfaces.BaseRestaurantHost;
import interfaces.BaseRestaurantWaiter;
import interfaces.Person;
import restaurant.restaurantGabe.interfaces.Cashier;
import restaurant.restaurantGabe.interfaces.Cook;
import restaurant.restaurantGabe.interfaces.Customer;
import restaurant.restaurantGabe.interfaces.Host;
import restaurant.restaurantGabe.interfaces.Waiter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import astar.AStarTraversal;
import cityGui.CityRestaurant;
import cityGui.CityRestaurantGabe;
import cityGui.CityRestaurantLinda;
//import cityGui.MarketRole;
import restaurant.ProducerConsumerMonitor;
import restaurant.Restaurant;
import restaurant.restaurantGabe.gui.ListPanel;
import restaurant.restaurantGabe.gui.RestaurantGui;
import restaurant.restaurantGabe.gui.WaiterPanel;
import restaurant.restaurantGabe.gui.CookGui;
import restaurant.restaurantGabe.gui.CustomerGui;
//import restaurant.restaurantGabe.gui.Gui;
import restaurant.restaurantGabe.gui.WaiterGui;
import role.Role;
import util.JobType;

public class RestaurantGabe extends Restaurant{
		
	
	
	
	
	//Host, cook, waiters, customers, markets, cashier that must be hacked in
		public CookRole cook = new CookRole("Cook",0);
	    public HostRole host2 = new HostRole("Host");
	    public CashierRole cashier = new CashierRole("Cashier");
	    
//	    public CookRole cook = new CookRole("Cook",0);
//	    public HostRole host = new HostRole("Host");
//	    public CashierRole cashier = new CashierRole("Cashier");

	    private Vector<CustomerRole> customers = new Vector<CustomerRole>();


	    public CityRestaurantGabe cityRestaurantGabe; //reference to main gui
	    
	    public int[] xTables;
	    public int[] yTables;

	    public RestaurantGabe(CityRestaurantGabe cg) {
	    	
	    	System.out.println("Instantiating a Gabe Restaurant");
	    	
	        this.cityRestaurantGabe = cg;
	        
	        cityRestaurantGabe.stand.setCook(cook);
	        cook.setRevolvingStand(cityRestaurantGabe.stand);
	        
	        //host.startThread();
	        
	        //cashier.startThread();
	        
	        CookGui cGui = new CookGui(cook);
	        cityRestaurantGabe.animationPanel.addGui(cGui);
	        cook.setGui(cGui);
	        
	        //cook.startThread();
	        
	   
	    }
	    
	    public void setCustHungry(String name){
	    	for (int i = 0; i < customers.size(); i++) {
	            CustomerRole temp = customers.get(i);
	            if (temp.getName() == name)
	                cityRestaurantGabe.setCustomerHungry(temp);
	        }
	    	
	    	
	    }

	    /**
	     * Adds a customer or waiter to the appropriate list
	     *
	     * @param type indicates whether the person is a customer or waiter (later)
	     * @param name name of person
	     */
	    public void addPerson(String type, String name) {

	    	if (type.equals("Customers")) {
	    		CustomerRole c = new CustomerRole(name);	
	    		CustomerGui g = new CustomerGui(c, cityRestaurantGabe);

	    		cityRestaurantGabe.animationPanel.addGui(g);// dw
	    		c.setHost(host);
	    		c.setGui(g);
	    		c.setCashier(cashier);
	    		customers.add(c);
	    		c.startThread();
	    	}
	    }
	
	
	
	

	
	//Override this to get your restaurant started
	@Override
	public void customerEntering(BaseRestaurantCustomer c){
		
//		cityRestaurantGabe.animationPanel.addGui(g);// dw
//		((Customer Role)c).setHost(host);
//		c.setGui(g);
//		c.setCashier(cashier);
//		customers.add(c);
		
		
		
		
		CustomerGui cg = new CustomerGui((CustomerRole)c,cityRestaurantGabe);
		((CustomerRole)c).setGui(cg);
		cityRestaurantGabe.animationPanel.addGui(cg);
		((CustomerRole)c).msgAtRestaurant(this);
		
		
		
		
	}
	
	public boolean unStaffed(){
		return !host.isPresent() || !cook.isPresent() || !cashier.isPresent() || waiters.isEmpty();
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

}
