package restaurant.restaurantGabe;


import restaurant.restaurantGabe.gui.CustomerGui;
import restaurant.restaurantGabe.gui.RestaurantGui;
import restaurant.restaurantGabe.interfaces.Customer;
//import restaurant.restaurantGabe.interfaces.Market;
import restaurant.restaurantGabe.interfaces.Waiter;
import restaurant.restaurantGabe.interfaces.Cashier;
import restaurant.restaurantGabe.test.mock.EventLog;
import restaurant.restaurantGabe.test.mock.LoggedEvent;
import restaurant.restaurantGabe.util.*;
import role.Role;
import agent.Agent;
import interfaces.MarketDeliveryMan;

import java.util.*;

import person.PersonAgent;
import market.Market;
import market.MarketInvoice;

public class CashierRole extends Role implements Cashier{
	
	//INITIALIZATION
	public CashierRole(String name){
		this.name  = name;
		Foods.put("Pizza", new FoodItem("Pizza",12));
		Foods.put("Steak", new FoodItem("Steak",15));
		Foods.put("Chicken",new FoodItem("Chicken",10));
		Foods.put("Salad", new FoodItem("Salad",5));
		foods.add("Pizza");
		foods.add("Steak");
		foods.add("Chicken");
		foods.add("Salad");
		
	}
	
	//GETTERS
	public String getName(){
		return name;
	}
	
	public int getMoney(){
		return this.RestaurantMoney;
	}
	
	//METHODS
	public Debt findDebt(Customer c){
		Debt debtFound = null;
		
		synchronized(debts){
			for(Debt b:debts){
				if(b.c == c){
					debtFound = b;
					break;
				}
			}
		}
		
		
		return debtFound;
	}
	
	//SETTERS
	public void setMoney(int amount){
		this.RestaurantMoney = amount;
	}
	
	/*public void addCook(CookAgent m){
		Markets.add(m);
		m.setCook(this);
	}*/
	
	
	//TODO UNCOMMENT THIS ONCE RESTAURANT IS IN
	/*public void setBank(Bank b){
		this.RestaurantBank = b;
	}*/
	
	//DATA
	
	//person agent running the role
	PersonAgent person;
	
	public EventLog log = new EventLog();
	
	
	//private Bank RestaurantBank;
	//private BankTellerRole teller;
	private int RestaurantMoney = 1000;
	private boolean ReadyToBank = false;
	private int pendingDeposit;
	//private int pendingWithdrawal;
	
	public class MarketBill{
		public MarketBill(Market m,int amount){
			this.m = m;
			this.amount = amount;
		}
		public Market m;
		public int amount;
	}
	public List<MarketBill> marketBills = Collections.synchronizedList(new ArrayList<MarketBill>());
	
	Menu menu;
	
	public enum DebtState {uncomputed,unpaid,needsChange,paid,cantPay,delinquent};
	public static class Debt{
		public Debt(Waiter w,Customer c,DebtState s){
			this.w = w;
			this.c = c;
			//this.choice = choice;
			this.s = s;
			amount = 0;
		}
		public Debt(){
		}
		
		public boolean equals(Object o){
			if(o instanceof Debt){
				return (((Debt) o).w == w)&&(((Debt) o).c ==c)&&(((Debt) o).s == s)&&(((Debt) o).amount ==amount);
			}
			return false;
		}
		//String choice;
		public int amount;
		public int change;
		public Customer c;
		public Waiter w;
		public DebtState s;
	}
	
	//will help with iterating
	private List<String> foods = new ArrayList<String>();
	
	private String name;
	
	//stores all of the cashier's bills
	public List<Debt> debts = Collections.synchronizedList(new ArrayList<Debt>());
	
	//stores cooktime information for restaurant's dishes
	private Map<String, FoodItem > Foods = new HashMap<String,FoodItem>();
	
	
	private class FoodItem{
		public FoodItem(String name,int price){
			this.name = name;
			this.price = price;
		}
		String name;
		int price;
	}
	
	
	
	//MESSAGES
	
	public void msgDeliveryBill(Market m,int amount){
		Do("Got a new bill from the market for $" + amount);
		marketBills.add(new MarketBill(m,amount));
		stateChanged();
	}
	
	public void msgICantPay(Check check,int cash){
		Debt b = findDebt(check.c);
		Do(b.c.getName()+" can't pay.");
		b.s = DebtState.cantPay;
		stateChanged();
	}
	
	public void msgComputeBill(Waiter w,Customer c,String choice){
		Debt b = findDebt(c);
		Do("Got a new bill from waiter "+w.getName()+ " and cust "+c.getName()+", who ate "+choice);

		if(b==null){
			Debt newDebt = new Debt(w,c,DebtState.uncomputed);
			newDebt.amount = Foods.get(choice).price;
			debts.add(newDebt);
			stateChanged();
			return;
		}
		if(b.s==DebtState.delinquent){
			b.amount += Foods.get(choice).price;
		}
		else{
			b.amount = Foods.get(choice).price;
		}
		b.s = DebtState.uncomputed;
		stateChanged();
	}
	
