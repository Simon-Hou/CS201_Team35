package market;

import person.PersonAgent;
import role.Role;
import testAgents.testPerson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.MarketCashier;
import interfaces.MarketCustomer;
import interfaces.Person;

public class MarketCashierRole extends Role implements MarketCashier{

	List<MyCustomer> customers = new ArrayList<MyCustomer>();
	Map<String, Integer> priceList = new HashMap<String,Integer>();
	Map<Person, Integer> debtorsList = new HashMap<Person,Integer>();
	Market market;
	Person p;
	public String name;
	
	//SETTERS
	public void setName(String name){
		this.name = name;
	}
	
	//GETTERS
	public String getName(){
		return name;
	}
	
	public boolean canLeave() {
		return true;
	}
	
	//CONSTRUCTOR
	public MarketCashierRole(String name, PersonAgent p){
		this.p = p;
		this.name = name;
	}

	
	public boolean YouAreDoneWithShift(){
		p.msgThisRoleDone(this);
		return true;
	}
	
	Timer timer = new Timer();
	List<Timer> timers = new ArrayList<Timer>();
	
	List<BusinessPayment> businessPayments = new ArrayList<BusinessPayment>();
	
	
	
	//Messages
	public void msgPleaseServiceCustomer(MarketCustomer c, Map<String, Integer> groceries) {
		customers.add(new MyCustomer(c, groceries));
		p.msgStateChanged();
	}
	
	public void msgFinishedComputing(MyCustomer mc){
	    mc.status = CustomerState.hasTotal;
	    p.msgStateChanged();
	}

	public void msgCustomerPayment(MarketCustomer c, int payment){
	    for (MyCustomer mc: customers){
	    	if (mc.c == c){
	    		mc.payment = payment;
	    	    mc.status = CustomerState.paid;
	    	    break;
	    	}
	    		
	    }	
	    p.msgStateChanged();
	}

	public void msgHereIsBusinessPayment(int pay){
		BusinessPayment payment = new BusinessPayment(pay);
	    businessPayments.add(payment);
	    p.msgStateChanged();
	}
	
	//-----------------------Scheduler---------------------------
	public boolean pickAndExecuteAnAction() {
		for (MyCustomer mc: customers){
			if (mc.status == CustomerState.needsTotal){
				ComputeTotal(mc);
				return true;
			}
		}
		for (MyCustomer mc: customers){
			if (mc.status==CustomerState.hasTotal){
				AskCustomerToPay(mc);
				return true;
			}
		}
		for (MyCustomer mc: customers){
			if (mc.status == CustomerState.paid){
				AcceptPayment(mc);
				return true;
			}
		}
		if (!businessPayments.isEmpty()){
		    ProcessBusinessPayment(businessPayments.get(0));
		    return true;
		}
		return false;
	}
	
	
	//Actions
	private void ComputeTotal(MyCustomer mc){
		//Do("computing total");
	    int total=0;
	    if (debtorsList.containsKey(mc.c.getPerson())){
	    	total+= debtorsList.get(mc.c.getPerson());
            debtorsList.remove(mc.c.getPerson());
	    }
	    
	    for (String item: mc.order.keySet()){
	        total+= mc.order.get(item) * priceList.get(item);
	    }
	    
	    mc.total = total;
	    
	    final MyCustomer cust = mc;
	    
	    //final Timer t = new Timer();
	    //timers.add(t);
	    mc.status = CustomerState.computingTotal;
	    timer.schedule(new TimerTask(){
	    	public void run(){
	    		msgFinishedComputing(cust);
	    		//timer.cancel();
	    	}}, mc.order.size()*1000+1000);
	}

	private void AskCustomerToPay(MyCustomer mc){
	    mc.status = CustomerState.askedToPay;
	    mc.c.msgHereIsTotal(mc.total);
	}

	private void AcceptPayment(MyCustomer mc){
		Do("Accepting Payment");
	    int change = mc.payment-mc.total;
	    if (change>=0){
	        mc.c.msgHereIsYourChange(new Receipt(mc.order, mc.total, mc.payment, this), change);
	        customers.remove(mc);

	    }
	    else{
	        mc.c.msgYouOweMoney(new Receipt(mc.order, mc.total, mc.payment, this), -1*change);
	        debtorsList.put(mc.c.getPerson(), -1*change);
	        customers.remove(mc);
	    }
	}

	private void ProcessBusinessPayment(BusinessPayment payment){
	    market.cash+= payment.amount;
	    businessPayments.remove(payment);
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
	
	private class BusinessPayment{
		int amount;
		
		BusinessPayment(int pay){
			amount = pay;
		}
		
	}


	
}
