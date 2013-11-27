package house;

import javax.swing.JPanel;

import util.Loc;
import cityGui.CityHouse;
import cityGui.CityHouseCard;
import house.gui.InhabitantGui;
import interfaces.Person;

public class House {
	
	
	CityHouseCard animation;
	public CityHouse houseGui;
	
	LivingUnit room;
	public Loc address;
	public House(){
		room=new LivingUnit();
		
	}
	
	
	public House(Loc loc){
		this.address = loc;
	}
	
	
	public void setAnimationPanel(CityHouseCard chc){
		animation=chc;
		animation.addGui(room.inhabitant.gui);
		
	}
	
	public void personIn(Person p){
		room.inhabitant.self=p;
	}
	
	
	
	

}
