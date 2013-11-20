package market;

import role.Person;
import role.Role;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.MarketCashier;
import interfaces.MarketCustomer;

public class MarketCashierRole extends Role implements MarketCashier{

	List<MyCustomer> customers;
	Map<String, Integer> priceList;
	Map<Person, Integer> debtorsList;
	Market market;

	Timer timer;

	List<BusinessPayment> businessPayments;
	
	//Messages
	public void msgPleaseServiceCustomer(MarketCustomer c, Map<String, Integer> groceries) {
		customers.add(new MyCustomer(c, groceries));
	}
	
	public void msgFinishedComputing(MyCustomer mc){
	    mc.status = CustomerState.hasTotal;
	}

	public void msgCustomerPayment(MarketCustomer c, int payment){
	    for (MyCustomer mc: customers){
	    	if (mc.c == c){
	    		mc.payment = payment;
	    	    mc.status = CustomerState.paid;
	    	    break;
	    	}
	    		
	    }		
	}

	public void msgHereIsBusinessPayment(BusinessPayment payment){
	    businessPayments.add(payment);
	}
	
	//Scheduler
	protected boolean pickAndExecuteAnAction() {
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
	    
	    timer.schedule(new TimerTask(){
	    	public void run(){
	    		msgFinishedComputing(cust);
	    	}}, mc.order.size()*1000);
	}

	private void AskCustomerToPay(MyCustomer mc){
	    mc.status = CustomerState.askedToPay;
	    mc.c.msgHereIsTotal(mc.total);
	}

	private void AcceptPayment(MyCustomer mc){
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
	    market.cash+= payment.amountPaid;
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
	
}
