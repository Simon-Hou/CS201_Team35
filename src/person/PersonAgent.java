package person;

import house.InhabitantRole;
import interfaces.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bank.BankCustomerRole;
import bank.BankTellerRole;
import market.Market;
import market.MarketCustomerRole;
import role.Role;
import util.Bank;
import util.CityMap;
import util.Job;
import util.MarketMapLoc;
import util.BankMapLoc;
import util.Place;
import util.Task;
import util.deposit;
import util.openAccount;
import util.takeLoan;
import util.withdrawal;
import interfaces.Person;

import java.util.ArrayList;
import java.util.List;

import public_Object.Food;
import role.Role;
import agent.Agent;

public class PersonAgent extends Agent implements Person {
	
	public PersonAgent(String name, CityMap city) {
		this.name = name;
		this.city = city;
		belongings = new Belongings();
		myJob = new Job();
		purse = new Purse();
		bankRole = new BankCustomerRole(name+"Bank",this);
	}
	
	//GETTERS
	public String getName(){
		return name;
	}
	
	
	//SETTERS
	public void setTime(int time){
		this.time = time;
	}
	
	
	//data
	public List<Role> roles = new ArrayList<Role>();
	public int time;
	public String name;
	public CityMap city;
	public int activeRoleCalls = 0;
	//Time time;
	public int hungerLevel = 0;
	public int tiredLevel = 0;
	public int personalAddress;
	public Purse purse;
	public Belongings belongings;
	public Map<String, Integer> shoppingList;
	public Job myJob;
	public Role activeRole = null;
	public Role nextRole = null;
	public boolean wantsToBuyCar = false;
	public BankCustomerRole bankRole;
	
	public enum Personality
	{Normal, Wealthy, Deadbeat, Crook};
	private Personality personality;
	
	//I JUST MOVED THE JOB CLASS TO A PUBLIC UTIL CLASS SO THE CITY CAN ACCESS IT
	
	public class Belongings {
		
		public Belongings() {
			myLiving = new Property();
			myEstates = new ArrayList<Property>();
			myCars = new ArrayList<Car>();
			myFoods = new ArrayList<Food>();
			myAccounts = new ArrayList<BankAccount>();
		}
		
		public Property myLiving;
		public List<Property> myEstates;
		public List<Car> myCars;
		public List<Food> myFoods;
		public List<BankAccount> myAccounts;
	}
	
	public class BankAccount {
		
		public BankAccount(int accNumber,int amt,String name,String pw) {
			accountNumber = accNumber;
			amount = amt;
			custName = name;
			password = pw;
		}
		
		public int amount;
		public int accountNumber;
		public String custName;
		public String password;
		public List<Loan> myLoans;
	}
	
	public class Loan {
		public int loanNumber;
		public int total;
		public int amountLeft;
		
		public void loan(int totalAmount) {
			total = totalAmount;
			amountLeft = total;
		}
		
		public void payoff(int payment) {
			amountLeft -= payment;
		}
	}
	
	public class Purse {
		public Map<String, Integer> bag = new HashMap<String,Integer>();
		public int wallet;
	}
	
	public class Property {
		public int address;
		public Person tenant;
		public int maintenanceLevel;
	}
	
	public class Car {
		public int licensePlateNumber;
	}
	

	//msg
	public void msgDoneEating(){
		hungerLevel=0;
		stateChanged();
	}
	
