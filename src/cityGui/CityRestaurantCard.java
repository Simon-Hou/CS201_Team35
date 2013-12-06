package cityGui;

import java.util.ArrayList;
import java.util.List;

//import restaurant.restaurantLinda.gui.CookGui;


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

}
