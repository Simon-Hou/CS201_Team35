package market;

import java.util.ArrayList;
import java.util.List;

import cityGui.CityMarket;
import market.gui.MarketPanel;
import role.Role;
import util.JobType;
import interfaces.MarketCashier;
import interfaces.MarketCustomer;
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
	int money;
	public CityMarket gui;
	
	MarketPanel panel;
	
	//CONSTRUCTOR
	public Market(){
		this.host = new MarketHostRole(null,null);
		this.cashier = new MarketCashierRole(null,null);
	}
	
	
	public boolean CanIStartWorking(MarketEmployee m){
		employees.add(m);
		if(host.NewEmployee(m)){
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
			employees.add((MarketEmployee) m);
			if(host.NewEmployee((MarketEmployee) m)){
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
			System.err.println("Delivery man not dealt with yet");
			return null;
		}
		
		
		System.err.println("A non-markter is trying to work at the market");
		return null;
	}
	
	public void removeCustomer(MarketCustomer cust){
		//send the cust to the panel to remove it!
		panel.removeCustomer();
	}
	
	public void setMarketPanel(MarketPanel p){
		panel = p;
	}
	
}
