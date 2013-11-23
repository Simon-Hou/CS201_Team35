package cityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CityRestaurant extends CityComponent {

	private int buildingSize = 50;
	public CityRestaurant(int x, int y) {
		super(x, y, Color.red, "Restaurant 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
	}
	
	public CityRestaurant(int x, int y, String ID) {
		super(x, y, Color.red, ID);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
	}

	public void updatePosition() {
		
	}

	
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, buildingSize, buildingSize);
		g.fill3DRect(x, y, buildingSize, buildingSize, true);
	}

	
//	public boolean contains(int x, int y) {
//		if (x >= this.x && x <= this.x+20)
//			if (y >= this.y && y <= this.y+20)
//				return true;
//		return false;
//	}

}