	//Scheduler
	 public boolean pickAndExecuteAnAction() {
		
		if (activeRole != null) {
			activeRoleCalls++;
			return activeRole.pickAndExecuteAnAction();
		}
		
<<<<<<< HEAD
		Do("GOT HERE");
		
		if (nextRole != null) {
			activeRole = nextRole;
			nextRole = null;
			return true;
		}
		
=======
>>>>>>> a6c2e034f5580c0f7f2554414031d94129044197
		if (time >= myJob.shiftStart && time < myJob.shiftEnd) {
			goToWork();
			return true;
		}		

		if (purse.wallet > 500 && wantsToBuyCar) {
			buyCar();
		}

		if(belongings.myAccounts.size()==0){
			goToBank();
			return true;
		}

		if ((purse.wallet <= 10 || purse.wallet >= 100) && !wantsToBuyCar) {
			goToBank();
			return true;
		}
		
		if (hungerLevel > 6) {
			getFood();
			return true;
		}
		
		if (belongings.myFoods.isEmpty()) {
			goToMarket();
			return true;
		}
		
		if (tiredLevel > 14) {
			getSleep();
			return true;
		}
		
		if (!belongings.myEstates.isEmpty()) {
			for (Property property: belongings.myEstates) {
				if (property.maintenanceLevel > 168) {
					goDoMaintenance(property);
					return true;
				}
			}
			return true;
		}
		
		if (belongings.myLiving.maintenanceLevel > 168) {
			goDoMaintenance(belongings.myLiving);
			return true;
		}
		
		if (getNetWorth() > 500) {
			buyCar();
			return true;
		}
		return false;
	}
	
	//Actions
	private void goToWork() {
		Do("I am going to work");
		doGoToWork(myJob.location);
		
		if(this.myJob.jobRole instanceof BankTellerRole){
			((BankMapLoc) myJob.placeOfWork).bank.startTellerShift(((BankTellerRole) myJob.jobRole));
		}
		
		activeRole = myJob.jobRole;
		Do(""+ activeRole);
		System.out.flush();
	}
	
	private void goToBank() {

		Bank b = ((BankMapLoc) city.map.get("Bank").get(0)).bank;
		
		//Gets customerRole or creates customerRole
		/*BankCustomerRole bankRole = null;
		boolean containsRole = false;
		for (Role r: roles) {
			if (r instanceof BankCustomerRole) {
				activeRole = r;
				bankRole = (BankCustomerRole) r;
				containsRole = true;
			}
		}
		if (!containsRole) {
			bankRole = new BankCustomerRole(this.name,this);
			activeRole = bankRole;
			roles.add(activeRole);
		}*/
		
		activeRole = bankRole;
		
		//open account
		if(belongings.myAccounts.isEmpty()){
			Do("Going to bank to open new account");
			bankRole.Tasks.add(new openAccount((int) Math.floor(purse.wallet*.5),name));
			doGoToBank();
			bankRole.msgYouAreAtBank(b);
			activeRole = bankRole;
			return;
		}
		
		//deposit
		if (purse.wallet >= 100) {
			Do("I am going to the bank to deposit $" + (purse.wallet-50));
			bankRole.Tasks.add(new deposit((purse.wallet-50),belongings.myAccounts.get(0).accountNumber,belongings.myAccounts.get(0).password));
		}
		
		//withdrawal
		if (purse.wallet <= 10 && getMoneyInBank() >= 50)  {
			Do("I am going to the bank to withdraw $" + (60 - purse.wallet));
			bankRole.Tasks.add(new withdrawal((70 - purse.wallet),belongings.myAccounts.get(0).accountNumber,belongings.myAccounts.get(0).password));
		}
		
		//loan
		if (purse.wallet <= 10 && getMoneyInBank() < 50) {
			Do("I am going to the bank to withdraw $" + (getMoneyInBank()) + " and to loan $" + (50 - getMoneyInBank()));
			bankRole.Tasks.add(new withdrawal(getMoneyInBank(),belongings.myAccounts.get(0).accountNumber,belongings.myAccounts.get(0).password));
			bankRole.Tasks.add(new takeLoan(50 - getMoneyInBank(),belongings.myAccounts.get(0).accountNumber,belongings.myAccounts.get(0).password));
		}
		
		doGoToBank();
		bankRole.msgYouAreAtBank(b);
		activeRole = bankRole;
		
	}
	
