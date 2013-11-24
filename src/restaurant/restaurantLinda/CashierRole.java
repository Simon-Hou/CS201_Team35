package restaurant.restaurantLinda;

import UnitTests.mock.EventLog;
import UnitTests.mock.LoggedEvent;
import agent.Agent;

import interfaces.restaurantLinda.Cashier;
import interfaces.restaurantLinda.Customer;
import interfaces.restaurantLinda.Market;
import interfaces.restaurantLinda.Waiter;

import java.util.*;

import role.Role;



public class CashierRole extends Role implements Cashier{
	private String name;
	private Map<String,Integer> priceList = new HashMap<String,Integer>();
	private List<Bill> bills = Collections.synchronizedList(new ArrayList<Bill>());
	private List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	private List<MyBill> myBills = Collections.synchronizedList(new ArrayList<MyBill>());
	private Map<Customer, Integer> debtors = new HashMap<Customer, Integer>();
	private Timer timer = new Timer();
	int cash=0;
	
	public CashierRole(String name) {
		super();	
		priceList.put("Steak", 1599);
		priceList.put("Chicken", 1099);
		priceList.put("Salad", 599);
		priceList.put("Pizza", 899);
		this.name = name;
	}
	
	//messages
	public void msgPleaseComputeBill(Waiter w, String choice, Customer cust){
		bills.add(new Bill(w,choice,cust));
		stateChanged();
	}
	
	public void msgHereIsPayment(Customer cust, Check bill, int payment){
		log.add(new LoggedEvent("Received payment of $" + payment/100 + "." + payment%100 + " from customer " + cust.getName() + " for the bill " + bill));
		customers.add(new MyCustomer(cust, bill, payment));
		stateChanged();
	}
	
	public void msgTimerDone(Bill b){
		log.add(new LoggedEvent("Finished computing bill for waiter " + b.w.getName() + " for customer " + b.cust.getName() + ". For the order " + b.choice + ", the total is " + b.total));
		Do("Finished computing");
		b.status=BillState.computed;
		stateChanged();
	}
	
