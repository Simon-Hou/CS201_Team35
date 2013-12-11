package restaurant.restaurantParker.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import restaurant.restaurantParker.RestaurantParker;
import cityGui.BuildingControlPanel;
import cityGui.CityRestaurantParkerCard;

public class RestaurantParkerControlPanel extends BuildingControlPanel{
	final private int PANELX = 180;
	final private int PANELY = 500;
	
	
public RestaurantParkerControlPanel(CityRestaurantParkerCard anim, RestaurantParker r){
	Dimension dim = new Dimension(PANELX, PANELY); //x value can't be over 180
	setMaximumSize(dim);
	setMinimumSize(dim);
	setPreferredSize(dim);
	setBackground(Color.BLUE);
}
public RestaurantParkerControlPanel(){
		
		Dimension dim = new Dimension(PANELX, PANELY); //x value can't be over 180
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		//setBackground(Color.BLACK);
	}

public RestaurantParkerControlPanel(int doot){
	Dimension dim = new Dimension(PANELX, PANELY); //x value can't be over 180
	setMaximumSize(dim);
	setMinimumSize(dim);
	setPreferredSize(dim);
	setBackground(Color.BLUE);
}

}