	public void msgPayment(Check check,int cash){
		Do("Cust is paying me");
		Debt b = findDebt(check.c);
		b.change = cash - b.amount;
		b.s = DebtState.needsChange;
		this.RestaurantMoney += cash;
		stateChanged();
	}
	
	/*public void msgReadyForBanking(BankTellerRole bt){
		this.ReadyToBank = true;
		this.teller = bt;
		
	}*/
	
	public void msgDepositCompleted(int amount){
		//this.teller = null;
		if(amount==this.pendingDeposit){
			this.RestaurantMoney -= this.pendingDeposit;
		}
		
	}
	
	
	//SCHEDULER
	public boolean pickAndExecuteAnAction(){
		
		
		//if the bank is ready for a deposit, then do it
		if(ReadyToBank){
			BankBusiness();
		}
		
		//if there is an uncomputed bill, compute it and give it to waiter
		synchronized(debts){
			for(Debt b:debts){
				//Do("in here");
				if(b.s == DebtState.uncomputed){
					ComputeBill(b);
					return true;
				}
			}
		}
		
		//if there are any bills that need change, give it
		synchronized(debts){
			for(Debt b:debts){
				if(b.s == DebtState.needsChange){
					GiveChange(b);
					return true;
				}
			}
		}
		
		synchronized(debts){
			for(Debt b:debts){
				if(b.s == DebtState.cantPay){
					LetGo(b);
					return true;
				}
			}
		}
		
		synchronized(marketBills){
			if(!marketBills.isEmpty()){
				PayFirstBill();
				return true;
			}
		}
		
		
		if(RestaurantMoney >= 1500 || RestaurantMoney<=1000){
			finance();
			return true;
		}
		
	
		return false;
		
	}
	
	//ACTIONS
	
	private void PayFirstBill(){
		
		MarketBill pay = marketBills.get(0);
		log.add(new LoggedEvent("Attempting to pay the market a bill of $"+pay.amount));
		
		if(pay.amount<=this.RestaurantMoney){
			pay.m.cashier.msgHereIsBusinessPayment(pay.amount);
			this.RestaurantMoney -= pay.amount;
			marketBills.remove(0);
			return;
		}
		
		Do("Can't pay for the Market Order.");
		
		pay.m.cashier.msgHereIsBusinessPayment(0);
		//pay.m.msgCantPay();
		
		marketBills.remove(0);
		
	}
	
	
	//TODO consolidate LetGo and GiveChange and get rid of delinquent state of debt
	private void LetGo(Debt b){
		log.add(new LoggedEvent("Letting "+b.c.getName()+" pay next time"));
		
		Do("We'll let "+ b.c.getName()+" pay next time.");
		b.s = DebtState.delinquent;
		b.c.msgPayNextTime();
	}
	
	//compute bill
	private void ComputeBill(Debt b){
		log.add(new LoggedEvent("Computing bill for waiter "+b.w.getName()+" and customer "+b.c.getName()));
		
		//Do("computing bill");
		//b.amount = Foods.get(b.choice).price;
		//Do("Here computing bill");
		b.w.msgHereIsCheck(new Check(b.c,b.amount));
		b.s = DebtState.unpaid;
	}
	
	//give change
	private void GiveChange(Debt b){
		log.add(new LoggedEvent("Giving "+b.c.getName()+" change"));
		b.c.msgHereIsChange(b.change);
		b.s = DebtState.paid;
	}
	
	
	private void finance(){
		
		//This call will put Cashier at the front of the Queue in the bank
		//So the bank business can take place quickly
		//this.RestaurantBank.RestaurantBanking(this);
		
		
		
	}
	
	private void BankBusiness(){
		if(RestaurantMoney>=1500){
			pendingDeposit = this.RestaurantMoney - 300;
			this.ReadyToBank = false;
			//teller.msgRestaurantDeposit(this,pendingDeposit);
		}
		else if(RestaurantMoney<=1000){
			pendingDeposit = -1000;
			this.ReadyToBank = false;
			//teller.msgRestaurantWithdrawal(this,pendingDeposit);
		}
		
	}
	
	
	
	//GUI
	
	private void DoGiveOrder(Order o){
		//Do("Here's that order of "+o.choice+ " for "+o.c.getName());
	}

	@Override
	public void msgHereIsInvoice(MarketDeliveryMan deliveryMan,
			MarketInvoice order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPresent() {
		// TODO Auto-generated method stub
		return person!=null;
	}

	@Override
	public boolean canLeave() {
		// TODO Auto-generated method stub
		return true;
	}

}
