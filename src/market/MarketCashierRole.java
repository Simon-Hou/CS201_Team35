package market;

import role.Person;

import java.util.List;
import java.util.Map;
import java.util.Timer;

import interfaces.MarketCashier;
import interfaces.MarketCustomer;

public class MarketCashierRole implements MarketCashier{

	List<MyCustomer> customers;
	Map<String, Integer> priceList;
	Map<Person, Integer> debtorsList;

	Timer timer;

	List<BusinessPayment> businessPayments;
	
	//Messages
	public void PleaseServiceCustomer(MarketCustomer c, Map<String, Integer> groceries) {
		customers.add(new MyCustomer(c, groceries));
	}
	
	public void msgFinishedComputing(MyCustomer mc){
	    mc.status = CustomerState.hasTotal;
	}

	public void CustomerPayment(MarketCustomer c, int payment){
	    for (MyCustomer mc: customers){
	    	if (mc.c == c){
	    		mc.payment = payment;
	    	    mc.status = CustomerState.paid;
	    	    break;
	    	}
	    		
	    }		
	}

	public void HereIsBusinessPayment(BusinessPayment payment){
	    businessPayments.add(payment);
	}
	
	//Inner Classes
	public class MyCustomer{
	    MarketCustomer c;
	    Map<String, Integer> order;
	    int total;
	    int payment;
	    CustomerState status;
	    
	    MyCustomer(MarketCustomer c, Map<String, Integer> order){
	    	this.c = c;
	    	this.order = order;
	    	status = CustomerState.needsTotal;
	    }
	}
	enum CustomerState{needsTotal, computingTotal, hasTotal, askedToPay, paid }
}
