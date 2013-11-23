package city;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.Timer;

import person.PersonAgent;
import role.Role;
import market.Market;
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
	
	int currentTime;
	static int TIMER_DELAY = 10000;
	
	
	public List<PersonAgent> people = new ArrayList<PersonAgent>();
	public final int NUM_PEOPLE = 1;
	
	
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
		/*PersonAgent p = new PersonAgent("John",cityMap);
		
		BankCustomerRole c0 = new BankCustomerRole("c0",p);
		c0.stateChanged();
		c0.setBank(((BankMapLoc) cityMap.map.get("Bank").get(0)).bank);
		c0.startThread();
		c0.msgYouAreAtBank(((BankMapLoc) cityMap.map.get("Bank").get(0)).bank);
		
		PersonAgent p2 = new PersonAgent("S",cityMap);
		BankTellerRole t = new BankTellerRole("fuckHead");
		t.setPerson(p2);
		t.setBank(((BankMapLoc) cityMap.map.get("Bank").get(0)).bank);
		t.startThread();*/
		
		PersonAgent p3 = new PersonAgent("p3",cityMap);
		p3.myJob = new Job(new BankTellerRole("p3Teller"),0,0,100,cityMap.map.get("Bank").get(0),p3);
		p3.startThread();
		
		
		
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
		/*System.out.println("CITY: Updating time");
		currentTime = (currentTime++)%100;
		for(PersonAgent p:people){
			p.setTime(currentTime);
		}*/
		
	}
	
	
	
	
	public static void main(String [] args){
		
		CityObject city = new CityObject();
		
		return;
	}
	
	
	
}
