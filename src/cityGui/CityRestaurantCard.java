package cityGui;

import java.util.ArrayList;
import java.util.List;

import restaurant.restaurantLinda.gui.CookGui;
import restaurant.restaurantLinda.gui.Gui;

public class CityRestaurantCard extends CityCard{

	protected List<Gui> guis = new ArrayList<Gui>();
	
	public CityRestaurantCard(SimCityGui city) {
		super(city);
	}

    public void addGui(Gui gui) {
        guis.add(gui);
    }
    
	
	public void removeGui(Gui gui) {
		guis.remove(g);		
	}

}
