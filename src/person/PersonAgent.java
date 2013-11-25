package person;

import house.InhabitantRole;
import interfaces.Occupation;
import interfaces.Person;
import interfaces.PlaceOfWork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bank.BankCustomerRole;
import bank.BankTellerRole;
import market.Market;
import market.MarketCustomerRole;
import market.MarketEmployeeRole;
import role.Role;
import util.Bank;
import util.BusStop;
import util.CityMap;
import util.Job;
import util.JobType;
import util.Loc;
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
		marketRole = new MarketCustomerRole(name+"Market",this);
		inhabitantRole = new InhabitantRole(name+"Home",this);
		
		this.belongings.myFoods.add(new Food("Steak",10));
		this.belongings.myFoods.add(new Food("Chicken",10));
		this.belongings.myFoods.add(new Food("Pizza",10));
		this.belongings.myFoods.add(new Food("Salad",10));
		
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
	public int myBank = 0;
	public int personalAddress;
	public Purse purse;
	public Belongings belongings;
	public Map<String, Integer> shoppingList;
	public Job myJob;
	public Role activeRole = null;
	public Role nextRole = null;
	public boolean wantsToBuyCar = false;
	public BankCustomerRole bankRole;
	public MarketCustomerRole marketRole;
	public InhabitantRole inhabitantRole;
	//List<String> foodNames;
	
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
			myLoans = new ArrayList<Loan>();
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
	
	public void msgCarArrivedAtLoc(Loc destination){
		//blah
		//stateChanged();
	}
	
	public void msgBusAtStop(BusStop stop){
		//blah
		//stateChanged();
	}
	
	
	public void msgDoneEating(){
		hungerLevel=0;
		stateChanged();
	}
	
	//Scheduler
	 public boolean pickAndExecuteAnAction() {
		//Do("Deciding what to do - "+ time);
		//Do("Role: "+activeRole);
		if (activeRole != null) {
			activeRoleCalls++;
			
			//This takes care of getting off work
			if(activeRole == myJob.jobRole && time >= myJob.shiftEnd){
				if(myJob.jobRole instanceof BankTellerRole){
					if(((BankTellerRole) myJob.jobRole).canLeave()){
						Do("It's quitting time.");
						activeRole = null;
						return true;
					}
				}
				if(((Occupation) myJob.jobRole).canLeave()){
					Do("It's quitting time.");
					activeRole = null;
					return true;
				}
			}
			
			return activeRole.pickAndExecuteAnAction();
		}
		
		if(time == myJob.shiftStart-1){
			return false;
		}
		
		/*if (nextRole != null) {
			activeRole = nextRole;
			nextRole = null;
			return true;
		}*/
		/*if(name.equals("p1")){
			Do("DECIDING WHAT TO DO");
		}*/
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
		Do("I am going to work as a "+myJob.jobType);
		doGoToWork();
		
		Role tempJobRole = myJob.placeOfWork.canIStartWorking(this, myJob.jobType, myJob.jobRole);
		
		//THIS IS JUST A TEMPORARY FIX, IF SOMEONE DOESN'T GET TO WORK,
		//WE JUST MOVE THEIR SHIFT BACK BY ONE TIME STEP
		if(tempJobRole==null){
			myJob.shiftStart+=1;
			myJob.shiftEnd+=1;
			return;
		}
		
		/*
		//Bank Employment
		if(this.myJob.jobRole instanceof BankTellerRole){
			((BankMapLoc) myJob.placeOfWork).bank.startTellerShift(((BankTellerRole) myJob.jobRole));
		}
		
		//Market Employment
		else if(myJob.jobType==JobType.MarketCashier){
			
		}
		else if(myJob.jobType==JobType.MarketCashier){
			
		}
		else if(myJob.jobType==JobType.MarketEmployee){
			
		}
		else if(myJob.jobType==JobType.MarketDeliveryMan){
			
		}
		
		//Restaurant Employment
		else if(myJob.jobType==JobType.RestaurantHost){
			
		}
		else if(myJob.jobType==JobType.RestaurantCashier){
			
		}
		else if(myJob.jobType==JobType.RestaurantCook){
			
		}
		else if(myJob.jobType==JobType.RestaurantWaiter){
			
		}*/
		
		
		myJob.jobRole = tempJobRole;
		activeRole = tempJobRole;
		//Do(""+ activeRole);
		//System.out.flush();
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
		//MarketCustomerRole marketRole = null;
		Market m = ((MarketMapLoc) city.map.get("Market").get(0)).market;
		
		
		//ShoppingList shoppingList = makeShoppingList();
		
		//Gets customerRole or creates customerRole
		/*boolean containsRole = false;
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
		}*/
		
		for(Food f:this.belongings.myFoods){
			if(f.quantity<10){
				marketRole.addToShoppingList(f.type, f.quantity);
			}
		}
		
		//marketRole.setMarket(m);
		marketRole.msgYouAreAtMarket(m);
		activeRole = marketRole;
	}
	
	private void getFood() {
		if (!belongings.myFoods.isEmpty()) {
			Do("I am going to eat at home");
			//goHome();
			/*InhabitantRole inhabitantRole;
			
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
			}*/
			activeRole = inhabitantRole;
		}
		else if(belongings.myFoods.isEmpty()) {
			Do("I am going to eat at a restaurant");
			goToRestaurant();
		}
	}
	private void getSleep() {
		Do("I am going home to sleep");
		//doGoToHome();
		/*InhabitantRole inhabitantRole;
		
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
		}*/
		activeRole = inhabitantRole;
	}
	
	private void goToRestaurant() {
		//doGoToRestaurant();
		//activeRole = new RestaurantCustomer();
	}
	
	private void buyCar() {
		//MarketCustomerRole marketRole = null;
		if (purse.wallet < 500) {
			Do("I am going to get money from the bank and then I'm going to buy a car");
			//doGoToBank();
			/*BankCustomerRole bankRole;
			
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
			}*/
			wantsToBuyCar = true;
			
			//Gets customerRole or creates customerRole
			/*boolean containsRole2 = false;
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
			}*/
			doGoToBank();
			bankRole.Tasks.add(new withdrawal(500, belongings.myAccounts.get(0).accountNumber, belongings.myAccounts.get(0).password));
			bankRole.msgYouAreAtBank(((BankMapLoc) city.map.get("Market").get(myBank)).bank);
			activeRole = bankRole;
		}
		else {
			Market m = ((MarketMapLoc) city.map.get("Market").get(0)).market;
			Do("I am going to buy a car from the market");
			
			//Gets customerRole or creates customerRole
			/*boolean containsRole = false;
			for (Role r: roles) {
				if (r instanceof MarketCustomerRole) {
					activeRole = r;
					marketRole = (MarketCustomerRole) r;
					containsRole = true;
				}
			}
			if (!containsRole) {
				marketRole = new MarketCustomerRole(this.name,this);
				marketRole.msgYouAreAtMarket(m);
				activeRole = marketRole;
				roles.add(activeRole);
			}*/
			
			marketRole.msgYouAreAtMarket(m);
			activeRole = marketRole;
		}
	}
	
	private void goDoMaintenance(Property p) {
		/*InhabitantRole inhabitantRole;
		
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
		}*/
		activeRole = inhabitantRole;
		p.maintenanceLevel = 0;
	}
	
	//ANIMATION
	
	private void doGoToBank(){
		
	}
	
	private void doGoToWork(){
		
	}
	
	
	
	
	
	//Utilities
	
	public void setJob(PlaceOfWork placeOfWork,JobType jobType,int start,int end){
		
		Role jobRole = null;
		if(jobType==JobType.MarketHost || jobType==JobType.MarketCashier 
				|| jobType==jobType.RestaurantHost){
			jobRole = null;
			//myJob = new Job(null,start,end,placeOfWork,this,jobType);
			//return;
		}
		else if(jobType==JobType.BankTeller){
			jobRole = new BankTellerRole(name+"Teller",this);
			//myJob = new Job(jobRole,start,end,placeOfWork,this,jobType);
			//return;
		}
		else if(jobType==JobType.MarketEmployee){
			jobRole = new MarketEmployeeRole(name+"MarketEmployee",this);
			//myJob = new Job(jobRole,start,end,placeOfWork,this,jobType);
		}
		myJob = new Job(jobRole,start,end,placeOfWork,this,jobType);
		
		
	}
	
	public void msgStateChanged() {
		//this.pickAndExecuteAnAction();
		this.stateChanged();
	}
	
	public void putInBag(String item,int amount){
		this.purse.bag.put(item,amount);
	}
	
	public void addToWallet(int amount) {
		this.purse.wallet += amount;
	}
	
	public void takeFromWallet(int amount) {
		this.purse.wallet -= amount;
	}
	
	public int getWalletAmount(){
		return purse.wallet;
	}
	
	public void addFoodToBag(String type, int quantity){
		if (purse.bag.containsKey(type))
			purse.bag.put(type, purse.bag.get(type)+quantity);
		else
			purse.bag.put(type, purse.bag.get(type));
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
		this.activeRole = null;
	}
	
	
	
	
	
}
