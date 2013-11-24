package market;

import java.util.ArrayList;
import java.util.List;

import role.Role;
import interfaces.MarketCashier;
import interfaces.MarketEmployee;
import interfaces.MarketHost;
import interfaces.Person;
import interfaces.PlaceOfWork;

public class Market implements PlaceOfWork{
	
	int cash;

	public MarketHost host;
	public MarketCashier cashier;
	public List<MarketEmployee> employees = new ArrayList<MarketEmployee>();
	int money;
	
	//CONSTRUCTOR
	public Market(){
		this.host = new MarketHostRole();
		this.cashier = new MarketCashierRole(null);
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
		if(host.YouAreDoneWithShift()){
			return host;
		}
		System.err.println("New host wasn't allowded to take over");
		return null;
	}
	
	public MarketCashier CanIBeCashier(Person person){
		if(cashier.YouAreDoneWithShift()){
			return cashier;
		}
		System.err.println("New cashier wasn't allowed to take over");
		return null;
	}


	@Override
	public Role canIStartWorking(Role r) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
