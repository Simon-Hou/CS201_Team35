package city;
import java.util.*;

import market.Market;
import market.MarketHostRole;
import bank.BankTellerRole;
import testAgents.testPerson;
import util.Bank;
import util.BankMapLoc;
import util.CityMap;
import util.MarketMapLoc;
import util.Place;

public class CityObject {

	
	
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
	
	
	/*class BankMapLoc extends Place{
		public BankMapLoc(Bank bank){
			this.bank = bank;
		}
		Bank bank;
	}
	
	class MarketMapLoc extends Place{
		
	}
	
	class RestaurantMapLoc extends Place{
		
	}*/
	
	public CityObject(){
		
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
		
		MarketHostRole h0 = new MarketHostRole();
		((MarketMapLoc) cityMap.map.get("Market").get(0)).market.host = h0;
		h0.startThread();
		
		
		
		
		testPerson p0 = new testPerson("p0",cityMap);
		p0.startThread();
		
		p0.msgGoToMarket();
		
		
		//======TRANSPORTATION======
		
		//==Buses==
		
	}
		
	
	public static void main(String [] args){
		
		CityObject city = new CityObject();
		
		return;
	}
}
