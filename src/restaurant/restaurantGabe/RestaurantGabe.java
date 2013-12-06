package restaurant.restaurantGabe;

import interfaces.BaseRestaurantCashier;
import interfaces.BaseRestaurantCook;
import interfaces.BaseRestaurantCustomer;
import interfaces.BaseRestaurantHost;
import interfaces.BaseRestaurantWaiter;
import interfaces.MarketEmployee;
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

import person.PersonAgent;
import market.MarketEmployeeRole;
import market.MarketHostRole;
import astar.AStarTraversal;
import cityGui.CityRestaurant;
import cityGui.CityRestaurantCardGabe;
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
		public CookRole cook;
	    public HostRole host;
	    public CashierRole cashier;
	    
//	    public CookRole cook = new CookRole("Cook",0);
//	    public HostRole host = new HostRole("Host");
//	    public CashierRole cashier = new CashierRole("Cashier");

	    private Vector<CustomerRole> customers = new Vector<CustomerRole>();


	    public CityRestaurantGabe cityRestaurantGabe; //reference to main gui
	    
	    public int[] xTables;
	    public int[] yTables;

	    public RestaurantGabe(CityRestaurantGabe cg) {
	    	
	    	System.out.println("Instantiating a Gabe Restaurant");
	    	
	    	
	    	cook = new CookRole("DefaultCook",0);
		    host = new HostRole("DefaultHost");
		    cashier = new CashierRole("DefaultCashier");
	    	
	        this.cityRestaurantGabe = cg;
	        
	        cityRestaurantGabe.stand.setCook(cook);
	        cook.setRevolvingStand(cityRestaurantGabe.stand);
	        
	        //host.startThread();
	        
	        //cashier.startThread();
	        
	        CookGui cGui = new CookGui(cook);
	        cityRestaurantGabe.animationPanel.addGui(cGui);
	        ((CityRestaurantCardGabe) cityRestaurantGabe.animationPanel).setCookNumbers(cGui);
	        cook.setGui(cGui);
	        
	        //cook.startThread();
	        
	   
	    }
	    
	    
	    public HostRole getHost(){
	    	return host;
	    }
	    
	    public CashierRole getCashier(){
	    	return cashier;
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
//	    public void addPerson(String type, String name) {
//
//	    	if (type.equals("Customers")) {
//	    		CustomerRole c = new CustomerRole(name);	
//	    		CustomerGui g = new CustomerGui(c, cityRestaurantGabe);
//
//	    		cityRestaurantGabe.animationPanel.addGui(g);// dw
//	    		c.setHost(host);
//	    		c.setGui(g);
//	    		c.setCashier(cashier);
//	    		customers.add(c);
//	    		c.startThread();
//	    	}
//	    }
	
	
	
	

	
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
		((CityRestaurantCardGabe) cityRestaurantGabe.animationPanel).addGui(cg);
		//((CustomerRole)c).msgAtRestaurant(this);
		
		
		
		
	}
	
	public boolean unStaffed(){
		return !host.isPresent() || !cook.isPresent() || !cashier.isPresent() || waiters.isEmpty();
	}
	
	public Role canIBeHost(Person person){
		if(((HostRole) host).p==null || ((HostRole)host).YouAreDoneWithShift()){
			((HostRole) host).name = person.getName()+"RestaurantHost";
			((HostRole) host).person = (PersonAgent) person;
			//System.out.println(host==null);
			return (Role) host;
		}
		System.err.println("New host wasn't allowded to take over");
		return null;
	}
	
	public Role canIBeCook(Person person){
		if(((CookRole) cook).p==null || ((CookRole)cook).YouAreDoneWithShift()){
			((CookRole) cook).name = person.getName()+"RestaurantCook";
			((CookRole) cook).person = (PersonAgent) person;
			//System.out.println(host==null);
			
			
			//CookGui cGui = new CookGui((CookRole) r);
			
			return (Role) cook;
		}
		System.err.println("New cook wasn't allowded to take over");
		return null;
	}
	
	public void setUpWaiter(WaiterRole r){
		
		r.setRestaurant(this);
		
		WaiterGui wGui = new WaiterGui((WaiterRole) r);
		((WaiterRole) r).setGui(wGui);
		wGui.xTables = ((CityRestaurantGabe) cityRestaurantGabe).x_table;
		wGui.yTables = ((CityRestaurantGabe) cityRestaurantGabe).y_table;
		((HostRole) host).addWaiter((WaiterRole)r);
		
		
		((CityRestaurantCardGabe) cityRestaurantGabe.animationPanel).addGui(wGui);
	}
	
	public Role canIBeCashier(Person person){
		
		if(((CashierRole) cashier).p==null || ((CashierRole)cashier).YouAreDoneWithShift()){
			((CashierRole) cashier).name = person.getName()+"RestaurantCashier";
			((CashierRole) cashier).person = (PersonAgent) person;
			//System.out.println(host==null);
			return (Role) cashier;
		}
		System.err.println("New cashier wasn't allowded to take over");
		return null;
		
	}

	@Override
	public Role canIStartWorking(Person p, JobType type, Role r) {
		if (type == JobType.RestaurantHost){
			return canIBeHost(p);
		}
		
		else if (type == JobType.RestaurantWaiter1){
			
			
			System.out.println("Adding a new waiter "+p.getName());
			//waiters.add((Waiter) r);
			//((MarketEmployeeRole) m).inEmployeeList = true;
			//panel.addEmployee((MarketEmployeeRole) r);
			
			setUpWaiter((WaiterRole) r);
			
			return r;
			
		}
		else if (type == JobType.RestaurantCook){
			return canIBeCook(p);
		}
		
		else if (type == JobType.RestaurantCashier){
			return canIBeCashier(p);
		}
		
		/*else if (type == JobType.RestaurantWaiter2){
			((WaiterRole)r).setRestaurant(this);
			((ProducerConsumerWaiterRole)r).setMonitor(orderMonitor);
			waiterComingToWork((Waiter) r);
			return r;
		}
		
		
		*/
		
		System.out.println("Unrecognized job type: " + type);
		return null;
	}

}
