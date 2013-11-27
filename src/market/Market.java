package market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cityGui.CityMarket;
import market.gui.MarketPanel;
import role.Role;
import util.JobType;
import interfaces.MarketCashier;
import interfaces.MarketCustomer;
import interfaces.MarketDeliveryMan;
import interfaces.MarketEmployee;
import interfaces.MarketHost;
import interfaces.Person;
import interfaces.PlaceOfWork;
import person.*;

public class Market implements PlaceOfWork{
	
	int cash = 0;

	public MarketHost host;
	public MarketCashier cashier;
	public List<MarketEmployee> employees = new ArrayList<MarketEmployee>();
	public List<MarketDeliveryMan> deliveryMen = new ArrayList<MarketDeliveryMan>();
	int money;
	public CityMarket gui;
	
	public MarketPanel panel;
	
	public Map<String, Integer> inventory = new HashMap<String, Integer>();
	
	
	//CONSTRUCTOR
	public Market(){
		this.host = new MarketHostRole("DefaultUnmannedHost",null,this);
		this.cashier = new MarketCashierRole("DefaultUnmannedCashier",null, this);
		
		host.setMarket(this);
		cashier.setMarket(this);
		
		inventory.put("Steak", 10);
		inventory.put("Chicken", 10);
		inventory.put("Pizza", 10);
		inventory.put("Salad", 10);
		inventory.put("Car", 5);
	}
	
	
	public boolean CanIStartWorking(MarketEmployee m){
		System.out.println("Deciding whether to let employee work");
		if(host.NewEmployee(m)){
			employees.add(m);
			return true;
		}
		System.err.println("Market Employee wasn't allowed to work");
		return false;
		
	}
	
	public MarketHost CanIBeHost(Person person){
		if(((MarketHostRole) host).p==null || host.YouAreDoneWithShift()){
			((MarketHostRole) host).name = person.getName()+"MarketHost";
			((MarketHostRole) host).p = person;
			return host;
		}
		System.err.println("New host wasn't allowded to take over");
		return null;
	}
	
	public MarketCashier CanIBeCashier(Person person){
		if(((MarketCashierRole) cashier).p==null || cashier.YouAreDoneWithShift()){
			((MarketCashierRole) cashier).name = person.getName()+ "MarketCashier";
			((MarketCashierRole) cashier).p = person;
			return cashier;
		}
		System.err.println("New cashier wasn't allowed to take over");
		return null;
	}


	@Override
	public Role canIStartWorking(Person p,JobType jobType,Role m) {
		// TODO Auto-generated method stub
		if(jobType == JobType.MarketEmployee){

			if(host.NewEmployee((MarketEmployee) m)){
				employees.add((MarketEmployee) m);
				panel.addEmployee((MarketEmployeeRole) m);
				return m;
			}
			System.err.println("Market Employee wasn't allowed to work");
		}
		
		else if(jobType == JobType.MarketHost){
			return (Role) CanIBeHost(p);
		}
		
		else if(jobType == JobType.MarketCashier){
			return (Role) CanIBeCashier(p);
		}
		
		else if(jobType == jobType.MarketDeliveryMan){
			
				deliveryMen.add((MarketDeliveryMan) m);
				for (MarketEmployee e : employees){
					e.addDeliveryMan((MarketDeliveryMan)m);
				}
				return m;
			
		}
		
		
		System.err.println("A non-markter is trying to work at the market");
		return null;
	}
	
	public void newCustomer(MarketCustomer cust){
		panel.addCustomer((MarketCustomerRole) cust);
	}
	
	public void removeCustomer(MarketCustomer cust){
		//send the cust to the panel to remove it!
		if(panel!=null){
			panel.removeCustomer();
		}
	}
	
	public void setMarketPanel(MarketPanel p){
		panel = p;
	}
	
	public void updateInventory(){
		if(panel!=null){
			panel.updateInventory();
		}
	}
	
	public void DefaultName(Role r){
		if(r instanceof MarketHost){
			((MarketHostRole) r).name = "DefaultUnmannedHost";
		}
		if(r instanceof MarketCashier){
			((MarketCashierRole) r).name = "DefaultUnmannedCashier";
		}
	}
	
}
