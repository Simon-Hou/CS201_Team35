package city;
import java.util.*;

public class CityObject {

	
	
	//THIS WILL BE THE MAIN CITY OBJECT WHICH WILL HOLD THE CITY 
	//AND EVERYTHING IN IT.
	
	//IT WILL ALSO CONNECT AGENTS/ROLES/BUILDINGS VIA THE 
	//APPROPRIATE POINTER-SETTER METHODS
	
	
	//========================A City Will Need:====================================
	
	//======MAP======
	
	Map<String,ArrayList<Place>> cityMap = new HashMap<String,ArrayList<Place>>();
	
	public final int numBanks = 1;
	public final int numMarkets = 1;
	public final int numRestaurants = 1;
	
	class Place{
		int address;
	}
	
	class BankMapLoc extends Place{
		Bank bank;
	}
	
	class MarketMapLoc extends Place{
		
	}
	
	class RestaurantMapLoc extends Place{
		
	}
	
	public CityObject(){
		
		cityMap.put("Bank", new ArrayList<Place>());
		cityMap.put("Market", new ArrayList<Place>());
		cityMap.put("Restaurant", new ArrayList<Place>());
		
		
		//CREATE THE OBJECTS, ADD THEM TO THE MAP
		
		
		//======BUILDINGS======
		
		//==Banks==
		
		for(int i = 0;i<numBanks;++i){
			Bank b = new Bank();
			BankMapLoc bMap = new BankMapLoc(b);
			cityMap.get("Bank").add(bMap);
		}
		
		
		//==Restaurants==
		
		//==Markets==
		
		//==Houses==
		
		
		//======PEOPLE======
		
		//==People
			//+ Will need to be instantiated with different jobs/housing situations
			//+ Instantiate with map - they'll have almost all the pointers they'll need
		
		
		
		//======TRANSPORTATION======
		
		//==Buses==
		
	}
		
	
	public static void main(String [] args){
		
		return;
	}
}