	private void goToMarket() {
		Do("I am going to the market to buy food for home");
		//doGoToMarket();
		MarketCustomerRole marketRole = null;
		Market m = ((MarketMapLoc) city.map.get("Market").get(0)).market;
		//ShoppingList shoppingList = makeShoppingList();
		
		//Gets customerRole or creates customerRole
		boolean containsRole = false;
		for (Role r: roles) {
			if (r instanceof MarketCustomerRole) {
				//r.shoppingList = shoppingList;
				activeRole = r;
				marketRole = (MarketCustomerRole) r;
				containsRole = true;
			}
		}
		if (!containsRole) {
			marketRole = new MarketCustomerRole(this.name,this);
			activeRole = marketRole;
			roles.add(activeRole);
		}
		
		marketRole.msgYouAreAtMarket(m.host);
		activeRole = marketRole;
	}
	
	private void getFood() {
		if (!belongings.myFoods.isEmpty()) {
			Do("I am going to eat at home");
			//goHome();
			InhabitantRole inhabitantRole;
			
			//Gets inhabitantRole or creates inhabitantRole
			boolean containsRole = false;
			for (Role r: roles) {
				if (r instanceof InhabitantRole) {
					activeRole = r;
					inhabitantRole = (InhabitantRole) r;
					containsRole = true;			
					//inhabitantRole.msgYouAreAtHome();
					activeRole = inhabitantRole;
				}
			}
			if (!containsRole) {
				inhabitantRole = new InhabitantRole();
				activeRole = inhabitantRole;
				roles.add(activeRole);	
				//inhabitantRole.msgYouAreAtHome();
				activeRole = inhabitantRole;
			}
		}
		else if(belongings.myFoods.isEmpty()) {
			Do("I am going to eat at a restaurant");
			goToRestaurant();
		}
	}
	private void getSleep() {
		Do("I am going home to sleep");
		//doGoToHome();
		InhabitantRole inhabitantRole;
		
		//Gets inhabitantRole or creates inhabitantRole
		boolean containsRole = false;
		for (Role r: roles) {
			if (r instanceof InhabitantRole) {
				activeRole = r;
				inhabitantRole = (InhabitantRole) r;
				containsRole = true;			
				//inhabitantRole.msgYouAreAtHome();
				activeRole = inhabitantRole;
			}
		}
		if (!containsRole) {
			inhabitantRole = new InhabitantRole();
			activeRole = inhabitantRole;
			roles.add(activeRole);	
			//inhabitantRole.msgYouAreAtHome();
			activeRole = inhabitantRole;
		}
	}
	
	private void goToRestaurant() {
		//doGoToRestaurant();
		//activeRole = new RestaurantCustomer();
	}
	
	private void buyCar() {
		MarketCustomerRole marketRole = null;
		if (purse.wallet < 500) {
			Do("I am going to get money from the bank and then I'm going to buy a car");
			//doGoToBank();
			BankCustomerRole bankRole;
			
			//Gets customerRole or creates customerRole
			boolean containsRole = false;
			for (Role r: roles) {
				if (r instanceof BankCustomerRole) {
					activeRole = r;
					bankRole = (BankCustomerRole) r;
					bankRole.Tasks.add(new withdrawal(500, belongings.myAccounts.get(0).accountNumber, belongings.myAccounts.get(0).password));
					activeRole = bankRole;
					containsRole = true;
				}
			}
			if (!containsRole) {
				bankRole = new BankCustomerRole(this.name, this);
				bankRole.Tasks.add(new withdrawal(500, belongings.myAccounts.get(0).accountNumber, belongings.myAccounts.get(0).password));
				activeRole = bankRole;
				roles.add(activeRole);
			}
			wantsToBuyCar = true;
			
			//Gets customerRole or creates customerRole
			boolean containsRole2 = false;
			for (Role r: roles) {
				if (r instanceof MarketCustomerRole) {
					nextRole = r;
					marketRole = (MarketCustomerRole) r;
					containsRole2 = true;
				}
			}
			if (!containsRole2) {
				marketRole = new MarketCustomerRole(this.name,this);
				activeRole = marketRole;
				roles.add(activeRole);
			}
		}
		else {
			Market m = ((MarketMapLoc) city.map.get("Market").get(0)).market;
			Do("I am going to buy a car from the market");
			
			//Gets customerRole or creates customerRole
			boolean containsRole = false;
			for (Role r: roles) {
				if (r instanceof MarketCustomerRole) {
					activeRole = r;
					marketRole = (MarketCustomerRole) r;
					containsRole = true;
				}
			}
			if (!containsRole) {
				marketRole = new MarketCustomerRole(this.name,this);
				marketRole.msgYouAreAtMarket(m.host);
				activeRole = marketRole;
				roles.add(activeRole);
			}
			marketRole.msgYouAreAtMarket(m.host);
			activeRole = marketRole;
		}
	}
	
