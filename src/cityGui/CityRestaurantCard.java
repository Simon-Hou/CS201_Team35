package cityGui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;


import restaurant.restaurantLinda.gui.CookGui;
import public_Gui.Gui;


public class CityRestaurantCard extends CityCard{

	public List<Gui> guis = new ArrayList<Gui>();
	
	public CityRestaurantCard(SimCityGui city) {
		super(city);
	}

    public void addGui(Gui gui) {
    	//System.out.println("Adding cook gui");
        guis.add(gui);
    }
    
	
	public void removeGui(Gui gui) {
		guis.remove(gui);		
	}
	
	//Override this if you don't use this super gui list
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("repaint being called!");
		//moveComponents();
		
		if(!guis.isEmpty()){
			for(Gui gui : guis) {
    			if (gui.isPresent()) {
    				gui.updatePosition();//the number here doesn't actually matter.
    			}
    		}
		}
		
		repaint();
	}

}
