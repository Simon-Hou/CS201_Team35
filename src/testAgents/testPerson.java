package testAgents;

import role.Role;
import util.Bank;
import util.BankMapLoc;
import util.CityMap;
//import market.MarketCustomerRole;
import bank.BankCustomerRole;
//import bank.Person.Purse;
import agent.Agent;

public class testPerson extends Agent{
	//Constructor
	public testPerson(String name,CityMap cityMap){
		this.name = name;
		this.cityMap = cityMap;
		bankCustRole.setPerson(this);
	}
	
	String name;
	
	public String getName(){
		return name;
	}
	
	//Setter
	public void setMap(CityMap c){
		this.cityMap = c;
	}
	
	boolean wantsToGoToBank = false;
	boolean wantsToGoToMarket = false;
	boolean wantsToGoToRestaurant = false;
	
	
	public BankCustomerRole bankCustRole = new BankCustomerRole(this.name);
	
	//public MarketCustomerRole marketCustRole = new MarketCustomerRole();
	public Role activeRole;
	
	int myBank = 0;
	int myMarket = 0;
	
	public CityMap cityMap;
	
	public void msgThisRoleDone(Role r){
		activeRole = null;
	}
	
	public void msgStateChanged(){
		stateChanged();
	}
	
	public void msgGoToBank(){
		this.wantsToGoToBank = true;
		this.bankCustRole.name = this.name;
		stateChanged();
	}
	
	public void msgGoToMarket(){
		this.wantsToGoToMarket = true;
		stateChanged();
	}
	
	public void msgGoToRestaurant(){
		this.wantsToGoToRestaurant = true;
		stateChanged();
	}
	
	
	
	public boolean pickAndExecuteAnAction(){
		
		//First do all the person rules.
		
		
		//then do the active role
		if(activeRole!=null){
			
			return activeRole.pickAndExecuteAnAction();
		}
		
		
		//then, if there is no active role, figure out what else to do.
		if(wantsToGoToBank){
			Do("Got a message to go to bank, will do.");
			GoToBank();
			return true;
		}
		
		if(wantsToGoToMarket){
			GoToMarket();
			return true;
		}
		
		if(wantsToGoToMarket){
			GoToRestaurant();
			return true;
		}
		
		return false;
		
	}
	
	
	private void GoToBank(){
		Bank b = ((BankMapLoc) cityMap.map.get("Bank").get(myBank)).bank;
		this.bankCustRole.msgYouAreAtBank(b);
		this.activeRole = this.bankCustRole;
		this.wantsToGoToBank = false;
	}
	
	private void GoToMarket(){
		
	}
	
	private void GoToRestaurant(){
		
	}
	
	//Bunch of needed data
	
	Purse purse = new Purse();
	
	class Purse{
		public int wallet;
	}
	
	public void addToAccount(int accNum,int amount){
		
	}
	
	public void takeFromAccount(int accNum,int amount){
		
	}
	
	public void createAccount(int accountNumber,int amount,String name,String passWord){
		
	}
	
	public void addLoan(int accountNumber,int cash, int loanNumber){
		
	}
	
	
	
	
	
	

}
