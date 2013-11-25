package house;

import util.Loc;
import cityGui.CityHouse;
import interfaces.Person;

public class House {
	
	public House(){
		
	}
	
	public House(Loc loc){
		this.address = loc;
	}
	
	public CityHouse houseGui;
	LivingUnit room;
	public Loc address;

}
