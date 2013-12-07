package cityGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import restaurant.restaurantSimon.RestaurantSimon;


public class CityRestaurantSimon extends CityRestaurant {

	public CityRestaurantSimonCard animationPanel;
	
	public CityRestaurantSimon(){
		super(0,0);
	}
	public CityRestaurantSimon(int x, int y) {
		super(x, y);
	}
	public CityRestaurantSimon(int x, int y, String ID){
		super(x, y, ID);
		
		type="Restaurant";
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		
	}

	@Override
	public void initializeRestaurant() {
		restaurant = new RestaurantSimon(this);
		
	}

	@Override
	public void createAnimationPanel(SimCityGui city) {
		animationPanel=new CityRestaurantSimonCard(city);
		
	}
public void updatePosition() {
		
	}

}