	public void msgPleasePay(Market m, Map<String,Integer> order, int total){
		log.add(new LoggedEvent("Received request for payment from " + m + " to pay " + total + " for food shipment " + order));
		Do("Received request for payment from " + m + " to pay $" + total/100 + "." + total%100 + " for food shipment " + order);
		myBills.add(new MyBill(m, order, total));
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		synchronized(myBills){
			for (MyBill b: myBills){
				if (b.status==MyBillState.newlyReceived){
					PayBill(b);
					return true;
				}
			}
		}
		synchronized(myBills){
			for (MyBill b: myBills){
				if (b.status==MyBillState.partiallyPaid && cash>=b.owed){
					PayBill(b);
					return true;
				}
			}
		}
		synchronized(bills){
			for (Bill b: bills){
				if (b.status==BillState.computed){
					NotifyWaiter(b);
					return true;
				}
			}
		}
		if (!customers.isEmpty()){
			AcceptPayment(customers.get(0));
			return true;
		}
		synchronized(bills){
			for (Bill b: bills){
				if (b.status==BillState.pending){
					ComputeBill(b);
					return true;
				}
			}
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	//actions
	private void ComputeBill(Bill b){
		//log.add(new LoggedEvent("Computing bill from waiter " + b.w.getName() + " for customer " + b.cust.getName() + " who ordered " + b.choice));
		Do("Computing bill from waiter " + b.w.getName() + " for customer " + b.cust.getName() + " who ordered " + b.choice);
		b.status=BillState.computing;
		int debt = 0;
		if (debtors.containsKey(b.cust)){
			debt=debtors.get(b.cust);
			debtors.remove(b.cust);
		}
		
		b.total = priceList.get(b.choice) + debt;
		
		final Bill bill = b;
		timer.schedule(new TimerTask(){
			public void run(){
				msgTimerDone(bill);
			}
		}, 3000);
	}
	
	private void NotifyWaiter(Bill b){
		log.add(new LoggedEvent("Notifying waiter of finished bill for customer " + b.cust.getName() + " who ordered " + b.choice + ". Total is $" + b.total/100 + "." + b.total%100));
		b.w.msgHereIsBill(b.choice, b.cust, b.total);
		b.status=BillState.done;
	}
	
	private void AcceptPayment(MyCustomer mc){
		log.add(new LoggedEvent("Processing payment from customer"));
		Do("Payment received: $" + mc.payment/100 + "." + mc.payment%100);
		cash+=mc.payment;
		int debt = mc.bill.getTotal()-mc.payment;
		if (mc.bill.getTotal()>mc.payment){
			Do(mc.c.getName()+" owes $"+debt/100 + "." + debt%100);
			
			if (debtors.containsKey(mc.c))
				debtors.put(mc.c, debtors.get(mc.c)+debt);
			else
				debtors.put(mc.c, debt);
		}
		
		mc.c.msgPaymentReceived(debt);
		customers.remove(mc);
	}
	
	private void PayBill(MyBill b){
	
		int payment;
		
		if (cash >= b.owed){
			cash-= b.owed;
			payment = b.owed;
			b.status = MyBillState.fullyPaid;
		}
		else{
			payment = cash;
			cash=0;
			b.status = MyBillState.partiallyPaid;
		}
		
		b.owed-=payment;
		
		log.add(new LoggedEvent("Paying " + b.m +  " " + payment + " for food shipment. Still owe " + b.owed + ". " + cash + " left in cash."));
		Do("Paying " + b.m +  " $" + payment/100 + "." + payment%100 + " for food shipment. Still owe $" + b.owed/100 + "." + b.owed%100 + ". $" + cash/100 + "." + cash%100 + " left.");
		
		b.m.msgHereIsPayment(this, b.order, payment);
		
	}
	
	
	//inner classes
	public class Bill{
		Waiter w;
		String choice;
		Customer cust;
		int total=0;
		BillState status;
		
		public Bill(Waiter w, String choice, Customer cust){
			this.w=w;
			this.choice=choice;
			this.cust=cust;
			status = BillState.pending;
		}
		public boolean equals (Object anObject){
			if (anObject instanceof Bill){
				Bill b = (Bill) anObject;
				return b.w==w && b.choice.equals(choice) && b.cust == cust && b.total==total && b.status.equals(status);
			}
			return false;
		}
	}
	enum BillState{pending, computing, computed, done}
	
	public class MyCustomer{
		Customer c;
		Check bill;
		int payment;
		
		public MyCustomer(Customer cust, Check bill, int payment){
			c=cust;
			this.bill = bill;
			this.payment = payment;
		}
		
		public boolean equals (Object anObject){
			if (anObject instanceof MyCustomer){
				MyCustomer mc = (MyCustomer) anObject;
				return mc.c==c && mc.bill.equals(bill) && mc.payment==payment;
			}
			return false;
		}
	}
	
	public class MyBill{
		Market m;
		Map<String, Integer> order;
		int total;
		int owed;
		public MyBillState status;
		
		public MyBill(Market m, Map<String, Integer> o, int amount){
			this.m = m;
			this.order = o;
			this.owed = this.total = amount;
			status = MyBillState.newlyReceived;
		}
		
		public boolean equals (Object anObject){
			if (anObject instanceof MyBill){
				MyBill mb = (MyBill) anObject;
				return mb.m==m && mb.order.equals(order) && mb.total==total && mb.owed==owed && mb.status.equals(status);
			}
			return false;
		}
	}
	public enum MyBillState {newlyReceived, fullyPaid, partiallyPaid};
	
	//utilities
	
	public List<Bill> getBills(){
		return bills;
	}
	
	public List<MyCustomer> getCustomers(){
		return customers;
	}
	
	public Map<Customer, Integer> getDebtors(){
		return debtors;
	}
	
	public void addDebtor(Customer c,int debt){
		debtors.put(c, debt);
	}
	
	public List<MyBill> getMyBills(){
		return myBills;
	}
	
	public void setCash(int c){
		cash = c;
		stateChanged();
	}
	
	public int getCash(){
		return cash;
	}
	
	public String getName() {
		return name;
	}
}

