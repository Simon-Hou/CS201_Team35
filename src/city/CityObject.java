package city;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.Timer;

import person.PersonAgent;
import role.Role;
import market.Market;
import market.MarketCashierRole;
import market.MarketEmployeeRole;
import market.MarketHostRole;
import bank.BankCustomerRole;
import bank.BankTellerRole;
import testAgents.testPerson;
import util.Bank;
import util.BankMapLoc;
import util.CityMap;
import util.Job;
import util.MarketMapLoc;
import util.Place;

public class CityObject implements ActionListener{

	
	
	//THIS WILL BE THE MAIN CITY OBJECT WHICH WILL HOLD THE CITY 
	//AND EVERYTHING IN IT.
	
	//IT WILL ALSO CONNECT AGENTS/ROLES/BUILDINGS VIA THE 
	//APPROPRIATE POINTER-SETTER METHODS
	
	
	//========================A City Will Need:====================================
	
	//======MAP======
	
	CityMap cityMap = new CityMap();
	
	public final int numBanks = 1;
	public final int numMarkets = 1;
	public final int numRestaurants = 1;
	
	int currentTime = 0;
	static int TIMER_DELAY = 3000;
	
	
	public List<PersonAgent> people = new ArrayList<PersonAgent>();
	public final int NUM_PEOPLE = 2;
	
	
	public CityObject(){
		
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
		
		cityMap.map.put("Bank", new ArrayList<Place>());
		cityMap.map.put("Market", new ArrayList<Place>());
		cityMap.map.put("Restaurant", new ArrayList<Place>());
		
		
		//CREATE THE OBJECTS, ADD THEM TO THE MAP
		
		
		//======BUILDINGS======
		
		//==Banks==
		
		for(int i = 0;i<numBanks;++i){
			Bank b = new Bank();
			BankMapLoc bMap = new BankMapLoc(b);
			cityMap.map.get("Bank").add(bMap);
		}
		
		
		//==Restaurants==
		
		//==Markets==
		
		for(int i = 0;i<numMarkets;i++){
			Market m = new Market();
			m.host = new MarketHostRole(null,null);
			MarketMapLoc mMap = new MarketMapLoc(m);
			cityMap.map.get("Market").add(mMap);
		}
		
		
		
		//==Houses==
		
		
		//======PEOPLE======
		
		//==People
			//+ Will need to be instantiated with different jobs/housing situations
			//+ Instantiate with map - they'll have almost all the pointers they'll need
		
		//method we've agreed on new Person(String name,CityMap cityMap)
		
		/*BankTellerRole t0 = new BankTellerRole("t0");
		t0.setBank(((BankMapLoc) cityMap.map.get("Bank").get(0)).bank);
		t0.startThread();*/
		
		/*MarketHostRole h0 = new MarketHostRole();
		((MarketMapLoc) cityMap.map.get("Market").get(0)).market.host = h0;
		h0.startThread();*/
		
		for(int i = 0;i<NUM_PEOPLE;++i){
			PersonAgent p = new PersonAgent("p"+i,cityMap);
			p.startThread();
			people.add(p);
			
		}
		people.get(0).myJob = new Job(new BankTellerRole("p0Teller"),0,1,10,cityMap.map.get("Bank").get(0),people.get(0));
		
		
		PersonAgent p3 = new PersonAgent("p3",cityMap);
		p3.myJob = new Job(new BankTellerRole("p3Teller"),0,0,1,cityMap.map.get("Bank").get(0),p3);
		p3.startThread();
		people.add(p3);
		
		PersonAgent p4 = new PersonAgent("p4",cityMap);
		p4.activeRole = (Role) ((MarketMapLoc) cityMap.map.get("Market").get(0)).market.host;
		((MarketHostRole) p4.activeRole).setPerson(p4);
		p4.activeRole.setName("p4MarketHost");
		p4.startThread();
		people.add(p4);
		
		PersonAgent p5 = new PersonAgent("p5",cityMap);
		p5.activeRole = new MarketEmployeeRole(p5);
		((MarketHostRole) p4.activeRole).addEmployee(((MarketEmployeeRole)p5.activeRole));
		p5.activeRole.setName("p5MarketEmployee");
		p5.startThread();
		people.add(p5);
		
		PersonAgent p6 = new PersonAgent("p6",cityMap);
		MarketCashierRole r = new MarketCashierRole(p6);
		((MarketMapLoc) cityMap.map.get("Market").get(0)).market.cashier = r;
		p6.activeRole = (Role) r;
		p6.activeRole.setName("p6MarketCashier");
		p6.startThread();
		people.add(p6);
		
		
		
		
		
		/*testPerson p0 = new testPerson("p0",cityMap);
		p0.startThread();
		
		p0.msgGoToMarket();*/
		
		
		//======TRANSPORTATION======
		
		//==Buses==
		
	}
		

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		updateTime();
		
	}
	
	
	public void updateTime(){
		System.out.println("CITY: Updating time");
		currentTime = (currentTime+1)%100;
		for(PersonAgent p:people){
			p.setTime(currentTime);
			p.msgStateChanged();
		}
		
		
	}
	
	
	
	
	public static void main(String [] args){
		
		CityObject city = new CityObject();
		
		return;
	}
	
	
	
}