	private void goDoMaintenance(Property p) {
		InhabitantRole inhabitantRole;
		
		//Gets inhabitantRole or creates inhabitantRole
		boolean containsRole = false;
		for (Role r: roles) {
			if (r instanceof InhabitantRole) {
				activeRole = r;
				inhabitantRole = (InhabitantRole) r;
				containsRole = true;			
				//inhabitantRole.msgYouAreAtHome();
				activeRole = inhabitantRole;
			}
		}
		if (!containsRole) {
			inhabitantRole = new InhabitantRole();
			activeRole = inhabitantRole;
			roles.add(activeRole);	
			//inhabitantRole.msgYouAreAtHome();
			activeRole = inhabitantRole;
		}
		p.maintenanceLevel = 0;
	}
	
	//ANIMATION
	
	private void doGoToBank(){
		
	}
	
	private void doGoToWork(int loc){
		
	}
	
	
	
	
	
	//Utilities
	
	public void msgStateChanged() {
		//this.pickAndExecuteAnAction();
		this.stateChanged();
	}
	
	public void addToWallet(int amount) {
		this.purse.wallet += amount;
	}
	
	public void takeFromWallet(int amount) {
		this.purse.wallet -= amount;
	}
	
	public int getMoneyInBank() {
		int totalMoney = 0;
		for (BankAccount account: belongings.myAccounts) {
			totalMoney += account.amount;
		}
		return totalMoney;
	}
	
	public int getNetWorth() {
		int totalMoney = 0;
		for (BankAccount account: belongings.myAccounts) {
			totalMoney += account.amount;
			for (Loan loan: account.myLoans) {
				totalMoney -= loan.amountLeft;
			}
		}
		totalMoney += purse.wallet;
		return totalMoney;
	}
	
	public void msgThisRoleDone() {
		activeRole = null;
	}
	
	
	
	//Bank Utilities
	
	public void addToAccount(int accNum,int amount){
		for (BankAccount account: belongings.myAccounts) {
			if (account.accountNumber == accNum) {
				account.amount += amount;
			}
		}
	}
	
	public void takeFromAccount(int accNum,int amount){
		for (BankAccount account: belongings.myAccounts) {
			if (account.accountNumber == accNum) {
				account.amount -= amount;
			}
		}
	}
	
	public void createAccount(int accountNumber,int amount,String name,String passWord){
		BankAccount account = new BankAccount(accountNumber,amount,name,passWord);
		belongings.myAccounts.add(account);
	}
	
	public void addLoan(int accountNumber,int cash, int loanNumber){
		Loan loan = new Loan();
		loan.amountLeft = cash;
		loan.total = cash;
		loan.loanNumber = loanNumber;
		for (BankAccount account: belongings.myAccounts) {
			if (account.accountNumber == accountNumber) {
				account.myLoans.add(loan);
			}
		}
	}

	@Override
	public void msgThisRoleDone(Role role) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
