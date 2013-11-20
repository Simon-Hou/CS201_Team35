package person;

import house.InhabitantRole;
import interfaces.Person;

import java.util.ArrayList;
import java.util.List;

import bank.BankCustomerRole;
import market.MarketCustomerRole;
import role.Role;
import util.Task;
import util.deposit;
import util.takeLoan;
import util.withdrawal;
import agent.Agent;

public class PersonAgent extends Agent implements Person {
	
	//data
	public List<Role> roles = new ArrayList<Role>();
	Time time;
	int hungerLevel;
	int tiredLevel;
	int personalAddress;
	Purse purse;
	Belongings belongings;
	Job myJob;
	Role activeRole = null;
	Role nextRole = null;
	
	public enum Personality
	{Normal, Wealthy, Deadbeat, Crook};
	private Personality personality;
	
	public class Job {
		Role jobRole;
		int location;
		int shiftStart;
		int shiftEnd;
	}
	
	public class Belongings {
		Property myLiving;
		List<Property> myEstates;
		List<Car> myCars;
		List<Food> myFoods;
		List<BankAccount> myAccounts;
	}
	
	public class BankAccount {
		int amount;
		int accountNumber;
		String custName;
		String password;
		List<Loan> myLoans;
	}
	
	public class Loan {
		int loanNumber;
		int total;
		int amountLeft;
		
		void loan(int totalAmount) {
			total = totalAmount;
			amountLeft = total;
		}
		
		void payoff(int payment) {
			amountLeft -= payment;
		}
	}
	
	public class Purse {
		List<Food> bag;
		int wallet;
	}
	
	public class Property {
		int address;
		Person tenant;
		int maintenanceLevel;
	}
	
	public class Car {
		int licensePlateNumber;
	}
	
	public class Food {
		String type;
		int quantity;
	}
	//msg
	public void msgDoneEating(){
		hungerLevel=0;
		stateChanged();
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction() {
		
		if (activeRole != null) {
			return activeRole.pickAndExecuteAnAction();
		}
		
		if (nextRole != null) {
			activeRole = nextRole;
			nextRole = null;
			return true;
		}
		
		if (myJob.shiftStart >= time && myJob.shiftEnd < time) {
			goToWork();
			return true;
		}
		
		if (purse.wallet <= 10 || purse.wallet >= 100) {
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
	}
	
	//Actions
	private void goToWork() {
		Do("I am going to work");
		//doGoToWork(myJob.location);
		activeRole = myJob.jobRole;
	}
	
	private void goToBank() {
		//doGoToBank();
		boolean containsRole = false;
		for (Role r: roles) {
			if (r instanceof BankCustomerRole) {
				activeRole = r;
				containsRole = true;
			}
		}
		if (!containsRole) {
			activeRole = new BankCustomerRole();
			roles.add(activeRole);
		}
		
		List<Task> bankTasks = new ArrayList<Task>();
		
		//deposit
		if (purse.wallet >= 100) {
			Do("I am going to the bank to deposit $" + (purse.wallet-50));
			bankTasks.add(new deposit((purse.wallet-50),belongings.myAccounts.get(0).accountNumber,belongings.myAccounts.get(0).password));
		}
		
		//withdrawal
		if (purse.wallet <= 10 && getMoneyInBank() >= 50)  {
			Do("I am going to the bank to withdraw $" + (60 - purse.wallet));
			bankTasks.add(new withdrawal((70 - purse.wallet),belongings.myAccounts.get(0).accountNumber,belongings.myAccounts.get(0).password));
		}
		
		//loan
		if (purse.wallet <= 10 && getMoneyInBank() < 50) {
			Do("I am going to the bank to withdraw $" + (getMoneyInBank()) + " and to loan $" + (50 - getMoneyInBank()));
			bankTasks.add(new withdrawal(getMoneyInBank(),belongings.myAccounts.get(0).accountNumber,belongings.myAccounts.get(0).password));
			bankTasks.add(new takeLoan(50 - getMoneyInBank(),belongings.myAccounts.get(0).accountNumber,belongings.myAccounts.get(0).password));
		}
	}
	
	private void goToMarket() {
		Do("I am going to the market to buy food for home");
		//doGoToMarket();
		ShoppingList shoppingList = makeShoppingList();
		boolean containsRole = false;
		for (Role r: roles) {
			if (r instanceof MarketCustomerRole) {
				r.shoppingList = shoppingList;
				activeRole = r;
				containsRole = true;
			}
		}
		if (!containsRole) {
			activeRole = new MarketCustomerRole(shoppingList);
			roles.add(activeRole);
		}
	}
	
	private void getFood() {
		if (!belongings.myFoods.isEmpty()) {
			Do("I am going to eat at home");
			//goHome();
			boolean containsRole = false;
			for (Role r: roles) {
				if (r instanceof InhabitantRole) {
					activeRole = r;
					containsRole = true;
				}
			}
			if (!containsRole) {
				activeRole = new InhabitantRole();
				roles.add(activeRole);
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
		boolean containsRole = false;
		for (Role r: roles) {
			if (r instanceof InhabitantRole) {
				activeRole = r;
				containsRole = true;
			}
		}
		if (!containsRole) {
			activeRole = new InhabitantRole();
			roles.add(activeRole);
		}
	}
	
	private void goToRestaurant() {
		//doGoToRestaurant();
		activeRole = new RestaurantCustomer();
	}
	
	private void buyCar() {
		if (purse.wallet < 500) {
			Do("I am going to get money from the bank and then I'm going to buy a car");
			//doGoToBank();
			boolean containsRole = false;
			for (Role r: roles) {
				if (r instanceof BankCustomerRole) {
					activeRole = r;
					r.addTask(new withdrawal(500, belongings.myAccounts.get(0).accountNumber, belongings.myAccounts.get(0).password);
					containsRole = true;
				}
			}
			if (!containsRole) {
				activeRole = new BankCustomerRole();
				activeRole.addTask(new withdrawal(500, belongings.myAccounts.get(0).accountNumber, belongings.myAccounts.get(0).password);
				roles.add(activeRole);
			}
			boolean containsRole2 = false;
			for (Role r: roles) {
				if (r instanceof MarketCustomerRole) {
					nextRole = r;
					containsRole2 = true;
				}
			}
			if (!containsRole2) {
				nextRole = new MarketCustomerRole();
				roles.add(activeRole);
			}
		}
		else {
			Do("I am going to buy a car from the market");
			boolean containsRole = false;
			for (Role r: roles) {
				if (r instanceof MarketCustomerRole) {
					activeRole = r;
					containsRole = true;
				}
			}
			if (!containsRole) {
				activeRole = new MarketCustomerRole();
				roles.add(activeRole);
			}
		}
	}
	
	private void goDoMaintenance(Property p) {
		//doGoToProperty
		p.maintenanceLevel = 0;
	}
	
	//Utilities
	
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
}
