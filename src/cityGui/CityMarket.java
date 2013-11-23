package CityGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Not to be confused with CitiBank
 */
public class CityMarket extends CityComponent {


	public CityMarket(int x, int y) {
		super(x, y, Color.blue, "Bank 1");
		rectangle = new Rectangle(x, y, 60, 60);
	}

	public CityMarket(int x, int y, String I) {
		super(x, y, Color.blue, I);
		rectangle = new Rectangle(x, y, 60, 60);
	}

	public void updatePosition() {

	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, 80, 80);
	}
}
