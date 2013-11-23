package CityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Not to be confused with CitiBank
 */
public class CityBank extends CityComponent {

	private int buildingSize = 50;
	public CityBank(int x, int y) {
		super(x, y, Color.green, "Bank 1");
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
	}

	public CityBank(int x, int y, String I) {
		super(x, y, Color.green, I);
		rectangle = new Rectangle(x, y, buildingSize, buildingSize);
	}

	public void updatePosition() {

	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, buildingSize, buildingSize);
	}
}
