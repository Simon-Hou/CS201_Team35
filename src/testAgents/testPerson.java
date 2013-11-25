package testAgents;

import interfaces.Person;

import java.util.HashMap;
import java.util.Map;

import market.Market;
import market.MarketCustomerRole;
import role.Role;
import util.Bank;
import util.BankMapLoc;
import util.BusStop;
import util.CityMap;
import util.Loc;
import util.MarketMapLoc;
//import market.MarketCustomerRole;
import bank.BankCustomerRole;
//import bank.Person.Purse;
import agent.Agent;

public class testPerson extends Agent implements Person{
	//Constructor
	public testPerson(String name,CityMap cityMap){
		this.name = name;
		this.cityMap = cityMap;
		bankCustRole.setPerson(this);
		this.marketCustRole.setName(this.name);
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
	
	
	public BankCustomerRole bankCustRole = new BankCustomerRole(this.name,this);
	public MarketCustomerRole marketCustRole = new MarketCustomerRole(this.name,this);
	
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
		Market m = ((MarketMapLoc) cityMap.map.get("Market").get(myMarket)).market;
		
		this.marketCustRole.msgYouAreAtMarket(m);
		this.activeRole = this.marketCustRole;
		this.wantsToGoToMarket = true;
	}
	
	private void GoToRestaurant(){
		
	}
	
	//Bunch of needed data
	
	public Purse purse = new Purse();
	
	public class Purse{
		public int wallet;
		public Map<String, Integer> bag = new HashMap<String,Integer>();  
	}
	
	public void addToAccount(int accNum,int amount){
		
	}
	
	public void takeFromAccount(int accNum,int amount){
		
	}
	
	public void createAccount(int accountNumber,int amount,String name,String passWord){
		
	}
	
	public void addLoan(int accountNumber,int cash, int loanNumber){
		
	}

	@Override
	public void msgBusAtStop(BusStop stop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgCarArrivedAtLoc(Loc destination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToWallet(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeFromWallet(int amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneEating() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWalletAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addFoodToBag(String type, int quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putInBag(String item, int amount) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	

}
