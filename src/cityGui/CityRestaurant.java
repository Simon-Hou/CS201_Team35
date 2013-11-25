package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import util.BankMapLoc;
import util.Loc;
import util.RestaurantMapLoc;

public class CityRestaurant extends CityComponent implements ImageObserver {
	//public Restaurant restaurant;
	private int buildingSize = 35;
	public CityRestaurant(int x, int y) {
		super(x, y, Color.red, "Restaurant 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		//System.out.println("First");
	}
	
	public CityRestaurant(int x, int y, String ID) {
		super(x, y, Color.red, ID);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
		//System.out.println("Second");

	}

	public void updatePosition() {
		
	}
	
	@Override
	public JPanel addAgentObjectToMap(){
//		RestaurantMapLoc rMap = new RestaurantMapLoc(restaurant);
//		rMap.loc = new Loc(x,y);
//		int tempX = rMap.loc.x;
//		int tempY = rMap.loc.y;
//		System.out.println("Old Building X Value: " + rMap.loc.x);
//		System.out.println("Old Building Y Value: " + rMap.loc.y);
//		rMap.loc.x = sidewalkX(tempX,tempY);
//		rMap.loc.y = sidewalkY(tempX,tempY);
//		System.out.println("New Building X Value: " + rMap.loc.x);
//		System.out.println("New Building Y Value: " + rMap.loc.y);
//		this.cityObject.cityMap.map.get("Bank").add(rMap);
		return null;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, buildingSize, buildingSize);
		g.fill3DRect(x, y, buildingSize, buildingSize, true);
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

	
//	public boolean contains(int x, int y) {
//		if (x >= this.x && x <= this.x+20)
//			if (y >= this.y && y <= this.y+20)
//				return true;
//		return false;
//	}

}
